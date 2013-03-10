/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.RobotMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Uses a bang-bang speed controller (see ChiefDelphi post
 * by Ether) to control the shooter flywheel speed. This approach
 * is (supposedly) simpler/more stable than a PID Controller.
 * @author Nam Tran, Vincent Gargiulo, Connor Willison
 */
public class Shooter extends Subsystem{
//        PIDSubsystem {

    //PID variables
//    private static final double Kp = .01;
//    private static final double Ki = 0.0;
//    private static final double Kd = 0.0;
//    private static final double Kf = 1.0;
//    private static final double pidTolerancePct = 5;
//    private double pidOutput = 0.0;
    
    //bang-bang speed controller constants
    private static final double accelerationSpeed = 1.0;
    private static final double deccelerationSpeed = 0.0;
    private static final double toleranceRPMs = 100;
    
    //bang-bang controller variables
    private BangBangSpeedController bangBangController;
    private Timer taskTimer;
    
    //Shooter variables
    private boolean feederExtended = true;
    private Encoder shooterEncoder;
    private SpeedController vic1,vic2,vic3;
    private Solenoid feederSolenoid;

    // Initialize your subsystem here
    public Shooter() {
//        super("Shooter", Kp, Ki, Kd);
        super("Shooter");
        //initializes the different motor controllers and sensors
        shooterEncoder = new Encoder(RobotMap.shooterEncoderA, RobotMap.shooterEncoderB,true,Encoder.EncodingType.k1X);
        vic1 = new Jaguar(RobotMap.shooterVictor1);
        vic2 = new Jaguar(RobotMap.shooterVictor2);
        vic3 = new Jaguar(RobotMap.shooterVictor3);
        feederSolenoid = new Solenoid(RobotMap.feederSolenoid);
        shooterEncoder.setDistancePerPulse(1 / 250.0);
        
        shooterEncoder.start();
        
        //schedule the bang-bang controller to run every 20ms, as per the Chief Delphi post.
        bangBangController = new BangBangSpeedController();
        
        taskTimer = new Timer();
        taskTimer.scheduleAtFixedRate(bangBangController,0,20);

        
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
        
        //set pid constants
//        this.getPIDController().setPID(Kp, Ki, Kd, Kf);
//        
//        //Sets the pid tolerance
//        this.setPercentTolerance(pidTolerancePct);
//        //pid Input Range
//        this.setInputRange(-9000, 9000);
//        
//        LiveWindow.addActuator("Shooter","PIDSubsystem Controller",getPIDController());
//        LiveWindow.addSensor("Shooter","Encoder", shooterEncoder);
        
    }
    
    public void setSpeed(double wheelSpeedRPMs) {
        if (wheelSpeedRPMs == 0.0)
        {
//            this.setSetpoint(0.0);
//            //Disables PID
//            this.disable();
            //disable bang-bang
            bangBangController.disable();
            
            //Shut off Motor
            setPWM(0.0);
        }
        else
        {
//            this.setSetpoint(wheelSpeedRPMs);
//            //Enables PID
//            this.enable();
            
            //use bang-bang to control speed
            bangBangController.setSetpoint(wheelSpeedRPMs);
            bangBangController.enable();
        }      
    }
    
    public void setPWM(double speed)
    {
//        if(this.getPIDController().isEnable()) disable();
        
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
//       return this.onTarget();
        return bangBangController.isOnTarget();
    }
    
    public void retractSolenoid()
    {
        feederSolenoid.set(!feederExtended);
    }
    
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
//    protected double returnPIDInput() {
//        // Return your input value for the PID loop
//        // e.g. a sensor, like a potentiometer:
//        // yourPot.getAverageVoltage() / kYourMaxVoltage;
//        return shooterEncoder.getRate() * 60;
//    }
    
    public double getSpeedRPMs()
    {
        return shooterEncoder.getRate() * 60;
    }
    
//    protected void usePIDOutput(double output) {
//        // Use output to drive your system, like a motor
//        // e.g. yourMotor.set(output);
//        vic1.set(output);
//        vic2.set(output);
//        vic3.set(output);
//        
//        pidOutput = output;
////        vic1.set(1.0);
////        vic2.set(1.0);
////        vic3.set(1.0);
//    }  
    
    public void sendToDash()
    {
//        SmartDashboard.putNumber("ShooterRPM",returnPIDInput());
//        SmartDashboard.putNumber("ShooterPIDOutput",pidOutput);
        SmartDashboard.putNumber("ShooterRPM", getSpeedRPMs());
        SmartDashboard.putBoolean("FeederPiston", feederSolenoid.get());
    }
    
    private class BangBangSpeedController extends TimerTask
    {
        private boolean enabled = false;
        private double goalRPMs = 0.0;
        
        public void run() {
            if(enabled)
            {
                if(getSpeedRPMs() <= goalRPMs)
                {
                    setPWM(accelerationSpeed);
                }else
                {
                    setPWM(deccelerationSpeed);
                }
            }
        }
        
        public void setSetpoint(double rpms)
        {
            goalRPMs = rpms;
        }
        
        public void enable()
        {
            enabled = true;
        }
        
        public void disable()
        {
            enabled = false;
        }
        
        public boolean isEnabled()
        {
            return enabled;
        }
        
        public boolean isOnTarget()
        {
            return Math.abs(goalRPMs - getSpeedRPMs()) < toleranceRPMs;
        }
    }
}
