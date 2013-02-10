/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bridge;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Gyro;
import io.IOConstants;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.templates.Drivetrain;
import scripting.Configurable;
import scripting.Configuration;

/**
 * The arm is a device mounted on the front (roller side)
 * of the robot that is capable of lowering the bridge.
 * It is operated by one motor and its rotation can be
 * measured using a potentiometer.
 * @author 708
 */
public class Arm implements Configurable{

    private SpeedController motor;
    private AnalogChannel pot;
    private Gyro tiltSensor;

    //run motor at 70% power when rotating arm
    private static final double raisingSpeed = .7;
    private static final double loweringSpeed = -1.0;

    //must measure up/down pot values
    //private static final int potAvgDown = 590;
    private static int potAvgDown = 520;
    private static int potAvgUp = 850;

    private static final int RAISING = 0;
    private static final int LOWERING = 1;

    //arm raises to start position when robot is enabled
    private int state = RAISING;

    //must decide on a maximum tilt angle
    private double tiltValue = 0.0;

    public Arm(Drivetrain robot){
        motor = new Victor(IOConstants.PWMChannels.kArmMotor);
        pot = new AnalogChannel(IOConstants.AnalogInputChannels.kArmPot);
        tiltSensor = robot.getGyro();

        Configuration.getInstance().register("Arm",this);
    }
    
    /**
     * Called by manipulator thread to control
     * arm. Raises or lowers the arm according to
     * the current state and the pot measurement.
     */
    public void control(){

        int s;

        //access shared variable
        synchronized(this)
        {
            s = this.state;
        }

//        //lower the arm if the robot is tilted
//        if(isTilted())
//        {
//            synchronized(this)
//            {
//                state = LOWERING;
//            }
//        }

        switch(s)
        {
            case RAISING:
                if(!isUp())
                {
                    motor.set(raisingSpeed);
                }else
                {
                    motor.set(0.0);
                }

                break;

            case LOWERING:
                if(!isDown())
                {
                    motor.set(loweringSpeed);
                }else
                {
                    motor.set(0.0);
                }

                break;

            default:
                motor.set(0.0);
                break;
        }

    }


    /*
     * These two methods are the main thread's interface to
     * the arm. <state> is the shared variable.
     */
    public synchronized void lower(){
        state = LOWERING;
    }

    public synchronized void raise(){
        state = RAISING;
    }

    /**
     * @return True if the arm is down.
     */
    public boolean isDown(){
        return pot.getAverageValue() <= potAvgDown;
    }

    /**
     * True if the arm is up.
     * @return
     */
    public boolean isUp(){
        return pot.getAverageValue() >= potAvgUp;
    }
    
    /**
     * Check the gyro to determine if the robot
     * has tilted past a certain angle.
     * @return
     */
    public boolean isTilted()
    {
        if(tiltValue > 0)
        {
            return tiltSensor.getAngle() > tiltValue;
        }else
        {
            return tiltSensor.getAngle() < tiltValue;
        }
    }

    /**
     * Reads the potentiometer to find the arm's
     * current angle.
     * @return Potentiometer average value (int)
     */
    public int getPotValue()
    {
        return pot.getAverageValue();
    }


    /**
     * Reads the gyro to determine the
     * robot's tilt angle.
     * @return Gyro angle (degrees)
     */
    public double getTilt()
    {
        return tiltSensor.getAngle();
    }

    public void update(String name, String[] values) {
        if(name.equalsIgnoreCase("armdown="))
        {
            if(values.length > 0)
            {
                potAvgDown = (int)Double.parseDouble(values[0]);
            }
        }else if(name.equalsIgnoreCase("armup="))
        {
            if(values.length > 0)
            {
                potAvgUp = (int)Double.parseDouble(values[0]);
            }
        }
    }



}
