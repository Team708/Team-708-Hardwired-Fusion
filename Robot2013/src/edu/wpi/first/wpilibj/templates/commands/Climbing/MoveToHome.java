/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.Climbing;

import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 *
 * @author Robotics
 */
public class MoveToHome extends CommandBase {
    
    private static final double movementSpeed = .5;
    
    private boolean leftArmDone = false;
    private boolean rightArmDone = false;
    
    public MoveToHome() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        super("MoveToHome");
        requires(leftArm);
        requires(rightArm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        leftArm.stop();
        rightArm.stop();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(leftArm.getEncoderCounts() < leftArm.HOME_COUNTS)
        {
            leftArm.setMotorSpeed(movementSpeed);
        }else{
            leftArmDone = true;
        }
        
        if(rightArm.getEncoderCounts() < rightArm.HOME_COUNTS)
        {
            rightArm.setMotorSpeed(movementSpeed);
        }else
        {
            rightArmDone = true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return leftArmDone && rightArmDone;
    }

    // Called once after isFinished returns true
    protected void end() {
        leftArm.stop();
        rightArm.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
