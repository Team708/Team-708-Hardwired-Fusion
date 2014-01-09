
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
    
    private final SpeedController leftMotor;
    private final SpeedController rightMotor;
    private final RobotDrive driver;
    
    // Determines the drive controls
    private String driveMode = "halo";

    public void initDefaultCommand() 
    {
        // Drives the robot in either halo or tank controls
         setDefaultCommand(new Drive());
    }
    
    /*
    * Constructor - run once when Drivetrain object is created.
    */
    public Drivetrain()
    {
        leftMotor = new Jaguar(RobotMap.leftMotor);     //create left motor controller object
        rightMotor = new Jaguar(RobotMap.rightMotor);   //create right motor controller object
        driver = new RobotDrive(leftMotor,rightMotor);
    }
    
    /**
     * Use joystick values to do skid-steer/tank drive.
     * @param leftAxis
     * @param rightAxis 
     */
    
    // Drives with L and R stick controlling one side of the wheels
    public void tankDrive(double leftAxis, double rightAxis)
    {
        driver.tankDrive(leftAxis, rightAxis);
    }
    
    /**
     * Use joystick values to do Halo drive.
     * @param leftAxis
     * @param rightAxis 
     */
    
    // Drives with L stick as Forward/Back, R stick as Left/Right
    public void haloDrive (double leftAxis, double rightAxis)
    {
        driver.arcadeDrive(leftAxis, rightAxis);
    }
    
    // Used to get what drive mode the robot is set to
    public String getDriveMode() {
        return driveMode;
    }
    
    // Used to change what the drive mode should be
    public void setDriveMode(String newMode) {
        driveMode = newMode;
    }
}
