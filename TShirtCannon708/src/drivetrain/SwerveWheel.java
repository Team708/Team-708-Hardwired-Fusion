/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drivetrain;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

/**
 * This class represents a swerve drive wheel, which
 * is powered independently and can rotate 180 degrees.
 * 
 * NOTE: This will not work as of yet.
 * The wheel can only turn 180 degrees.
 * The remaining 180 degrees of movement is
 * accomplished through reversing the 
 * direction of the motor. This logic
 * must be implemented or else the robot
 * will break itself.
 * @author Connor
 */
public class SwerveWheel {
    
    private Relay headingMotor;
    private SpeedController speedMotor;
    
    private HeadingPotentiometer pot;
    private Encoder encoder; //may replace with pid encoder to measure rpm
    
    //these values must be verified
    private final Relay.Value headingHigher = Relay.Value.kForward;
    private final Relay.Value headingLower = Relay.Value.kReverse;
    
    private double goalHeadingDeg = 0.0;
    private final double headingEpsilonDeg = 1.0; //must tune through testing
    
    public SwerveWheel(int relayChannel,int speedChannel,int potChannel,
            int encoderAChannel,int encoderBChannel,boolean reverseEnc,
            double potLowVlts,double potHighVlts)
    {
        headingMotor = new Relay(relayChannel);
        speedMotor = new Victor(speedChannel);
        pot = new HeadingPotentiometer(potLowVlts,potHighVlts,0,180,potChannel);
        encoder = new Encoder(encoderAChannel,encoderBChannel,reverseEnc);
    }
    
    /**
     * Call periodically to keep the swerve wheel's heading on
     * target.
     */
    public void update()
    {
        //handle >180 problem here
        double error = pot.getAngleDeg() - goalHeadingDeg;
        if(error > headingEpsilonDeg)
        {
            headingMotor.set(headingLower);
        }else if(error < headingEpsilonDeg)
        {
            headingMotor.set(headingHigher);
        }else
        {
            headingMotor.set(Relay.Value.kOff);
        }
    }
    
    public void setGoalHeading(double headingDeg)
    {
        goalHeadingDeg = headingDeg;
    }
}
