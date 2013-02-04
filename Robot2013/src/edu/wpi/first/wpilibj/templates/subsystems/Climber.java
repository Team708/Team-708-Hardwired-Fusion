/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 * @author Debbie Musselman
 */
public class Climber extends PIDSubsystem{
    
    private static final double kp = 0;
    private static final double ki = 0;
    private static final double kd = 0;
    public static final int extendedArm = 0;
    public static final double tolerance = 50;
    
    private Jaguar motor;
    private Encoder encoder;
    
    public Climber(int encoderChannelA, int encoderChannelB, int motorChannel){
        super("Climber", kp, ki, kd);
        
        motor = new Jaguar(motorChannel);
        encoder = new Encoder(encoderChannelA, encoderChannelB);
        encoder.reset();
        setAbsoluteTolerance(tolerance);
        setInputRange(0, extendedArm);
    }
    
    public void extend()
    {
        setSetpoint(extendedArm);
        enable();
    }
    
    public void retract()
    {
        setSetpoint(0);
        enable();
    }

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
}
