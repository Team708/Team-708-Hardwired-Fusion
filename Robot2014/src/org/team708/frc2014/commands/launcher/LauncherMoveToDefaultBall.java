/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.commands.launcher;

import org.team708.frc2014.commands.CommandBase;
import org.team708.util.Math708;

/**
 *
 * @author Robotics
 */
public class LauncherMoveToDefaultBall extends CommandBase {
    
    private static final int ENGAGEMENT_COUNTS = 1000;       //# counts away from goal to enable speed reduction
    private static final double REDUCTION_CUTOFF = .5;      //reduce smoothly to x as target is approached 
    private static final int TOLERANCE_COUNTS = 100;        //# counts away from goal to stop

    private int goalCounts = launcher.HAS_BALL_POSITION;
    private boolean precision = false;
    private boolean done = false;
    
    public LauncherMoveToDefaultBall() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(launcher);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        int countsDiff = goalCounts - launcher.getCounts();
        int abs_countsDiff = Math.abs(countsDiff);
        double scalar = 1.0;

        /*
         * Check if goal has been reached or launcher has stopped at a boundary.
         */
        if (abs_countsDiff <= TOLERANCE_COUNTS || launcher.stoppedAtBoundary()) {
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

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        cancel();
    }
}
