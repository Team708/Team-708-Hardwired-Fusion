/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.Climbing;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Connor Willison
 */
public class Home extends CommandGroup {
    
    public Home() {
        addSequential(new RetractArms());
        addSequential(new MoveToHome());
        addSequential(new ResetClimberEncoders());
    }
}
