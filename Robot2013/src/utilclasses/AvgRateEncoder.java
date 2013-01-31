/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilclasses;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;


/**
 *
 * @author Connor Willison
 */
public class AvgRateEncoder extends Encoder{
    
    private static final double sampleTime = .025;
    private double encoderRate = 0.0;
    private double distancePerPulse = 0.0;
    
    public AvgRateEncoder(int Achan,int Bchan)
    {
        super(Achan,Bchan);
    }
    
    public void start()
    {
        super.start();
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
        encoderRate = r * distancePerPulse;
    }
    
    public void setDistancePerPulse(double dist)
    {
        super.setDistancePerPulse(distancePerPulse);
        distancePerPulse = dist;
    }
    
    private int getPulseCount()
    {
        return this.get();
    }
    
    private class ReaderThread extends Thread
    {
        private Timer timer;
        private int prevPulseCount;
        
        public ReaderThread()
        {
            timer = new Timer();
        }
        
        public void run() {
            int pulseCount = getPulseCount();
            timer.start();
            
            while(true)
            {
                if(timer.get() >= sampleTime)
                {
                    pulseCount = getPulseCount() - prevPulseCount;
                    prevPulseCount = pulseCount;
                    
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
