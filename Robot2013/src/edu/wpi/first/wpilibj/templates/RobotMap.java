package edu.wpi.first.wpilibj.templates;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static final int leftMotor = 1;
    // public static final int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int rangefinderPort = 1;
    // public static final int rangefinderModule = 1;
    
    //pwm outputs
    public static final int leftJag = 1;
    public static final int rightJag = 2;
    
    //digital inputs
    public static final int leftEncoderA = 1;
    public static final int leftEncoderB = 2;
    
    public static final int rightEncoderA = 3;
    public static final int rightEncoderB = 4;
    
    //solenoids
    public static final int shiftingSolenoid = 1;
    
    //driverstation controls
    public static final int driverGamepad = 1;
           
}
