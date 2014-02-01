/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.testCatapult;

import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 *
 * @author robot
 */
public class Fling extends CommandBase {
    
    public Fling() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (testCatapult.getState() == testCatapult.getStopped()) {
            testCatapult.stop();
        } else if (testCatapult.getState() == testCatapult.getForward()) {
            if (!testCatapult.getUpperSwitch()) {
                testCatapult.forward();
            } else {
                testCatapult.stop();
                testCatapult.setState(testCatapult.getStopped());
            }
        } else if (testCatapult.getState() == testCatapult.getBackward()) {
            if (!testCatapult.getLowerSwitch () ) {
                testCatapult.backward();
            } else {
                testCatapult.stop();
                testCatapult.setState(testCatapult.getStopped());
            }
        }
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
