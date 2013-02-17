/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.Climbing;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;
import edu.wpi.first.wpilibj.templates.subsystems.Climber;

/**
 * Lowers the arms to the start position.
 * @author Connor Willison
 */
public class RetractArms extends CommandBase {
    
//    private static final double moveSpeed = Preferences.getInstance().getDouble("RetractSpeed",.5);
    private static final double moveSpeed = 1.0;
    
    public RetractArms() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        super("Retract Arms");
        
        requires(leftArm);
        requires(rightArm);
        requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        leftArm.stop();
        rightArm.stop();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
        double speedAdjustment = Climber.calcSpeedAdjustment();
        
        if(!leftArm.isRetracted())
        {
            leftArm.setMotorSpeed(-moveSpeed - speedAdjustment);
        }
        
        if(!rightArm.isRetracted())
        {
            rightArm.setMotorSpeed(-moveSpeed + speedAdjustment);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return leftArm.isRetracted() && rightArm.isRetracted();
    }

    // Called once after isFinished returns true
    protected void end() {
        leftArm.stop();
        rightArm.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
//        System.out.println(this.getName() + " interrupted");
        this.end();
    }
}
