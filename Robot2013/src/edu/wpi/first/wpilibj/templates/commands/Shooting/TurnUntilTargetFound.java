/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.Shooting;

import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 *
 * @author Robotics
 */
public class TurnUntilTargetFound extends CommandBase {
    
    private double rotation;
    private double maxRotation;
    private double minRotation;
    
    private boolean turningBack = false;
    private boolean doneTurning = false;
    
    public TurnUntilTargetFound(double rotation,double maxRotation, double minRotation) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        super("Turn Until Target");
        
        this.rotation = rotation;
        this.maxRotation = maxRotation;
        this.minRotation = minRotation;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        drivetrain.resetAngle();
        turningBack = false;
        doneTurning = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(!turningBack){
            drivetrain.arcadeDrive(0.0, rotation);
            
            if(drivetrain.getAngle() > maxRotation)
            {
                turningBack = true;
            }
        }
        else{
            drivetrain.arcadeDrive(0.0, -rotation);
            
            if(drivetrain.getAngle() < minRotation)
            {
                doneTurning = true;
            }
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return visionProcessor.hasTarget() || doneTurning;
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
