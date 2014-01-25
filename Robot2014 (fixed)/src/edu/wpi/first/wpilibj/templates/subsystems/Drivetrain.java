
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
//import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.drivetrain.Drive;
/**
 *
 */
public class Drivetrain extends Subsystem {
    
    // Creates speed controllers
    private final SpeedController leftMotor1;
    private final SpeedController rightMotor1;
    private final SpeedController leftMotor2;
    private final SpeedController rightMotor2;
    
    // Creates drivers (one for two motors running, one for three running)
    private final RobotDrive driver;
    private final RobotDrive swagDriver;
    
    // Drivetrain Modes
    private String driveMode = "halo";
    private boolean swagMode = false;
    
    // Dead-zones for the joysticks
    private final double MIN_DEAD_ZONE = -0.25;
    private final double MAX_DEAD_ZONE = 0.25;

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
//        leftMotor1 = new Victor(RobotMap.leftMotor1);    //create left motor controller object
//        leftMotor2 = new Victor(RobotMap.leftMotor2);
//        
//        rightMotor1 = new Victor(RobotMap.rightMotor1);  //create right motor controller object
//        rightMotor2 = new Victor(RobotMap.rightMotor2);
        
        leftMotor1 = new Jaguar(RobotMap.leftMotor1);    //create left motor controller object
        leftMotor2 = new Jaguar(RobotMap.leftMotor2);
        
        rightMotor1 = new Jaguar(RobotMap.rightMotor1);  //create right motor controller object
        rightMotor2 = new Jaguar(RobotMap.rightMotor2);
        
        driver = new RobotDrive(leftMotor1,rightMotor1); //creates driver
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
        if (MIN_DEAD_ZONE < leftAxis && leftAxis < MAX_DEAD_ZONE) {
            leftAxis = 0.0;
        }
        if (MIN_DEAD_ZONE < rightAxis && rightAxis < MAX_DEAD_ZONE) {
            rightAxis = 0.0;
        }
        
        driver.tankDrive(leftAxis, rightAxis);
    }
    
    /**
     * Use joystick values to do Halo drive.
     * @param leftAxis
     * @param rightAxis 
     */
    public void haloDrive (double leftAxis, double rightAxis)
    {
        if (MIN_DEAD_ZONE < leftAxis && leftAxis < MAX_DEAD_ZONE) {
            leftAxis = 0.0;
        }
        if (MIN_DEAD_ZONE < rightAxis && rightAxis < MAX_DEAD_ZONE) {
            rightAxis = 0.0;
        }
        
        driver.arcadeDrive(leftAxis, rightAxis);
    }
    
    /*
     * Swag Drive modes
     */
    
    /**
     * Use joystick values to do skid-steer/tank drive.
     * @param leftAxis
     * @param rightAxis 
     */
    public void tankSwagDrive (double leftAxis, double rightAxis)
    {
        if (MIN_DEAD_ZONE < leftAxis && leftAxis < MAX_DEAD_ZONE) {
            leftAxis = 0.0;
        }
        if (MIN_DEAD_ZONE < rightAxis && rightAxis < MAX_DEAD_ZONE) {
            rightAxis = 0.0;
        }
        
        swagDriver.tankDrive(leftAxis, rightAxis);
    }
    
    /**
     * Use joystick values to do Halo drive.
     * @param leftAxis
     * @param rightAxis 
     */
    public void haloSwagDrive (double leftAxis, double rightAxis)
    {
        if (MIN_DEAD_ZONE < leftAxis && leftAxis < MAX_DEAD_ZONE) {
            leftAxis = 0.0;
        }
        if (MIN_DEAD_ZONE < rightAxis && rightAxis < MAX_DEAD_ZONE) {
            rightAxis = 0.0;
        }
        
        swagDriver.arcadeDrive(leftAxis, rightAxis);
    }

    public boolean isSwagMode() {
        return swagMode;
    }
    
    public void setSwagMode(boolean newMode) {
        swagMode = newMode;
    }

    public String getDriveMode() {
        return driveMode;
    }
    
    public void setDriveMode(String newMode) {
        driveMode = newMode;
    }
}
