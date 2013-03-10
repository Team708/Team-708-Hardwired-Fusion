/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.Shooting;

import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 *
 * @author Connor
 */
public class JoystickShooterControl extends CommandBase {
    
    public JoystickShooterControl() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        super("Joystick Shooter Control");
        requires(shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        shooter.setPWM(0.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        shooter.setPWM(oi.getShooterControlAxis());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return oi.isShooterJoystickControlButtonHeld();
    }

    // Called once after isFinished returns true
    protected void end() {
        shooter.setPWM(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}
