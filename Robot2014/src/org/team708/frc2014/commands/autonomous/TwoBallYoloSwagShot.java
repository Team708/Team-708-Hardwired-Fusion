/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team708.frc2014.commands.drivetrain.DriveForwardToTargetUltrasonic;
import org.team708.frc2014.commands.intake.DeployIntake;
import org.team708.frc2014.commands.intake.DispenseBallTimed;
import org.team708.frc2014.commands.intake.IntakeBallTimed;
import org.team708.frc2014.commands.launcher.LauncherGoalShot;
import org.team708.frc2014.commands.launcher.LauncherMoveTo;
import org.team708.frc2014.commands.launcher.LauncherMoveToBottom;
import org.team708.frc2014.commands.launcher.LauncherMoveToTop;

/**
 *
 * @author Nam Tran
 */
public class TwoBallYoloSwagShot extends CommandGroup {
    
    public TwoBallYoloSwagShot() {
        addSequential(new DeployIntake ());
        addParallel(new DeployIntake());
        addSequential(new IntakeBallTimed(0.10));
        addSequential(new LauncherMoveTo(4500));
        addSequential(new DriveForwardToTargetUltrasonic(0));
        addSequential(new DispenseBallTimed(0.10));
        addSequential(new LauncherGoalShot());
        addSequential(new LauncherMoveToBottom());
        addSequential(new IntakeBallTimed(0.25));
        addSequential(new LauncherGoalShot());
        
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
