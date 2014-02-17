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
import org.team708.frc2014.commands.LEDs.LEDStates;
import org.team708.util.Math708;

/**
 * An array of LEDs that can be configured to blink at a certain period. The
 * LEDs are powered off the solenoid breakout, so they look like solenoid.
 *
 * @author Connor Willison
 */
public class LEDArray extends Subsystem {

    private Solenoid leds1;
    private Timer waveTimer;
    private double onoffTimeSec = 1.0;
    private boolean flashing = false;
    private boolean squareWaveValue = false;
    
    private double flashRate = 0.25; // Rate of flashing LEDS
    
    public final int OFF = 0;
    public final int FLASHING = 1;
    public final int SOLID = 2;
    private int state = OFF;

    /**
     * constructor
     */
    public LEDArray() {
        leds1 = new Solenoid(RobotMap.LEDArrayA);
        waveTimer = new Timer();
        waveTimer.start();
    }

    /**
     * "Maintains the LED's cycling (also known as "jawn")." -Connor WIllison
     */
    protected void initDefaultCommand() {
        setDefaultCommand(new LEDStates());
    }

    /**
     * Sends constant power to the LEDs.
     */
    public void setSolid() {
        solenoidWrite(true);
        flashing = false;
    }

    /**
     * Sends no power to the LEDs.
     */
    public void turnOff() {
        flashing = false;
        solenoidWrite(false);
    }

    /**
     * Sends on-off power to the LEDs.
     *
     * @param onoffTime
     */
    public void setFlashing(double onoffTime) {
        this.onoffTimeSec = onoffTime;
        flashing = true;
        solenoidWrite(squareWaveValue());
    }
    
    /**
     * Returns if the LEDs should be flashing.
     * @return 
     */
    public boolean isFlashing() {
        return flashing;
    }
    
    /**
     * Returns if the value is part of the square wave.
     * @return 
     */
    public boolean squareWaveValue() {
        return Math708.squareWave(waveTimer, onoffTimeSec, squareWaveValue);
    }

    /**
     * Sets the value that the solenoid is which is then translated to the state
     * of the LEDs.
     *
     * @param value
     */
    public void solenoidWrite(boolean value) {
        leds1.set(value);
    }
    
    public int getState() {
        return state;
    }
    
    public void setState(int newState) {
        state = newState;
    }
    
    public double getFlashRate() {
        return flashRate;
    }
    
    public void setFlashRate(double newRate) {
        flashRate = newRate;
    }
}
