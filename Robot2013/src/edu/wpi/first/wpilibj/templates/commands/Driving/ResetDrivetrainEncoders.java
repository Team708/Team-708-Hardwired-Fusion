/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.Driving;

import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 *
 * @author Connor Willison
 */
public class ResetDrivetrainEncoders extends CommandBase{
    
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
