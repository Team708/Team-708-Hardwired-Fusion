package org.team708.frc2014.commands.drivetrain;

import org.team708.frc2014.commands.CommandBase;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Robotics
 */
public class EncoderDrive extends CommandBase {

    private double goalDistance;
    private double goalAngle;
    private double driveSpeed;
    private double rotationSpeed;
    private double driveParam;
    private double rotateParam;
    private double distanceDiff;
    private double angleDiff;
    private static final double angleToleranceDeg = 2.5;
    private static final double distanceToleranceIn = 5.0;
    private static final double defaultDriveSpeed = .6;
    private static final double defaultRotationSpeed = .6;

    public EncoderDrive(double goalDistance, double goalAngle, double driveSpeed, double rotationSpeed) {
        // Use requires() here to declare subsystem dependencies
        requires(drivetrain);

        this.goalDistance = goalDistance;
        this.goalAngle = goalAngle;
        this.driveSpeed = Math.abs(driveSpeed);
        this.rotationSpeed = Math.abs(rotationSpeed);
    }
    
    public EncoderDrive(double goalDistance,double goalAngle)
    {
        this(goalDistance,goalAngle,defaultDriveSpeed,defaultRotationSpeed);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        drivetrain.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        driveParam = 0.0;
        rotateParam = 0.0;
        
        distanceDiff = 0.0;
        angleDiff = 0.0;

        if (goalDistance != 0.0) {
            distanceDiff = drivetrain.getAverageEncoderDistance() - goalDistance;

            if (distanceDiff < 0) {
                //needs to move forward
                driveParam = driveSpeed;  
            }else if(distanceDiff > 0)
            {
                //needs to move backward
                driveParam = -driveSpeed;
            }
        }

        if (goalAngle != 0.0) {
            angleDiff = drivetrain.getAngleDeg() - goalAngle;
            
            if (angleDiff < 0) {
                //needs to turn right
                rotateParam = rotationSpeed;  
            }else if(angleDiff > 0)
            {
                //needs to move backward
                rotateParam = -rotationSpeed;
            }
        }
        
        drivetrain.haloDrive(driveParam, rotateParam);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(distanceDiff) <= distanceToleranceIn && Math.abs(angleDiff) <= angleToleranceDeg;
    }

    // Called once after isFinished returns true
    protected void end() {
        drivetrain.haloDrive(0.0,0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
