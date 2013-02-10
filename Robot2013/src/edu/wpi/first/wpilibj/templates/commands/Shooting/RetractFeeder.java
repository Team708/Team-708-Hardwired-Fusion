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
public class RetractFeeder extends CommandBase{

    public RetractFeeder()
    {
        requires(shooter);
    }
    
    protected void initialize() {
    }

    protected void execute() {
        shooter.retractSolenoid();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
