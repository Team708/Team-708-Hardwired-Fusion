/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.commands.launcher;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Moves the launcher to the home position (the bottom photogate).
 * Constructor contains a boolean option for raising first in order
 * to avoid getting stuck beneath the bottom photogate.
 * @author Connor Willison
 */
public class LauncherHome extends CommandGroup {

    private static final int HOMING_RAISE_COUNTS = 250;

    public LauncherHome(boolean raiseFirst) {
        if(raiseFirst)
        {
            addSequential(new LauncherResetEncoder());                      //First, reset so we can move relative to start
            addSequential(new LauncherMoveTo(HOMING_RAISE_COUNTS, true));   //Raise a bit in case we started below low sensor
        }
        addSequential(new LauncherMoveToBottom());                      //Move to the bottom photogate
        addSequential(new LauncherResetEncoder());                      //re-zero at the bottom
    }
}
