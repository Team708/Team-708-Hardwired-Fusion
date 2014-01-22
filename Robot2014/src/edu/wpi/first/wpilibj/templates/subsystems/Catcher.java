/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Robotics
 */
public class Catcher extends Subsystem {
    
    private boolean isOpen = false;
    
    public Catcher() {
        
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public boolean isOpen() {
        return isOpen;
    }
    
    public void changeState() {
        if (isOpen) {
            isOpen = false;
        } else if (!isOpen) {
            isOpen = true;
        } else {}
    }
}