/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.commands.drivetrain;

import org.team708.frc2014.commands.CommandBase;

/**
 *
 * @author Robotics
 */
public class DriveBackwardToEncoder extends CommandBase {
    
    private double goalCount;
    private double moveSpeed = -0.90;
    private final double TOLERANCE = 100;
    
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
        drivetrain.haloDrive(moveSpeed, 0.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(goalCount - drivetrain.getAverageEncoderDistance()) <= TOLERANCE;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
