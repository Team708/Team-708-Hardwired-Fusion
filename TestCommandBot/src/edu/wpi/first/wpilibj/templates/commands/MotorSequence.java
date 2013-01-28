/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Robotics
 */
public class MotorSequence extends CommandGroup{
    public MotorSequence(){
     //addParallel or AddSequential
     addSequential(new SpinMotor(5));
     addSequential(new Wait(3));
     addSequential(new SpinMotor(2));
    }
}