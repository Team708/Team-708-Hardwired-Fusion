/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.Shooting;

import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 * Adds RPMs to shooter speed.
 * @author Robotics
 */
public class IncreaseShooterSpeed extends CommandBase{

    protected void initialize() {
    }

    protected void execute() {
        shooter.increaseRPM();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
