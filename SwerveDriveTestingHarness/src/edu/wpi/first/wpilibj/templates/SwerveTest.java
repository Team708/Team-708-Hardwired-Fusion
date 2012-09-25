/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class SwerveTest extends IterativeRobot {

    private Gamepad gamepad;
    private SwerveWheel wheel1, wheel2, wheel3, wheel4;
    public static final Relay.Value kFrontRightHeadingLower = Relay.Value.kForward;
    public static final Relay.Value kFrontRightHeadingHigher = Relay.Value.kReverse;
    public static final Relay.Value kFrontLeftHeadingLower = Relay.Value.kForward;
    public static final Relay.Value kFrontLeftHeadingHigher = Relay.Value.kReverse;
    public static final Relay.Value kRearLeftHeadingLower = Relay.Value.kForward;
    public static final Relay.Value kRearLeftHeadingHigher = Relay.Value.kReverse;
    public static final Relay.Value kRearRightHeadingLower = Relay.Value.kForward;
    public static final Relay.Value kRearRightHeadingHigher = Relay.Value.kReverse;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        wheel1 = new SwerveWheel(Constants.RelayChannels.kFrontRightSpike,
                Constants.PWMChannels.kFrontRightCIM,
                Constants.AnalogInputChannels.kFrontRightPot,
                Constants.DigitalIOChannels.kFrontRightEncoderA,
                Constants.DigitalIOChannels.kFrontRightEncoderB,
                false, Constants.Calibrations.kFrontRightPotLowVlts,
                Constants.Calibrations.kFrontRightPotHighVlts,
                kFrontRightHeadingLower,kFrontRightHeadingHigher);

        wheel2 = new SwerveWheel(Constants.RelayChannels.kFrontLeftSpike,
                Constants.PWMChannels.kFrontLeftCIM,
                Constants.AnalogInputChannels.kFrontLeftPot,
                Constants.DigitalIOChannels.kFrontLeftEncoderA,
                Constants.DigitalIOChannels.kFrontLeftEncoderB,
                false, Constants.Calibrations.kFrontLeftPotLowVlts,
                Constants.Calibrations.kFrontLeftPotHighVlts,
                kFrontLeftHeadingLower,kFrontLeftHeadingHigher);

        wheel3 = new SwerveWheel(Constants.RelayChannels.kRearLeftSpike,
                Constants.PWMChannels.kRearLeftCIM,
                Constants.AnalogInputChannels.kRearLeftPot,
                Constants.DigitalIOChannels.kRearLeftEncoderA,
                Constants.DigitalIOChannels.kRearLeftEncoderB,
                false, Constants.Calibrations.kRearLeftPotLowVlts,
                Constants.Calibrations.kRearLeftPotHighVlts,
                kRearLeftHeadingLower,kRearLeftHeadingHigher);

        wheel4 = new SwerveWheel(Constants.RelayChannels.kRearRightSpike,
                Constants.PWMChannels.kRearRightCIM,
                Constants.AnalogInputChannels.kRearRightPot,
                Constants.DigitalIOChannels.kRearRightEncoderA,
                Constants.DigitalIOChannels.kRearRightEncoderB,
                false, Constants.Calibrations.kRearRightPotLowVlts,
                Constants.Calibrations.kRearRightPotHighVlts,
                kRearRightHeadingLower,kRearRightHeadingHigher);

        gamepad = new Gamepad(1);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        double rotation = gamepad.getAxis(Gamepad.leftStick_X);

        if (gamepad.getButton(Gamepad.button_A)) {
            setRotation(rotation, wheel1);
        }

        if (gamepad.getButton(Gamepad.button_X)) {
            setRotation(rotation, wheel2);
        }

        if (gamepad.getButton(Gamepad.button_Y)) {
            setRotation(rotation, wheel3);
        }

        if (gamepad.getButton(Gamepad.button_B)) {
            setRotation(rotation, wheel4);
        }
    }

    private void setRotation(double rotation, SwerveWheel wheel) {
        if (rotation > 0.0) {
            wheel.increaseHeading();
        } else if (rotation < 0.0) {
            wheel.decreaseHeading();
        } else {
            wheel.stopRotation();
        }
    }
}
