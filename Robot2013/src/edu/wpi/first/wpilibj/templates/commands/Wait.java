/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

/**
 * Waits for the specified amount of time in seconds before continuing
 * to the next command.
 * @author Debbie Musselman
 */
public class Wait extends CommandBase{
    
    public Wait(double time){
       super("Wait for " + time + " sec");
       setTimeout(time);
    }

    protected void initialize() {
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
    
}
