/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.Shooting;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 *
 * @author Connor Willison + Vince Garguilo
 */
public class Aim extends CommandBase {
    
    private static final double rotationTolerancePx= 10;
    private static final double rotationSpeed = .6;
    private double rotation;
    private boolean linedUp;
    private Timer lostTargetTimer;
    
    private static final double targetLostTolerance = 1.0;
    
    public Aim() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        super("Aim");
        lostTargetTimer = new Timer();
        lostTargetTimer.start();
        requires(drivetrain);
        requires(visionProcessor);
    }
 
    // Called just before this Command runs the first time
    protected void initialize() {
        rotation = 0;
        linedUp = false;
        lostTargetTimer.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        visionProcessor.processData();
        //If the vision processor has a target, the robot adjusts its angle
        //to line up with the target.
        if (visionProcessor.hasTarget())
        {
            lostTargetTimer.reset(); 
            
            if (visionProcessor.getDifferencePx() > rotationTolerancePx)
            {
                rotation = rotationSpeed;
            }
            else if(visionProcessor.getDifferencePx() < -rotationTolerancePx)
            {
                rotation = -rotationSpeed;
            }
            else
            {
                rotation = 0;
                linedUp = true;
            }
            
            drivetrain.arcadeDrive(0.0, rotation);
        }else
        {
            if(lostTargetTimer.get() > targetLostTolerance)
            {
                //stop aiming because target was lost
                linedUp = true;
            }
        }
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return linedUp;
    }

    // Called once after isFinished returns true
    protected void end() {
        drivetrain.arcadeDrive(0.0,0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}
