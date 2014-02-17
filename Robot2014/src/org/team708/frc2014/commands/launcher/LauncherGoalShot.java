package org.team708.frc2014.commands.launcher;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team708.frc2014.commands.CommandBase;
import org.team708.frc2014.commands.intake.DeployIntake;
import org.team708.frc2014.commands.intake.IntakeStop;
import org.team708.frc2014.commands.intake.JoystickMotorControl;
import org.team708.frc2014.commands.intake.ManualDispense;
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
        if (!CommandBase.intake.isDeployed()) {
            /*
             * Line below won't work since manual dispense is designed for use with whileHeld()
            */
            //addSequential(new ManualDispense()); //spin the intake backwards so that the ball stays in the claw
            addSequential(new DeployIntake()); //ensure the intake is out
            addSequential(new JoystickMotorControl());  //spin the intake backwards so ball stays in claw
            addSequential(new WaitCommand(.4)); //wait for it to deploy
            addSequential(new IntakeStop());    //stop the intake's spinning
            //addSequential(new IntakeStopRotation());
        }

        addSequential(new LauncherMoveToTop()); //launch ball
        addSequential(new WaitCommand(.1));     //wait to allow for follow through
        addSequential (new LauncherMoveToBottom());  //reset launcher
//        addSequential(new LauncherMoveTo(Launcher.REGULAR_SHOT_ENC_COUNTS, false));
//        addSequential(new WaitCommand(.1));   //wait a bit for safety
//        addSequential(new LauncherHome(false)); //go home but do not raise first
    }
}
