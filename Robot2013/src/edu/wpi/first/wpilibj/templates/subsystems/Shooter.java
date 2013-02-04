/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;

/**
 *
 * @author Nam Tran and Vincent Gargiulo
 */
public class Shooter extends PIDSubsystem {

    //PID variables
    private static final double Kp = 0.0;
    private static final double Ki = 0.0;
    private static final double Kd = 0.0;
    private static final double pidTolerancePct = 5;
    //Shooter variables
    private boolean feederExtended = false;
    private double rpmsPerCount = 0;
    private Encoder shooterEncoder;
    private Victor shooterVictor;
    private Solenoid feederSolenoid;

    // Initialize your subsystem here
    public Shooter() {
        super("Shooter", Kp, Ki, Kd);
        //initializes the different motor controllers and sensors
        shooterEncoder = new Encoder(RobotMap.shooterEncoderA, RobotMap.shooterEncoderB);
        shooterVictor = new Victor(RobotMap.shooterVictor);
        feederSolenoid = new Solenoid(RobotMap.feederSolenoid);
        shooterEncoder.setDistancePerPulse(rpmsPerCount);
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
        
        //Sets the pid tolerance
        this.setPercentTolerance(pidTolerancePct);
        //pid Input Range
        this.setInputRange(-9000, 9000);
        
    }
    
    public void setSpeed(double wheelSpeedRPMs) {
        if (wheelSpeedRPMs == 0)
        {
            this.setSetpoint(wheelSpeedRPMs);
            //Disables PID
            this.disable();
            //Shut off Motor
            shooterVictor.set(0);
        }
        else
        {
            this.setSetpoint(wheelSpeedRPMs);
            //Enables PID
            this.enable();
        }      
    }
    
    public void extendSolenoid()
    {
        feederSolenoid.set(feederExtended);
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
        return shooterEncoder.getRate();
    }
    
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
        shooterVictor.set(output);
    }  
}
