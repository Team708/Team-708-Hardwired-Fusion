/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.commands.launcher;

import org.team708.frc2014.commands.CommandBase;
import org.team708.util.Math708;

/**
 * The basic Launcher movement command. Use to move to a specified encoder count
 * value. Has a boolean option for precision movement (smoothly reduces speed as
 * target is approached) or max power movement (used for shooting). Usage:
 * addSequential(new LauncherMoveTo(counts,precision t/f));
 *
 * @author Connor Willison
 */
public class LauncherMoveTo extends CommandBase {

    //parameters for precision movement
    private static final int ENGAGEMENT_COUNTS = 1000;       //# counts away from goal to enable speed reduction
    private static final double REDUCTION_CUTOFF = .5;      //reduce smoothly to x as target is approached 
    private static final int TOLERANCE_COUNTS = 100;        //# counts away from goal to stop
    
    private int goalCounts;
    private boolean precision;
    private boolean done;

    public LauncherMoveTo(int counts) {
        this(counts, false);
    }

    public LauncherMoveTo(int counts, boolean precision) {
        super("MoveTo");

        this.goalCounts = counts;
        this.precision = precision;
        done = false;
        
        requires(launcher);
        requires(intake);
    }

    protected void initialize() {
        
    }

    protected void execute() {
        //assume the launcher is zeroed at the bottom (low photogate)
        int countsDiff = goalCounts - launcher.getCounts();
        int abs_countsDiff = Math.abs(countsDiff);
        double scalar = 0.59;

        /*
         * Check if goal has been reached or launcher has stopped at a boundary.
         */
        if (abs_countsDiff <= TOLERANCE_COUNTS || launcher.getUpperGate()) {
            launcher.stop();
            done = true;
            return;
        }

        if (precision) {
            //only enabled precision if within engagement counts of goal
            if (abs_countsDiff <= ENGAGEMENT_COUNTS) {
                //use linear interpolation to smoothly adjust speed as launcher approaches target
                scalar = Math708.lerp(ENGAGEMENT_COUNTS, 1.0, 0, REDUCTION_CUTOFF, abs_countsDiff);
            }
        }

        //launcher needs to move up
        if (countsDiff > 0) {
            launcher.goUpward(scalar);
        } else {
            launcher.goDownward(scalar);
        }
    }

    protected boolean isFinished() {
        return done;
    }

    protected void end() {
        launcher.stop();
        done = false;
    }

    protected void interrupted() {
        end();
    }

}
