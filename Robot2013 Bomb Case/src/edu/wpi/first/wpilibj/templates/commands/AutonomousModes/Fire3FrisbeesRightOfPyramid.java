/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.AutonomousModes;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;
import edu.wpi.first.wpilibj.templates.commands.Driving.DriveForDistance;
import edu.wpi.first.wpilibj.templates.commands.Driving.RotateToAngle;
import edu.wpi.first.wpilibj.templates.commands.Shooting.Aim;
import edu.wpi.first.wpilibj.templates.commands.Shooting.Shoot;
import edu.wpi.first.wpilibj.templates.commands.Shooting.SpinDown;
import edu.wpi.first.wpilibj.templates.commands.Shooting.SpinUp;
import edu.wpi.first.wpilibj.templates.commands.Wait;

/**
 *
 * @author Robotics
 */
public class Fire3FrisbeesRightOfPyramid extends CommandGroup {
    
    public Fire3FrisbeesRightOfPyramid() {
        //back off from pyramid 1ft
        addSequential(new DriveForDistance(-12,.7,0.0));
        
        //rotate right to face targets
        addSequential(new RotateToAngle(80,.8));
        
        /*
         * Go through the aim/spin up/fire cycle three times,
         * once for each frisbee. The first cycle should take the
         * longest, but after the robot has aimed and spun up the
         * second and third shots should be quick.
         */
        addSequential(new Aim());
        addSequential(new SpinUp());
        addSequential(new Wait(3.0));
        
        for(int iterations = 0; iterations < 10; iterations++)
        {
            addSequential(new Shoot());
        }
        
        addSequential(new SpinDown());
    }
    
    public void end()
    {
        super.end();
        
        CommandBase.shooter.setPWM(0.0);
    }
}
