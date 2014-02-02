/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.catapult.ManualFling;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import utilclasses.Potentiometer;

/**
 *
 * @author Robotics
 */
public class Catapult extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    // Creates motor
    private final Talon catapultMotor1;
    private final Talon catapultMotor2;
    
    // Sensors
    private final Encoder catapultEncoder;
    private final DigitalInput catapultLowerSwitch; 
    private final DigitalInput catapultUpperSwitch; 
    private final Potentiometer catapultPotentiometer;   
    
    private final int potentiometerRotations = 1;
   
    // Arm Movement Constants
    private static final double MIN_ARM_ANGLE = 0.0;
    private static final double MAX_ARM_ANGLE = 45.0;
    
    // State of catapult
    private final int STOPPED = 0;
    private final int FORWARD = 1;
    private final int BACKWARD = 2;
    private int state = STOPPED;
    
    // Motor Speeds
    private final double FORWARD_SPEED = 1.0;
    private final double BACKWARD_SPEED = 1.0;
    
    public Catapult() {
        // Creates motors
        catapultMotor1 = new Talon(RobotMap.catapultMotor1);
        catapultMotor2 = new Talon(RobotMap.catapultMotor2);
        
        // Creates sensors
        catapultEncoder = new Encoder(RobotMap.catapultEncoderA, RobotMap.catapultEncoderB);
        catapultLowerSwitch = new DigitalInput(RobotMap.catapultLowerSwitch);
        catapultUpperSwitch = new DigitalInput(RobotMap.catapultUpperSwitch); 
        catapultPotentiometer = new Potentiometer(RobotMap.catapultPotentiometer, potentiometerRotations);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new ManualFling());
    }
    
    public void goForward() {
        catapultMotor1.set(FORWARD_SPEED);
        catapultMotor2.set(-FORWARD_SPEED);
    }
    
    public void goBackward() {
        catapultMotor1.set(-BACKWARD_SPEED);
        catapultMotor2.set(BACKWARD_SPEED);
    }
    
    public void stop() {
        catapultMotor1.set(0.0);
        catapultMotor2.set(0.0);
    }
    
    public void setState(int newState) {
        state = newState;
    }
    
    public int getState() {
        return state;
    }
    
    public int Forward() {
        return FORWARD;
    }
    
    public int Backward() {
        return BACKWARD;
    }
    
    public int Stopped() {
        return STOPPED;
    }
    
    public double getMinAngle() {
        return MIN_ARM_ANGLE;
    }
    
    public double getMaxAngle() {
        return MAX_ARM_ANGLE;
    }
    
    public boolean getLowerSwitch () {
        return catapultLowerSwitch.get();
    } 
    
    public boolean getUpperSwitch () {
        return catapultUpperSwitch.get(); 
    }
    
    public double getCatapultAngle () {
        return catapultPotentiometer.getAngle();
    } 
    public double getCatapultSpeed () {
        return catapultEncoder.getRate();  
    }
}
