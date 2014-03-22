package org.team708.frc2014.commands.launcher;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team708.frc2014.commands.CommandBase;
import org.team708.frc2014.commands.intake.DeployIntake;
import org.team708.frc2014.commands.intake.IntakeBall;
import org.team708.frc2014.commands.intake.IntakeDispense;
import org.team708.frc2014.commands.intake.IntakeStop;
import org.team708.frc2014.commands.intake.JoystickMotorControl;
import org.team708.frc2014.commands.intake.ManualDispense;
import org.team708.frc2014.commands.intake.ManualIntake;
import org.team708.frc2014.subsystems.Launcher;

/**
 * Launch the ball with maximum power, then return to home position. Must make
 * sure that the intake is deploy before firing, or else the robot will damage
 * itself.
 *
 * @author Kyumin Lee, Connor Willison
 */
public class LauncherGoalShot extends CommandGroup {

    public LauncherGoalShot() {
        requires(CommandBase.launcher);
        addSequential(new LauncherMoveToTop()); //launch ball
        addSequential(new WaitCommand(.1));     //wait to allow for follow through
        addSequential (new LauncherMoveToBottom());  //reset launcher
//        addSequential(new LauncherMoveTo(Launcher.REGULAR_SHOT_ENC_COUNTS, false));
//        addSequential(new WaitCommand(.1));   //wait a bit for safety
//        addSequential(new LauncherHome(false)); //go home but do not raise first
    }
}
