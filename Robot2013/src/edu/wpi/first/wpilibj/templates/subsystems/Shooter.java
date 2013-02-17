/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.RobotMap;

/**
 *
 * @author Nam Tran and Vincent Gargiulo
 */
public class Shooter extends PIDSubsystem {

    //PID variables
    private static final double Kp = .01;
    private static final double Ki = 0.0;
    private static final double Kd = 0.0;
    private static final double Kf = 1.0;
    private static final double pidTolerancePct = 5;
    private double pidOutput = 0.0;
    //Shooter variables
    private boolean feederExtended = true;
    private Encoder shooterEncoder;
    private Victor vic1,vic2,vic3;
    private Solenoid feederSolenoid;

    // Initialize your subsystem here
    public Shooter() {
        super("Shooter", Kp, Ki, Kd);
        //initializes the different motor controllers and sensors
        shooterEncoder = new Encoder(RobotMap.shooterEncoderA, RobotMap.shooterEncoderB,true,Encoder.EncodingType.k1X);
        vic1 = new Victor(RobotMap.shooterVictor1);
        vic2 = new Victor(RobotMap.shooterVictor2);
        vic3 = new Victor(RobotMap.shooterVictor3);
        feederSolenoid = new Solenoid(RobotMap.feederSolenoid);
        shooterEncoder.setDistancePerPulse(1 / 250.0);
        
        shooterEncoder.start();
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
        
        //set pid constants
        this.getPIDController().setPID(Kp, Ki, Kd, Kf);
        
        //Sets the pid tolerance
        this.setPercentTolerance(pidTolerancePct);
        //pid Input Range
        this.setInputRange(-9000, 9000);
        
        LiveWindow.addActuator("Shooter","PIDSubsystem Controller",getPIDController());
        LiveWindow.addSensor("Shooter","Encoder", shooterEncoder);
        
    }
    
    public void setSpeed(double wheelSpeedRPMs) {
        if (wheelSpeedRPMs == 0)
        {
            this.setSetpoint(0.0);
            //Disables PID
            this.disable();
            //Shut off Motor
            vic1.set(0.0);
            vic2.set(0.0);
            vic3.set(0.0);
        }
        else
        {
            this.setSetpoint(wheelSpeedRPMs);
            //Enables PID
            this.enable();
        }      
    }
    
    public void setPWM(double speed)
    {
        if(this.getPIDController().isEnable()) disable();
        
        vic1.set(speed);
        vic2.set(speed);
        vic3.set(speed);
    }
    
    public void extendSolenoid()
    {
        feederSolenoid.set(feederExtended);
    }
    
    public boolean isAtSpeed()
    {
       return this.onTarget();
    }
    
    public void retractSolenoid()
    {
        feederSolenoid.set(!feederExtended);
    }
    
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
        return shooterEncoder.getRate() * 60;
    }
    
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
        vic1.set(output);
        vic2.set(output);
        vic3.set(output);
        
        pidOutput = output;
//        vic1.set(1.0);
//        vic2.set(1.0);
//        vic3.set(1.0);
    }  
    
    public void sendToDash()
    {
        SmartDashboard.putNumber("ShooterRPM",returnPIDInput());
        SmartDashboard.putNumber("ShooterPIDOutput",pidOutput);
        SmartDashboard.putBoolean("FeederPiston", feederSolenoid.get());
    }
}
