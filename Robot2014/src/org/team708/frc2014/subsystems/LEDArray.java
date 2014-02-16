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
    
    /**
     * constructor
     */
    public LEDArray()
    {
        leds1 = new Solenoid(RobotMap.LEDArrayA);
        waveTimer = new Timer();
        waveTimer.start();
    }
    
    /**
     * "Maintains the LED's cycling (also known as "jawn")."
     * -Connor WIllison
     */
    protected void initDefaultCommand() {
        setDefaultCommand(new CommandBase()
        {
            private boolean squareWaveValue = false;

            //initializer block (like a constructor for anonymous class)
            {
                requires(CommandBase.ledArray);
            }
            
            protected void initialize() {

            }

            /**
             * Sets the flashing based off the output of a square wave.
             */
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
    
    /**
     * Sends constant power to the LEDs.
     */
    public void setSolid()
    {
        solenoidWrite(true);
        flashing = false;
    }
    
    /**
     * Sends no power to the LEDs.
     */
    public void turnOff()
    {
        flashing = false;
        solenoidWrite(false);
    }
    
    /**
     * Sends on-off power to the LEDs.
     * @param onoffTime 
     */
    public void setFlashing(double onoffTime)
    {
        this.onoffTimeSec = onoffTime;
        flashing = true;
    }
    
    /**
     * Sets the value that the solenoid is which
     * is then translated to the state of the LEDs.
     * @param value 
     */
    private void solenoidWrite(boolean value)
    {
        leds1.set(value);
        
        //insert lines to set other leds here
    }
}
