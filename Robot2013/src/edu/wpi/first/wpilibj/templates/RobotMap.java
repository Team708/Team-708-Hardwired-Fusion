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
    public static final int rightArmJag = 3;
    public static final int leftArmJag = 4;
    public static final int shooterVictor1 = 5;
    public static final int shooterVictor2 = 6;
    public static final int shooterVictor3 = 7;
    
    //digital inputs
    public static final int leftEncoderA = 1;
    public static final int leftEncoderB = 2;
    
    public static final int rightEncoderA = 3;
    public static final int rightEncoderB = 4;
    
    public static final int shooterEncoderA = 5;
    public static final int shooterEncoderB = 6;
    
    public static final int leftArmEncoderA = 7;
    public static final int leftArmEncoderB = 8;
    
    public static final int rightArmEncoderA = 9;
    public static final int rightArmEncoderB = 10;
    
    public static final int leftArmTopSwitch = 11;
    public static final int leftArmBottomSwitch = 12;
    
    public static final int rightArmTopSwitch = 13;
    public static final int rightArmBottomSwitch = 14;
    

   
    //solenoids
    public static final int shiftingSolenoid = 1;
    public static final int feederSolenoid = 2;
    public static final int tippingSolenoid = 3;
    
    //driverstation controls
    public static final int driverGamepad = 1;
    public static final int operatorGamepad = 2;
    
}
