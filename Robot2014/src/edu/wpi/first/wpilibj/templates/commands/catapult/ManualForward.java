/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.catapult;

import edu.wpi.first.wpilibj.templates.commands.CommandBase;
import edu.wpi.first.wpilibj.templates.subsystems.Catapult;

/**
 *
 * @author Robotics
 */
public class ManualForward extends CommandBase {
    
    public ManualForward() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(catapult);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(catapult.getState() == catapult.Forward() || catapult.getState() == catapult.Backward()) {
            catapult.setState(catapult.Stopped());
        } else if(catapult.getState() == catapult.Stopped()){
            catapult.setState(catapult.Forward());
        } else {
            catapult.setState(catapult.Stopped());
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