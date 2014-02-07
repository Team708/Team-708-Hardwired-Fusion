package org.team708.frc2014.commands.launcher;

import  org.team708.frc2014.commands.CommandBase; 

/**
 *
 * @author Kyumin Lee
 */
public class Backward extends CommandBase {
    
    public Backward() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(launcher);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        launcher.setState (launcher.Backward()); 
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
