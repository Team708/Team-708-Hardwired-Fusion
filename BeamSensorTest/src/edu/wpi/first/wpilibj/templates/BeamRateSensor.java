/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Connor Willison
 */
public class BeamRateSensor {
    
    private static final double sampleTime = .1;
    private double encoderRate = 0.0;
    private DigitalInput beamSensor;
    private Counter counter;
    
    public BeamRateSensor(int channel)
    {
        beamSensor = new DigitalInput(channel);
        counter = new Counter(beamSensor);
    }
    
    public void start()
    {
        new ReaderThread().start();
    }
    
    /**
     * Returns the encoder's rate of speed, 
     * @return 
     */
    public synchronized double getRate()
    {
        return encoderRate;
    }
    
    private synchronized void setRate(double r)
    {
        encoderRate = r;
    }
    
    private int getPulseCount()
    {
        return counter.get();
    }
    
    public boolean getBeamState()
    {
        return beamSensor.get();
    }
    
    public void resetCounter()
    {
        counter.reset();
    }
    
    private class ReaderThread extends Thread
    {
        private Timer timer;
        
        public ReaderThread()
        {
            timer = new Timer();
        }
        
        public void run() {
            long pulseCount = 0;
            long prevPulseCount = getPulseCount();
            timer.start();
            
            while(true)
            {
                if(timer.get() >= sampleTime)
                {
                    pulseCount = getPulseCount() - prevPulseCount;
                    prevPulseCount += pulseCount;
                    
                    setRate((double)pulseCount/sampleTime);
                    timer.reset();
                }else
                {
                    //do nothing
                }
            }
        }   

}
}
