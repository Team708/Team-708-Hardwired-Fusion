package org.team708.frc2014;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    
    // Motor/ PWM Ports -- Drivetrain
    public static final int leftMotor1 = 1;    //normal drive
    public static final int leftMotor2 = 2;    //swag drive enabled
    public static final int rightMotor1 = 3;   //normal drive
    public static final int rightMotor2 = 4;   //swag drive enabled
    
    
    // Motor/ PWM Ports -- Launcher
    public static final int launcherMotor1 = 5; //Forward Motor
    public static final int launcherMotor2 = 6; //Reversed Motor
    
    // Motor/ PWM Ports -- Intake
    public static final int intakeMotor = 7; //Intake Motor
    
    // Digital Inputs -- Drivetrain
    public static final int leftEncoderA = 1; //Channel-A for left Encoder
    public static final int leftEncoderB = 2; //Channel-B for left Encoder
    
    public static final int rightEncoderA = 3; //Channel-A for right Encoder
    public static final int rightEncoderB = 4; //Channel-B for right Encoder
    
    // Digital Inputs -- Launcher
    public static final int launcherEncoderA = 6; //Channel-A for launcher Encoder
    public static final int launcherEncoderB = 7; //Channel-B for launcher Encoder
    public static final int launcherLowerSwitch= 8; //Switch for top/max of launcher
    public static final int launcherUpperSwitch = 9; //Switch for bottom/min of launcher
    
    // Analog Sensors -- Launcher
    public static final int launcherPotentiometer = 1;
    
    // Solenoids -- Intake
    public static final int intakeSolenoidA = 1;
    public static final int intakeSolenoidB = 2;
    
    // Joystick Ports
    public static final int driverGamepad = 1;
    public static final int operatorGamepad = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int rangefinderPort = 1;
    // public static final int rangefinderModule = 1;
}
