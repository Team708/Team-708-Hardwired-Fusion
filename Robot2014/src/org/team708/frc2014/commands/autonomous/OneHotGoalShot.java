/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team708.frc2014.commands.CommandBase;
import org.team708.frc2014.commands.drivetrain.DriveBackwardToEncoder;
import org.team708.frc2014.commands.drivetrain.DriveForwardToTargetUltrasonic;
import org.team708.frc2014.commands.drivetrain.ResetEncoders;
import org.team708.frc2014.commands.intake.DeployIntake;
import org.team708.frc2014.commands.intake.IntakeBall;
import org.team708.frc2014.commands.intake.IntakeStop;
import org.team708.frc2014.commands.launcher.LauncherGoalShot;
import org.team708.frc2014.commands.launcher.LauncherHoldBall;
import org.team708.frc2014.commands.launcher.LauncherMoveToBottom;
import org.team708.frc2014.commands.launcher.LauncherMoveToTop;

/**
 *
 * @author Nam Tran
 */
public class OneHotGoalShot extends CommandGroup {
    
    public OneHotGoalShot() {
        //initial driving
        addSequential(new ResetEncoders());
        addSequential(new DriveBackwardToEncoder(-4000));
        addSequential(new DriveForwardToTargetUltrasonic(0));
        
        //open intake and prepare to shoot
        addSequential(new IntakeBall());
        addSequential(new WaitCommand(.5));
        addSequential(new DeployIntake());
        addSequential(new WaitCommand(1.0));
        addSequential(new IntakeStop());
        addSequential(new LauncherHoldBall());
        
        if (CommandBase.visionProcessor.isGoalLit()) {
            //shoot
            addSequential(new LauncherMoveToTop());
            addSequential(new WaitCommand(0.1));
            addSequential(new LauncherMoveToBottom());
        } else {
            addSequential(new WaitCommand(5));
            //shoot
            addSequential(new LauncherMoveToTop());
            addSequential(new WaitCommand(0.1));
            addSequential(new LauncherMoveToBottom());
        }
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