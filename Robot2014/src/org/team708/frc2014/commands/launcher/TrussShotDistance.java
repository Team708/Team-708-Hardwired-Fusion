/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.commands.launcher;

import org.team708.frc2014.commands.CommandBase;

/**
 *
 * @author Robotics
 */
public class TrussShotDistance extends CommandBase {
    
    private boolean done = false;
    
    public TrussShotDistance() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(launcher);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (launcher.getDistance() < launcher.TRUSS_SHOT_ENC_COUNTS) {
            launcher.goUpward();
        } else {
            launcher.stop();
            done = true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
        launcher.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}