/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team708.frc2014.commands.drivetrain;

import org.team708.frc2014.OI;
import org.team708.frc2014.commands.CommandBase;
import org.team708.util.Gamepad;

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
        drivetrain.haloDrive(OI.driverGamepad.getAxis(Gamepad.leftStick_Y),-OI.driverGamepad.getAxis(Gamepad.rightStick_X));
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
    }
}
