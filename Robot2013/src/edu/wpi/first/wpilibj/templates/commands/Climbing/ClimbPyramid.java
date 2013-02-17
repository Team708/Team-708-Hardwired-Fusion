package edu.wpi.first.wpilibj.templates.commands.Climbing;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.templates.subsystems.Climber;

/**
 *
 * @author Connor
 */
public class ClimbPyramid extends CommandGroup {
    
    public ClimbPyramid() {
 
        addSequential(new MoveTo(Climber.EXTENDED_COUNTS - Climber.HOME_COUNTS));
        addSequential(new ResetClimberEncoders());
        addSequential(new MoveTo(-Climber.EXTENDED_COUNTS));
        addSequential(new ResetClimberEncoders());

        for(int i = 0; i< 4; i++)
        {
            addSequential(new MoveTo(Climber.EXTENDED_COUNTS));
            addSequential(new MoveTo(0));
            addSequential(new ResetClimberEncoders());
        }
        
//        addSequential(new GoHome());
        
//        addSequential(new MoveTo(900));
    }
}
