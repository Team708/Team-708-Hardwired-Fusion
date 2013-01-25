
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.Preferences;

/**
 *
 * @author bradmiller
 */
public class SpinMotor extends CommandBase {
    
    public SpinMotor(double timeSec) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        super("SpinMotor");
        requires(motorSubsystem);
        
        setTimeout(timeSec);
    }
    
    public SpinMotor()
    {
        super("SpinMotor");
        requires(motorSubsystem);
        
        setTimeout(Preferences.getInstance().getDouble("SpinMotorTime", 5.0));
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        motorSubsystem.setSpeed(1.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
        motorSubsystem.setSpeed(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}
