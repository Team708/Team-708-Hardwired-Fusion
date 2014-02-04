package org.team708.frc2014.commands.catapult;

import org.team708.frc2014.commands.CommandBase;

/**
 *
 * @author Nam Tran, Pat Walls, Jillan Wang
 */
public class ManualFling extends CommandBase {
    
    public ManualFling() {
        // Use requires() here to declare subsystem dependencies
        requires(catapult);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        catapult.manualFling();
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