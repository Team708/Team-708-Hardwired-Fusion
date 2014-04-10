/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.commands.drivetrain;

import org.team708.frc2014.OI;
import org.team708.frc2014.commands.CommandBase;
import org.team708.util.Gamepad;

/**
 *
 * @author Nam Tran
 */
public class DriveForwardToTargetUltrasonic extends CommandBase {
    
    private final int ERROR_ZONE = 3;
    private double targetDistance;
    
    public DriveForwardToTargetUltrasonic(int shotType) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(drivetrain);
        
        if(shotType == drivetrain.AUTONOMOUS_SHOT) {
            targetDistance = drivetrain.AUTONOMOUS_SHOT_DISTANCE;
        } else if (shotType == drivetrain.TELEOP_SHOT) {
            targetDistance = drivetrain.TELEOP_SHOT_DISTANCE;
        } else {
            targetDistance = drivetrain.PASS_SHOT_DISTANCE;
        }
        
        drivetrain.setUltrasonicDistance ((targetDistance - ERROR_ZONE), (targetDistance + ERROR_ZONE), false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        ledArray.setState(ledArray.FLASHING);
        drivetrain.haloDrive(-drivetrain.getForwardSpeed(targetDistance - ERROR_ZONE, targetDistance + ERROR_ZONE), -OI.driverGamepad.getAxis(Gamepad.rightStick_X));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return drivetrain.isAtOptimumDistance();
    }

    // Called once after isFinished returns true
    protected void end() {
        drivetrain.stop();
//        System.out.println(drivetrain.getLeftDistance());
//        System.out.println(drivetrain.getRightDistance());
        ledArray.setState(ledArray.SOLID);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}