/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.Climbing.ManualClimb;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 *
 * @author Debbie Musselman + Connor Willison
 */
public class Climber extends Subsystem
//extends PIDSubsystem
{   
//    private static final double kp = 0;
//    private static final double ki = 0;
//    private static final double kd = 0;
//    public static final double tolerance = 50;
    
    //needs to be calibrated
//    private static final double inchesPerCount = 2.0;
    
    //length in inches of one stroke on the climber
    public static final int EXTENDED_COUNTS = 1280;
    
    //changed due to encoder malfunction
//    public static final int HOME_COUNTS = 350;
    public static final int HOME_COUNTS = 230;
    
    private static final DoubleSolenoid.Value LIFTER_EXTENDED = DoubleSolenoid.Value.kReverse;
    private static final DoubleSolenoid.Value LIFTER_RETRACTED = DoubleSolenoid.Value.kForward;
    
    private static final DoubleSolenoid.Value CLIMBER_EXTENDED = DoubleSolenoid.Value.kForward;
    private static final DoubleSolenoid.Value CLIMBER_RETRACTED = DoubleSolenoid.Value.kReverse;
    
    //constants for speed synchronization on right & left arms
    private static final double maxSpeedAdjustment = .15;
    private static final double maxEncoderDifference = 50;
    private static final double speedDifferenceTolerance = 10;

    private SpeedController motor;
    private Encoder encoder;
    private AnalogChannel topSwitchAnalog, bottomSwitchAnalog;
    private AnalogTrigger topSwitchTrigger, bottomSwitchTrigger;
    private static DoubleSolenoid liftPiston = new DoubleSolenoid(RobotMap.liftingSolenoidChannelA,RobotMap.liftingSolenoidChannelB);
//    private static DoubleSolenoid extendPiston = new DoubleSolenoid(RobotMap.climberExtendSolenoidChannelA,RobotMap.climberExtendSolenoidChannelB);
        
    private boolean reverseMotor;
    
    static
    {
        liftPiston.set(LIFTER_RETRACTED);
//        extendPiston.set(CLIMBER_RETRACTED);
    }
    
    public Climber(String name,int encoderChannelA, int encoderChannelB, int motorChannel,
            int topSwitchChan, int bottomSwitchChan,boolean flipEncoder,boolean reverseMotor){
//        super(name + " Climber", kp, ki, kd);
        super(name + " Climber");
        motor = new Victor(motorChannel);
        encoder = new Encoder(encoderChannelA, encoderChannelB,flipEncoder,Encoder.EncodingType.k2X);
//        encoder.setDistancePerPulse(inchesPerCount);
        encoder.start();
        
        topSwitchAnalog = new AnalogChannel(topSwitchChan);
        bottomSwitchAnalog = new AnalogChannel(bottomSwitchChan);
        topSwitchTrigger = new AnalogTrigger(topSwitchAnalog);
        topSwitchTrigger.setLimitsVoltage(2.5,2.5);
        bottomSwitchTrigger = new AnalogTrigger(bottomSwitchAnalog);
        bottomSwitchTrigger.setLimitsVoltage(2.5,2.5);
        this.reverseMotor = reverseMotor;
       
//        setAbsoluteTolerance(tolerance);
//        setInputRange(0, extendedArm);
    }
    
    public SpeedController getSpeedController()
    {
        return motor;
    }
    
    public void setMotorSpeed(double pwm)
    {
        motor.set((reverseMotor)?-pwm:pwm);
    }
    
//    public void extendArms()
//    {
////        setSetpoint(extendedArmCounts);
////        enable();
////        motor.set(extendSpeed);
//    }
//    
//    public void retractArms()
//    {
////        setSetpoint(0.0);
////        enable();
////        motor.set(-extendSpeed);
//    }
    
//    public boolean onTarget()
//    {
//        return this.onTarget();
//    }
    
    public static void extendClimber()
    {
//        extendPiston.set(CLIMBER_EXTENDED);
    }
    
    public static void liftRobot()
    {
        liftPiston.set(LIFTER_EXTENDED);
    }
    
    public static void retractClimber()
    {
//        extendPiston.set(CLIMBER_RETRACTED);
    }
    
    public static void lowerRobot()
    {
        liftPiston.set(LIFTER_RETRACTED);
    }
    
    
    public void stop()
    {
        motor.set(0.0);
    }
    
    public static void toggleExtendClimber()
    {
//        if(extendPiston.get() == DoubleSolenoid.Value.kForward)
//        {
//            extendPiston.set(DoubleSolenoid.Value.kReverse);
//        }else if(extendPiston.get() == DoubleSolenoid.Value.kReverse)
//        {
//            extendPiston.set(DoubleSolenoid.Value.kForward);
//        }
    }
    
    public static void toggleLiftRobot()
    {
        if(liftPiston.get() == DoubleSolenoid.Value.kForward)
        {
            liftPiston.set(DoubleSolenoid.Value.kReverse);
        }else if(liftPiston.get() == DoubleSolenoid.Value.kReverse)
        {
            liftPiston.set(DoubleSolenoid.Value.kForward);
        }
    }
    
    public boolean isExtended()
    {
        return topSwitchTrigger.getTriggerState();
    }
    
    public boolean isRetracted()
    {
        return bottomSwitchTrigger.getTriggerState();
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

//    protected double returnPIDInput() {
//        return encoder.get();
//    }
//
//    protected void usePIDOutput(double d) {
//        setMotorSpeed(d);
//    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public void resetEncoder()
    {
        encoder.reset();
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new ManualClimb());
    }
    
    public static double calcSpeedAdjustment() {
        int encoderDifference = CommandBase.leftArm.getEncoderCounts() - CommandBase.rightArm.getEncoderCounts();
        
        if (Math.abs(encoderDifference) > speedDifferenceTolerance) {
            double speedAdjustment = maxSpeedAdjustment * encoderDifference / maxEncoderDifference;

            speedAdjustment = Math.min(maxSpeedAdjustment, speedAdjustment);
            speedAdjustment = Math.max(-maxSpeedAdjustment, speedAdjustment);

            return speedAdjustment;
        } else {
            return 0.0;
        }
    }
    
    public void sendToDash()
    {
        SmartDashboard.putNumber(this.getName() + " EncCounts",encoder.get());
//        SmartDashboard.putNumber(this.getName() + " EncInches",encoder.getDistance());
        SmartDashboard.putBoolean(topSwitchAnalog.getChannel() + " " + 
                this.getName() + " TopSwitch",topSwitchTrigger.getTriggerState());
        SmartDashboard.putNumber(topSwitchAnalog.getChannel() + " " + 
                this.getName() + " TopVoltage",topSwitchAnalog.getVoltage());
        SmartDashboard.putBoolean(bottomSwitchAnalog.getChannel() + " " + 
                this.getName() + " BottomSwitch",bottomSwitchTrigger.getTriggerState());
        SmartDashboard.putNumber(bottomSwitchAnalog.getChannel() + " " + 
                this.getName() + " BottomVoltage",bottomSwitchAnalog.getVoltage());
    }
}
