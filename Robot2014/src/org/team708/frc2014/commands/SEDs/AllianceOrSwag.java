/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.commands.SEDs;

import edu.wpi.first.wpilibj.DriverStation;
import org.team708.frc2014.commands.CommandBase;

/**
 *
 * @author Robotics
 */
public class AllianceOrSwag extends CommandBase {
    
    private DriverStation.Alliance currentAlliance;
    private final DriverStation.Alliance redAlliance = DriverStation.Alliance.kRed;
    private final DriverStation.Alliance blueAlliance = DriverStation.Alliance.kBlue;
    
    public AllianceOrSwag() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(sedArray);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        currentAlliance = DriverStation.getInstance().getAlliance();
        
        if (drivetrain.getSwag()) {
            if (currentAlliance.equals(redAlliance)) {
                sedArray.setColor(sedArray.VIOLET);
            } else if (currentAlliance.equals(blueAlliance)) {
                sedArray.setColor(sedArray.TEAL);
            } else {
                sedArray.setColor(sedArray.YELLOW);
            }
        } else {
            if (currentAlliance.equals(redAlliance)) {
                sedArray.setColor(sedArray.RED);
            } else if (currentAlliance.equals(blueAlliance)) {
                sedArray.setColor(sedArray.BLUE);
            } else {
                sedArray.setColor(sedArray.WHITE);
            }
        }
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
