/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.commands.launcher;

import org.team708.frc2014.OI;
import org.team708.frc2014.commands.CommandBase;
import org.team708.util.Gamepad;

/**
 *
 * @author Robotics
 */
public class LauncherManualControl extends CommandBase {
    
    public LauncherManualControl() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(launcher);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (intake.isDeployed()) {
            launcher.manualControl(-OI.operatorGamepad.getAxis(Gamepad.leftStick_Y));
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        launcher.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}