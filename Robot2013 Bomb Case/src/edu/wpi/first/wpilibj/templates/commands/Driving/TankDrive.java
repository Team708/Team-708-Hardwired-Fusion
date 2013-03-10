/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.Driving;

import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 *
 * @author Nam Tran
 */
public class TankDrive extends CommandBase{
        
public TankDrive() {
        // Use requires() here to declare subsystem dependencies
        super("TankDrive");
        requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        drivetrain.tankDrive(oi.getTankLeftAxis(), oi.getTankRightAxis());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        drivetrain.tankDrive(0.0,0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}
