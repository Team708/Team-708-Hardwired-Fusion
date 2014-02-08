package org.team708.frc2014.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team708.frc2014.RobotMap;
import org.team708.frc2014.commands.drivetrain.Drive;
import org.team708.frc2014.sensors.UltrasonicSensor;

/**
 * @author Matt Foley, Nam Tran, Pat Walls, Jillan Wang, Connor Willison
 */
public class Drivetrain extends Subsystem {
    
    // Creates speed controllers for left side (1 = normal/crawl, 2 = swag)
    private final SpeedController leftMotor1, leftMotor2;
    // Creates speed controllers for right side (1 = normal/crawl, 2 = swag)
    private final SpeedController rightMotor1, rightMotor2;
    
    // Sensors
    private final Encoder leftEncoder, rightEncoder;
    
    // Creates drivers (one for two motors running, one for three running)
    private final RobotDrive driver, swagDriver;
    
    //Creates Ultrasonic Sensors
    private final UltrasonicSensor leftUltrasonic, rightUltrasonic;
    
    // Drivetrain Modes
//    private boolean haloDrive = true;
    private final int NORMAL = 0;
    private final int SWAG = 1;
    private final int ANTISWAG = 2;
    private int mode = NORMAL;
    
    // Scaling for drive modes
    private final double[] scalarFB = {0.75, 1.00, 0.50};
    private final double[] scalarLR = {0.75, 1.00, 0.50};
    
    // Scaling for ultrasonic direction correction
    private final double ultrasonicScalar = .5;
    
    // Variable for optimum distance to shoot
    public final double optimumShootingDistance = 96.0;

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
        
        // Creates normal drive mode using two motor controllers (2 motors on each side)
        driver = new RobotDrive(leftMotor1,rightMotor1);
        // Creates drive mode using four motor controllers (3 motors on each side)
        swagDriver = new RobotDrive(leftMotor1, leftMotor2, rightMotor1, rightMotor2);
        
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
        if (mode != SWAG) {
            // Driver for two motors on
            driver.arcadeDrive((scalarFB[mode] * leftAxis), (scalarLR[mode] * rightAxis));
        } else {
            // Driver for three motors on
            swagDriver.arcadeDrive((scalarFB[SWAG] * leftAxis), (scalarLR[SWAG] * rightAxis));
        }
    }
    
    public void driveForwardToTarget() {
        if(!this.isAtOptimumDistance()) {
            double turnSpeed = (leftUltrasonic.getDistance() - rightUltrasonic.getDistance()) * ultrasonicScalar;
            driver.arcadeDrive(scalarFB[NORMAL], -turnSpeed);
        } else {
            driver.arcadeDrive(0.0, 0.0);
        }
    }
    
    public int getMode() {
        return mode;
    }

    public void setMode(int newMode) {
        mode = newMode;
        
        if (mode != SWAG) {
            leftMotor2.set(0.0);
            rightMotor2.set(0.0);
        }
    }
    
    public int NORMAL() {
        return NORMAL;
    }
    
    public int SWAG() {
        return SWAG;
    }
    
    public int ANTISWAG() {
        return ANTISWAG;
    }
    
    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }
    
    public void setUltrasonicDistance(double lowerDistance, double upperDistance, boolean inverted) {
        leftUltrasonic.setTriggerBounds(lowerDistance, upperDistance, inverted);
        rightUltrasonic.setTriggerBounds(lowerDistance, upperDistance, inverted);
    }
    
    public boolean isAtOptimumDistance() {
        return leftUltrasonic.isTriggered() || rightUltrasonic.isTriggered();
    }
    
//    /**
//     * Use joystick values to do skid-steer/tank drive.
//     * @param leftAxis
//     * @param rightAxis 
//     */
//    public void tankDrive(double leftAxis, double rightAxis)
//    {
//        if (mode == SWAG) {
//            // Driver for three motors on
//            swagDriver.tankDrive((scalarFB[SWAG] * leftAxis), (scalarFB[SWAG] * rightAxis));
//        } else {
//            // Driver for two motors on
//            driver.tankDrive((scalarFB[mode] * leftAxis), (scalarFB[mode] * rightAxis));
//        }
//    }
    
//    public boolean isHaloDrive() {
//        return haloDrive;
//    }
    
//    public void setIsHaloDrive(boolean newMode) {
//        haloDrive = newMode;
//    }
    
//    public void drive(Gamepad gamepad) {
//        if (this.isHaloDrive()) {
//            this.haloDrive(gamepad.getAxis(Gamepad.leftStick_Y),gamepad.getAxis(Gamepad.rightStick_X));
//        } else {
//            this.tankDrive(gamepad.getAxis(Gamepad.leftStick_Y),gamepad.getAxis(Gamepad.rightStick_Y));
//        }
//    }
}
