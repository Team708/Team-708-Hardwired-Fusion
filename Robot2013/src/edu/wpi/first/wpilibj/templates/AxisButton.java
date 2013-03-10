/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import utilclasses.Gamepad;

/**
 * A button that reads a gamepad to determine whether an axis is positive or negative.
 * @author Robotics
 */
public class AxisButton extends JoystickButton{
    
    private Gamepad stick;
    private int axisNumber;
    private boolean positiveTrue = true;
    
    public AxisButton(Gamepad stick, int axisNumber, boolean positiveTrue)
    {
        super(stick,axisNumber);
        
        this.stick = stick;
        this.axisNumber = axisNumber;
        this.positiveTrue = positiveTrue;
    }
    
    public boolean get()
    {
        if(positiveTrue)
        {
            return stick.getAxis(axisNumber) > 0.0;
        }else
        {
            return stick.getAxis(axisNumber) < 0.0;
        }
    }
    
}
