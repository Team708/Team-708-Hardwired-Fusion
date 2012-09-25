/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drivetrain;

import edu.wpi.first.wpilibj.RobotDrive;

/**
 * This class adds swerve drive functionality
 * to the RobotDrive class.
 * 
 * Wheel 1: right forward wheel (1st quadrant)
 * Wheel 2: left forward wheel (2nd quadrant)
 * Wheel 3: left rear wheel (3rd quadrant)
 * Wheel 4: right rear wheel (4th quadrant)
 * @author Connor
 */
public class RobotDriveSwerve extends RobotDrive{

    //must verify measurements on robot:
    //used to determine directions of rotational vectors
    private final double robotWidthIn = 36;
    private final double robotLengthIn = 60;
    
    //rotational vectors - notation:
    //r(rotation)x(x component)1(first wheel)
    private final double rx1 = -robotWidthIn/2;
    
    public RobotDriveSwerve(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor) {
        super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
        
        
    }
    
    public void swerveDrive(double vx,double vy,double rotationRads)
    {
        
    }
    
}
