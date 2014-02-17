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
public class DriveForwardToTargetUltrasonic extends CommandBase {
    
    public DriveForwardToTargetUltrasonic(int shotType) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(drivetrain);
        
        if(shotType == drivetrain.REGULAR) {
              drivetrain.setUltrasonicDistance((drivetrain.REGULAR_DISTANCE - 6), (drivetrain.REGULAR_DISTANCE + 6), false);
        } else {
              drivetrain.setUltrasonicDistance ((drivetrain.PASS_SHOT_DISTANCE - 6), (drivetrain.PASS_SHOT_DISTANCE + 6), false);
        }
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        ledArray.setState(ledArray.FLASHING);
        drivetrain.haloDrive(-drivetrain.normalPercent, -drivetrain.getTurnSpeed());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return drivetrain.isAtOptimumDistance();
    }

    // Called once after isFinished returns true
    protected void end() {
        drivetrain.stop();
        ledArray.setState(ledArray.SOLID);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}