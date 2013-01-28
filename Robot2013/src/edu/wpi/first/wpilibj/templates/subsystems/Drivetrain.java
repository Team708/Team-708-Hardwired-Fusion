/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import Team708Classes.EncoderRotationSensor;
import Team708Classes.RotationSensor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.ArcadeDrive;

/**
 *
 * @author Vince & Nam
 */
public class Drivetrain extends PIDSubsystem {

    private static final double Kp = 0.0;
    private static final double Ki = 0.0;
    private static final double Kd = 0.0;
    
    private static final double wheelDiameterIn = 6.0;
    private static final double gearMultiplier = 7.5;
    private static final double encoderCountsPerRotation = 360.0;
    private static final double distancePerCount = wheelDiameterIn * Math.PI /
            (gearMultiplier * encoderCountsPerRotation);
    private static final double robotDiameterIn = 26.5;
    
    private static final boolean HIGH_GEAR = true;
    private static final boolean DEFAULT_GEAR = HIGH_GEAR;
    
    private RobotDrive robotDrive;
    private Encoder leftEnc,rightEnc;
    private RotationSensor rotationSensor;
    private Solenoid shifter;

    // Initialize your subsystem here
    public Drivetrain() {
        super("DriveTrain", Kp, Ki, Kd);
        robotDrive = new RobotDrive(RobotMap.leftJag, RobotMap.rightJag);
        
        leftEnc = new Encoder(RobotMap.leftEncoderA,RobotMap.leftEncoderB);
        rightEnc = new Encoder(RobotMap.rightEncoderA,RobotMap.rightEncoderB);
        
        leftEnc.setDistancePerPulse(distancePerCount);
        rightEnc.setDistancePerPulse(distancePerCount);
        
        leftEnc.start();
        rightEnc.start();
        
        rotationSensor = new EncoderRotationSensor(leftEnc,rightEnc,robotDiameterIn);
        
        shifter = new Solenoid(RobotMap.shiftingSolenoid);
        shifter.set(DEFAULT_GEAR);

        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
    
    /**
     * Returns the robot's current heading in degrees.
     * @return 
     */
    public double getAngle()
    {
        return rotationSensor.getAngle();
    }
    
    public void resetAngle()
    {
        rotationSensor.reset();
    }
    
    public void resetEncoders()
    {
        leftEnc.reset();
        rightEnc.reset();
    }
    
    public double getLeftEncDistance()
    {
        return leftEnc.getDistance();
    }
    
    public double getRightEncDistance()
    {
        return rightEnc.getDistance();
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ArcadeDrive());
    }
    
    public void shiftHigh()
    {
        shifter.set(HIGH_GEAR);
    }
    
    public void shiftLow()
    {
        shifter.set(!HIGH_GEAR);
    }
    
    public boolean getDefaultGear()
    {
        return DEFAULT_GEAR;
    }
    
    public boolean getGear()
    {
        return shifter.get();
    }
    
    public void changeGear()
    {
        if(shifter.get() == HIGH_GEAR)
        {
            shiftLow();
        }else
        {
            shiftHigh();
        }
    }
    
    protected double returnPIDInput() {
        return rotationSensor.getAngle();
    }
    
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
        
    }
    
    public void arcadeDrive(double move, double rotate)
    {
        robotDrive.arcadeDrive(move,rotate);
    }
    public void tankDrive(double leftStick, double rightStick)
    {
        robotDrive.tankDrive(leftStick, rightStick);
    }
    
    public void sendToDash()
    {
        SmartDashboard.putNumber("LeftEncCounts",leftEnc.get());
        SmartDashboard.putNumber("RightEncCounts",rightEnc.get());
        SmartDashboard.putNumber("LeftEncDist",leftEnc.getDistance());
        SmartDashboard.putNumber("RightEncDist",rightEnc.getDistance());
        
        SmartDashboard.putNumber("RobotAngle",rotationSensor.getAngle());
    }
}
