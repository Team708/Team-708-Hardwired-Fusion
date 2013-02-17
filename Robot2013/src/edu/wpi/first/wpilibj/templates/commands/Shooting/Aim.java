/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.Shooting;

import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 *
 * @author Connor Willison + Vince Garguilo
 */
public class Aim extends CommandBase {
    
    public Aim() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        super("Aim");
        requires(drivetrain);
        requires(visionProcessor);
    }
    private double rotationTolerancePx= 0;
    private double rotationSpeed = 0.3;
    private double rotation = 0;
    private boolean linedUp = false;
    // Called just before this Command runs the first time
    protected void initialize() {
        requires(drivetrain);
        requires(visionProcessor);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        visionProcessor.processData();
        //If the vision processor has a target, the robot adjusts its angle
        //to line up with the target.
        if (visionProcessor.hasTarget())
        {
            if (rotationTolerancePx < visionProcessor.getDifferencePx())
            {
                rotation = rotationSpeed;
            }
            else if(rotationTolerancePx > -visionProcessor.getDifferencePx())
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
            //stop aiming because target was lost
            linedUp = true;
        }
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return linedUp;
    }

    // Called once after isFinished returns true
    protected void end() {
        
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
