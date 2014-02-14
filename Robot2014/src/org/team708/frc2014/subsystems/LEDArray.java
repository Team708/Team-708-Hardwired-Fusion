/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team708.frc2014.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team708.frc2014.RobotMap;
import org.team708.frc2014.commands.CommandBase;
import org.team708.util.Math708;

/**
 * An array of LEDs that can be configured to blink at a certain
 * period. The LEDs are powered off the solenoid breakout, so they
 * look like solenoid. 
 * @author Connor Willison
 */
public class LEDArray extends Subsystem{

    private Solenoid leds1;
    
    private Timer waveTimer;
    private double onoffTimeSec = 1.0;
    private boolean flashing = false;
    
    public LEDArray()
    {
        leds1 = new Solenoid(RobotMap.LEDArrayA);
        waveTimer = new Timer();
        waveTimer.start();
    }
    
    protected void initDefaultCommand() {
        //maintain the LED's cycling (also known as "jawn")
        setDefaultCommand(new CommandBase()
        {
            private boolean squareWaveValue = false;

            protected void initialize() {
            }

            protected void execute() {
                if(flashing)
                {
                    squareWaveValue = Math708.squareWave(waveTimer,onoffTimeSec,squareWaveValue);
                    solenoidWrite(squareWaveValue);
                }
            }

            protected boolean isFinished() {
                return false;
            }

            protected void end() {
            }

            protected void interrupted() {
            }
            
        });
    }
    
    public void setSolid()
    {
        solenoidWrite(true);
        flashing = false;
    }
    
    public void turnOff()
    {
        flashing = false;
        solenoidWrite(false);
    }
    
    public void setFlashing(double onoffTime)
    {
        this.onoffTimeSec = onoffTime;
        flashing = true;
    }
    
    private void solenoidWrite(boolean value)
    {
        leds1.set(value);
        
        //insert lines to set other leds here
    }
}
