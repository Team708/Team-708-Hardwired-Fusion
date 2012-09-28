/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bridge;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.templates.Drivetrain;
import io.IOConstants;
import scripting.Configurable;
import scripting.Configuration;

/**
 *
 * @author 708
 */
public class Balancer implements Configurable{

    private Gyro sensor;
    private Drivetrain robot;
    private PIDController controller;

    private static double P = 0.0;
    private static double I = 0.0;
    private static double D = 0.0;

    private static double tolerance = 10.0;
    private static double maxDeg = 18.0;
    private boolean enabled = false;

    public Balancer(Drivetrain robot){
        this.robot = robot;
        sensor = robot.getGyro();

        //set up the pid controller - input to pid is the gyro degrees, output is motor PWM signal
        controller = new PIDController(P, I, D, sensor, robot);
        controller.setTolerance(tolerance);   //must set these values so that the pid has a frame of reference.
        controller.setInputRange(-maxDeg, maxDeg);
        controller.setOutputRange(-1, 1);

        //register with config file
        Configuration.getInstance().register("Balancer",this);
    }

    public double getAngle(){
        return sensor.getAngle();
    }

    public void startBalancing(){
        enabled = true;
        controller.setSetpoint(0.0);
        controller.enable();
    }

    public void stopBalancing(){
        enabled = false;
        controller.disable();
    }

    public boolean isEnabled(){
        return enabled;
    }

    public void update(String param,String[] values){
        if(param.equalsIgnoreCase("PID=")){
            if(values.length >= 3){
                P = Double.parseDouble(values[0]);
                I = Double.parseDouble(values[1]);
                D = Double.parseDouble(values[2]);
            }
            controller.setPID(P, I, D);

        }else if(param.equalsIgnoreCase("tolerance=")){
            if(values.length >= 1){
                tolerance = Double.parseDouble(values[0]);
            }

            controller.setTolerance(tolerance);
        }
    }

}
