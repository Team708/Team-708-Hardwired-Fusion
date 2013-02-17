/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.Shooting;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 * Allows the operator to manually control the shooter.
 * @author Connor Willison
 */
public class ManualSpinUp extends CommandBase {
    
    private double shooterSpeedRPM = 2000;
    
    public ManualSpinUp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        super("ManualSpinUp");
    }

    // Called just before this Command runs the first time
    protected void initialize() {
//        shooterSpeedRPM = Preferences.getInstance().getDouble("ManualShooterSpeedRPM",0.0);
        SmartDashboard.putNumber("Manual Spin Up Speed RPM", shooterSpeedRPM);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        shooter.setSpeed(shooterSpeedRPM);  
//        shooter.setPWM(1.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !oi.isShooterOverrideButtonHeld();
    }

    // Called once after isFinished returns true
    protected void end() {
        shooter.setSpeed(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}
