package org.team708.frc2014.commands.launcher;

import org.team708.frc2014.commands.CommandBase;

/**
 *
 * @author Nam Tran
 */
public class ManualBackward extends CommandBase {
    
    public ManualBackward() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(launcher);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(launcher.getState() == launcher.Stopped()) {
            launcher.setState(launcher.Backward());
        } else {
            launcher.setState(launcher.Stopped());
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}