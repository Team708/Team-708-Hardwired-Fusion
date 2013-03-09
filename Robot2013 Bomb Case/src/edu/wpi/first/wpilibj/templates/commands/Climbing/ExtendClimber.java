/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.Climbing;

import edu.wpi.first.wpilibj.templates.commands.CommandBase;
import edu.wpi.first.wpilibj.templates.subsystems.Climber;

/**
 * Uses the climber piston to tip the climber forward onto the pyramid.
 * @author Connor Willison
 */
public class ExtendClimber extends CommandBase {
    
    public ExtendClimber() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        super("Extend Climber");
        requires(rightArm);
        requires(leftArm);
//        requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Climber.extendClimber();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
