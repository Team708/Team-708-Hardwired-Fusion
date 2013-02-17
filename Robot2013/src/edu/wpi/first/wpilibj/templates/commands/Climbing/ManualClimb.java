/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.Climbing;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 * This command lets the operator manually control the climber.
 * @author Connor Willison
 */
public class ManualClimb extends CommandBase {
    double leftAxis  = 0;
    double rightAxis  = 0;
    
    private static final double maxSpeedAdjustment = .15;
    private static final double maxEncoderDifference = 50;
    private static final double speedDifferenceTolerance = 0;
    
    public ManualClimb() {
        super("Manual Climb");
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
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
        //use the gamepad joysticks to move the climber manually
        
        double updown  = oi.getClimbingUpDownAxis();
//        double leftRight = oi.getClimbingRightAxis();
        
        double speedAdjustment = calcSpeedAdjustment(leftArm.getEncoderCounts() - rightArm.getEncoderCounts());
 
        if((leftArm.isExtended() && rightArm.isExtended()) || (leftArm.isRetracted() && rightArm.isRetracted()))
        {
            leftArm.resetEncoder();
            rightArm.resetEncoder();
        }
        
        if(updown > 0)
        {
            if(!leftArm.isExtended()) {
                leftArm.setMotorSpeed(updown - speedAdjustment);
            }else{
                leftArm.stop();
            }
            
            if(!rightArm.isExtended()) {
                rightArm.setMotorSpeed(updown + speedAdjustment);
            }else{
                rightArm.stop();
            }
        } else if(updown < 0)
        {
            if(!leftArm.isRetracted()) {
                leftArm.setMotorSpeed(updown - speedAdjustment);
            }else{
                leftArm.stop();
            }
            
            if(!rightArm.isRetracted()) {
                rightArm.setMotorSpeed(updown + speedAdjustment);
            }else{
                rightArm.stop();
            }
        } else
        {
            leftArm.stop();
            rightArm.stop();
        }
        
//        leftAxis  = oi.getClimbingLeftAxis();
//        rightAxis = oi.getClimbingRightAxis();
//        
//        if(leftAxis > 0)
//        {
//            if(!leftArm.isExtended()) {leftArm.setMotorSpeed(moveSpeed);}
//        } else if(leftAxis < 0)
//        {
//            if(!leftArm.isRetracted()) {leftArm.setMotorSpeed(-moveSpeed);}
//        } else
//        {
//            leftArm.stop();
//        }
//        
//        if(rightAxis > 0)
//        {
//            if(!rightArm.isExtended()) {rightArm.setMotorSpeed(moveSpeed);}
//        } else if(rightAxis < 0)
//        {
//            if(!rightArm.isRetracted()) {rightArm.setMotorSpeed(-moveSpeed);}
//        } else
//        {
//            rightArm.stop();
//        }
//        
//        if(oi.getClimbingLeftAxis() > 0)
//        {
//            if(leftArm.isExtended()) leftArm.stop();
//            else leftArm.setMotorSpeed(oi.getClimbingLeftAxis());
//        }else if(oi.getClimbingLeftAxis() < 0)
//        {
//            if(leftArm.isRetracted()) leftArm.stop();
//            else leftArm.setMotorSpeed(oi.getClimbingLeftAxis());
//        }else{
//            leftArm.stop();
//        }
//        
//        if(oi.getClimbingRightAxis() > 0)
//        {
//            if(rightArm.isExtended()) rightArm.stop();
//            else rightArm.setMotorSpeed(oi.getClimbingRightAxis());
//        }else if(oi.getClimbingRightAxis() < 0)
//        {
//            if(rightArm.isRetracted()) rightArm.stop();
//            else rightArm.setMotorSpeed(oi.getClimbingRightAxis());
//        }else{
//            rightArm.stop();
//        }
    }
    
    private double calcSpeedAdjustment(int encoderDifference) {
        if (Math.abs(encoderDifference) > speedDifferenceTolerance) {
            double speedAdjustment = maxSpeedAdjustment * encoderDifference / maxEncoderDifference;

            speedAdjustment = Math.min(maxSpeedAdjustment, speedAdjustment);
            speedAdjustment = Math.max(-maxSpeedAdjustment, speedAdjustment);

            return speedAdjustment;
        } else {
            return 0.0;
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
        System.out.println(this.getName() + " interrupted");
        this.end();
    }
}
