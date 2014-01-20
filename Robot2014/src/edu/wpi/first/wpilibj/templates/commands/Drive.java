/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.OI;
import utilclasses.Gamepad;

/**
 *
 * @author Connor Willison, Pat Walls, Nam Tran
 */
public class Drive extends CommandBase {
    
    public Drive() {
        // Use requires() here to declare subsystem dependencies
        requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        // Changes how the joysticks are read according to what driveMode is set to
        if (drivetrain.getDriveMode().equals("halo")) {
            drivetrain.haloDrive(OI.driverGamepad.getAxis(Gamepad.leftStick_Y),- OI.driverGamepad.getAxis(Gamepad.rightStick_X));
        } else if (drivetrain.getDriveMode().equals("tank")) {
            drivetrain.tankDrive(OI.driverGamepad.getAxis(Gamepad.leftStick_Y),OI.driverGamepad.getAxis(Gamepad.rightStick_Y));
        }
        
        if (!drivetrain.getOverdrive()) {
            
        } else {
            
        }
    }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        drivetrain.haloDrive(0.0,0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
    
}
