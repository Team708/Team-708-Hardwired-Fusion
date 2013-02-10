/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

/**
 * Spins up the shooter for a shot.
 * @author Connor Willison
 */
public class SpinUp extends CommandBase {
    
    public SpinUp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(shooter);
        requires(visionProcessor);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        /*Gets distance from the camera, and uses that to retrieve from the table
         the correct RPMs to set the speed of the shooter to. (Assuming Vision
         Processor has a .getRPM command)*/
        shooter.setSpeed(visionProcessor.getRPM());
        
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return shooter.isAtSpeed();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
