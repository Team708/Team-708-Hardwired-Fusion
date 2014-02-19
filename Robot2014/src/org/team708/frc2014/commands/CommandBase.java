package org.team708.frc2014.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team708.frc2014.OI;
import org.team708.frc2014.commands.autonomous.TwoBallYoloSwagShot;
import org.team708.frc2014.commands.autonomous.YoloSwagShot;
import org.team708.frc2014.commands.drivetrain.ResetEncoders;
import org.team708.frc2014.commands.drivetrain.TurnToTargetUltrasonic;
import org.team708.frc2014.commands.intake.DispenseBallTimed;
import org.team708.frc2014.commands.intake.IntakeBallTimed;
import org.team708.frc2014.commands.launcher.LauncherMoveToBottom;
import org.team708.frc2014.commands.launcher.LauncherMoveToTop;
import org.team708.frc2014.subsystems.Drivetrain;
import org.team708.frc2014.subsystems.Intake;
import org.team708.frc2014.subsystems.LEDArray;
import org.team708.frc2014.subsystems.Launcher;
import org.team708.frc2014.subsystems.SEDArray;
import org.team708.frc2014.subsystems.VisionProcessor;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 * @author Nam Tran, Connor Willison
 */
public abstract class CommandBase extends Command {

    public static OI oi;
    // Create a single static instance of all of your subsystems
    public static Launcher launcher = new Launcher();
    public static Drivetrain drivetrain = new Drivetrain();
    public static Intake intake = new Intake();
    public static VisionProcessor visionProcessor = new VisionProcessor();
    public static LEDArray ledArray = new LEDArray();
    public static SEDArray sedArray = new SEDArray();

    public static void init() {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();

        // Show what command your subsystem is running on the SmartDashboard
        SmartDashboard.putData(launcher);
        SmartDashboard.putData(drivetrain);
        SmartDashboard.putData(intake);
        SmartDashboard.putData(visionProcessor);
        SmartDashboard.putData(ledArray);
        
        //send commands to SmartDashboard for debugging
        SmartDashboard.putData("YOLOSWAG Shot", new YoloSwagShot());
        SmartDashboard.putData("2 Ball YOLOSWAG Shot", new TwoBallYoloSwagShot());
        
        SmartDashboard.putData("Intake for 5 second", new IntakeBallTimed(5));
        SmartDashboard.putData("Dispense for 5 second", new DispenseBallTimed(5));
        
        SmartDashboard.putData("Move To Top Thing", new LauncherMoveToTop());
        SmartDashboard.putData("Move To Bottom Thing", new LauncherMoveToBottom());
        
        SmartDashboard.putData("Drivetrain Encoder Reset", new ResetEncoders());
        SmartDashboard.putData("Drivetrain Auto Turn", new TurnToTargetUltrasonic());
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
