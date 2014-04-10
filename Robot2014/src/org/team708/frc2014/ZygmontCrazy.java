/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team708.frc2014;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team708.frc2014.commands.CommandBase;
import org.team708.frc2014.commands.autonomous.OneHotGoalShot;
import org.team708.frc2014.commands.autonomous.ThreeBallYoloSwagShot;
import org.team708.frc2014.commands.autonomous.TwoBallYoloSwagShot;
import org.team708.frc2014.commands.autonomous.YoloSwagShot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class ZygmontCrazy extends IterativeRobot {

    Command autonomousCommand;                      //the current autonomous command
    Compressor compressor;                          //compressor for the pneumatics
    SendableChooser autoChooser;                    //creates choose object on SmartDashboard
    Timer statsTimer;                               //timer used for Smart Dash statistics
    private final double sendStatsIntervalSec = .5; //number of seconds between sending stats to SmartDash
    
    private final boolean debug = true; // Set to false if not using SmartDashboard

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        //initialize timer periodic debug messages
        statsTimer = new Timer();
        statsTimer.start();
        
        // Initialises and starts the compressor
        compressor = new Compressor(RobotMap.compressorPressureSwitch, RobotMap.compressorSpike);
        compressor.start();
        
        // Initialises the autonomous selection on SmartDashboard
        autoChooser = new SendableChooser();
        queueAutonomousCommands();
        
        // Initialize all subsystems
        CommandBase.init();
    }

    public void autonomousInit() {
        // instantiate the command used for the autonomous period
        autonomousCommand = ((Command)autoChooser.getSelected());
        // schedule the autonomous command (example)
        autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        
        sendStats();
    }

    public void teleopInit() {
	// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        
        sendStats();
    }
    
    public void disabledPeriodic()
    {
        CommandBase.visionProcessor.processData();
        sendStats();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    /**
     * Send debug/driver display statistics to SmartDashboard.
     */
    private void sendStats()
    {
        if (debug) {
            if(statsTimer.get() >= sendStatsIntervalSec)
            {
                statsTimer.reset();

                // Various debug information
                CommandBase.visionProcessor.sendToDash();
                CommandBase.drivetrain.sendToDash();
                CommandBase.intake.sendToDash();
                CommandBase.launcher.sendToDash();
            }
        }
    }
    
    // Adds options for autonomous modes
    private void queueAutonomousCommands() {
        autoChooser.addDefault("One Ball YOLOSWAG", new YoloSwagShot());
        autoChooser.addObject("Two Ball YOLOSWAG", new TwoBallYoloSwagShot());
        autoChooser.addObject("Three Ball YOLOSWAG", new ThreeBallYoloSwagShot());
        SmartDashboard.putData("Autonomous Selector", autoChooser);
    }
}
