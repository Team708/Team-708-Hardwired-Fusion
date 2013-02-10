package scripting;

import com.sun.squawk.io.BufferedReader;
import com.sun.squawk.microedition.io.FileConnection;
import com.sun.squawk.util.StringTokenizer;
import edu.wpi.first.wpilibj.Timer;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import javax.microedition.io.Connector;

/**
 * The script class is used internally by the ScriptEngine to
 * encapsulate the process of parsing/access a script file.
 */
public class Script {

    //parsing vars
    private String name;		    //the name (filename) of this script.
    private String includedScript;	    //holds the name of a script that this script may include.
    private Vector lines;                   //holds the lines of the script that have been buffered in memory.
    private int currentLine = 0;
    private static final String commentChar = "#";     //character in a script that indicates a comment line
    //wait vars
    private double waitTime = 0;
    private Timer waitTimer = new Timer();
    //repeat vars
    private int timesToRepeat = 1;                  //used to keep track of script looping.
    private static final int INFINITY = -1;         //use as timesToRepeat value for inf. loop
    
    private boolean hasFinishedOnce = false;
    //include vars
    private Script parent;			    //the script object that included this script
    //command vars
    private Vector executingCommands;		    //pool of currently executing commands
    //state vars
    public static final byte READY = 0;		    //script is ready to parse command objects from the file. May also have commands ready to execute.
    public static final byte WAITING_COMMANDS = 1;  //script is waiting for its executing commands to execute before parsing. 
    public static final byte WAITING_TIME = 2;	    //script is waiting for a certain period of time before parsing.
    public static final byte DONE = 3;		    //script is complete, but may have commands that need to finish executing.
    public static final byte INCLUDING_SCRIPT = 4;  //script has included another script and needs to be put on hold.
    public static final byte ON_HOLD = 5;           //script is not parsing, but may still have commands that need to execute.
    private byte state;

    public Script(String scriptName) {
	name = scriptName;
        lines = new Vector(25);
	executingCommands = new Vector(10);

	if (readLines()) {
	    state = READY;	//script opened successfully - begin processing
	} else {
	    state = DONE;	//script did not open - do not process
	}
    }
    
    public void setParent(Script script){
	parent = script;
    }
    
    public Script getParent(){
	return parent;
    }
    
    /**
     * Executes commands waiting in this script's command pool.
     * Eliminates any finished commands.
     */
    public void executeCommands(){
	Command c;
	for(int i = 0; i<executingCommands.size();i++){
	    c = (Command)executingCommands.elementAt(i);
	    
	    //remove if done
	    if(c.execute()){
		executingCommands.removeElementAt(i);
	    }
	}
    }
    
    /**
     * Returns whether or not this script is
     * currently executing commands.
     * @return 
     */
    public boolean hasCommands(){
	return !executingCommands.isEmpty();
    }

    /**
     * This method causes the script to parse command
     * objects from its file and schedule their execution.
     */
    public void parseCommands(CommandFactory commands) {

	//parse new commands only if script is ready to do so.
	if (state == READY) {
	    String line;

	    //keep parsing the file until a wait is reached.
	    while (state == READY) {
                //increment line
                currentLine++;
                
		//parse a command from the line
		if (currentLine <= lines.size()) {
                    //get line from buffer
                    line = (String)lines.elementAt(currentLine - 1);
                    
		    //parse the line's tokens
		    StringTokenizer tokens = new StringTokenizer(line);
		    String commandKey;

		    //parse command name
		    if (tokens.hasMoreTokens()) {
			commandKey = tokens.nextToken();
		    } else {
			//ignore whitespace - no more tokens
                        System.out.println("Skipping whitespace - line " + currentLine);
			continue;   //read next line
		    }

		    //detect special commands and pass the parameters
		    if(commandKey.startsWith(commentChar)){
			continue;   //skip comments
		    }else if (commandKey.equalsIgnoreCase("Include")) {
			includeCommand(tokens);
		    } else if (commandKey.equalsIgnoreCase("Wait")) {
			waitCommand(tokens);
		    } else if (commandKey.equalsIgnoreCase("Store")) {
			storeCommand(tokens);
                    }else if(commandKey.equalsIgnoreCase("End")){
                        endCommand(tokens);
		    } else if (commandKey.equalsIgnoreCase("Repeat")) {
			repeatCommand(tokens);
			//look up robot command in hashtable
		    } else if (commands.isDefined(commandKey.toLowerCase())) {          
                        try{
                            //create the object
                            Command command = commands.getCommand(commandKey.toLowerCase());
                            
                            //give it the read parameters
                            command.passParameters(tokens);
                            
                            //schedule for execution
                            executingCommands.addElement(command);
                        }catch(Exception e){
                            error("Command instantiation failed: " + commandKey);
                            e.printStackTrace();
                            continue; //parse next line
                        }
		    }else{
                        error("Unrecognized command: " + commandKey);
                        continue;
                    }
		}else {
			//reached EOF - no more lines
			state = DONE;	    //script is finished
                        if(timesToRepeat != INFINITY){
                            timesToRepeat--;    //register a finished run
                        }
                        hasFinishedOnce = true; //be sure to skip the repeat command next run - to avoid inf. loop
                        System.out.println("Script: " + name + " finished.\n" + timesToRepeat +
                                " runs to go.");
		}
	    }
	}else if(state == WAITING_TIME){
	    //check if done waiting
	    if((waitTimer.get() ) >= waitTime){
		//prepare script for parsing
		state = READY;
		
		//stop timer
		waitTimer.stop();
		waitTimer.reset();
	    }
	}else if(state == WAITING_COMMANDS){
	    //check if done waiting
	    if(executingCommands.isEmpty()){
		//prepare script for parsing
		state = READY;
	    }
	}
	//anything else is a finished script - ready to stop executing
    }
    
    /**
     * Inform the script to restart its execution,
     * as its included script has finished.
     */
    public void notifyIncludedScriptCompleted(){
	if(state == ON_HOLD){
	    //clear previous included script
	    includedScript = null;
	    
	    //start parsing again
	    state = READY;
	}else{
	    //sanity check
	    System.out.println("Script: " + name + " attempted to restart but did not go on hold.");
	}
    }
    
    public void notifyIncludedScriptStarted(){
	if(state == INCLUDING_SCRIPT){
            //pause the script while child begins
	    state = ON_HOLD;
	}else{
	    //sanity check
	    System.out.println("Script: " + name + " attempted to pause but did not include.");
    	} 
    }

    /**
     * The include command is a request to process a different script before
     * continuing this one. This method will toss the current script object
     * onto a stack to be processed later, and set the current script to the
     * specified included script.
     * @param tokens 
     */
    private void includeCommand(StringTokenizer tokens) {
	String param = "";
	if(tokens.hasMoreTokens()){
	    param = tokens.nextToken("\""); //parse script name delimited by double quotes
	    
	    includedScript = param;
	    state = INCLUDING_SCRIPT;
	}
    }

    /**
     * The stop commands stops all currently executing commands
     * from executing.
     * @param tokens
     */
    private void endCommand(StringTokenizer tokens){
        executingCommands.removeAllElements();
    }

    /**
     * The wait command is a request to delay parsing of any more script lines
     * either for a specified amount of time or until all commands are finished
     * executing. This method will set a flag to notify the program about this
     * status. Note: times are passed in as doubles, measured in seconds.
     * @param tokens 
     */
    private void waitCommand(StringTokenizer tokens) {
	if (tokens.hasMoreTokens()) {
	    //wait for amount of time
	    state = WAITING_TIME;
	    
	    //read in value as variable or hard-coded
	    String param = tokens.nextToken();
	    if(CommandFactory.isVariable(param)){
		waitTime = CommandFactory.getVariableValue(param);
	    }else{
		waitTime = Double.parseDouble(param);
	    }
	    
	    //set timer
	    waitTimer.reset();
	    waitTimer.start();
	
	} else {
	    //wait until commands are finished
	    state = WAITING_COMMANDS;
	}
    }

    /**
     * The store command declares a script variable, which then can be
     * substituted for its value in the script. This method uses a hash table to map
     * a variable string to its value. If a variable has already been declared,
     * a call to this method will over write the previous value. Note: all values 
     * are read in as doubles.
     * @param tokens
     */
    private void storeCommand(StringTokenizer tokens) {
	String variable,value;
	
	if(tokens.hasMoreTokens()){
	    variable = tokens.nextToken();
	    
	    if(tokens.hasMoreTokens()){
		value = tokens.nextToken();
		
		//map in hashtable
		CommandFactory.defineVariable(variable,value);
	    }
	}    
    }

    /**
     * The repeat command tells a script that
     * it must repeat itself x number of times.
     * @param tokens 
     */
    private void repeatCommand(StringTokenizer tokens) {
        if(!hasFinishedOnce){
            if(tokens.hasMoreTokens()){
                String param = tokens.nextToken();
                if(CommandFactory.isVariable(param)){
                    timesToRepeat = (int)CommandFactory.getVariableValue(param);
                }else{
                    timesToRepeat = (int)Double.parseDouble(param);
                }
            }else{
                //no parameters means inf. loop
                timesToRepeat = INFINITY;
            }
	}
    }

    /**
     * Returns whether or not this script needs to be repeated.
     * @return 
     */
    public boolean needsToRepeat() {
	return timesToRepeat > 0 || timesToRepeat == INFINITY;
    }
    
    /**
     * Resets the script for its next run through.
     * Call needsToRepeat() to determine if a call
     * to this method is necessary.
     */
    public void prepareToRepeat(){
        //reset line number
        currentLine = 0;
        
        //ready for parsing
        state = READY;
    }

    /**
     * Gets the state of the script in terms
     * of the Script class's state definitions.
     * @return 
     */
    public byte getState(){
	return state;
    }

    /**
     * Returns the current included script. Note:
     * the included script itself may include other scripts.
     * @return 
     */
    public String getIncludedScript() {
	return includedScript;
    }
    
    /**
     * This method reads each line of the file and stores
     * it in a Vector. This strategy exists to prevent multiple
     * FileConnections from being open at once, which causes strange
     * errors.
     * @return 
     */
    private boolean readLines(){
        try{
            //open file
            FileConnection fc = (FileConnection) Connector.open(getFilePath(name), Connector.READ);
	    BufferedReader fileInput = new BufferedReader(new InputStreamReader(fc.openInputStream()));
            String line = fileInput.readLine();
            
            //read each line
            while(line != null){
                lines.addElement(line);
                line = fileInput.readLine();
            }
            
            //close file
            fileInput.close();
            
            return true;
            
        }catch(IOException e){
            System.out.println("Script: " + name + " failed to open.");
            return false;
        }
    }

    /**
     * Returns the string name of the script.
     * @return 
     */
    public String getName() {
	return name;
    }

    /**
     * Creates the script file path.
     */
    public static String getFilePath(String name) {
	return ScriptEngine.scriptDirectory + name + ".txt";
    }

    /**
     * Gets the line of the script that the parser is currently on. This is reset
     * to 1 when the script is opened again for reading.
     */
    public int getLineNumber() {
	return currentLine;
    }

    private void error() {
	System.out.println("Script parsing error: " + name + " Line: " + currentLine);
    }
    
    private void error(String mess){
        error();
        System.out.println(mess);
    }
}
