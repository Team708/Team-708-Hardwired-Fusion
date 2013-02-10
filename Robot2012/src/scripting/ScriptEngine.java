/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scripting;

import com.sun.squawk.io.BufferedReader;
import com.sun.squawk.microedition.io.FileConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import javax.microedition.io.Connector;

/**
 * -Pattern Info
 * ScriptEngine is the core class of the scripting system. In the command
 * design pattern, it plays the role of both the Invoker and the Client, while Command
 * and inner classes of Commands play the role of the concrete commands. Robot part objects,
 * either from the FIRST api or created by us, are used as the Receivers.
 * 
 * - Usage
 * An instance of ScriptEngine will be created in the robot's main class and a beginning
 * script can be supplied using the constructor. In autonomousPeriodic() and teleopPeriodic(),
 * ScriptEngine.run() should be called once. This will cause the ScriptEngine to parse
 * lines from the current script if necessary and create and command objects from them. These objects
 * will then be added to vectors for execution through Script objects' execute methods.
 * Execute returns a boolean value specifying whether execution has completed, and commands
 * are automatically removed from the vectors when they are finished. The ScriptEngine
 * also provides functionality for several special behaviors, namely Wait, Include, Store, and Repeat (moved to Script class).
 * Additionally, scripts can be scheduled for execution manually, which is useful for running
 * button routines during teleop.
 * 
 * - To Do (by importance)
 *	- Review, Testing, and Debugging - as of right now, the code has never been run in a real robot. The best option for
 *	    this is probably to create a new project for the robot testing board, and then write code to use
 *	    the new scripting system around that.
 *	- Must be incorporated into the actual robot project, which will require extensive rewriting and testing.
 *	    Most likely, the ramp riot will use old code from last year (if it ain't broke don't fix it).
 *	- (implemented 9/25) ScriptEngine lacks a system for keeping track of the possible file names for scripts. This will be necessary
 *	    when incrementing the autonomous mode from the driverStation. One flexible approach to this would be
 *	    for the ScriptEngine to read a list of filenames from another file on startup and store it as an array.
 *	    A "refresh" method could then be implemented to reread from this file if it is modified (a new autonomous
 *	    mode is added).
 * 
 * - Possible problems
 *	- When the ScriptEngine processes and Include command, it leaves the current file open (to preserve the parser's
 *	    line location in the file) and moves to the Included file. This may or may not cause problems during testing.
 *	    If it does, then the parser's location will have to be kept track of manually.
 *	- The recursion used when the parseScript() method hits an error is supposed to get the next line
 *	of the script for parsing. In reality, this approach might be a bit sticky. - removed!!!
 *	- Where wpi timers are used, a their get() values are divided by 1,000,000 because the java docs say
 *	that the times are expressed in microseconds. Must be verified. Verified: timers really read in seconds
 *	
 * @author Connor Willison
 */
public class ScriptEngine {
    
    private Vector autoModeNames;			//to hold the possible autonomous mode names (read from a file)
    private int currentAutoMode = 0;		//holds the position of the current auto mode name in the above vector
    private Vector executingScripts;		//scripts that are executing
    private CommandFactory commands;
    /*
     * These states must be toggled MANUALLY, they can not be triggered directly from within scripts.
     */
    public static final byte READY = 0;
    public static final byte WAITING = 1;
    private byte state;

    //filename constants
    public static final String startupDirectory = "file:////ni-rt/startup/";
    public static final String scriptDirectory = startupDirectory + "scripts/";
    private static final String autoModeNameFile = scriptDirectory + "ModeNames.txt";

    public ScriptEngine(CommandFactory commands) {
        this.commands = commands;
        
	//instantiate
	autoModeNames = new Vector(10);
	executingScripts = new Vector(10);
	
	//read possible autonomous mode names from file
	readNamesFile();
	
	//begin executing scripts
	state = READY;
    }
    
    /**
     * This method adds the specified script to the list
     * of scripts that will be parsed the next time that run()
     * is called.
     * @param scriptName 
     */
    public void scheduleScript(String scriptName) {
	//ignore new schedules if script engine isn't ready
	if(state == READY){
	    executingScripts.addElement(new Script(scriptName));
	}
    }

    /**
     * Returns a list of all the names of the scripts
     * that are currently executing.
     * @return 
     */
    public String[] getScriptNames() {
	String[] names = new String[executingScripts.size()];
	for (int i = 0; i < executingScripts.size(); i++) {
	    names[i] = ((Script) executingScripts.elementAt(i)).getName();
	}
	return names;
    }

    public int getAutonomousMode(){
	return currentAutoMode;
    }
    
    public String getAutonomousModeName(){
	return (String)autoModeNames.elementAt(currentAutoMode);
    }
    
    /**
     * Schedules the script corresponding to the current autonomous mode,
     * based on autonomous mode names read from the auto mode names file.
     */
    public void initAutonomous(){
	scheduleScript((String)autoModeNames.elementAt(currentAutoMode));
    }
    
    /**
     * This method should be called periodically. It parses lines from executing
     * scripts as necessary and runs their commands.
     */
    public void run() {
	
	//stop waiting if all scripts have finished executing
	if(state == WAITING){
	    if(executingScripts.isEmpty()){
		state = READY;
	    }
	}
	
	Script s;
	
	//evaluate, parse, and execute scripts
	for(int i = 0; i<executingScripts.size(); i++){
	    s = (Script)executingScripts.elementAt(i);
	    
	    //start of script state evaluation - these script states require ScriptEngine actions
	    if(s.getState() == Script.DONE){
                //execute waiting commands
                if(s.hasCommands()){
                    s.executeCommands();
		//prepare for repeat if need be
                }else if(s.needsToRepeat()){
		    s.prepareToRepeat();
		}else{
		    //if script was included from another script, restart the original script
		    if(s.getParent() != null){
                        System.out.println(s.getParent().getName() + " restarting...");
                        
			//restart the parent script, which included this one
			s.getParent().notifyIncludedScriptCompleted();
		    }
                    
		    //remove finished script
		    executingScripts.removeElementAt(i);
		}
	    }else if(s.getState() == Script.INCLUDING_SCRIPT){
                //tell this script to pause itself while child script finishes
		s.notifyIncludedScriptStarted();
                
                Script includedScript = new Script(s.getIncludedScript());
                
                //set the script's parent
                includedScript.setParent(s);
                
		//schedule the included script - must bypass scheduleScript() b/c Engine state may be WAITING.
		executingScripts.addElement(includedScript);
	    }else if(s.getState() == Script.ON_HOLD){
		//execute any running commands - but do not parse
                if(s.hasCommands()) s.executeCommands();
	    }else{
		//other script states - READY,WAITING_TIME,WAITING_COMMANDS - require no ScriptEngine action
		//use command factory object to create command objects
                s.parseCommands(commands);
                
                //execute already scheduled commands regardless of engine state
		s.executeCommands();
	    }	
	}

    }

    /**
     * Stops parsing the current script during calls to run() and
     * immediately stops any executing commands and scripts. Also deletes any
     * scripts that have been scheduled for parsing with the Include
     * command. This is useful when entering teleop from autonomous, as
     * it would be quite stressful if autonomous code started running
     * while the driver is in control.
     */
    public void abort() {
	executingScripts.removeAllElements();
        System.out.println("Scripts aborted...");
    }
    
    public boolean hasScripts(){
        return !executingScripts.isEmpty();
    }
    
    /**
     * Tells the ScriptEngine to stop
     * accepting new scripts until all scripts have
     * stopped executing.
     */
    public void setWaiting(){
	state = WAITING;
    }
    
    /**
     * Sets the ScriptEngine to its default state (not waiting).
     */
    public void setReady(){
	state = READY;
    }
    
    /**
     * Gets the Script Engine's state as defined
     * in the ScriptEngine class.
     * @return 
     */
    public byte getState(){
	return state;
    }

    /**
     * This method reads the possible script names from an external file.
     * That approach is necessary because the API lacks a method of getting
     * all of the filenames in a directory. This method may be called multiple
     * times to refresh the list of scripts.
     */
    public final void readNamesFile() {
	try {
            
            autoModeNames.removeAllElements();

	    //open file
	    FileConnection fc = (FileConnection) Connector.open(autoModeNameFile, Connector.READ);
	    BufferedReader fileInput = new BufferedReader(
		    new InputStreamReader(
		    fc.openInputStream()));

	    String name;

	    //parse all script names
	    while (true) {
		name = fileInput.readLine();

		//exit if no more lines
		if (name == null) {
		    break;
		} else {
		    autoModeNames.addElement(name);
		}
	    }

	} catch (IOException e) {
	    System.out.println("Error occured while reading autonomous names file - " + autoModeNameFile);
            e.printStackTrace();
	}

	//reset auto mode
	currentAutoMode = 0;
	
	//error condition
	if (autoModeNames.isEmpty()) {
	    System.out.println("Warning: autonomous mode names could not be read from file - " + autoModeNameFile);
	} else {
	    System.out.println("Auto Mode names read from file - " + autoModeNameFile);
	}
    }

    /**
     * Changes the autonomous mode to the next one in the list.
     */
    public void nextAutoMode() {
	currentAutoMode++;
	if (currentAutoMode > autoModeNames.size() - 1) {
	    currentAutoMode = 0;
	}
    }

    /**
     * Changes the current autonomous mode to the previous one in the list.
     */
    public void previousAutoMode() {
	currentAutoMode--;
	if (currentAutoMode < 0) {
	    currentAutoMode = autoModeNames.size() - 1;
	}
    }
}
