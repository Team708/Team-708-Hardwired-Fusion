package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.drivetrain.Drive;

/**
 * @author Matt Foley, Nam Tran, Pat Walls, Jillan Wang, Connor Willison
 */
public class Drivetrain extends Subsystem {
    
    // Creates speed controllers
    private final SpeedController leftMotor1;
    private final SpeedController leftMotor2;
    private final SpeedController rightMotor1;
    private final SpeedController rightMotor2;
    
    // Sensors
    private final Encoder leftEncoder;
    private final Encoder rightEncoder;
    
    // Creates drivers (one for two motors running, one for three running)
    private final RobotDrive driver;
    private final RobotDrive swagDriver;
    
    // Drivetrain Modes
    private boolean haloDrive = true;
    public final int NORMAL = 0;
    public final int SWAG = 1;
    public final int CRAWL = 2;
    private int mode = NORMAL;
    private boolean thirdMotorEnabled = false;
    
    // Scaling for drive modes
    private final double[] scalarFB = {1.00, 1.00, 1.00};
    private final double[] scalarLR = {1.00, 1.00, 1.00};

    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
         setDefaultCommand(new Drive());
    }
    
    /*
    * Constructor - run once when Drivetrain object is created.
    */
    public Drivetrain()
    {
        // Creates left motor controllers
        leftMotor1 = new Talon(RobotMap.leftMotor1);
        leftMotor2 = new Talon(RobotMap.leftMotor2);
        
        // Creates right motor controllers
        rightMotor1 = new Talon(RobotMap.rightMotor1);
        rightMotor2 = new Talon(RobotMap.rightMotor2);
        
        //Creates encoders
        leftEncoder = new Encoder(RobotMap.leftEncoderA, RobotMap.leftEncoderB);
        rightEncoder = new Encoder(RobotMap.rightEncoderA, RobotMap.rightEncoderB);
        
        // Creates normal drive mode using two motor controllers (2 motors on each side)
        driver = new RobotDrive(leftMotor1,rightMotor1);
        
        // Creates drive mode using four motor controllers (3 motors on each side)
        swagDriver = new RobotDrive(leftMotor1, leftMotor2, rightMotor1, rightMotor2);
    }
    
    /*
     * Normal drive modes
     */
    
    /**
     * Use joystick values to do skid-steer/tank drive.
     * @param leftAxis
     * @param rightAxis 
     */
    public void tankDrive(double leftAxis, double rightAxis)
    {
        if (thirdMotorEnabled) {
            // Driver for three motors on
            swagDriver.tankDrive((scalarFB[SWAG] * leftAxis), (scalarFB[SWAG] * rightAxis));
        } else {
            // Driver for two motors on
            driver.tankDrive((scalarFB[mode] * leftAxis), (scalarFB[mode] * rightAxis));
        }
    }
    
    /**
     * Use joystick values to do Halo drive.
     * @param leftAxis
     * @param rightAxis 
     */
    public void haloDrive (double leftAxis, double rightAxis)
    {
        if (thirdMotorEnabled) {
            // Driver for three motors on
            swagDriver.arcadeDrive((scalarFB[SWAG] * leftAxis), (scalarLR[SWAG] * rightAxis));
        } else {
            // Driver for two motors on
            driver.arcadeDrive((scalarFB[mode] * leftAxis), (scalarLR[mode] * rightAxis));
        }
    }
    
    public int getMode() {
        return mode;
    }

    public boolean isHaloDrive() {
        return haloDrive;
    }
    
    public void setIsHaloDrive(boolean newMode) {
        haloDrive = newMode;
    }
    
    public void setMode(int newMode) {
        if (!(newMode >= NORMAL && newMode <= CRAWL)) {return;}
        
        mode = newMode;
        
        if (mode == SWAG) {
            thirdMotorEnabled = true;
        } else {
            leftMotor2.set(0.0);
            rightMotor2.set(0.0);
            thirdMotorEnabled = false;
        }
//        currentScalarFB = scalarFB[mode];
//        currentScalarLR = scalarLR[mode];
    }
    
    public boolean isThirdMotorEnabled() {
        return thirdMotorEnabled;
    }
    
//    public void drive(Gamepad gamepad) {
//        if (this.isHaloDrive()) {
//            this.haloDrive(gamepad.getAxis(Gamepad.leftStick_Y),gamepad.getAxis(Gamepad.rightStick_X));
//        } else {
//            this.tankDrive(gamepad.getAxis(Gamepad.leftStick_Y),gamepad.getAxis(Gamepad.rightStick_Y));
//        }
//    }
}
