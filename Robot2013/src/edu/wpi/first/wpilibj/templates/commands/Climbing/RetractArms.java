/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.Climbing;

import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 * Lowers the arms to the start position.
 * @author Connor Willison
 */
public class RetractArms extends CommandBase {
    
    private static final double homeSpeed = .5;
    
    public RetractArms() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        super("Reset Climber to Home");
        
        requires(leftArm);
        requires(rightArm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        rightArm.stop();
        leftArm.stop();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(!leftArm.isRetracted())
        {
            leftArm.setMotorSpeed(-homeSpeed);
        }
        
        if(!rightArm.isRetracted())
        {
            rightArm.setMotorSpeed(-homeSpeed);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return leftArm.isRetracted() && rightArm.isRetracted();
    }

    // Called once after isFinished returns true
    protected void end() {
        leftArm.resetEncoder();
        rightArm.resetEncoder();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
