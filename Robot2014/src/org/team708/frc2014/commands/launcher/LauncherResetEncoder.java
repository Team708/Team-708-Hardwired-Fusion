/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team708.frc2014.commands.launcher;

import org.team708.frc2014.commands.CommandBase;

/**
 * Resets the launcher's encoder.
 * @author Connor Willison
 */
public class LauncherResetEncoder extends CommandBase{

    protected void initialize() {
    }

    protected void execute() {
        launcher.resetEncoder();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
