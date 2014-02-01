/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
//import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.testCatapult.ManualFling;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DigitalInput;
import utilclasses.Potentiometer;

/**
 *
 * @author Robotics
 */
public class TestCatapult extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    // Creates motor
    private final Talon testMotor;
    
    // Sensors
    private final Encoder catapultEncoder;
    private final DigitalInput catapultLowerSwitch; 
    private final DigitalInput catapultUpperSwitch; 
    private final Potentiometer catapultPotentiometer;   
    
    private final int potentiometerRotations = 1;
   
    // State of catapult
    private final int STOPPED = 0;
    private final int FORWARD = 1;
    private final int BACKWARD = 2;
    private int state = STOPPED;
    
    // Motor Speeds
    private final double FORWARD_SPEED = 1.0;
    private final double BACKWARD_SPEED = -0.50;
    
    public TestCatapult() {
        testMotor = new Talon(RobotMap.catapultMotor);
        catapultEncoder = new Encoder(RobotMap.catapultEncoderA, RobotMap.catapultEncoderB);
        catapultLowerSwitch = new DigitalInput(RobotMap.catapultLowerSwitch);
        catapultUpperSwitch = new DigitalInput(RobotMap.catapultUpperSwitch); 
        catapultPotentiometer = new Potentiometer(RobotMap.catapultpotentiometer, potentiometerRotations);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new ManualFling());
    }
    
    public void forward() {
        testMotor.set(FORWARD_SPEED);
    }
    
    public void backward() {
        testMotor.set(BACKWARD_SPEED);
    }
    
    public void stop() {
        testMotor.set(0.0);
    }
    
    public void setState(int newState) {
        state = newState;
    }
    
    public int getState() {
        return state;
    }
    
    public int getForward() {
        return FORWARD;
    }
    
    public int getBackward() {
        return BACKWARD;
    }
    
    public int getStopped() {
        return STOPPED;
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
        return catapultEncoder.getRate ();  
    }
}
