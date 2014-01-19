/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.LaunchBall;

/**
 *
 * @author Nam Tran
 */
public class Catapult extends Subsystem {
    // Put methods for controlling this subsystem here. Call these from Commands.
    
    // Constants relating to the movement of the arm
    private static final double MIN_ARM_ANGLE = 0.0;
    private static final double MAX_ARM_ANGLE = 45.0;
    private static final double ARM_MOVE_SPEED = 1.00;
    
    // Stuff based on potentiometer for finding the scaling factor in getAngle()
    private static final int MIN_POTENTIOMETER_VOLTAGE = 0;
    private static final int MAX_POTENTIOMETER_VOLTAGE = 5;
    private static final int MIN_POTENTIOMETER_ANGLE = 0;
    private static final int MAX_POTENTIOMETER_ANGLE = 360;
    
    // Sensors
    private AnalogChannel potentiometer;
    
    // Motor Controller
    private SpeedController catapultMotor;
    
    public Catapult() {
        catapultMotor = new Victor(RobotMap.catapultMotor);
        potentiometer = new AnalogChannel(RobotMap.potentiometerA, RobotMap.potentiometerB);
    }
    
    // Default Command for the catapult
    public void initDefaultCommand() {
        setDefaultCommand(new LaunchBall());
    }
    
    // Reads the potentiometer's voltage, then converts it into an angle reading
    public double getAngle() {
        // Finds the scaling factor for the voltage
        double scalingFactor = 
                (MAX_POTENTIOMETER_ANGLE - MIN_POTENTIOMETER_ANGLE) /
                (MAX_POTENTIOMETER_VOLTAGE - MIN_POTENTIOMETER_VOLTAGE); 
        
        double voltage = potentiometer.getVoltage();
        double offset = 0.0;
        
        return (scalingFactor * voltage) + offset;
    }
    
    public double getMinAngle() {
        return MIN_ARM_ANGLE;
    }
    
    public double getMaxAngle() {
        return MAX_ARM_ANGLE;
    }
    
    public double getArmMoveSpeed() {
        return ARM_MOVE_SPEED;
    }
    
    public void setMotorSpeed(double speed) {
        catapultMotor.set(speed);
    }
    
    public void stop() {
        catapultMotor.set(0.0);
    }
}