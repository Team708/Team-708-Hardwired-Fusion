/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

/**
 * This class represents a swerve drive wheel, which
 * is powered independently and can rotate 180 degrees.
 * The wheel's logical heading is as calculated by the
 * atan2() function and ranges from -180 - 180.
 * positive direction denotes counter clockwise rotation,
 * negative direction denotes clockwise rotation.
 * 
 * NOTE:
 * The wheel can only physically turn 180 degrees.
 * The remaining 180 degrees of movement is
 * accomplished through reversing the 
 * direction of the motor. This logic
 * must be implemented or else the robot
 * will tangle its power cables by
 * over-rotating the wheels. Another
 * failsafe involves waiting until the
 * wheel headings are on target because premature
 * movement could result in damage to
 * the drivetrain.
 * 
 * FUTURE ADDITIONS:
 * The Encoder can be used for measuring distance as
 * well as speed. Use it to allow for more accurate
 * swerve drive.
 * 
 * @author Connor
 */
public class SwerveWheel {
    
    private Relay headingMotor;
    private SpeedController speedMotor;
    
    private HeadingPotentiometer pot;
    private Encoder encoder; //may replace with pid encoder to measure rpm
    
    /*
     * These values must be verified:
     * headingLower - decreases the pot's value (degrees)
     * headingHigher - increases the pot's value (degrees)
     */
    private Relay.Value headingHigher = Relay.Value.kForward;
    private Relay.Value headingLower = Relay.Value.kReverse;
    
    private double goalHeadingDeg = 0.0;
    private final double headingEpsilonDeg = 1.0; //must tune through testing
    
    private double wheelSpeedPWM = 0.0;
    private boolean reverseDirection = false;
    private boolean onTarget = false;
    
    public SwerveWheel(int relayChannel,int speedChannel,int potChannel,
            int encoderAChannel,int encoderBChannel,boolean reverseEnc,
            double potLowVlts,double potHighVlts,Relay.Value headingLower,Relay.Value headingHigher)
    {
        headingMotor = new Relay(relayChannel);
        speedMotor = new Victor(speedChannel);
        pot = new HeadingPotentiometer(potLowVlts,potHighVlts,0,180,potChannel);
        encoder = new Encoder(encoderAChannel,encoderBChannel,reverseEnc);
        this.headingLower = headingLower;
        this.headingHigher = headingHigher;
    }
    
    /**
     * Call periodically to keep the swerve wheel's heading on
     * target. Returns whether the wheel's heading is on target.
     */
    public boolean update()
    {
        double currentHeadingDeg;
        if(reverseDirection)
        {
            //use pot-180 for input angle, since reversal of wheel
            //concerns negative side of circle
            currentHeadingDeg = pot.getAngleDeg() - 180;
        }else
        {
            currentHeadingDeg = pot.getAngleDeg();
        }
        
        //calculate error
        double error = currentHeadingDeg - goalHeadingDeg;
        
        //seek goal heading
        if(error > headingEpsilonDeg)
        {
            headingMotor.set(headingLower);
            onTarget = false;
        }else if(error < headingEpsilonDeg)
        {
            headingMotor.set(headingHigher);
            onTarget = false;
        }else
        {
            headingMotor.set(Relay.Value.kOff);
            onTarget = true;
        }
        
        if(onTarget)
        {
            //send power to wheel
            if(reverseDirection)
                speedMotor.set(-wheelSpeedPWM);
            else 
                speedMotor.set(wheelSpeedPWM);
        }else
        {
            //stop wheel
            speedMotor.set(0.0);
        }
        
        return onTarget;
    }
    
    /**
     * Set the heading to which the wheel will rotate,
     * as well as the speed once it reaches the goal
     * heading.
     * @param headingDeg -180 - 180
     * @param speedPWM -1.0 - 1.0
     */
    public void set(double speedPWM,double headingDeg)
    {
        wheelSpeedPWM = speedPWM;
        goalHeadingDeg = headingDeg;
        
        //validate heading - find coterminal angle for OOB parameters
        while(goalHeadingDeg > 180) goalHeadingDeg -= 360;
        while(goalHeadingDeg < -180) goalHeadingDeg += 360;
        
        //decide whether a wheel reversal is necessary: -180 <= x < 0
        reverseDirection = goalHeadingDeg < 0;
    }
    
    /**
     * Sets the wheel's orientation in vector form.
     * Assumes the vector is already normalized.
     * (Length -1.0 - 1.0)
     * @param xcomp - the x component of the wheel vector.
     * @param ycomp - the y component of the wheel vector.
     */
    public void setVector(double xcomp,double ycomp)
    {
        set(Math.sqrt(xcomp * xcomp + ycomp * ycomp),MathUtils.atan2(ycomp,xcomp));
    }
    
    
    /**
     * Three testing functions. Used in absence of update function.
     */
    public void decreaseHeading()
    {
        headingMotor.set(headingLower);
    }
    
    public void increaseHeading()
    {
        headingMotor.set(headingHigher);
    }
    
    public void stopRotation()
    {
        headingMotor.set(Relay.Value.kOff);
    }
    
    public void setPWM(double speed)
    {
        speedMotor.set(speed);
    }
    
    public double getHeading()
    {
        return pot.getAngleDeg();
    }
    
    public double getPotVlts()
    {
        return pot.getVoltage();
    }
}
