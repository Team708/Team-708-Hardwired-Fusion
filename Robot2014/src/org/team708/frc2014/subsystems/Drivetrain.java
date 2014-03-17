package org.team708.frc2014.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team708.frc2014.RobotMap;
import org.team708.frc2014.commands.drivetrain.Drive;
import org.team708.frc2014.sensors.EncoderRotationSensor;
import org.team708.frc2014.sensors.RotationSensor;
import org.team708.frc2014.sensors.UltrasonicSensor;

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
    
    private final Encoder leftEncoder, rightEncoder; // Encoders
    
    //virtual rotation sensor using encoders
    private final RotationSensor encoderRotationSensor;
    private static final int encWheelCounts = 360; //360 count encoders
    private static final double wheelDiameterIn = 4.0; //4in colson wheels
    private static final double distancePerCountIn = Math.PI * wheelDiameterIn / encWheelCounts;
    private static final double robotDiameterIn = 27.5;
    
    // Creates drivers (one for two motors running, one for three running)
    private final RobotDrive driver, swagDriver;
    
    //Creates Ultrasonic Sensors
    private final UltrasonicSensor leftUltrasonic, rightUltrasonic;
    
    private boolean swagSpeed = false; // Changes the speed of the drivetrain
    private boolean swagDrive = false; // Changes how many motors to use
    
    // Scaling for drive modes
    public final double NORMAL_PERCENT = 0.85;
    public final double SWAG_PERCENT = 1.00;
    public double currentPercent = NORMAL_PERCENT;
    
    // Scaling for ultrasonic direction correction
    private final double ultrasonicScalar = .10;
    private final double ultrasonicMoveSpeed = 0.70;
    private final double turnTolerance = 4;
    
    //Encoder correction
    private boolean encodersZeroed = false;
    private double zeroedRightEncoder;
    private double zeroedLeftEncoder;
    private double turnSpeed = 0.0;
    private final double TURN_TOLERANCE = 100.0;
    
    // Shooting type constants
    public final int REGULAR = 0;
    public final int PASS_SHOT = 1;
    
    // Shooting distances
    public final int REGULAR_DISTANCE = 58;   //was 64
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
        leftEncoder.setReverseDirection(true); //left encoder must be reversed due to drivetrain mirror image
        leftEncoder.setDistancePerPulse(distancePerCountIn);
        rightEncoder.setDistancePerPulse(distancePerCountIn);
        leftEncoder.start();
        rightEncoder.start();
        
        //create virtual encoder rotation sensor
        encoderRotationSensor = new EncoderRotationSensor(leftEncoder,rightEncoder,robotDiameterIn);
        
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
        if (rightAxis == 0.0) {
            if (!encodersZeroed) {
                zeroedLeftEncoder = -getLeftEncoder();
                zeroedRightEncoder = getRightEncoder();
                encodersZeroed = true;
            }
            
            double encoderDifference = (-getLeftEncoder() - zeroedLeftEncoder) - (getRightEncoder() - zeroedRightEncoder);
            if (encoderDifference < -TURN_TOLERANCE || encoderDifference > TURN_TOLERANCE) {
                rightAxis = encoderDifference / 1000;
            } else {
                rightAxis = 0.0;
            }
        } else {
            encodersZeroed = false;
        }
        
        if (swagDrive) {
            // Driver for two motors on
            swagDriver.arcadeDrive((currentPercent * leftAxis), (currentPercent * rightAxis));
        } else {
            // Driver for three motors on
            driver.arcadeDrive((currentPercent * leftAxis), (currentPercent * rightAxis));
        }
    }
    /**
     * Returns the swag drive state
     * @return 
     */
    public boolean getSwagDrive() {
        return swagDrive;
    }
    
    /**
     * Sets whether swag mode is enabled
     * @param newSwag 
     */
    public void setSwagDrive(boolean newSwag) {
        swagDrive = newSwag;
        
        if (!swagDrive) {
            leftMotor2.set(0.0);
            rightMotor2.set(0.0);   
        }
    }
    
    /**
     * Returns if the drivetrain should be in swag speed
     * @return 
     */
    public boolean getSwagSpeed() {
        return swagSpeed;
    }
    
    /**
     * Sets whether swag speed is enabled
     * @param newSwag 
     */
    public void setSwagSpeed(boolean newSwag) {
        swagSpeed = newSwag;
        
        if (swagSpeed) {
            currentPercent = SWAG_PERCENT;
        } else {
            currentPercent = NORMAL_PERCENT;
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
        return (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2;
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
    
    public void setMotors(double speed) {
        leftMotor1.set(speed);
        rightMotor1.set(speed);
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
    
    public double getLeftEncoder() {
        return leftEncoder.get();
    }
    
    public double getRightEncoder() {
        return rightEncoder.get();
    }
    
    public double getAngleDeg()
    {
        return encoderRotationSensor.getAngle();
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
        SmartDashboard.putBoolean("Swag Mode", swagDrive);
        SmartDashboard.putNumber("Current Speed Scaling", currentPercent);
    }
}