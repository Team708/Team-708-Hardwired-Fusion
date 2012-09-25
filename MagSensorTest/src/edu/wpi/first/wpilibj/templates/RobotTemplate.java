/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    
    DigitalInput sensor;
    Jaguar motor;
    Timer timer;
    private double sampleInterval = 10.0;
    private double rpms = 0.0;
    private int numRevs = 0;
    private boolean hasRotated = true;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
          sensor = new DigitalInput(1);
          motor = new Jaguar(2);
          timer = new Timer();
    }
    
    public void disabledInit(){
        motor.set(0.0);
    }
    
    public void disabledPeriodic()
    {
        send();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    public void teleopInit()
    {
        motor.set(.2);
        timer.start();
        timer.reset();
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        send();
        
        //count revolutions
        if(sensor.get())
        {
            if(hasRotated)
            {
                numRevs++;
                hasRotated = false;
            }
            
        }else
        {
            hasRotated = true;
        }
        
        //reset count after interval passes
        if(timer.get() > sampleInterval)
        {
            rpms = numRevs / timer.get() * 60.0;
            timer.reset();
            numRevs = 0;
        }
        
    }
    
    private void send(){
        SmartDashboard.putBoolean("Reed Switch",sensor.get());
        SmartDashboard.putDouble("Motor PWM",motor.get());
        SmartDashboard.putDouble("RPMs",rpms);
        SmartDashboard.putInt("Revs", (int)numRevs);
        SmartDashboard.putDouble("Sample Time",timer.get());
    }
    
}
