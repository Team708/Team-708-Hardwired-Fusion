/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Debbie Musselman + Connor Willison
 */
public class Climber extends PIDSubsystem
{   
    private static final double kp = 0;
    private static final double ki = 0;
    private static final double kd = 0;
    public static final double tolerance = 50;
    
    //needs to be calibrated
//    private static final double inchesPerCount = 2.0;
    
    //length in inches of one stroke on the climber
    public static final double extendedArmCounts = 1300;
    public static final double HOME_COUNTS = 300;

    private Jaguar motor;
    private Encoder encoder;
    private DigitalInput topSwitch,bottomSwitch;
    
    public Climber(String name,int encoderChannelA, int encoderChannelB, int motorChannel,
            int topSwitchChan, int bottomSwitchChan,boolean flipEncoder){
        super(name + " Climber", kp, ki, kd);
        motor = new Jaguar(motorChannel);
        encoder = new Encoder(encoderChannelA, encoderChannelB,flipEncoder,Encoder.EncodingType.k2X);
//        encoder.setDistancePerPulse(inchesPerCount);
        encoder.start();
        
        topSwitch = new DigitalInput(topSwitchChan);
        bottomSwitch = new DigitalInput(bottomSwitchChan);
//        setAbsoluteTolerance(tolerance);
//        setInputRange(0, extendedArm);
    }
    
    public void setMotorSpeed(double pwm)
    {
        motor.set(pwm);
    }
    
    public void extend()
    {
        setSetpoint(extendedArmCounts);
        enable();
//        motor.set(extendSpeed);
    }
    
    public void retract()
    {
        setSetpoint(0.0);
        enable();
//        motor.set(-extendSpeed);
    }
    
    public boolean onTarget()
    {
        return this.onTarget();
    }
    
    public void stop()
    {
        motor.set(0.0);
        disable();
    }
    
    public boolean isExtended()
    {
        return topSwitch.get();
    }
    
    public boolean isRetracted()
    {
        return bottomSwitch.get();
    }
    
    public int getEncoderCounts()
    {
        return encoder.get();
    }
    
//    public double getEncoderReadingInches()
//    {
//        return encoder.getDistance();
//    }
    
//    public int getTopDifference()
//    {
//        return extendedArm - encoder.get();
//    }

    protected double returnPIDInput() {
        return encoder.get();
    }

    protected void usePIDOutput(double d) {
        motor.set(d);
    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public void resetEncoder()
    {
        encoder.reset();
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void sendToDash()
    {
        SmartDashboard.putNumber(this.getName() + " EncCounts",encoder.get());
//        SmartDashboard.putNumber(this.getName() + " EncInches",encoder.getDistance());
        SmartDashboard.putBoolean(this.getName() + " TopSwitch",topSwitch.get());
        SmartDashboard.putBoolean(this.getName() + " BottomSwitch",bottomSwitch.get());
    }
}
