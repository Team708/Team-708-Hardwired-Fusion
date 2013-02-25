/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.Climbing;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;
import edu.wpi.first.wpilibj.templates.subsystems.Climber;

/**
 * Extends the climber arms to their full height, ensuring
 * that the limit switches are pressed.
 * @author Connor Willison
 */
public class ExtendArms extends CommandBase {
    
     private static final double moveSpeed = 1.0;
    
    public ExtendArms() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        super("ExtendArm");
        
        requires(leftArm);
        requires(rightArm);
//        requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
//        moveSpeed = Preferences.getInstance().getDouble("ExtendSpeed",.5);
        
        leftArm.stop();
        rightArm.stop();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
        double speedAdjustment = Climber.calcSpeedAdjustment();
        
        if(!leftArm.isExtended())
        {
            leftArm.setMotorSpeed(moveSpeed - speedAdjustment);
        }
        
        if(!rightArm.isExtended())
        {
            rightArm.setMotorSpeed(moveSpeed + speedAdjustment);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return leftArm.isExtended() && rightArm.isExtended();
    }

    // Called once after isFinished returns true
    protected void end() {
        leftArm.stop();
        rightArm.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}
