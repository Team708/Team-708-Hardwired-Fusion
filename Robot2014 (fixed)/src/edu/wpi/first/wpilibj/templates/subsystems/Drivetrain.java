
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.Drive;
/**
 *
 */
public class Drivetrain extends Subsystem {
    
    private final SpeedController leftMotor1;
    private final SpeedController rightMotor1;
    private final SpeedController leftMotor2;
    private final SpeedController rightMotor2;
    
    private final RobotDrive driver;
    private final RobotDrive swagDriver;
    
    private String driveMode = "halo";
    
    private boolean swagMode = false;

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
        leftMotor1 = new Jaguar(RobotMap.leftMotor1);    //create left motor controller object
        leftMotor2 = new Jaguar(RobotMap.leftMotor2);
        
        rightMotor1 = new Jaguar(RobotMap.rightMotor1);  //create right motor controller object
        rightMotor2 = new Jaguar(RobotMap.rightMotor2);
        
        driver = new RobotDrive(leftMotor1,rightMotor1); //creates driver
        swagDriver = new RobotDrive(leftMotor1, leftMotor2, rightMotor1, rightMotor2);
    }
    
    /**
     * Use joystick values to do skid-steer/tank drive.
     * @param leftAxis
     * @param rightAxis 
     */
    public void tankDrive(double leftAxis, double rightAxis)
    {
        if(!swagMode){
            if ((leftAxis < 0.25 && leftAxis > -0.25)) {
                leftAxis = 0.0;
            }
            if (rightAxis < 0.25 && rightAxis > -0.25){
                rightAxis = 0.0;
            }
            
            driver.tankDrive(leftAxis, rightAxis);
        } else {
            if ((leftAxis < 0.25 && leftAxis > -0.25)) {
                leftAxis = 0.0;
            }
            if (rightAxis < 0.25 && rightAxis > -0.25){
                rightAxis = 0.0;
            }
            
            swagDriver.tankDrive(leftAxis, rightAxis);
        }
    }
    
    /**
     * Use joystick values to do Halo drive.
     * @param leftAxis
     * @param rightAxis 
     */
    public void haloDrive (double leftAxis, double rightAxis)
    {
        System.out.println("Before: " + leftAxis + " , " + rightAxis);
        if(!swagMode){
            if ((leftAxis < 0.25 && leftAxis > -0.25)) {
                leftAxis = 0.0;
            }
            if (rightAxis < 0.25 && rightAxis > -0.25){
                rightAxis = 0.0;
            }
            
            driver.arcadeDrive(leftAxis, rightAxis);
            System.out.println("After: " + leftAxis + " , " + rightAxis);
        } else {
            if ((leftAxis < 0.25 && leftAxis > -0.25)) {
                leftAxis = 0.0;
            }
            if (rightAxis < 0.25 && rightAxis > -0.25){
                rightAxis = 0.0;
            }
                
            swagDriver.arcadeDrive(leftAxis, rightAxis);
        }
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
