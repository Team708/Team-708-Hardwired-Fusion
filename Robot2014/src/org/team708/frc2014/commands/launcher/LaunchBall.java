package org.team708.frc2014.commands.launcher;

import org.team708.frc2014.commands.CommandBase;

/**
 *
 * @author Kyumin Lee, Nam Tran, Debbie Musselman
 */
public class LaunchBall extends CommandBase {
    
    private boolean done = false;
    
    public LaunchBall() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(launcher);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (!launcher.getUpperSwitch()) {
            launcher.goUpward();
        } else {
            done = true;
            launcher.stop();
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
