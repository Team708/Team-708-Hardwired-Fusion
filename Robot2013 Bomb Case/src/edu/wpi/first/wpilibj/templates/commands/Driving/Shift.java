/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.Driving;

import edu.wpi.first.wpilibj.templates.OI;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 *
 * @author Connor Willison
 */
public class Shift extends CommandBase{
    
    protected void initialize() {
    }

    protected void execute() {
        
        //shift into the alternate gear as long as the shift command is being run
        if(drivetrain.getGear() == drivetrain.getDefaultGear())
        {
            drivetrain.changeGear();
        }
    }

    protected boolean isFinished() {
        return !oi.isShiftButtonHeld();
    }

    protected void end() {
        //when the shift command ends, shift the drivetrain back to the default gear
        if(drivetrain.getGear() != drivetrain.getDefaultGear())
        {
            drivetrain.changeGear();
        }
    }

    protected void interrupted() {
    }
    
}
