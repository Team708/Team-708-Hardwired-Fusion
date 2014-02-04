package org.team708.frc2014;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    /*
    * Motor/PWM Ports:
    */
    
    // Drivetrain
    public static final int leftMotor1 = 1;    //normal drive
    public static final int leftMotor2 = 2;    //swag drive enabled
    public static final int rightMotor1 = 3;   //normal drive
    public static final int rightMotor2 = 4;   //swag drive enabled
    
    
    // Catapult
    public static final int catapultMotor1 = 5;
    public static final int catapultMotor2 = 6;
    
    /*
     * Digital Inputs
     */
    
    // Drivetrain
    public static final int leftEncoderA = 1;
    public static final int leftEncoderB = 2;
    
    public static final int rightEncoderA = 3;
    public static final int rightEncoderB = 4;
    
    // Catapult
    public static final int catapultEncoderA = 6;
    public static final int catapultEncoderB = 7;
    public static final int catapultLowerSwitch= 8;
    public static final int catapultUpperSwitch = 9; 
    
    /*
     * Analog
     */
    
    //Catapult
    public static final int catapultPotentiometer = 1;
    
    /*
     * Joystick Ports:
    */
    public static final int driverGamepad = 1;
    public static final int operatorGamepad = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int rangefinderPort = 1;
    // public static final int rangefinderModule = 1;
}
