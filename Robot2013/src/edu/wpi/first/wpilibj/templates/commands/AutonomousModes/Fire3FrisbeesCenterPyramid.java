
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.AutonomousModes;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.templates.commands.Shooting.Aim;
import edu.wpi.first.wpilibj.templates.commands.Shooting.Shoot;
import edu.wpi.first.wpilibj.templates.commands.Shooting.SpinDown;
import edu.wpi.first.wpilibj.templates.commands.Shooting.SpinUp;
import edu.wpi.first.wpilibj.templates.commands.Wait;

/**
 * Maneuvers the robot to line up for a shot in autonomous,
 * then fires all frisbees.
 * @author Connor Willison
 */
public class Fire3Frisbees extends CommandGroup {
    
    public Fire3Frisbees() {
        
        //insert code to maneuver robot before first shot here.
        
        /*
         * Go through the aim/spin up/fire cycle three times,
         * once for each frisbee. The first cycle should take the
         * longest, but after the robot has aimed and spun up the
         * second and third shots should be quick.
         */
        addSequential(new Aim());
        addSequential(new SpinUp());
        addSequential(new Wait(3.0));
        
        for(int iterations = 0; iterations < 3; iterations++)
        {
            addSequential(new Shoot());
        }
        
        addSequential(new SpinDown());
    }
}
