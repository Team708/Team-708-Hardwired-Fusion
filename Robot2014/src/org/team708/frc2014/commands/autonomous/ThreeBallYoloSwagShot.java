/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team708.frc2014.commands.drivetrain.DriveBackwardToEncoder;
import org.team708.frc2014.commands.drivetrain.ResetEncoders;
import org.team708.frc2014.commands.intake.DeployIntake;
import org.team708.frc2014.commands.intake.DispenseBallTimed;
import org.team708.frc2014.commands.intake.IntakeBallTimed;
import org.team708.frc2014.commands.intake.IntakeUntilHasBall;
import org.team708.frc2014.commands.launcher.LauncherGoalShot;
import org.team708.frc2014.commands.launcher.LauncherMoveTo;

/**
 *
 * @author Nam Tran
 */
public class ThreeBallYoloSwagShot extends CommandGroup {
    
    public ThreeBallYoloSwagShot() {
        // Opens the intake while keeping the first ball in by spinning
        addSequential(new DeployIntake());
        addSequential(new IntakeBallTimed(0.8));    // Was 1.2
        
        // Move the arm up to make room to intake the second ball, then intakes the ball
        addSequential(new LauncherMoveTo(900));
        addSequential(new IntakeBallTimed(0.3)); // Was 0.3
        
        // Drive to the goal
        addSequential(new ResetEncoders());
        addSequential(new DriveBackwardToEncoder(-4000));
        addSequential(new WaitCommand(0.2));
        
        // Moves second ball out of the way and shoots the first ball
        addSequential(new DispenseBallTimed(0.08));  // Was 0.15
        addSequential(new WaitCommand(0.7));
        addSequential(new LauncherGoalShot());
        
        // Intakes the second ball and shoots it
        addSequential(new IntakeUntilHasBall());
        addSequential(new WaitCommand(0.5));
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
