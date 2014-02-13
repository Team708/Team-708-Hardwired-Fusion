package org.team708.frc2014.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team708.frc2014.RobotMap;
import org.team708.frc2014.commands.launcher.JoystickFling;
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
//    private final Potentiometer launcherPotentiometer;   
    
//    private final int potentiometerRotations = 1;
   
    // Arm Movement Constants
    private static final double MIN_ARM_ANGLE = 0.0;
    private static final double MAX_ARM_ANGLE = 45.0;
    private static final double MAX_DISTANCE = 28.0;
    
    // State of launcher
    private final int STOPPED = 0;
    private final int FORWARD = 1;
    private final int BACKWARD = 2;
    private int state = STOPPED;
    
    // Motor Speeds
    private final double FORWARD_SPEED = 1.0;
    private final double BACKWARD_SPEED = -0.5;
    
    public Launcher() {
        // Creates motors
        launcherMotor1 = new Talon(RobotMap.launcherMotor1);
        launcherMotor2 = new Talon(RobotMap.launcherMotor2);
        
        // Creates sensors
        launcherEncoder = new Encoder(RobotMap.launcherEncoderA, RobotMap.launcherEncoderB);
        launcherLowerSwitch = new DigitalInput(RobotMap.launcherLowerSwitch); //This is a photogate
        launcherUpperSwitch = new DigitalInput(RobotMap.launcherUpperSwitch); //This is a photogate
//        launcherPotentiometer = new Potentiometer(RobotMap.launcherPotentiometer, potentiometerRotations);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
//        setDefaultCommand(new ManualFling());
        setDefaultCommand(new JoystickFling());
    }
    
    public void goForward() {
        launcherMotor1.set(FORWARD_SPEED);
        launcherMotor2.set(FORWARD_SPEED);
    }
    
    public void goBackward() {
        launcherMotor1.set(BACKWARD_SPEED);
        launcherMotor2.set(BACKWARD_SPEED);
    }
    
    public void stop() {
        launcherMotor1.set(0.0);
        launcherMotor2.set(0.0);
    }
    
    public void joystickFling(double axis) {
        launcherMotor1.set(-axis);
        launcherMotor2.set(-axis);
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
    
    public boolean getLowerBound () {
        //Checks for the encoders reading that the distance is below the min
        boolean belowMinDistance = (launcherEncoder.getDistance() <= -MAX_DISTANCE);
        //Returns true if either the photogate or previous statement trips
        return (this.getLowerSwitch() || belowMinDistance);
    } 
    
    public boolean getUpperBound () {
        //Checks for the encoders reading that the distance is past the max
        boolean pastMaxDistance = (launcherEncoder.getDistance() >= MAX_DISTANCE);
        //Returns true if either the photogate or previous statement trips
        return (this.getUpperSwitch() || pastMaxDistance); 
    }
    
    // Returns the upper bound switch
    public boolean getUpperSwitch() {
        // Reverses the state of the switch because it reads "true" when not tripped
        return !launcherUpperSwitch.get();
    }
    
    // Returns the lower bound switch
    public boolean getLowerSwitch() {
        // Reverses the state of the switch because it reads "true" when not tripped
        return !launcherLowerSwitch.get();
    }
    
//    public double getLauncherAngle () {
//        return launcherPotentiometer.getAngle();
//    } 
    
    // Gets the rate of the encoder
    public double getLauncherSpeed () {
        return launcherEncoder.getRate();  
    }
    
    // Gets the distance that the encoder reads
    public double getDistance() {
        return launcherEncoder.getDistance();
    }
    
    // Getter for the maximum distance for the launcher to move
    public double getMaxDistance() {
        return MAX_DISTANCE;
    }
    
    // Resets encoder values to zero
    public void resetEncoder() {
        launcherEncoder.reset();
    }
    
   public void sendToDash() {
       SmartDashboard.putNumber("Launcher Encoder", launcherEncoder.getDistance());
       SmartDashboard.putBoolean("Lower Switch", this.getLowerSwitch());
       SmartDashboard.putBoolean("Upper Switch", this.getUpperSwitch());
       SmartDashboard.putBoolean("Upper Bound", this.getUpperBound());
       SmartDashboard.putBoolean("Lower Bound", this.getLowerBound());
       SmartDashboard.putNumber("Launcher Mode", state);
   }
}
