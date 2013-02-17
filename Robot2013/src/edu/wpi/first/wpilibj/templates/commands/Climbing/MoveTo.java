/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.Climbing;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 * This command is used to move the shooter up or down a given number of encoder
 * counts. The speed of each arm is varied in proportion to the encoder
 * difference so that the arms will move in unison.
 *
 * @author Connor Willison
 */
public class MoveTo extends CommandBase {

//    private static double defaultMovementSpeedNoLoad =
//            Preferences.getInstance().getDouble("DefaultMoveSpeedNoLoad", .5);
//    private static double defaultMovementSpeedUnderLoad =
//            Preferences.getInstance().getDouble("DefaultMoveSpeedUnderLoad", .5);
//    private static double maxSpeedAdjustment =
//            Preferences.getInstance().getDouble("maxSpeedAdjustment", .15);
//    private static double maxEncoderDifference =
//            Preferences.getInstance().getDouble("maxEncoderDifference", 100);
//    private static double speedDifferenceTolerance =
//            Preferences.getInstance().getDouble("speedDifferenceTolerance", 5);
//    private static int countsTolerance = Preferences.getInstance().getInt("ClimberPositionTolerance", 10);
    
    private static final double defaultMovementSpeedNoLoad = 1.0;
    private static final double defaultMovementSpeedUnderLoad = 1.0;
    private static final double maxSpeedAdjustment = .15;
    private static final double maxEncoderDifference = 50;
    private static final double speedDifferenceTolerance = 0;
    private static final int countsTolerance = 40;
    
    //number of counts away from goal that the climber begins
    //to decrease speed in order to avoid overshoot.
    private static final int proportionalEngagementCounts = 400;
    
    private int goalCounts = 0;
    private boolean leftArmDone = false;
    private boolean rightArmDone = false;

    public MoveTo(int goalCounts) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        super("Move To " + goalCounts);

        this.goalCounts = goalCounts;
        requires(leftArm);
        requires(rightArm);
//        requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        System.out.println(this.getName() + " initialized");

//        defaultMovementSpeedNoLoad =
//                Preferences.getInstance().getDouble("DefaultMoveSpeedNoLoad", .5);
//        defaultMovementSpeedUnderLoad =
//                Preferences.getInstance().getDouble("DefaultMoveSpeedUnderLoad", .5);
//        maxSpeedAdjustment =
//                Preferences.getInstance().getDouble("MaxSpeedAdjustment", .15);
//        maxEncoderDifference =
//                Preferences.getInstance().getDouble("MaxEncoderDifference", 100);
//        speedDifferenceTolerance =
//                Preferences.getInstance().getDouble("SpeedDifferenceTolerance", 5);
//
//        countsTolerance = Preferences.getInstance().getInt("ClimberPositionTolerance", 15);

        rightArmDone = false;
        leftArmDone = false;
//        leftArm.stop();
//        rightArm.stop();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        System.out.println(this.getName() + " executed");

        int countsDiffLeft = goalCounts - leftArm.getEncoderCounts();
        int countsDiffRight = goalCounts - rightArm.getEncoderCounts();
        
        double leftCoefficient = calcSpeedCoefficient(countsDiffLeft);
        double rightCoefficient = calcSpeedCoefficient(countsDiffRight);

        double speedAdjustment = calcSpeedAdjustment(leftArm.getEncoderCounts() - rightArm.getEncoderCounts());

        System.out.println(this.getName() + "goalCounts=" + goalCounts + " countsdiffleft=" + countsDiffLeft);

        if (!leftArmDone) {
            if (countsDiffLeft > countsTolerance) {
                if (leftArm.isExtended()) {
                    System.out.println("Left Arm Extended");
                    leftArm.stop();
                    leftArmDone = true;
                } else {
//                    System.out.println("Set Left Arm to " + (defaultMovementSpeedNoLoad - speedAdjustment));
                    leftArm.setMotorSpeed((defaultMovementSpeedNoLoad - speedAdjustment) * leftCoefficient);
                    //leftArm.setMotorSpeed(defaultMovementSpeedNoLoad);
                }
            } else if (countsDiffLeft < -countsTolerance) {
                if (leftArm.isRetracted()) {
                    System.out.println("Left Arm Retracted");
                    leftArm.stop();
                    leftArmDone = true;
                } else {
//                    System.out.println("Set Left Arm to " + (-defaultMovementSpeedUnderLoad - speedAdjustment));
                    leftArm.setMotorSpeed((-defaultMovementSpeedUnderLoad - speedAdjustment) * leftCoefficient);
                    //leftArm.setMotorSpeed(-defaultMovementSpeedUnderLoad);
                }
            } else {
                System.out.println("Left Arm In Range");
                leftArm.stop();
                leftArmDone = true;
            }
        }

        System.out.println(this.getName() + "goalCounts=" + goalCounts + " countsdiffright=" + countsDiffRight);
        if (!rightArmDone) {
            if (countsDiffRight > countsTolerance) {
                if (rightArm.isExtended()) {
                    System.out.println("Right Arm Extended");
                    rightArm.stop();
                    rightArmDone = true;
                } else {
//                    System.out.println("Set Right Arm to " + (defaultMovementSpeedNoLoad + speedAdjustment));
                    rightArm.setMotorSpeed((defaultMovementSpeedNoLoad + speedAdjustment) * rightCoefficient);
                    //rightArm.setMotorSpeed(defaultMovementSpeedNoLoad);
                }
            } else if (countsDiffRight < -countsTolerance) {
                if (rightArm.isRetracted()) {
                    System.out.println("Right Arm Retracted");
                    rightArm.stop();
                    rightArmDone = true;
                } else {
//                    System.out.println("Set Right Arm to " + (-defaultMovementSpeedNoLoad + speedAdjustment));
                    rightArm.setMotorSpeed((-defaultMovementSpeedUnderLoad + speedAdjustment) * rightCoefficient);
                    //rightArm.setMotorSpeed(-defaultMovementSpeedUnderLoad);
                }
            } else {
                System.out.println("Right Arm In Range");

                rightArm.stop();
                rightArmDone = true;
            }
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return leftArmDone && rightArmDone;
    }

    private double calcSpeedAdjustment(int encoderDifference) {
        if (Math.abs(encoderDifference) > speedDifferenceTolerance) {
            double speedAdjustment = maxSpeedAdjustment * encoderDifference / maxEncoderDifference;

            speedAdjustment = Math.min(maxSpeedAdjustment, speedAdjustment);
            speedAdjustment = Math.max(-maxSpeedAdjustment, speedAdjustment);

            return speedAdjustment;
        } else {
            return 0.0;
        }
    }
    
    private double calcSpeedCoefficient(int countsToGoal) {
        if (Math.abs(countsToGoal) < proportionalEngagementCounts) {
            return Math.max((double)Math.abs(countsToGoal) / proportionalEngagementCounts, .8);
        } else {
            return 1.0;
        }
    }

    // Called once after isFinished returns true
    protected void end() {
        System.out.println(this.getName() + " finished");
        leftArm.stop();
        rightArm.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        System.out.println(this.getName() + " interrupted");
        this.end();
    }
}
