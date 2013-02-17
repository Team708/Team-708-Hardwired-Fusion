/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.Shooting;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.templates.commands.Wait;

/**
 * Autonomously uses the shooter to fire one shot at the
 * current target.
 * @author Vincent Gargiulo + Connor Willison
 */
public class Shoot extends CommandGroup {
    
    public Shoot()
    {
        addSequential(new Aim());
        addSequential(new SpinUp());
        addSequential(new ExtendFeeder());
        //addSequential(new Wait(.5));
        addSequential(new RetractFeeder());
        //addSequential(new Wait(.5));
        addSequential(new SpinDown());
    }
}
