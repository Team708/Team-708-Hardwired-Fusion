/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.templates.commands.testCatapult.Fling;

/**
 *
 * @author Robotics
 */
public class TestCatapult extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    // Creates motor
    private final Jaguar testMotor;
    
    // State of catapult
    private int state = 0;
    
    public TestCatapult() {
        testMotor = new Jaguar(1);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new Fling());
    }
    
    public void forward() {
        testMotor.set(1.0);
    }
    
    public void backward() {
        testMotor.set(-0.50);
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
}