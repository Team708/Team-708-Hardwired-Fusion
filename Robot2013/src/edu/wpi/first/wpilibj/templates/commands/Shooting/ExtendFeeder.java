/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.Shooting;

import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 *
 * @author Connor Willison
 */
public class ExtendFeeder extends CommandBase{

    public ExtendFeeder()
    {
        super("ExtendFeeder");
        requires(shooter);
    }
    
    protected void initialize() {
    }

    protected void execute() {
        //fire only if there is a target
        if(visionProcessor.hasTarget())
            shooter.extendSolenoid();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
