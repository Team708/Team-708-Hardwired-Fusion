package org.team708.frc2014.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team708.frc2014.RobotMap;
import org.team708.frc2014.commands.launcher.LauncherManualControl;

/**
 *
 * @author Kyumin Lee, Nam Tran, Pat Walls, Jillan Wang, Connor Willison
 */
public class Launcher extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    // Creates motor controllers
    private final SpeedController launcherMotor1, launcherMotor2;
    
    // Sensors
    private final Encoder launcherEncoder;
    private final DigitalInput launcherLowerSwitch, launcherUpperSwitch;
    
    public final int REGULAR_SHOT_ENC_COUNTS = 900;
    public final int TRUSS_SHOT_ENC_COUNTS = 600;
    
    //ranges for different manipulator heights
    private final int UPPER_RANGE = 0;
    private final int MIDDLE_RANGE = 1;
    private final int LOW_RANGE = 2;
    private int currentPosition = MIDDLE_RANGE;
    
    // Motor Speeds
    private final double UPWARD_SPEED = 1.0;
    private final double DOWNWARD_SPEED = -0.5;
    
    public Launcher() {
        // Creates motors
        launcherMotor1 = new Talon(RobotMap.launcherMotor1);
        launcherMotor2 = new Talon(RobotMap.launcherMotor2);
        
        // Creates sensors
        launcherEncoder = new Encoder(RobotMap.launcherEncoderA, RobotMap.launcherEncoderB);
        launcherEncoder.start();
        
        launcherLowerSwitch = new DigitalInput(RobotMap.launcherLowerSwitch); //This is a photogate
        launcherUpperSwitch = new DigitalInput(RobotMap.launcherUpperSwitch); //This is a photogate
//        launcherPotentiometer = new Potentiometer(RobotMap.launcherPotentiometer, potentiometerRotations);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
//        setDefaultCommand(new ManualFling());
        setDefaultCommand(new LauncherManualControl());
    }
    
    public void goUpward() {
        launcherMotor1.set(UPWARD_SPEED);
        launcherMotor2.set(UPWARD_SPEED);
    }
    
    public void goDownward() {
        launcherMotor1.set(DOWNWARD_SPEED);
        launcherMotor2.set(DOWNWARD_SPEED);
    }
    
    public void stop() {
        launcherMotor1.set(0.0);
        launcherMotor2.set(0.0);
    }
    
    public void manualControl(double axis) {
        launcherMotor1.set(axis);
        launcherMotor2.set(axis);
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
    
    // Gets the distance that the encoder reads
    public double getDistance() {
        return launcherEncoder.getDistance();
    }
    
    // Resets encoder values to zero
    public void resetEncoder() {
        launcherEncoder.reset();
    }
    
   public void sendToDash() {
       SmartDashboard.putNumber("Launcher Encoder", launcherEncoder.getDistance());
       SmartDashboard.putBoolean("Lower Switch", this.getLowerSwitch());
       SmartDashboard.putBoolean("Upper Switch", this.getUpperSwitch());
   }
}
