/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.commands.autonomous;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team708.frc2014.commands.drivetrain.DriveBackwardToEncoder;
import org.team708.frc2014.commands.drivetrain.DriveForwardToTargetUltrasonic;
import org.team708.frc2014.commands.drivetrain.FollowBall;
import org.team708.frc2014.commands.intake.DeployIntake;
import org.team708.frc2014.commands.intake.DispenseBallTimed;
import org.team708.frc2014.commands.intake.IntakeBall;
import org.team708.frc2014.commands.intake.IntakeBallTimed;
import org.team708.frc2014.commands.intake.IntakeStop;
import org.team708.frc2014.commands.intake.IntakeUntilHasBall;
import org.team708.frc2014.commands.launcher.LauncherGoalShot;
import org.team708.frc2014.commands.launcher.LauncherHoldBall;
import org.team708.frc2014.commands.launcher.LauncherMoveTo;

/**
 *
 * @author Viet
 */
public class CustomisableAuto extends CommandGroup {
    
    Preferences preferences;
    private int balls = 1;
    private boolean followHotGoal = false;
    private boolean followBall = false;
    
    public CustomisableAuto() {
        
        if (balls == 2 || balls == 3) {
            this.intakeSecondBall();
        }
        
        this.moveToGoal();
        
        if (balls == 1) {
            this.prepareFirstBall();
            addSequential(new LauncherHoldBall());
        } else if (balls == 2 || balls == 3) {
            addSequential(new DispenseBallTimed(0.15));
        }
        
        addSequential(new LauncherGoalShot());
        
        if (balls == 1) {
            if (followBall) {
                addSequential(new FollowBall());
            }
        } else {
            this.prepareSecondBall();
            addSequential(new LauncherGoalShot());
            
            if ((balls == 2 && followBall) || balls == 3) {
                addSequential(new FollowBall());
                if (balls == 3) {
                    addSequential(new IntakeUntilHasBall());
//                    addSequential(new RotateToEncoder(180));
                    addSequential(new LauncherGoalShot());
                }
            }
        }
    }
    
    public final void moveToGoal() {
        addSequential(new DriveBackwardToEncoder(-4000));
        addSequential(new DriveForwardToTargetUltrasonic(0));
    }
    
    public final void intakeSecondBall() {
        addSequential(new DeployIntake());
        addSequential(new IntakeBallTimed(1.1));
        addSequential(new LauncherMoveTo(900));
        addSequential(new IntakeBallTimed(0.3));
    }
    
    public final void prepareFirstBall() {
        addSequential(new IntakeBall());
        addSequential(new WaitCommand(.5));
        addSequential(new DeployIntake());
        addSequential(new WaitCommand(1.0));
        addSequential(new IntakeStop());
    }
    
    public final void prepareSecondBall() {
        addSequential(new IntakeUntilHasBall());
        addSequential(new WaitCommand(0.5));
        
    }
    
    public void getPreferences() {
        balls = preferences.getInt("Number of Balls", balls);
        followHotGoal = preferences.getBoolean("Follow Hot Goal", followHotGoal);
        followBall = preferences.getBoolean("Follow Ball After", followBall);
    }
}
