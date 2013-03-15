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
import edu.wpi.first.wpilibj.templates.commands.Shooting.TurnUntilTargetFound;
import edu.wpi.first.wpilibj.templates.commands.Wait;

/**
 *
 * @author Robotics
 */
public class Fire3FrisbeesRightOfPyramid extends CommandGroup {
    
    public Fire3FrisbeesRightOfPyramid() {
        //back off from pyramid 1ft
        addSequential(new DriveForDistance(-4.0,.8,0.0));
        
        //rotate right to face targets
////        addSequential(new RotateToAngle(80,.8));
//        addSequential(new TurnUntilTargetFound(true));
//        
//        addSequential(new DriveForDistance(-3.0,.7,0.0));
        
        addSequential(new Fire3Frisbees());
    }
    
    public void end()
    {
        super.end();
        
        CommandBase.shooter.setSpeed(0.0);
    }
}
