/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team708.frc2014.commands.drivetrain.EncoderDrive;
//import org.team708.frc2014.commands.drivetrain.FollowBall;
import org.team708.frc2014.commands.intake.IntakeUntilHasBall;

/**
 *
 * @author Nam Tran
 */
public class ThreeBallYoloSwagShot extends CommandGroup {
    
    public ThreeBallYoloSwagShot() {
        addSequential(new TwoBallYoloSwagShot());
//        addSequential(new FollowBall());
        addSequential(new IntakeUntilHasBall());
        addSequential(new EncoderDrive(0.0, 180));
        addSequential(new YoloSwagShot());
        
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
