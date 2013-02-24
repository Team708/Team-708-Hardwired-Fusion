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
        addSequential(new ExtendFeeder());
        addSequential(new Wait(.5));        //wait for flicker to respond
        addSequential(new RetractFeeder());
        addSequential(new Wait(.5));         //wait for frisbee to leave the launcher so shooter doesn't jam
//        addSequential(new Wait(.5));
//        addSequential(new SpinDown());
    }
}
