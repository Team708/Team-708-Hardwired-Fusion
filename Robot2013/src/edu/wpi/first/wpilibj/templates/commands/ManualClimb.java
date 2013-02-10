/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

/**
 * This command lets the operator manually control the climber.
 * @author Connor Willison
 */
public class ManualClimb extends CommandBase {
    
    public ManualClimb() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(leftArm);
        requires(rightArm);
        requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //use the gamepad joysticks to move the climber manually
        if(oi.getClimbingLeftAxis() > 0)
        {
            if(leftArm.isExtended()) leftArm.stop();
            else leftArm.setMotorSpeed(oi.getClimbingLeftAxis());
        }else if(oi.getClimbingLeftAxis() < 0)
        {
            if(leftArm.isRetracted()) leftArm.stop();
            else leftArm.setMotorSpeed(oi.getClimbingLeftAxis());
        }else{
            leftArm.stop();
        }
        
        if(oi.getClimbingRightAxis() > 0)
        {
            if(rightArm.isExtended()) rightArm.stop();
            else rightArm.setMotorSpeed(oi.getClimbingRightAxis());
        }else if(oi.getClimbingRightAxis() < 0)
        {
            if(rightArm.isRetracted()) rightArm.stop();
            else rightArm.setMotorSpeed(oi.getClimbingRightAxis());
        }else{
            rightArm.stop();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !oi.isClimberOverrideButtonHeld();
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
