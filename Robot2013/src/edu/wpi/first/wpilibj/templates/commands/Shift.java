/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.OI;

/**
 *
 * @author Connor Willison
 */
public class Shift extends CommandBase{
    
    public Shift()
    {
        requires(drivetrain);
    }
    
    protected void initialize() {
    }

    protected void execute() {
        if(drivetrain.getGear() == drivetrain.getDefaultGear())
        {
            drivetrain.changeGear();
        }
    }

    protected boolean isFinished() {
        return !oi.isShiftButtonHeld();
    }

    protected void end() {
        if(drivetrain.getGear() != drivetrain.getDefaultGear())
        {
            drivetrain.changeGear();
        }
    }

    protected void interrupted() {
    }
    
}
