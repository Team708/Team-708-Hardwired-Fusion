/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.commands.intake;

import org.team708.frc2014.commands.CommandBase;

/**
 *
 * @author Robotics
 */
public class IntakeUntilHasBall extends CommandBase {
    
    public IntakeUntilHasBall() {
        // Use requires() here to declare subsystem dependencies
        requires(intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        intake.intakeBall();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return intake.hasBall();
    }

    // Called once after isFinished returns true
    protected void end() {
        System.out.println(intake.hasBall());
        intake.stopIntake();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
