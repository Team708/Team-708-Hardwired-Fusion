
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Connor Willison
 */
public class VisionDebug extends CommandBase {
    
    public VisionDebug() {
        requires(visionProcessor);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        visionProcessor.processData();
        SmartDashboard.putString("Current Target:", visionProcessor.getTargetType());
        SmartDashboard.putNumber("Distance To Target:", visionProcessor.getDistanceToTarget());
        SmartDashboard.putNumber("Pixel Difference:", visionProcessor.getDifferencePx());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
