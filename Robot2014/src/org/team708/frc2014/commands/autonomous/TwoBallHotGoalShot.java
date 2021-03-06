/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team708.frc2014.commands.CommandBase;
import org.team708.frc2014.commands.drivetrain.FollowBall;
import org.team708.frc2014.commands.intake.ManualIntake;

/**
 *
 * @author Robotics
 */
public class TwoBallHotGoalShot extends CommandGroup {
    
    public TwoBallHotGoalShot() {
        addSequential(new OneHotGoalShot());
        addSequential(new WaitCommand(0.1));
        addSequential(new FollowBall());
        addSequential(new ManualIntake());
        if (CommandBase.intake.hasBall()) {
            addSequential(new OneHotGoalShot());
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
