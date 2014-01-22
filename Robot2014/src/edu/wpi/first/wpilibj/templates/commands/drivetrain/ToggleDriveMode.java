/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.drivetrain;

import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 *
 * @author Nam Tran
 * 
 */
public class ToggleDriveMode extends CommandBase {

    public ToggleDriveMode() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        // If driveMode is set to one drive control, then it changes to the other
        if (drivetrain.getDriveMode().equals("halo")) {
            drivetrain.setDriveMode("tank");
        } else if (drivetrain.getDriveMode().equals("tank")) {
            drivetrain.setDriveMode("halo");
        } else {}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
