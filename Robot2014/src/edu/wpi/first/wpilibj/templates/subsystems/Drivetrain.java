
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.TankDriveCommand;
import edu.wpi.first.wpilibj.templates.commands.HaloDriveCommand;
/**
 *
 */
public class Drivetrain extends Subsystem {
    
    private final SpeedController leftMotor;
    private final SpeedController rightMotor;
    private final RobotDrive driver;

    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
         setDefaultCommand(new HaloDriveCommand());
    }
    
    public void toggleDrivetrain()
    {
         if(this.getDefaultCommand() instanceof TankDriveCommand) 
        {
            setDefaultCommand(new HaloDriveCommand());
        }
         else if (this.getDefaultCommand() instanceof HaloDriveCommand)
        {
            setDefaultCommand(new TankDriveCommand());
        }
         else
        {
             //How on earth did you even do this you monster?
        }
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
     * Use joystick values to do skidsteer/tank drive.
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

}
