/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.commands.SEDs;

import edu.wpi.first.wpilibj.DriverStation;
import org.team708.frc2014.commands.CommandBase;
import org.team708.frc2014.subsystems.SEDArray;

/**
 *
 * @author Robotics
 */
public class AllianceOrSwag extends CommandBase {
    
    private DriverStation.Alliance currentAlliance;
    public final DriverStation.Alliance RED_ALLIANCE = DriverStation.Alliance.kRed;
    public final DriverStation.Alliance BLUE_ALLIANCE = DriverStation.Alliance.kBlue;
    
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
        
        if (drivetrain.getSwagDrive()) {
            if (currentAlliance.equals(RED_ALLIANCE)) {
                sedArray.setColor(SEDArray.VIOLET);
            } else if (currentAlliance.equals(BLUE_ALLIANCE)) {
                sedArray.setColor(SEDArray.TEAL);
            } else {
                sedArray.setColor(SEDArray.YELLOW);
            }
        } else {
            if (currentAlliance.equals(RED_ALLIANCE)) {
                sedArray.setColor(SEDArray.RED);
            } else if (currentAlliance.equals(BLUE_ALLIANCE)) {
                sedArray.setColor(SEDArray.BLUE);
            } else {
                sedArray.setColor(SEDArray.WHITE);
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
