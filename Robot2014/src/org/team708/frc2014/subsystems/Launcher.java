package org.team708.frc2014.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team708.frc2014.RobotMap;
import org.team708.frc2014.commands.launcher.LauncherManualControl;
import org.team708.util.Math708;

/**
 * The launcher for the 2014 robot is a "Jordan" style elevator catapult. A claw
 * holds the ball and is pulled upward by a chain. When the ball reaches the
 * top, the claw launches it at the goal. Two photogate sensors and one optical
 * encoder are used to keep track of the launcher's position.
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
    private final DigitalInput launcherLowerGate, launcherUpperGate;

    //encoder count values for different shot heights
    public static final int REGULAR_SHOT_ENC_COUNTS = 900;
    public static final int TRUSS_SHOT_ENC_COUNTS = 600;
    //ranges for different manipulator heights
    private final int UPPER_RANGE = 0;      //above top photogate
    private final int MIDDLE_RANGE = 1;     //between photogates
    private final int LOWER_RANGE = 2;        //below bottom photogate
    private final int UNKNOWN_POSITION = 3; //unsure of where manipulator is (need to do home command)
    private int currentPosition = UNKNOWN_POSITION;

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
        
        launcherLowerGate = new DigitalInput(RobotMap.launcherLowerSwitch); //This is a photogate
        launcherUpperGate = new DigitalInput(RobotMap.launcherUpperSwitch); //This is a photogate
//        launcherPotentiometer = new Potentiometer(RobotMap.launcherPotentiometer, potentiometerRotations);
    }

    public void initDefaultCommand() {
        //by default, the operator can control the launcher with the joystick
        setDefaultCommand(new LauncherManualControl());
    }

    public void goUpward() {
        move(UPWARD_SPEED);
    }

    public void goDownward() {
        move(DOWNWARD_SPEED);
    }
    
    /**
     * Same as goUpward() but can reduce speed using a positive
     * scalar.
     * @param scalar 
     */
    public void goUpward(double scalar)
    {
        move(UPWARD_SPEED * Math708.makeWithin(scalar,0.0,1.0)); //cannot increase or reverse speed with scalar
    }

    /**
     * Same as goDownward() but can reduce speed using a positive
     * scalar.
     * @param scalar 
     */
    public void goDownward(double scalar) {
        move(DOWNWARD_SPEED * Math708.makeWithin(scalar,0.0,1.0));
    }
    
    public void stop() {
        move(0.0);
    }

    public void manualControl(double axis) {
        move(axis);
    }

    // Returns the upper bound switch
    public boolean getUpperGate() {
        // Reverses the state of the switch because it reads "true" when not tripped
        return !launcherUpperGate.get();
    }

    // Returns the lower bound switch
    public boolean getLowerGate() {
        // Reverses the state of the switch because it reads "true" when not tripped
        return !launcherLowerGate.get();
    }
    
    // Gets the distance that the encoder reads
    public int getCounts() {
        return launcherEncoder.get();
    }

    // Resets encoder values to zero
    public void resetEncoder() {
        launcherEncoder.reset();
    }

    /**
     * Attempt to move the launcher at the desired speed. The states of the
     * photogate sensors, the launcher's current position, and the intended
     * speed are examined. An ensured safe speed is returned. I.E. zero is
     * returned if one tries to accelerate past the boundaries.
     *
     * @param newSpeed
     */
    private void move(double newSpeed) {
        double speed;

        switch (currentPosition) {
            case UPPER_RANGE:
                if (isUpward(newSpeed)) {
                    speed = 0.0; //stop launcher
                } else if (isDownward(newSpeed)) {
                    //passing boundary into middle range
                    if (getUpperGate()) {
                        currentPosition = MIDDLE_RANGE;
                    }
                    
                    speed = newSpeed;
                }
            case MIDDLE_RANGE:
                if (isUpward(newSpeed)) {
                    //passing boundary into upper range
                    if (getUpperGate()) {
                        currentPosition = UPPER_RANGE;
                    }

                } else if (isDownward(newSpeed)) {
                    //passing boundary into lower range
                    if (getLowerGate()) {
                        currentPosition = LOWER_RANGE;
                    }
                }
                //any speed is legal in the middle range
                speed = newSpeed;
            case LOWER_RANGE:
                if (isDownward(newSpeed)) {
                    speed = 0.0; //stop launcher
                } else if (isUpward(newSpeed)) {
                    //passing boundary into middle range
                    if (getLowerGate()) {
                        currentPosition = MIDDLE_RANGE;
                    }
                    speed = newSpeed;
                }

            default: //or UNKNOWN_POSITION
                /*
                 * A homing routine must be performed. Since we do not know
                 * Where the manipulator is, we cannot restrict its speed.
                 */
                speed = newSpeed;
                break;
        }
        
        //set motor speeds
        launcherMotor1.set(speed);
        launcherMotor2.set(speed);

    }

    /*
     * Checks if the given speed will cause the launcher to go upward.
     */
    private boolean isUpward(double speed) {
        return Math708.AreSameSign(speed, UPWARD_SPEED);
    }

    /*
     * Checks if the given speed will cause the launcher to go downward.
     */
    private boolean isDownward(double speed) {
        return Math708.AreSameSign(speed, DOWNWARD_SPEED);
    }
    
    public void sendToDash() {
        SmartDashboard.putNumber("Launcher Encoder", launcherEncoder.getDistance());
        SmartDashboard.putBoolean("Lower Switch", this.getLowerGate());
        SmartDashboard.putBoolean("Upper Switch", this.getUpperGate());
        SmartDashboard.putString("Launcher Position", (currentPosition == LOWER_RANGE) ? "Lower Limit"
                : (currentPosition == MIDDLE_RANGE) ? "Between Limits"
                : (currentPosition == UPPER_RANGE) ? "Upper Limit" : "Unknown");
    }
}
