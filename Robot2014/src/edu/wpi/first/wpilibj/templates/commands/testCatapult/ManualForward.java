/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.testCatapult;

import edu.wpi.first.wpilibj.templates.commands.CommandBase;
import edu.wpi.first.wpilibj.templates.subsystems.TestCatapult;

/**
 *
 * @author Robotics
 */
public class ManualForward extends CommandBase {
    
    public ManualForward() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(testCatapult);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(testCatapult.getState() == testCatapult.getForward() || testCatapult.getState() == testCatapult.getBackward()) {
            testCatapult.setState(testCatapult.getStopped());
        } else if(testCatapult.getState() == testCatapult.getForward()){
            testCatapult.setState(testCatapult.getForward());
        } else {
            testCatapult.setState(testCatapult.getStopped());
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