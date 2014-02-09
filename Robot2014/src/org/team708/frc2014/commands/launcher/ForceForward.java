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
public class ForceForward extends CommandBase {
    
    //Command to override and go forward should we choose to set JoystickFling as default
    //Otherwise state machines in ManualFling and Fling wouldn't work.
    
    public ForceForward() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(launcher);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(!(launcher.getUpperBound())) {
            launcher.goForward();
        } else {
            launcher.stop();
            launcher.resetEncoder();
            this.end();
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