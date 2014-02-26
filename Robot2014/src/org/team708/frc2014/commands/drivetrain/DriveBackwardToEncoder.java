/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.commands.drivetrain;

import org.team708.frc2014.commands.CommandBase;

/**
 *
 * @author Nam Tran
 */
public class DriveBackwardToEncoder extends CommandBase {
    
    private double goalCount;
    
    private double turnSpeed = 0.0;
    private final double MOVE_SPEED = -0.90;
    
    private final double DISTANCE_TOLERANCE = 100;
    private final double TURN_TOLERANCE = 5.0;
    
    public DriveBackwardToEncoder(double goalCount) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(drivetrain);
        
        this.goalCount = goalCount;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        drivetrain.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double encoderDifference = drivetrain.getLeftEncoder() - drivetrain.getRightEncoder();
        
        if (encoderDifference < -TURN_TOLERANCE || encoderDifference > TURN_TOLERANCE) {
            turnSpeed = encoderDifference / 10;
        } else {
            turnSpeed = 0.0;
        }
        drivetrain.haloDrive(MOVE_SPEED, turnSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(goalCount - drivetrain.getAverageEncoderDistance()) <= DISTANCE_TOLERANCE;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
