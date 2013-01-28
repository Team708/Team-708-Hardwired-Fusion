/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Team708Classes;

import edu.wpi.first.wpilibj.Encoder;

/**
 * This class determines relative rotation of the robot by finding
 * the difference between the encoder values on either side.
 * @author 708
 */
public class EncoderRotationSensor extends RotationSensor{

    private double robotDiameter; //inches

    private Encoder left, right;
    private double offset = 0.0;  //subtracted from value returned by getAngle(), used to reset sensor

    public EncoderRotationSensor(Encoder left,Encoder right,double robotDiameter){
        this.left = left;
        this.right = right;
        this.robotDiameter = robotDiameter;
    }

    /**
     * Math used here has been verified to produce
     * the current angle of the robot in degrees.
     * @return 
     */
    public double getAngle(){
        return Math.toDegrees((left.getDistance() - right.getDistance())/robotDiameter) - offset;
    }

    public void setRobotDiameter(double diameter){
        this.robotDiameter = diameter;
    }

    public void reset(){
        offset = getAngle();
    }
}
