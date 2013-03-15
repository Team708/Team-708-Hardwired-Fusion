/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.Shooting;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 * Runs the Aim and SpinUp commands while the prepare to shoot
 * button is held.
 * @author Connor Willison
 */
public class TeleopPrepareToShoot extends CommandGroup {
    
    public TeleopPrepareToShoot() {
        //addSequential(new Aim());
        addSequential(new SpinUp());
    }
    
    public boolean isFinished()
    {
        return !CommandBase.oi.isPrepareToShootButtonHeld();
    }
    
    public void end()
    {
        super.end();
        
        //stop the shooter
        CommandBase.shooter.setSpeed(0.0);
    }
    
    public void interrupted()
    {
        super.interrupted();
        
        this.end();
    }
}
