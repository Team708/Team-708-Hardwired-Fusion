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
public class DriveForwardToTargetUltrasonic extends CommandBase {
    
    private int shotType;
    
    public DriveForwardToTargetUltrasonic(int newShotType) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(drivetrain);
        shotType = newShotType;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(!drivetrain.isAtOptimumDistance()) {
            drivetrain.haloDrive(drivetrain.getScalarFB(drivetrain.NORMAL()), -drivetrain.getTurnSpeed());
        } else {
            drivetrain.stop();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}