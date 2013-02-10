/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

/**
 * This command is used to drive the robot during autonomous mode.
 * @author Vincent Gargiulo
 */
public class DriveForDistance extends CommandBase{
    
    private double distance, speed, rotation;
    private boolean finished = false;
    
    public DriveForDistance(double distance,double forwardSpeed,double rotationSpeed)
    {
        this.distance = distance;
        this.speed = forwardSpeed;
        this.rotation = rotationSpeed;
        
        requires(drivetrain);
    }

    protected void initialize() {
        drivetrain.resetEncoders();
    }

    protected void execute() {
        
        //Averages distance and compares it to the target distance
        if (((drivetrain.getRightEncDistance() + drivetrain.getLeftEncDistance())/2.0) < distance)
        {
            drivetrain.arcadeDrive(speed, rotation);
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
        System.out.println("DriveDistance interrupted.");
    }
    
    
}
