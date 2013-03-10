/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.Driving;

import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 *
 * @author Connor Willison
 */
public class RotateToAngle extends CommandBase{

    private double angle,rotationSpeed;
    private static final double rotationToleranceDeg = 3.0;
    private boolean finished = false;
    
    public RotateToAngle(double angle,double rotationSpeed)
    {
        this.angle = angle;
        this.rotationSpeed = rotationSpeed;
        
        requires(drivetrain);
    }
    
    protected void initialize() {
        drivetrain.resetEncoders();
    }

    protected void execute() {
        
        double diff = drivetrain.getAngle() - angle;
        
        if(diff < -rotationToleranceDeg)
        {
            drivetrain.arcadeDrive(0, rotationSpeed);
        }
        else if (diff > rotationToleranceDeg)
        {
            drivetrain.arcadeDrive(0, -rotationSpeed);
        }
        else
        {
               finished = true;   
        }
    }

    protected boolean isFinished() {
        return finished;
    }

    protected void end() {
        drivetrain.arcadeDrive(0, 0);
    }

    protected void interrupted() {
        this.end();
    }
    
    
}
