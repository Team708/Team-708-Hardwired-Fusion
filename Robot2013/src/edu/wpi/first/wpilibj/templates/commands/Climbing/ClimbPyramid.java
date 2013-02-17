package edu.wpi.first.wpilibj.templates.commands.Climbing;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.templates.subsystems.Climber;

/**
 * This command is called to make the robot climb the pyramid
 * autonomously. If the driver needs to stop the robot for any
 * reason (i.e. the robot begins to fall), the autonomous
 * command can be stopped using the abort button and manual
 * control can be used instead.
 * @author Connor Willison
 */
public class ClimbPyramid extends CommandGroup {
    
    public ClimbPyramid() {   
        /* 
         * TODO: Use ExtendArms and RetractArms to ensure switches are pressed before reseting encoders
         * use MoveTo(x) only when performing partial climber strokes, i.e. the last 3rd level stroke to
         * avoid hitting the "bird's nest" target.
         */
        
        //pseudo code for climb process:
        
        //extend first fingers
        //tip robot onto pyramid
        //retract first fingers
        //lift robot
        //do full strokes until at first "bump"
        //lower robot
        //stroke over first "bump"
        //lift robot
        //full strokes until at second "bump"
        //lower robot
        //stroke over second "bump"
        //lift robot
        //stroke until engaged on third rung
        //perform a partial stroke to lift robot off of second rung
        

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
