/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.Shooting;

import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 * Spins up the shooter for a shot.
 * @author Connor Willison + Vince Garguilo
 */
public class SpinUp extends CommandBase {
    
    public SpinUp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        super("SpinUp");
        requires(shooter);
        requires(visionProcessor);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //run image processing to calculate distance to current target
//        visionProcessor.processData();
//        
//        if(visionProcessor.hasTarget())
//        {
//        
//            /*Gets distance from the camera, and uses that to retrieve from the table
//             the correct RPMs to set the speed of the shooter to. (Assuming Vision
//             Processor has a .getRPM command)*/
//            shooter.setSpeed(visionProcessor.getRPM());
//        }
        
        shooter.setPWM(1.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //check whether shooter is done spinning up
//        return shooter.isAtSpeed() || !visionProcessor.hasTarget();
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
