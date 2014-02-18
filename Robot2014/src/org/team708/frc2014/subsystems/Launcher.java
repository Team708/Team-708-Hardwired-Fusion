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
    private final SpeedController launcherMotor;

    // Sensors
    private final Encoder launcherEncoder;
    private final DigitalInput launcherLowerGate, launcherUpperGate;

    //encoder count values for different shot heights
    public static final int REGULAR_SHOT_ENC_COUNTS = 900;
    public static final int TRUSS_SHOT_ENC_COUNTS = 600;
    public static final int HAS_BALL_POSITION = 290;
    //ranges for different manipulator heights
    private final int UPPER_RANGE = 0;      //above top photogate
    private final int MIDDLE_RANGE = 1;     //between photogates
    private final int LOWER_RANGE = 2;        //below bottom photogate
    private final int UNKNOWN_POSITION = 3; //unsure of where manipulator is (need to do home command)
    private int currentPosition = UNKNOWN_POSITION;

    // Motor Speeds
    private final double UPWARD_SPEED = -1.0;     //change these if launcher goes in wrong direction
    private final double DOWNWARD_SPEED = 0.8;
    
    // Motoro scaling
    private final double UPWARD_SCALING = 1.0;
    private final double DOWNWARD_SCALING = 0.25;
    
    private boolean stoppedAtBoundary = false;  //stores whether the launcher is currently stopped at a boundary

    /**
     * constructor
     */
    public Launcher() {
        // Creates motors
        launcherMotor = new Talon(RobotMap.launcherMotor);

        // Creates sensors
        launcherEncoder = new Encoder(RobotMap.launcherEncoderA, RobotMap.launcherEncoderB);
        launcherEncoder.start();

        launcherLowerGate = new DigitalInput(RobotMap.launcherLowerSwitch); //This is a photogate
        launcherUpperGate = new DigitalInput(RobotMap.launcherUpperSwitch); //This is a photogate
    }

    /**
     * By default, the operator can control the launcher with the joystick.
     */
    public void initDefaultCommand() {
        setDefaultCommand(new LauncherManualControl());
    }

    /**
     * Powers the Jordan's motors to send it upward.
     */
    public void goUpward() {
        move(UPWARD_SPEED);
    }

    /**
     * Powers the Jordan's motors to send it downward.
     */
    public void goDownward() {
        move(DOWNWARD_SPEED);
    }

    /**
     * Same as goUpward() but can reduce speed using a positive scalar.
     *
     * @param scalar
     */
    public void goUpward(double scalar) {
        move(UPWARD_SPEED * Math708.makeWithin(scalar, 0.0, 1.0)); //cannot increase or reverse speed with scalar
    }

    /**
     * Same as goDownward() but can reduce speed using a positive scalar.
     *
     * @param scalar
     */
    public void goDownward(double scalar) {
        move(DOWNWARD_SPEED * Math708.makeWithin(scalar, 0.0, 1.0));
    }

    /**
     * Stops the Jordan's motors.
     */
    public void stop() {
        move(0.0);
    }

    /**
     * Powers the Jordan's motors to move it in the direction of the joystick.
     * @param axis 
     */
    public void manualControl(double axis) {
        move(axis);
    }

    /**
     * Returns the upper bound switch.
     * @return 
     */
    public boolean getUpperGate() {
        // Reverses the state of the switch because it reads "true" when not tripped
        return !launcherUpperGate.get();
    }

    /**
     * Returns the lower bound switch.
     * @return 
     */
    public boolean getLowerGate() {
        // Reverses the state of the switch because it reads "true" when not tripped
        return !launcherLowerGate.get();
    }

    /**
     * Gets the distance that the encoder reads.
     * @return 
     */
    public int getCounts() {
        return launcherEncoder.get();
    }

    /**
     * Resets encoder values to zero.
     */
    public void resetEncoder() {
        launcherEncoder.reset();
    }
    
    /**
     * Returns true if the previous manipulation of the launcher's
     * speed caused it to reach the upper or lower boundaries and stop.
     * If this is the case, the command should terminate or react to the
     * situation accordingly.
     * @return 
     */
    public boolean stoppedAtBoundary()        
    {   
        return stoppedAtBoundary;
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
//        double speed;
//
//        switch (currentPosition) {
//            case UPPER_RANGE:
//                if (isUpward(newSpeed)) {
//                    speed = 0.0; //stop launcher
//                    stoppedAtBoundary = true;
//                } else if (isDownward(newSpeed)) {
//                    //passing boundary into middle range
//                    if (getUpperGate()) {
//                        currentPosition = MIDDLE_RANGE;
//                    }
//
//                    speed = newSpeed;
//                    stoppedAtBoundary = false;
//                }
//            case MIDDLE_RANGE:
//                if (isUpward(newSpeed)) {
//                    //passing boundary into upper range
//                    if (getUpperGate()) {
//                        currentPosition = UPPER_RANGE;
//                    }
//
//                } else if (isDownward(newSpeed)) {
//                    //passing boundary into lower range
//                    if (getLowerGate()) {
//                        currentPosition = LOWER_RANGE;
//                    }
//                }
//                //any speed is legal in the middle range
//                speed = newSpeed;
//                stoppedAtBoundary = false;
//            case LOWER_RANGE:
//                if (isDownward(newSpeed)) {
//                    speed = 0.0; //stop launcher
//                    stoppedAtBoundary = true;
//                } else if (isUpward(newSpeed)) {
//                    //passing boundary into middle range
//                    if (getLowerGate()) {
//                        currentPosition = MIDDLE_RANGE;
//                    }
//                    speed = newSpeed;
//                    stoppedAtBoundary = false;
//                }
//
//            default: //or UNKNOWN_POSITION
//                /*
//                 * Try to determine where the launcher is by looking at
//                 * photogates and the new speed.
//                 * This will probably occur during a homing routine.
//                 */
//                if (getLowerGate()) {
//                    if (isDownward(newSpeed)) {//entered lower range
//                        currentPosition = LOWER_RANGE;
//                        speed = 0.0;
//                        stoppedAtBoundary = true;
//                    } else {
//                        //entered middle range from below
//                        currentPosition = MIDDLE_RANGE;
//                        speed = newSpeed;
//                        stoppedAtBoundary = false;
//                    }
//                } else if (getUpperGate()) {
//                    if (isUpward(newSpeed)) {
//                        //entered upper range
//                        currentPosition = UPPER_RANGE;
//                        speed = 0.0;
//                        stoppedAtBoundary = true;
//                    } else {
//                        //entered middle range from above
//                        currentPosition = MIDDLE_RANGE;
//                        speed = newSpeed;
//                        stoppedAtBoundary = false;
//                    }
//                }else
//                {
//                    //cannot restrict speed because position of launcher is unknown
//                    speed = newSpeed;
//                    stoppedAtBoundary = false;
//                }
//                break;
//        }
//
//        //set motor speeds
        if (getUpperGate()) {
            if (isUpward(newSpeed)) {
                newSpeed = 0;
            }
        }
        
        if (isDownward(newSpeed)) {
            if (getLowerGate()) {
                newSpeed = 0;
                resetEncoder();
            } else {
               newSpeed *= DOWNWARD_SCALING; 
            }
        }
        
        launcherMotor.set(newSpeed);
    }

    /**
     * Checks if the given speed will cause the launcher to go upward.
     */
    private boolean isUpward(double speed) {
        return Math708.AreSameSign(speed, UPWARD_SPEED);
    }

    /**
     * Checks if the given speed will cause the launcher to go downward.
     */
    private boolean isDownward(double speed) {
        return Math708.AreSameSign(speed, DOWNWARD_SPEED);
    }

    /**
     * Sends data to the dumb-dashboard.
     */
    public void sendToDash() {
        SmartDashboard.putNumber("Launcher Encoder", launcherEncoder.get());
        SmartDashboard.putBoolean("Lower Switch", this.getLowerGate());
        SmartDashboard.putBoolean("Upper Switch", this.getUpperGate());
//        SmartDashboard.putString("Launcher Position", (currentPosition == LOWER_RANGE) ? "Lower Limit"
//                : (currentPosition == MIDDLE_RANGE) ? "Between Limits"
//                : (currentPosition == UPPER_RANGE) ? "Upper Limit" : "Unknown");
    }
}
