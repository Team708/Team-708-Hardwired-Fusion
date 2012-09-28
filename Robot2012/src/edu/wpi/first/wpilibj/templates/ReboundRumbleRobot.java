/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import bridge.Arm;
import bridge.Balancer;
import shooter.Shooter;
import harvester.Harvester;
import edu.wpi.first.wpilibj.IterativeRobot;
import harvester.Hold;
import io.DriverStation708;
import scripting.CommandFactory;
import scripting.Configuration;
import scripting.ScriptEngine;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class ReboundRumbleRobot extends IterativeRobot {


    private ScriptEngine engine;
    private Drivetrain drivetrain;
    private Harvester harvester;
    private Shooter shooter;
    private Hold hold;
    private Manipulator manipulator;
    private DriverStation708 driverStation;
    private Configuration configuration;
    private Balancer balancer;
    private Arm arm;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit(){
        drivetrain = new Drivetrain();
        hold = new Hold();
        arm = new Arm(drivetrain);
        harvester = new Harvester(hold);
        shooter = new Shooter(hold);
        manipulator = new Manipulator(shooter,harvester,hold,arm);
        engine = new ScriptEngine(new CommandFactory(drivetrain,shooter,arm));
        balancer = new Balancer(drivetrain);
        driverStation = new DriverStation708(engine,drivetrain,harvester,hold,shooter,arm);
        configuration = Configuration.getInstance();
        configuration.readConfig();

        //start harvester thread
        manipulator.enable();
    }

    public void disabledPeriodic(){
        driverStation.input();
        driverStation.output();
    }

    public void autonomousInit(){
        configuration.readConfig();

        engine.abort();
        engine.setReady();
        engine.initAutonomous();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

        //ds i/o
        driverStation.input();
        driverStation.output();

        //execute scripts
        engine.run();
    }

    public void teleopInit(){
        configuration.readConfig();
        //stop leftover autonomous scripts
//        engine.abort();
        engine.readNamesFile();
//        engine.setReady();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        //ds i/o
        driverStation.input();
        driverStation.output();

        //execute scripts
//        engine.run();
    }


    
}
