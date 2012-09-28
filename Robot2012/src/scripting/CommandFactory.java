/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scripting;

import bridge.Arm;
import com.sun.squawk.util.StringTokenizer;
import edu.wpi.first.wpilibj.templates.Drivetrain;
import java.util.Hashtable;
import shooter.Shooter;

/**
 * The CommandFactory class is a wrapper for classes defining script command
 * objects, all implementing the Command interface. A instance of type CommandFactory
 * is kept in the ScriptEngine object. The CommandFactory class should have static instances
 * of all robot parts available to Command objects that require them during
 * execution.
 * @author Connor Willison
 */
public class CommandFactory {

    private static Drivetrain robot;
    private static Shooter shooter;
    private static Arm arm;
    private static Hashtable scriptVariables; //binds script variable strings to their values
    private Hashtable commandNames; //binds command strings to the correct Command class
    private static final String variablePrefix = "&";

    //accepts instance of RobotParts (from main class)
    public CommandFactory(Drivetrain robot,Shooter shooter,Arm arm) {
        this.robot = robot;
        this.shooter = shooter;
        this.arm = arm;
        commandNames = new Hashtable();

        //associate command classes with strings - ALL LOWERCASE!
        commandNames.put("drive", Drive.class);
        commandNames.put("rotate",Rotate.class);
        commandNames.put("shoot",Shoot.class);
        commandNames.put("armdown",ArmDown.class);
        commandNames.put("armup",ArmUp.class);
    }

    /**
     * Drive command: used to tell the robot to drive a certain distance
     * (forward or backward) according to these parameters:
     *
     * - speed (-1.0 - 1.0)
     * - distance (in)
     *
     */
    public static class Drive extends Command {

        private static final double rotationSpeed = .3;
        private double setSpeed = 0.0,goalDistance = 0.0;
        private double actualSpeed = 0.0,actualRotationSpeed = 0.0;

        private boolean doneDriving = false;

        //tolerate rotational drift
        private double tolerance = 2;

        /*
         * 
         */
        public void passParameters(StringTokenizer params) {
            String s;

            while (params.hasMoreTokens()) {
                s = params.nextToken();

                if (s.equalsIgnoreCase("speed=")) {
                    setSpeed = getValue(params.nextToken());
                } else if (s.equalsIgnoreCase("distance=")) {
                    goalDistance = getValue(params.nextToken());
                } else {
                    //ignore
                }
            }

            //start counting distance on encoders
            robot.startEncoders();
            robot.resetEncoders();
            
            //reset rotation sensor
            robot.getRotation().reset();
        }

        public boolean execute(){
            //stop execution if there isn't a drivetrain object yet
            if (robot == null) {
                System.out.println("Drive command called before drivetrain instantiated.");
                return true;
            }

            //stop/slow down if at distance goal
            checkDistance();

            //compensate for drivetrain bias
            actualRotationSpeed = getRotationSpeed(0,robot.getRotation().getAngle(),rotationSpeed,tolerance);

            //stop executing command if reached distance and rotation goals
            if (doneDriving) {
                //make sure robot is stopped
                robot.drive(0.0,0.0);

                return true;
            } else {
                //power motors
                robot.drive(actualSpeed,actualRotationSpeed);
                return false;
            }
        }

        private void checkDistance() {
            //check maximum of left and right sides
            if (Math.abs(Math.max(robot.getLeftDistance(), robot.getRightDistance())) >= goalDistance) {
                actualSpeed = 0.0;    //stop moving, may need to reverse pulse the motors
                doneDriving = true;
            } else {
                actualSpeed = setSpeed; //may want to slow down as target approaches (proportionally)
            }
        }

        public String toString() {
            return "Command: Drive speed: "
                    + actualSpeed + " distance: " +
                    goalDistance;
        }
    }

    /**
     * Rotate command - used to rotate the robot by an angle in degrees.
     *
     * Parameters:
     * - heading (-180 - 180) could be higher or lower, 0 is straight
     * - speed
     */
    public static class Rotate extends Command{

        private int prevDriveMode;
        private double goalHeading = 0.0, setSpeed = 0.3;
        private double actualSpeed = 0.0;
        private double tolerance = 2;

        public void passParameters(StringTokenizer params){
            String s;

            while (params.hasMoreTokens()) {
                s = params.nextToken();

                if (s.equalsIgnoreCase("heading=")) {
                    goalHeading = getValue(params.nextToken());
                } else if (s.equalsIgnoreCase("speed=")) {
                    setSpeed = getValue(params.nextToken());
                } else {
                    //ignore
                }
            }

            //start encoders (for rotation)
            robot.startEncoders();
            robot.resetEncoders();

            //reset rotation sensor
            robot.getRotation().reset();
        }

        public boolean execute(){
            //find speed necessary to move towards goal angle
            actualSpeed = getRotationSpeed(goalHeading,robot.getRotation().getAngle(),setSpeed,tolerance);

            if(actualSpeed == 0.0){
                //stop the robot
                robot.drive(0.0,0.0);

                return true;
            }else{
                robot.drive(0.0,actualSpeed);
                return false;
            }
        }

        public String toString(){
            return "Commmand: Rotate heading: " + goalHeading +
                    " speed: " + actualSpeed;
        }
    }

    /**
     * Returns the rotation speed necessary to attain the goal heading
     * within tolerance.
     * @param goalHeading
     * @param currentHeading
     * @param tolerance
     * @return
     */
    private static double getRotationSpeed(double goalHeading,double currentHeading,double speed,double tolerance){
            double error =  goalHeading - currentHeading;

            //positive heading
            if (error > tolerance) {
                return speed;    //rotate positively
            } else if (error < -tolerance) {
                return -speed;   //rotate negatively
            } else {
                return 0.0; //stop rotating
            }
    }

    public static class Shoot extends Command{

        public boolean execute() {

            if(!shooter.isDoneShooting()){
                //params (manual mode: false, auto mode: true, left: false, right: false, shoot: true)
               shooter.setFlags(false,true,false,false,true);
               return false;
            }else{
                //pass control back to harvester
                shooter.setFlags(false,false,false,false,false);
                return true;
            }
        }

        /**
         * Parameters:
         * -
         * @param parameters
         */
        public void passParameters(StringTokenizer parameters) {

        }

        public String toString() {
            return "Shoot Command";
        }

    }

    public static class ArmDown extends Command{

        public boolean execute() {
            arm.lower();
            return true;
        }

        public void passParameters(StringTokenizer parameters) {
        }

        public String toString() {
            return "Command: ArmDown";
        }


    }

    public static class ArmUp extends Command{

        public boolean execute() {
            arm.raise();
            return true;
        }

        public void passParameters(StringTokenizer parameters) {
        }

        public String toString() {
            return "Command: ArmUp";
        }

    }

//    /*
//     * Motor command: will set a specified motor to
//     * a specified speed.
//     *
//     * Syntax:
//     *
//     * Motor id= <motor id # (see array)> speed= <0.0 - 1.0>
//     */
//    public static class Motor extends Command{
//	private int index = 0;
//	private double speed = 0;
//
//	public void passParameters(StringTokenizer parameters){
//	    String paramName,paramValue;
//	    while(parameters.hasMoreTokens()){
//		paramName = parameters.nextToken();
//
//		if(paramName.equalsIgnoreCase("id=")){
//		    paramValue = parameters.nextToken();
//
//		    if(isVariable(paramValue)){
//			index = (int)getVariableValue(paramValue);
//		    }else{
//			index = (int)Double.parseDouble(paramValue);
//		    }
//		}else if(paramName.equalsIgnoreCase("speed=")){
//		    paramValue = parameters.nextToken();
//
//		    if(isVariable(paramValue)){
//			speed = getVariableValue(paramValue);
//		    }else{
//			speed = Double.parseDouble(paramValue);
//		    }
//		}
//
//	    }
//	}
//
//	public boolean execute(){
//	    robot.setMotor(this.index,this.speed);
//	    return true;
//	}
//	
//	public String toString(){
//	    return "Command: Motor id: " + index + " speed: " + speed;
//	}
//    }
    /**
     * Returns whether or not the specified command
     * has been defined.
     * @param commandName
     * @return
     */
    public boolean isDefined(String commandName) {
        return commandNames.containsKey(commandName);


    }

    public Command getCommand(String commandName) throws Exception {
        Class c = (Class) commandNames.get(commandName);


        return (Command) c.newInstance();


    }

    private static double getValue(String param) {
        if (isVariable(param)) {
            return getVariableValue(param);


        } else {
            return Double.parseDouble(param);


        }
    }

    /**
     * This method translates a defined variable in a script into a
     * double value. An IllegalArgumentException is thrown if an
     * undefined variable name is passed to it. This allows command
     * classes to implement error recovery code.
     * @param variable
     * @return
     * @throws IllegalArgumentException 
     */
    public static double getVariableValue(String variable) throws IllegalArgumentException {

        //parse off the prefix if it is there
        if (variable.startsWith(variablePrefix)) {
            variable = variable.substring(1, variable.length());


        } //look up the varible in the hashtable
        if (scriptVariables.containsKey(variable)) {
            Double d = (Double) scriptVariables.get(variable);


            return d.doubleValue();


        } else {
            throw new IllegalArgumentException("Undefined script variable found: " + "\"" + variable + "\"");


        }
    }

    /**
     * This method can be called to determine whether or not a given parameter
     * is a script variable. Note: This does not return whether or not the variable has
     * been defined. It only determines if the string has syntax indicative of
     * a variable, i.e. &foo
     * @param parameter
     * @return 
     */
    public static boolean isVariable(String parameter) {
        return parameter.startsWith(variablePrefix);


    }

    public static void defineVariable(String name, String val) {
        if (scriptVariables == null) {
            scriptVariables = new Hashtable();


        }

        scriptVariables.put(name, Double.valueOf(val));

    }
}
