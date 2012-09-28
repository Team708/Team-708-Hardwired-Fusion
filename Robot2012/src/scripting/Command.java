/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scripting;

import com.sun.squawk.util.StringTokenizer;

/**
 * This interface defines the structure of command objects
 * for use on the robot. Classes implementing this interface should
 * be defined in Commands.java.
 * @author Connor Willison
 */
public abstract class Command {
    
    /**
     * The execute method of a command is called periodically when a
     * command is being run. Execute should return a boolean value
     * every cycle indicating whether or not the command has finished
     * executing.
     */
    public abstract boolean execute();
    
    /**
     * The passParameters method is called with a string list of the
     * command's parameters as specified in the script file, once when the
     * command is first executed. Command objects should use this method
     * to parse and store the parameter values in preparation for execution.
     * @param parameters 
     */
    public abstract void passParameters(StringTokenizer parameters);
    
    public abstract String toString();
    
}
