package org.team708.frc2014.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team708.frc2014.RobotMap;
import org.team708.frc2014.commands.drivetrain.Drive;
import org.team708.frc2014.sensors.UltrasonicSensor;
import org.team708.util.Math708;

/**
 * A tank-style drivetrain that either uses 4 (normal) or 6 (swag) motors.
 * It has an encoder and an ultrasonic sensor per side of the robot. The 
 * drivetrain scales the PWM power signal depending on whether it is in swag or
 * not.
 * @author Matt Foley, Nam Tran, Pat Walls, Jialin Wang, Connor Willison
 */
public class Drivetrain extends Subsystem {
    
    // Creates speed controllers for left side (1 = normal/crawl, 2 = swag)
    private final SpeedController leftMotor1, leftMotor2;
    // Creates speed controllers for right side (1 = normal/crawl, 2 = swag)
    private final SpeedController rightMotor1, rightMotor2;
    
    private final Encoder leftEncoder, rightEncoder; // Sensors
    
    // Creates drivers (one for two motors running, one for three running)
    private final RobotDrive driver, swagDriver;
    
    //Creates Ultrasonic Sensors
    private final UltrasonicSensor leftUltrasonic, rightUltrasonic;
    
    private boolean swag = false; // Drivetrain mode check
    
    // Scaling for drive modes
    public final double normalPercent = 0.85;
    public final double swagPercent = 1.00;
    
    // Scaling for ultrasonic direction correction
    private final double ultrasonicScalar = .10;
    private final double ultrasonicMoveSpeed = 0.70;
    private final double turnTolerance = 4;
    
    //Encoder correction
    private boolean encodersZeroed = false;
    private double zeroedRightEncoder;
    private double zeroedLeftEncoder;
    private final double TURN_TOLERANCE = 25.0;
    private double correction;
    private double compensation_scalar = 400.0;
    
    // Shooting type constants
    public final int AUTONOMOUS_SHOT = 0;
    public final int PASS_SHOT = 1;
    public final int TELEOP_SHOT = 2;
    
    // Shooting distances
    public final int AUTONOMOUS_SHOT_DISTANCE = 60;
    public final int TELEOP_SHOT_DISTANCE = 39;
    public final int PASS_SHOT_DISTANCE = 108;

    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
         setDefaultCommand(new Drive());
    }
    
    /*
    * Constructor - run once when Drivetrain object is created.
    */
    public Drivetrain()
    {
        // Creates left motor controllers
        leftMotor1 = new Talon(RobotMap.leftMotor1);
        leftMotor2 = new Talon(RobotMap.leftMotor2);
        
        // Creates right motor controllers
        rightMotor1 = new Talon(RobotMap.rightMotor1);
        rightMotor2 = new Talon(RobotMap.rightMotor2);
        
        //Creates encoders
        leftEncoder = new Encoder(RobotMap.leftEncoderA, RobotMap.leftEncoderB);
        rightEncoder = new Encoder(RobotMap.rightEncoderA, RobotMap.rightEncoderB);
        leftEncoder.start();
        rightEncoder.start();
        
        // Creates normal drive mode using two motor controllers (2 motors on each side)
        driver = new RobotDrive(leftMotor1,rightMotor1);
        driver.setSafetyEnabled(false);
        // Creates drive mode using four motor controllers (3 motors on each side)
        swagDriver = new RobotDrive(leftMotor1, leftMotor2, rightMotor1, rightMotor2);
        swagDriver.setSafetyEnabled(false);
        
        //Creates Drivetrain ultrasonic sensors
        leftUltrasonic = new UltrasonicSensor(RobotMap.drivetrainLeftUltrasonic, UltrasonicSensor.MB1010);
        rightUltrasonic = new UltrasonicSensor(RobotMap.drivetrainRightUltrasonic, UltrasonicSensor.MB1010);
    }
    
    /**
     * Use joystick values to do Halo drive.
     * @param leftAxis
     * @param rightAxis 
     */
    public void haloDrive (double leftAxis, double rightAxis)
    {
        correction = rightAxis;
        
        if (leftAxis != 0.0 && rightAxis == 0.0) {
            if (!encodersZeroed) {
                zeroedLeftEncoder = -getLeftEncoder();
                zeroedRightEncoder = getRightEncoder();
                encodersZeroed = true;
            }
            
            double encoderDifference = (-getLeftEncoder() - zeroedLeftEncoder) - (getRightEncoder() - zeroedRightEncoder);
            if (encoderDifference < -TURN_TOLERANCE || encoderDifference > TURN_TOLERANCE) {
                correction = Math708.makeWithin(encoderDifference / compensation_scalar, -1.0,1.0);
            } else {
                correction = 0.0;
            }
        } else {
            encodersZeroed = false;
        }
        
        if (swag) {
            // Driver for two motors on
            swagDriver.arcadeDrive((swagPercent * leftAxis), (swagPercent * correction));
        } else {
            // Driver for three motors on
            driver.arcadeDrive((normalPercent * leftAxis), (normalPercent * correction));
        }
    }
    /**
     * Returns the swag state
     * @return 
     */
    public boolean getSwag() {
        return swag;
    }
    
    /**
     * Sets whether swag mode is enabled
     * @param newSwag 
     */
    public void setSwag(boolean newSwag) {
        swag = newSwag;
        
        if (!swag) {
            leftMotor2.set(0.0);
            rightMotor2.set(0.0);
        }
    }
    
    /**
     * Resets the encoder values
     */
    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }
    
    public double getAverageEncoderDistance() {
        return (-leftEncoder.getDistance() + (rightEncoder.getDistance())) / 2;
    }
    
    public double getLeftEncoder() {
        return leftEncoder.get();
    }
    
    public double getRightEncoder() {
        return rightEncoder.get();
    }
    
    /**
     * Stops the motors
     */
    public void stop() {
        leftMotor1.set(0.0);
        leftMotor2.set(0.0);
        rightMotor1.set(0.0);
        rightMotor2.set(0.0);
    }
    
    /**
     * Sets the lower and upper ranges for the target ultrasonic distance from
     * something. When inverted is true, then it returns if the distance is
     * outside the range. When inverted is false, then it returns if the distance
     * is inside the range.
     * @param lowerDistance
     * @param upperDistance
     * @param inverted 
     */
    public void setUltrasonicDistance(double lowerDistance, double upperDistance, boolean inverted) {
        leftUltrasonic.setTriggerBounds(lowerDistance, upperDistance, inverted);
        rightUltrasonic.setTriggerBounds(lowerDistance, upperDistance, inverted);
    }
    
    /**
     * Uses the Ultrasonic sensors to check if the robot is at the
     * optimum distance to score
     * @return 
     */
    public boolean isAtOptimumDistance() {
        return leftUltrasonic.isTriggered() || rightUltrasonic.isTriggered();
    }
    
    /**
     * Calculates the turn speed using the difference of the left and right
     * ultrasonic sensors, multiplied by a scalar for the rate of correction.
     * @return 
     */
    public double getTurnSpeed() {
        double ultrasonicDifference = leftUltrasonic.getDistance() - rightUltrasonic.getDistance();
        double turnSpeed = 0.0;
        
        if (ultrasonicDifference > turnTolerance) {
            turnSpeed = ultrasonicDifference * ultrasonicScalar;
        }
        
        return turnSpeed;
    }
    
    public double getForwardSpeed(double lowerDistance, double upperDistance) {
//        double averageDistance = ((leftUltrasonic.getDistance() + rightUltrasonic.getDistance())/2);
        double averageDistance = rightUltrasonic.getDistance();
        double forwardSpeed;
        
        if (averageDistance < lowerDistance) {
            forwardSpeed = -ultrasonicMoveSpeed;
        } else if (averageDistance > upperDistance) {
            forwardSpeed = ultrasonicMoveSpeed;
        } else {
            forwardSpeed = 0.0;
        }
        
        return forwardSpeed;
    }
    
    public double getLeftDistance() {
        return leftUltrasonic.getDistance();
    }
    
    public double getRightDistance() {
        return rightUltrasonic.getDistance();
    }
    
    /**
     * Sends data to the dumb-dashboard
     */
    public void sendToDash() {
        SmartDashboard.putNumber("Left Drivetrain Encoder", leftEncoder.get());
        SmartDashboard.putNumber("Right Drivetrain Encoder", rightEncoder.get());
        SmartDashboard.putNumber("Left Ultrasonic Avg V", leftUltrasonic.getAverageVoltage());
        SmartDashboard.putNumber("Right Ultrasonic Avg V", rightUltrasonic.getAverageVoltage());
        SmartDashboard.putNumber("Left Ultrasonic", (leftUltrasonic.getDistance()));
        SmartDashboard.putNumber("Right Ultrasonic", rightUltrasonic.getDistance());
        SmartDashboard.putBoolean("Swag Mode", swag);
        SmartDashboard.putNumber("Bias Compensation",correction);
    }
}