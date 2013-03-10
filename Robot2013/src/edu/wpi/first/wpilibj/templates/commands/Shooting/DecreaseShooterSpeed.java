/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.Shooting;

import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 * Subtracts RPMs from shooter speed.
 * @author Robotics
 */
public class DecreaseShooterSpeed extends CommandBase{

    protected void initialize() {
    }

    protected void execute() {
        shooter.decreaseRPM();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
    
}
