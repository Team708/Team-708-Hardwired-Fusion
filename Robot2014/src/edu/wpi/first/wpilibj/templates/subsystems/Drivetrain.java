package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.Drive;

/**
 *
 * @author Connor Willison, Pat Walls, Nam Tran
 */
public class Drivetrain extends Subsystem {

    // Creates the motor controllers for the drivetrain
    private final SpeedController leftMotor1;
    private final SpeedController leftMotor2;
    private final SpeedController rightMotor1;
    private final SpeedController rightMotor2;
    private final RobotDrive driver;  
    private final RobotDrive overDriver;
    
    // Determines the drive controls
    private String driveMode = "halo";
    
    // States if in "overdrive" mode or not
    private boolean overdrive = false;

    public void initDefaultCommand() {
        // Drives the robot in either halo or tank controls
        setDefaultCommand(new Drive());
    }

    /*
     * Constructor - run once when Drivetrain object is created.
     */
    public Drivetrain() {
        leftMotor1 = new Jaguar(RobotMap.leftMotor1);     //create left motor controller object
        leftMotor2 = new Jaguar(RobotMap.leftMotor2);
        rightMotor1 = new Jaguar(RobotMap.rightMotor1);   //create right motor controller object
        rightMotor2 = new Jaguar(RobotMap.rightMotor2);
        driver = new RobotDrive(leftMotor1, rightMotor1); //create driver stuff
        overDriver = new RobotDrive(leftMotor1, leftMotor2, rightMotor1, rightMotor2);
    }

    /**
     * Use joystick values to do skid-steer/tank drive.
     *
     * @param leftAxis
     * @param rightAxis
     */
    
    // Drives with L and R stick controlling one side of the wheels
    public void tankDrive(double leftAxis, double rightAxis) {
        if (leftAxis > -1 && leftAxis < 1) {
            if (leftAxis != 0) {
                if (leftAxis > 0) {
                    leftAxis = 0.50;
                } else {
                    leftAxis = -0.50;
                }
            }
        }
        if (rightAxis > -1 && rightAxis < 1) {
            if (rightAxis != 0) {
                if (rightAxis > 0) {
                    rightAxis = 0.50;
                } else {
                    rightAxis = -0.50;
                }
            }
        }
        
        if (!overdrive) {
            driver.tankDrive(leftAxis, rightAxis);
        } else {
            overDriver.tankDrive(leftAxis, rightAxis);
        }
    }

    /**
     * Use joystick values to do Halo drive.
     *
     * @param leftAxis
     * @param rightAxis
     */
    
    // Drives with L stick as Forward/Back, R stick as Left/Right
    public void haloDrive(double leftAxis, double rightAxis) {
        if (!overdrive) {
            driver.arcadeDrive(leftAxis, rightAxis);
        } else {
            overDriver.arcadeDrive(leftAxis, rightAxis);
        }
    }

    // Used to get what drive mode the robot is set to
    public String getDriveMode() {
        return driveMode;
    }

    // Used to change what the drive mode should be
    public void setDriveMode(String newMode) {
        driveMode = newMode;
    }
    
    // Used to get if the drivetrain is in overdrive
    public boolean getOverdrive() {
        return overdrive;
    }
    
    // Used to change the state of overdrive for the drivetrain
    public void setOverdrive(boolean overdriveState) {
        overdrive = overdriveState;
    }
}
