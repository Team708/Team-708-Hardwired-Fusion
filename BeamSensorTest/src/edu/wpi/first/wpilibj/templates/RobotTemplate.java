/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    
    private Encoder encoder;
    private Jaguar motor;
    private Gamepad gamepad;
    private BeamRateSensor rateSensor;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        encoder = new AvgRateEncoder(1,2);
        
        encoder.setDistancePerPulse(1/250.0);
        encoder.start();
        
        motor = new Jaguar(2);
        
        gamepad = new Gamepad(1);
        rateSensor = new BeamRateSensor(3);


        rateSensor.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }
    
    public void disabledInit()
    {
        encoder.reset();
        motor.set(0.0);
        rateSensor.resetCounter();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
//        SmartDashboard.putBoolean("Beam",rateSensor.getBeamState());
        double rev_rate = encoder.getRate();
//        double rev_rate = rateSensor.getRate();
        
        SmartDashboard.putNumber("EncoderRate RPM",rev_rate * 60);
//        SmartDashboard.putNumber("Counts",encoder.get());
        SmartDashboard.putNumber("Beam RPS",rateSensor.getRate());

        
        gamepad.sendAxesToDashboard();
        
        motor.set(gamepad.getAxis(Gamepad.leftStick_X));
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
