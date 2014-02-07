package org.team708.frc2014.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team708.frc2014.RobotMap;
import org.team708.frc2014.commands.launcher.ManualFling;
import org.team708.frc2014.sensors.Potentiometer;

/**
 *
 * @author Kyumin Lee, Nam Tran, Pat Walls, Jillan Wang
 */
public class Launcher extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    // Creates motor controllers
    private final SpeedController launcherMotor1, launcherMotor2;
    
    // Sensors
    private final Encoder launcherEncoder;
    private final DigitalInput launcherLowerSwitch, launcherUpperSwitch; 
    private final Potentiometer launcherPotentiometer;   
    
    private final int potentiometerRotations = 1;
   
    // Arm Movement Constants
    private static final double MIN_ARM_ANGLE = 0.0;
    private static final double MAX_ARM_ANGLE = 45.0;
    
    // State of launcher
    private final int STOPPED = 0;
    private final int FORWARD = 1;
    private final int BACKWARD = 2;
    private int state = STOPPED;
    
    // Motor Speeds
    private final double FORWARD_SPEED = 1.0;
    private final double BACKWARD_SPEED = 1.0;
    
    public Launcher() {
        // Creates motors
        launcherMotor1 = new Talon(RobotMap.launcherMotor1);
        launcherMotor2 = new Talon(RobotMap.launcherMotor2);
        
        // Creates sensors
        launcherEncoder = new Encoder(RobotMap.launcherEncoderA, RobotMap.launcherEncoderB);
        launcherLowerSwitch = new DigitalInput(RobotMap.launcherLowerSwitch);
        launcherUpperSwitch = new DigitalInput(RobotMap.launcherUpperSwitch); 
        launcherPotentiometer = new Potentiometer(RobotMap.launcherPotentiometer, potentiometerRotations);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new ManualFling());
    }
    
    public void goForward() {
        launcherMotor1.set(FORWARD_SPEED);
        launcherMotor2.set(-FORWARD_SPEED);
    }
    
    public void goBackward() {
        launcherMotor1.set(-BACKWARD_SPEED);
        launcherMotor2.set(BACKWARD_SPEED);
    }
    
    public void stop() {
        launcherMotor1.set(0.0);
        launcherMotor2.set(0.0);
    }
    
    public void setManualMove(int state) {
        if(state == STOPPED) {
            this.state = state;
        } else {
            state = STOPPED;
        }
    }
    
    public void manualFling() {
        if (state == STOPPED) {
            this.stop();
        } else if (state == FORWARD) {
            this.goForward();
        }else if (state == BACKWARD) {
            this.goBackward();
        }
    }
    
    public void fling() {
        if (state == STOPPED) {
            this.stop();
        } else if (state == FORWARD) {
            if (!this.getUpperSwitch()) {
                this.goForward();
            } else {
                this.stop();
                state = STOPPED;
            }
        } else if (state == BACKWARD) {
            if (!this.getLowerSwitch () ) {
                this.goBackward();
            } else {
                this.stop();
                state = STOPPED;
            }
        }
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
        return launcherLowerSwitch.get();
    } 
    
    public boolean getUpperSwitch () {
        return launcherUpperSwitch.get(); 
    }
    
    public double getLauncherAngle () {
        return launcherPotentiometer.getAngle();
    } 
    public double getLauncherSpeed () {
        return launcherEncoder.getRate();  
    }
}
