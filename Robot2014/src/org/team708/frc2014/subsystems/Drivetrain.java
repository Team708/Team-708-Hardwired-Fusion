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

/**
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
    private final double ultrasonicScalar = .5;
    
    // Shooting type constants
    public final int REGULAR = 0;
    public final int PASS_SHOT = 1;
    
    // Shooting distances
    public final int REGULAR_DISTANCE = 96;
    public final int PASS_SHOT_DISTANCE = 160;

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
        if (swag) {
            // Driver for two motors on
            swagDriver.arcadeDrive((swagPercent * leftAxis), (swagPercent * rightAxis));
        } else {
            // Driver for three motors on
            driver.arcadeDrive((normalPercent * leftAxis), (normalPercent * rightAxis));
        }
    }
    
    public boolean getSwag() {
        return swag;
    }

    public void setSwag(boolean newSwag) {
        swag = newSwag;
        
        if (!swag) {
            leftMotor2.set(0.0);
            rightMotor2.set(0.0);
        }
    }
    
    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }
    
    public void stop() {
        leftMotor1.set(0.0);
        leftMotor2.set(0.0);
        rightMotor1.set(0.0);
        rightMotor2.set(0.0);
    }
    
    public void setUltrasonicDistance(double lowerDistance, double upperDistance, boolean inverted) {
        leftUltrasonic.setTriggerBounds(lowerDistance, upperDistance, inverted);
        rightUltrasonic.setTriggerBounds(lowerDistance, upperDistance, inverted);
    }
    
    public boolean isAtOptimumDistance() {
        return leftUltrasonic.isTriggered() || rightUltrasonic.isTriggered();
    }
    
    public double getTurnSpeed() {
        return (leftUltrasonic.getDistance() - rightUltrasonic.getDistance()) * ultrasonicScalar;
    }
    
    public void sendToDash() {
        SmartDashboard.putNumber("Left Drivetrain Encoder", leftEncoder.get());
        SmartDashboard.putNumber("Right Drivetrain Encoder", rightEncoder.get());
        SmartDashboard.putNumber("Left Ultrasonic", (-leftUltrasonic.getDistance()));
        SmartDashboard.putNumber("Right Ultrasonic", rightUltrasonic.getDistance());
        SmartDashboard.putBoolean("Swag Mode", swag);
    }
}