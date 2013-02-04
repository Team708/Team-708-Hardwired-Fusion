/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Connor Willison
 */
public class DebugVision extends CommandBase {
    
    public DebugVision() {
        requires(visionProcessor);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        visionProcessor.processData();
        SmartDashboard.putString("Current Target:", visionProcessor.getTargetType());
        SmartDashboard.putNumber("Distance To Target:", visionProcessor.getDistanceToTarget());
        SmartDashboard.putNumber("Pixel Difference:", visionProcessor.getDifferencePx());
        SmartDashboard.putNumber("Aspect Ratio:", visionProcessor.getAspectRatio());
        SmartDashboard.putNumber("Blob Count:", visionProcessor.getBlobCount());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
