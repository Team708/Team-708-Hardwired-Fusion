/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *This command group is used to test the autonomous driving commands in the 
 * locker pods. It swerves in between the locker pods. 
 * @author Vincent Gargiulo
 */
public class DriveAroundLockerPods extends CommandGroup {
    
    int counter = 0;
    int iterations;
            
    public DriveAroundLockerPods(int Iterations) {
       iterations = Iterations;
       addSequential(new DriveForDistance(337, .7, 0));
       addSequential(new RotateToAngle(-125, .7));
       addSequential(new DriveForDistance(130, .7, 0));
       addSequential(new RotateToAngle(-125, .7));
       addSequential(new DriveForDistance(337, .7, 0));
       addSequential(new RotateToAngle(-90, .7));
       addSequential(new DriveForDistance(150, .6,0));
       addSequential(new RotateToAngle(-90, .7));
    }
    
    protected void execute()
    {
        super.execute();
        counter++;
    }
    
    protected boolean isFinished()
    {
        return (counter == iterations);
    }
    
}
