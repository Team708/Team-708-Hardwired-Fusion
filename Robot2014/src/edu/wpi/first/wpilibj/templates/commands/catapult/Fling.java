/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.catapult;

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
        if (catapult.getState() == catapult.Stopped()) {
            catapult.stop();
        } else if (catapult.getState() == catapult.Forward()) {
            if (!catapult.getUpperSwitch()) {
                catapult.goForward();
            } else {
                catapult.stop();
                catapult.setState(catapult.Stopped());
            }
        } else if (catapult.getState() == catapult.Backward()) {
            if (!catapult.getLowerSwitch () ) {
                catapult.goBackward();
            } else {
                catapult.stop();
                catapult.setState(catapult.Stopped());
            }
        }
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
