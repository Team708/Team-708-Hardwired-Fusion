/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * Stops all currently running commmands.
 * @author Connor Willison
 */
public class AbortCommands extends CommandBase {
    
    public AbortCommands() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        super("Stop Everything");
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Scheduler.getInstance().removeAll();
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
