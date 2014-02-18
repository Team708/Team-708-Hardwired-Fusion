/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.commands.intake;

import org.team708.frc2014.commands.CommandBase;

/**
 *
 * @author Connor Willison
 */
public class IntakeStop extends CommandBase{
    
    public IntakeStop() {
        requires(intake);
    }

    protected void initialize() {
    }

    protected void execute() {
        intake.stopIntake();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
