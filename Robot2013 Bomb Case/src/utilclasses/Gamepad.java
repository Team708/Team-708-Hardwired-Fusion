/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilclasses;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Represents a Logitech Gamepad connected to the driver
 * station. Note: the drivers for the gamepad should
 * be installed on the driver station netbook.
 * @author Connor Willison
 */
public class Gamepad extends Joystick{

    //floating-point values:
    public static final int leftStick_X = 1;
    public static final int leftStick_Y = 2;
    public static final int shoulderAxis = 3;
    public static final int rightStick_X = 4;
    public static final int rightStick_Y = 5;
    public static final int dpadAxis = 6;
    
    //boolean buttons:
    public static final int button_A = 1;
    public static final int button_B = 2;
    public static final int button_X = 3;
    public static final int button_Y = 4;
    public static final int button_L_Shoulder = 5;
    public static final int button_R_Shoulder = 6;
    public static final int button_Back = 7;
    public static final int button_Start = 8;
    public static final int button_LeftStick = 9;
    public static final int button_RightStick = 10;	//only 12 buttons allowed - must find out which can be read    
    
    private static final double axis_deadzone = .02;
    
    private int port;
    /**
     * Creates a gamepad attached to
     * the specified port on the driver station.
     * @param port 
     */
    public Gamepad(int port){
	super(port);
        this.port = port;
    }
    
    /**
     * Get the value of a gamepad axis, adjusting
     * for a dead zone.
     * @param axis
     * @return 
     */
    public double getAxis(int axis){
	double val = getRawAxis(axis);
        if(Math.abs(val) <= axis_deadzone){
            return 0;
        }else if(axis == Gamepad.rightStick_Y || axis == Gamepad.leftStick_Y)
        {
            //flip the y axes on both joysticks
            //they naturally read negative for upward motion, but they should read positive
            return -val;
        }else{
            return val;
        }
    }
    
    /**
     * Get the value of a gamepad button.
     * @param button
     * @return 
     */
    public boolean getButton(int button){
	return getRawButton(button);
    }
    
    public int getPort()
    {
        return port;
    }
    
    /**
     * Outputs the gamepad's axes to the SmartDashboard.
     */
    public void sendAxesToDashboard()
    {
        SmartDashboard.putNumber("Gamepad " + port + "Right Stick X:",
                Math708.round(getAxis(Gamepad.rightStick_X),2));
        SmartDashboard.putNumber("Gamepad " + port + "Right Stick Y:",
                Math708.round(getAxis(Gamepad.rightStick_Y),2));
        SmartDashboard.putNumber("Gamepad " + port + "Left Stick X:",
                Math708.round(getAxis(Gamepad.leftStick_X),2));
        SmartDashboard.putNumber("Gamepad " + port + "Left Stick Y:",
                Math708.round(getAxis(Gamepad.leftStick_Y),2));
        SmartDashboard.putNumber("Gamepad " + port + " Shoulder:",
                Math708.round(getAxis(Gamepad.shoulderAxis),2));
        SmartDashboard.putNumber("Gamepad " + port + " D-Pad:",
                Math708.round(getAxis(Gamepad.dpadAxis),2));
    }
}
