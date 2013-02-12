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
public class DownwardStroke extends CommandBase {
    
    private boolean leftArmDone = false;
    private boolean rightArmDone = false;
    
    public DownwardStroke() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        super("Downward Stroke");
        
        requires(leftArm);
        requires(rightArm);
        requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        leftArm.retract();
        rightArm.retract();
        
        if(leftArm.onTarget() || leftArm.isRetracted())
        {
            leftArm.resetEncoder();
            leftArm.stop();
            leftArmDone = true;
        }
        
        if(rightArm.onTarget() || rightArm.isRetracted())
        {
            rightArm.resetEncoder();
            rightArm.stop();
            rightArmDone = true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return leftArmDone && rightArmDone;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
