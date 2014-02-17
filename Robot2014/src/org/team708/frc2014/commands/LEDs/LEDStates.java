/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.commands.LEDs;

import org.team708.frc2014.commands.CommandBase;

/**
 *
 * @author Robotics
 */
public class LEDStates extends CommandBase {
    
    public LEDStates() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(ledArray);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        int currentState = ledArray.getState();
        
        if (currentState == ledArray.OFF) {
            ledArray.turnOff();
        } else if (currentState == ledArray.FLASHING) {
            ledArray.setFlashing(ledArray.getFlashRate());
        } else if (currentState == ledArray.SOLID) {
            ledArray.setSolid();
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
