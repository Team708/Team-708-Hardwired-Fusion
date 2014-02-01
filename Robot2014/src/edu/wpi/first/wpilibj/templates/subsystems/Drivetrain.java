
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
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
    private final SpeedController leftMotor2;
    private final SpeedController rightMotor1;
    private final SpeedController rightMotor2;
    
    // Creates drivers (one for two motors running, one for three running)
    private final RobotDrive driver;
    private final RobotDrive swagDriver;
    
    // Drivetrain Modes
    private boolean haloDrive = true;
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
        // Creates left motor controllers
        leftMotor1 = new Talon(RobotMap.leftMotor1);
        leftMotor2 = new Talon(RobotMap.leftMotor2);
        
        // Creates right motor controllers
        rightMotor1 = new Talon(RobotMap.rightMotor1);
        rightMotor2 = new Talon(RobotMap.rightMotor2);
        
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
        driver.tankDrive(leftAxis, rightAxis);
    }
    
    /**
     * Use joystick values to do Halo drive.
     * @param leftAxis
     * @param rightAxis 
     */
    public void haloDrive (double leftAxis, double rightAxis)
    {
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
        swagDriver.tankDrive(leftAxis, rightAxis);
    }
    
    /**
     * Use joystick values to do Halo drive.
     * @param leftAxis
     * @param rightAxis 
     */
    public void haloSwagDrive (double leftAxis, double rightAxis)
    {
        swagDriver.arcadeDrive(leftAxis, rightAxis);
    }

    public boolean isSwagMode() {
        return swagMode;
    }
    
    public void setSwagMode(boolean newMode) {
        swagMode = newMode;
    }

    public boolean isHaloDrive() {
        return haloDrive;
    }
    
    public void setIsHaloDrive(boolean newMode) {
        haloDrive = newMode;
    }
}
