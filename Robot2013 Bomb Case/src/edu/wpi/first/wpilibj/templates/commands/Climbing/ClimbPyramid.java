package edu.wpi.first.wpilibj.templates.commands.Climbing;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.templates.commands.Wait;
import edu.wpi.first.wpilibj.templates.commands.WaitForButtonPress;

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
        
        //extend arms
        //tip robot onto pyramid
        //partially retract arms
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
        
        
        //addSequential(new WaitForButtonPress());
        addSequential(new TipRobot());
        addSequential(new Wait(2.0));
        //addSequential(new WaitForButtonPress());
        
        //extend arms after tipping so that fingers do not get stuck
        addSequential(new ExtendArms());
        addSequential(new ResetClimberEncoders());
//        addSequential(new MoveTo(-400));
//        addSequential(new WaitForButtonPress());
        
//        addSequential(new RetractArms());
        addSequential(new MoveTo(-800));
//        addSequential(new ResetClimberEncoders());
        //addSequential(new WaitForButtonPress()); //10 pt climb
        addSequential(new LiftRobot());
        //addSequential(new WaitForButtonPress());
        
         //retract the tip piston here so that air pressure
        //has had time to build up after extending lift piston
        addParallel(new LevelRobot());
        
        //first extend/retract
//        addSequential(new ExtendArms());
//        addSequential(new ResetClimberEncoders());
//        addSequential(new WaitForButtonPress());
//        addSequential(new RetractArms());
//        addSequential(new ResetClimberEncoders());
//        addSequential(new WaitForButtonPress());
//        
//        //2nd extend/retract
//        addSequential(new ExtendArms());
//        addSequential(new ResetClimberEncoders());
//        addSequential(new WaitForButtonPress());
//        addSequential(new RetractArms());
//        addSequential(new ResetClimberEncoders());
//        addSequential(new WaitForButtonPress());
//        
//        //3rd extend to reach 2nd rung
//        addSequential(new ExtendArms());
//        addSequential(new ResetClimberEncoders());
//        addSequential(new WaitForButtonPress());
//        addSequential(new RetractArms());
//        addSequential(new ResetClimberEncoders());
//        addSequential(new WaitForButtonPress());
        
    }
}
