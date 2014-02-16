/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.commands;

/**
 *
 * @author Nam Tran
 */
public class ToggleLED extends CommandBase {
    
    private final double FLASH_RATE = 0.25; // Rate of flashing LEDS
    
    // States of LED
    private final int OFF = 0;
    private final int FLASHING = 1;
    private final int SOLID = 2;
    private int state = OFF;
    
    public ToggleLED() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (state == SOLID) {
            state = OFF;
        } else {
            state += state;
        }
        
        if (state == OFF) {
            ledArray.turnOff();
        } else if (state == FLASHING) {
            ledArray.setFlashing(FLASH_RATE);
        } else if (state == SOLID) {
            ledArray.setSolid();
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
