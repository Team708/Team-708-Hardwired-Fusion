package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.OI;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.subsystems.Climber;
import edu.wpi.first.wpilibj.templates.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.templates.subsystems.Shooter;
import edu.wpi.first.wpilibj.templates.subsystems.VisionProcessor;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 * @author Author
 */
public abstract class CommandBase extends Command {

    public static OI oi;
    // Create a single static instance of all of your subsystems
    public static Drivetrain drivetrain = new Drivetrain();
    public static VisionProcessor visionProcessor = new VisionProcessor();
    public static Shooter shooter = new Shooter();
    public static Climber leftArm = new Climber("Left",RobotMap.leftArmEncoderA, RobotMap.leftArmEncoderB,
            RobotMap.leftArmJag, RobotMap.leftArmTopSwitch,RobotMap.leftArmBottomSwitch,true,false);
    public static Climber rightArm = new Climber("Right",RobotMap.rightArmEncoderA, RobotMap.rightArmEncoderB,
            RobotMap.rightArmJag,RobotMap.rightArmTopSwitch,RobotMap.rightArmBottomSwitch,false,true);

    public static void init() {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();

        // Show what command your subsystem is running on the SmartDashboard
        SmartDashboard.putData(drivetrain);
        SmartDashboard.putData(visionProcessor);
        SmartDashboard.putData(shooter);
        SmartDashboard.putData(leftArm);
        SmartDashboard.putData(rightArm);
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
