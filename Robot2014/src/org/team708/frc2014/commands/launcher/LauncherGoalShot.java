package org.team708.frc2014.commands.launcher;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team708.frc2014.commands.CommandBase;

/**
 * Launch the ball with maximum power, then return to home position. Must make
 * sure that the intake is deploy before firing, or else the robot will damage
 * itself.
 *
 * @author Kyumin Lee, Connor Willison
 */
public class LauncherGoalShot extends CommandGroup {

    public LauncherGoalShot() {
        if (CommandBase.intake.isDeployed()) {
            addSequential(new LauncherMoveToTop()); //launch ball
            addSequential(new WaitCommand(.1));     //wait to allow for follow through
            addSequential (new LauncherMoveToBottom());  //reset launcher
        } else {
            this.cancel();
        }
    }
}
