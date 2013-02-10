/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;
import rotation.EncoderRotationSensor;
import rotation.GyroRotationSensor;
import rotation.RotationSensor;
import io.IOConstants;
import scripting.Configurable;
import scripting.Configuration;

/**
 *
 * @author 708
 */
public class Drivetrain implements PIDOutput,Configurable{
    private Jaguar left,right;
    private RobotDrive robot;

    private Encoder leftEnc,rightEnc;
    private Gyro gyro;

    private RotationSensor rotation;
    private GyroRotationSensor grotation;
    private EncoderRotationSensor erotation;

    private static double robotDiameter = 0.0;
    private static double wheelDiameterIn = 0.0;
    private static double gearReduction = 0.0;
    private final static double pulsesPerRotation = 250.0;

    
    public Drivetrain(){
        left = new Jaguar(IOConstants.PWMChannels.kLeftMotor);
        right = new Jaguar(IOConstants.PWMChannels.kRightMotor);

        robot = new RobotDrive(left,right);

        leftEnc = new Encoder(IOConstants.DigitalIOChannels.kLeftEncoderA,
                IOConstants.DigitalIOChannels.kLeftEncoderB,true,Encoder.EncodingType.k4X);

        rightEnc = new Encoder(IOConstants.DigitalIOChannels.kRightEncoderA,
                IOConstants.DigitalIOChannels.kRightEncoderB,false,Encoder.EncodingType.k4X);

        gyro = new Gyro(IOConstants.AnalogInputChannels.kGyro);

        grotation = new GyroRotationSensor(gyro);

        erotation = new EncoderRotationSensor(leftEnc,rightEnc,robotDiameter);

        rotation = erotation;

        setUpEncoderValues();

        startEncoders();

        Configuration.getInstance().register("Drivetrain",this);
    }

    public RotationSensor getRotation(){
        return rotation;
    }

    public void setUseGyroRotation(){
            rotation = grotation;
    }

    public void setUseEncoderRotation(){
            rotation = erotation;
    }
    
    public void drive(double movement,double rotation){
        robot.arcadeDrive(movement,rotation);
    }

    /**
     * Starts the left and right encoders
     * counting distance.
     */
    public final void startEncoders(){
        leftEnc.start();
        rightEnc.start();
    }

    public void resetEncoders(){
        leftEnc.reset();
        rightEnc.reset();
    }

    public int getLeftPulseCount(){
        return leftEnc.get();
    }

    public int getRightPulseCount(){
        return rightEnc.get();
    }

    public double getLeftDistance(){
        return leftEnc.getDistance();
    }

    public double getRightDistance(){
        return rightEnc.getDistance();
    }

    public Gyro getGyro(){
        return gyro;
    }

    /**
     * Drive the robot forwards or backwards
     * with PID Controller. (Balancing)
     * @param output
     */
    public void pidWrite(double output) {
        robot.arcadeDrive(output,0.0);
    }

    public void update(String param,String[] values){
        if(param.equalsIgnoreCase("robotdiameter=")){
            if(values.length > 0){
                robotDiameter = Double.parseDouble(values[0]);
            }
        }else if(param.equalsIgnoreCase("wheeldiameter=")){
            if(values.length > 0){
                wheelDiameterIn = Double.parseDouble(values[0]);
            }
        }else if(param.equalsIgnoreCase("gearreduction=")){
            if(values.length > 0){
                gearReduction = Double.parseDouble(values[0]);
            }
        }

        //use values read in from config
        setUpEncoderValues();
    }

    private void setUpEncoderValues(){
        /*
         * Set distance per pulse:
         */
        double distancePerPulse = Math.PI * wheelDiameterIn * gearReduction / pulsesPerRotation;
        leftEnc.setDistancePerPulse(distancePerPulse);
        rightEnc.setDistancePerPulse(distancePerPulse);

        erotation.setRobotDiameter(robotDiameter);
    }

}
