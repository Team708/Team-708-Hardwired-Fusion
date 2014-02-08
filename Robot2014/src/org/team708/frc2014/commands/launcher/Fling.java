package org.team708.frc2014.commands.launcher;

import org.team708.frc2014.commands.CommandBase;

/**
 *
 * @author Kyumin Lee
 */
public class Fling extends CommandBase {
    
    public Fling() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(launcher);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (launcher.getState() == launcher.Stopped()) {
            launcher.stop();
        } else if (launcher.getState() == launcher.Forward()) {
            if (!launcher.getUpperBound()) {
                launcher.goForward();
            } else {
                launcher.stop();
                launcher.setState(launcher.Stopped());
            }
        } else if (launcher.getState() == launcher.Backward()) {
            if (!launcher.getLowerBound () ) {
                launcher.goBackward();
            } else {
                launcher.stop();
                launcher.setState(launcher.Stopped());
            }
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