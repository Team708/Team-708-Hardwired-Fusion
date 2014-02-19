/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.commands.intake;

import edu.wpi.first.wpilibj.Timer;
import org.team708.frc2014.commands.CommandBase;

/**
 *
 * @author Robotics
 */
public class IntakeBallTimed extends CommandBase {
    
    private Timer runTime;
    private double timeToRun;
    
    public IntakeBallTimed(double newTimeToRun) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(intake);
        
        runTime = new Timer();
        timeToRun = newTimeToRun;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        runTime.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        intake.intakeBall();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timeToRun <= (runTime.get());
    }

    // Called once after isFinished returns true
    protected void end() {
        runTime.stop();
        System.out.println(runTime.get());
        runTime.reset();
        
        intake.stopIntake();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
