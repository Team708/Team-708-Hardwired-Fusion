/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

/**
 *
 * @author Connor Willison
 */
public class ResetEncoders extends CommandBase{
    
    protected void initialize() {
    }

    protected void execute() {
        drivetrain.resetEncoders();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
