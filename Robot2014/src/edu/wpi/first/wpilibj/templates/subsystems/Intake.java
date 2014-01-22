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
public class Intake extends Subsystem {
    
    private boolean isExtended = false;
    
    public Intake() {
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public boolean isExtended() {
        return isExtended;
    }
    
    public void changeState() {
        if (isExtended) {
            isExtended = false;
        } else if (!isExtended) {
            isExtended = true;
        } else {}
    }
}