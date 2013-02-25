/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.Climbing;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.templates.subsystems.Climber;

/**
 * Moves the climber arms to the "home" position.
 * @author Connor Willison
 */
public class GoHome extends CommandGroup {
    
    public GoHome() {
//        addSequential(new RetractArms());
//        addSequential(new ResetClimberEncoders());
//        addSequential(new MoveTo(Climber.HOME_COUNTS));
////        addSequential(new ResetClimberEncoders());
        
        addSequential(new ExtendArms());
        addSequential(new ResetClimberEncoders());
        addSequential(new MoveTo(Climber.HOME_COUNTS - Climber.EXTENDED_COUNTS));
        addSequential(new ResetClimberEncoders());
    }
        // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
//        System.out.println(this.getName() + " interrupted");
        this.end();
    }
}
