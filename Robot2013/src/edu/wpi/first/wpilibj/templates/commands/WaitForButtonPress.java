/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.Preferences;

/**
 * Waits for the next stage button to be pressed before continuing
 * to the next command.
 * @author Connor Willison
 */
public class WaitForButtonPress extends CommandBase {
    
    private boolean wasReleased = false;
    private boolean climberDebug = false;
    
    public WaitForButtonPress() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        super("WaitForButtonPress");
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        climberDebug = Preferences.getInstance().getBoolean("WaitBetweenStage",false);
        wasReleased = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(!oi.isNextStageButtonPressed())
        {
            wasReleased = true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (oi.isNextStageButtonPressed() && wasReleased) || !climberDebug;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
