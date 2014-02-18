/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team708.frc2014.commands.launcher;

import org.team708.frc2014.commands.CommandBase;

/**
 * Moves the launcher up until the upper photogate is triggered.
 * @author Connor Willison
 */
public class LauncherMoveToTop extends CommandBase{

    public LauncherMoveToTop()
    {
        requires(launcher);
        requires(intake);
    }
    
    protected void initialize() {
    }

    protected void execute() {
        if (intake.isDeployed()) {
            launcher.goUpward();
        }
    }

    protected boolean isFinished() {
        return launcher.getUpperGate();
    }

    protected void end() {
        launcher.stop();
    }

    protected void interrupted() {
    }
    
}
