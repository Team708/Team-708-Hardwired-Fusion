
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.OI;
import utilclasses.Gamepad;

/**
 *
 * @author Connor
 */
public class TankDriveCommand extends CommandBase {

    public TankDriveCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        drivetrain.tankDrive(OI.driverGamepad.getAxis(Gamepad.leftStick_Y),OI.driverGamepad.getAxis(Gamepad.rightStick_Y));
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
        end();
    }
}
