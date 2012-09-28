/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import bridge.Arm;
import bridge.Balancer;
import button.Button;
import button.LatchWhenPressedButton;
import button.SwitchUntilReleasedButton;
import button.SwitchWhenPressedButton;
import distance.DistanceSensor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.templates.Drivetrain;
import edu.wpi.first.wpilibj.templates.Manipulator;
import harvester.Harvester;
import harvester.Hold;
import scripting.Configurable;
import scripting.Configuration;
import scripting.ScriptEngine;
import shooter.Shooter;

/**
 * @author Connor Willison
 */
public class DriverStation708 implements Configurable {

    private DriverStation ds;
    private Drivetrain robot;
    private ScriptEngine engine;
    private Harvester harvester;
    private Hold hold;
    private Shooter shooter;
    private Arm arm;
    private static double DEFAULT_SCALE = .90;
    private static double NUDGE_SCALE = .65;
    private double joystickScaleFactor = DEFAULT_SCALE;
    private MessageCenter mc;
    private int updateCounter = 0;
    public Gamepad driver_Gamepad;
    public Gamepad operator_Gamepad;
    //driver controls
    private LatchWhenPressedButton nextAutoMode;
    private LatchWhenPressedButton prevAutoMode;
    private LatchWhenPressedButton nextScreen;
    private LatchWhenPressedButton prevScreen;
    private LatchWhenPressedButton resetEncoders;
    private SwitchWhenPressedButton nudgeToggle;
    private SwitchUntilReleasedButton autoBalance;
    private SwitchUntilReleasedButton stopBrush;
    private SwitchUntilReleasedButton reverseBrush;
    private LatchWhenPressedButton armUp;
    private LatchWhenPressedButton armDown;
    //operator controls
    private SwitchUntilReleasedButton autoAim;
    private SwitchUntilReleasedButton manualAim;
    private SwitchUntilReleasedButton shoot;
    private SwitchUntilReleasedButton rotateLeft;
    private SwitchUntilReleasedButton rotateRight;
    private boolean dsOutput = true; // = false
    //joystick acceleration vars - previous values
    private double ply = 0.0,
            prx = 0.0;
    //joystick acceleration vars - final, after processing
    private double fly = 0.0,
            frx = 0.0;
    private static double accelFactor = 0.0;
    private static double turnFactor = 0.0;
    private boolean brushReversed = false;

    public DriverStation708(ScriptEngine engine, Drivetrain robot, Harvester harvester,
            Hold hold, Shooter shooter, Arm arm) {
        driver_Gamepad = new Gamepad(IOConstants.Driverstation.driver_Gamepad);
        operator_Gamepad = new Gamepad(IOConstants.Driverstation.operator_Gamepad);

        this.robot = robot;
        this.engine = engine;
        this.harvester = harvester;
        this.shooter = shooter;
        this.hold = hold;
        this.arm = arm;
        mc = MessageCenter.getInstance();
        ds = DriverStation.getInstance();

        /*
         * Driver Gamepad Controls
         */

        //change autonomous mode
        nextAutoMode = new LatchWhenPressedButton(driver_Gamepad, Gamepad.button_Y, "Next Auto Mode.");
        prevAutoMode = new LatchWhenPressedButton(driver_Gamepad, Gamepad.button_X, "Previous Auto Mode.");

        //change driverstation display screen
        nextScreen = new LatchWhenPressedButton(driver_Gamepad, Button.TYPE_JOYSTICK_AXIS_POSITIVE, Gamepad.dpadAxis, "Next Screen.");
        prevScreen = new LatchWhenPressedButton(driver_Gamepad, Button.TYPE_JOYSTICK_AXIS_NEGATIVE, Gamepad.dpadAxis, "Previous Screen.");

        //reset encoders (and encoder rotation) to 0.
        resetEncoders = new LatchWhenPressedButton(driver_Gamepad, Gamepad.button_Back, "Reset encoders.");

        //toggle nudge (low speed) mode
        nudgeToggle = new SwitchWhenPressedButton(driver_Gamepad, Gamepad.button_A, "Toggle nudge mode.");

        //auto balancing function
        autoBalance = new SwitchUntilReleasedButton(driver_Gamepad, Gamepad.button_B, "Auto-balancing feature for bridge.");

        //brush controls
        stopBrush = new SwitchUntilReleasedButton(driver_Gamepad, Button.TYPE_JOYSTICK_AXIS_NEGATIVE, Gamepad.shoulderAxis, "Stop the brush.");

        reverseBrush = new SwitchUntilReleasedButton(driver_Gamepad, Button.TYPE_JOYSTICK_AXIS_POSITIVE, Gamepad.shoulderAxis, "Reverse brush direction.");

        //arm control
        armUp = new LatchWhenPressedButton(driver_Gamepad, Gamepad.button_R_Shoulder, "Raise the arm.");
        armDown = new LatchWhenPressedButton(driver_Gamepad, Gamepad.button_L_Shoulder, "Lower the arm.");

        /*
         * Operator Gamepad Controls
         */

        //shoot a ball (only functions if prepareToShoot() is held and shooter is ready)
        shoot = new SwitchUntilReleasedButton(operator_Gamepad, Gamepad.button_R_Shoulder, "Begins firing balls from the shooter.");

        //prepare the shooter for firing - 3 flags (aimed, ball loaded, spun up)
        autoAim = new SwitchUntilReleasedButton(operator_Gamepad, Gamepad.button_L_Shoulder, "Automatic aiming mode.");

        manualAim = new SwitchUntilReleasedButton(operator_Gamepad, Button.TYPE_JOYSTICK_AXIS_POSITIVE, Gamepad.shoulderAxis, "Manual aiming mode.");

        rotateLeft = new SwitchUntilReleasedButton(operator_Gamepad, Button.TYPE_JOYSTICK_AXIS_POSITIVE, Gamepad.dpadAxis, "Rotate shooter left.");

        rotateRight = new SwitchUntilReleasedButton(operator_Gamepad, Button.TYPE_JOYSTICK_AXIS_NEGATIVE, Gamepad.dpadAxis, "Rotate shooter right.");

        //register config
        Configuration.getInstance().register("Driverstation", this);
    }

    /*
     * Checks for button presses and joystick input.
     */
    public void input() {

        /*
         * Driver Gamepad Controls
         */
        //change driverstation screen - uses dpadaxis, shares with shooterOverride
        if (nextScreen.getButtonState()) {
            mc.nextScreenNumber();
        } else if (prevScreen.getButtonState()) {
            mc.prevScreenNumber();
        }

        if (!ds.isAutonomous()) {
            processJoystick(-driver_Gamepad.getAxis(Gamepad.leftStick_Y) * joystickScaleFactor,
                    //driver_Gamepad.getAxis(Gamepad.rightStick_X) * joystickScaleFactor
                    0.0);
            //robot.drive(fly, frx);
            robot.drive(fly, driver_Gamepad.getAxis(Gamepad.rightStick_X) * joystickScaleFactor);

            //power the brush
            if (stopBrush.getButtonState()) {
                if (!harvester.isOverriden()) {
                    harvester.setOverride(true);
                }

                harvester.setBrushes(Relay.Value.kOff);
            } else if (reverseBrush.getButtonState()) {
                if (!harvester.isOverriden()) {
                    harvester.setOverride(true);
                }
                if (!brushReversed) {
                    harvester.reverseBrush();
                    brushReversed = true;
                }
                //do not give control to harvester if brush is being used by arm function
            } else {
                brushReversed = false;
            }

            //reset encs
            if (resetEncoders.getButtonState()) {
                robot.resetEncoders();
            }

            //change autonomous mode
            if (nextAutoMode.getButtonState()) {
                engine.nextAutoMode();
            } else if (prevAutoMode.getButtonState()) {
                engine.previousAutoMode();
            }

            //adjust input scale factor based on nudge mode - shares buttons with shooterOverride
            if (nudgeToggle.getButtonState()) {
                joystickScaleFactor = NUDGE_SCALE;
            } else {
                joystickScaleFactor = DEFAULT_SCALE;
            }

            //arm control
            if (armUp.getButtonState()) {
                arm.raise();
            } else if (armDown.getButtonState()) {
                arm.lower();
            }

            //give control back to harvester if no brush related buttons are pressed.
            if (!stopBrush.getButtonState() && !reverseBrush.getButtonState()) {
                if (harvester.isOverriden()) {
                    harvester.setOverride(false);
                }
            }

            //auto balance function - shares button with shooterOverride
//        if (autoBalance.getButtonState()) {
//            if (!balancer.isEnabled()) {
//                balancer.startBalancing();
//            }
//        } else {
//            balancer.stopBalancing();
//        }      //prepare shooter for firing

            /*
             * Operator Controls
             */
            //engage manual aim mode
            shooter.setFlags(manualAim.getButtonState(),
                    autoAim.getButtonState(),
                    rotateLeft.getButtonState(),
                    rotateRight.getButtonState(),
                    shoot.getButtonState());

        }
    }

    /*
     * All driverstation messages here.
     */
    public void output() {

        if (++updateCounter >= 50) {
//            if (++updateCounter >= 10) {

            mc.println(MessageCenter.kFirstScreen, MessageCenter.kLine1, "AMode: " + engine.getAutonomousModeName());

            if (dsOutput) {

                mc.println(MessageCenter.kFirstScreen, MessageCenter.kLine2, "Shoulder: " + driver_Gamepad.getAxis(Gamepad.shoulderAxis));
                mc.println(MessageCenter.kFirstScreen, MessageCenter.kLine3, "RightX: " + driver_Gamepad.getAxis(Gamepad.rightStick_X));
                mc.println(MessageCenter.kFirstScreen, MessageCenter.kLine4, "RightY: " + driver_Gamepad.getAxis(Gamepad.rightStick_Y));
                mc.println(MessageCenter.kFirstScreen, MessageCenter.kLine5, "LeftX: " + driver_Gamepad.getAxis(Gamepad.leftStick_X));
                mc.println(MessageCenter.kFirstScreen, MessageCenter.kLine6, "LeftY: " + driver_Gamepad.getAxis(Gamepad.leftStick_Y));


                mc.println(MessageCenter.kSecondScreen, MessageCenter.kLine1, "Rotation: " + robot.getRotation().getAngle());
                mc.println(MessageCenter.kSecondScreen, MessageCenter.kLine2, "LeftDist: " + robot.getLeftDistance());
                mc.println(MessageCenter.kSecondScreen, MessageCenter.kLine3, "RightDist: " + robot.getRightDistance());
                mc.println(MessageCenter.kSecondScreen, MessageCenter.kLine4, "LeftCount: " + robot.getLeftPulseCount());
                mc.println(MessageCenter.kSecondScreen, MessageCenter.kLine5, "RightCount: " + robot.getRightPulseCount());
                mc.println(MessageCenter.kSecondScreen, MessageCenter.kLine6, "Rotation: " + shooter.getRotation());

                DistanceSensor s;

                mc.println(MessageCenter.kThirdScreen, MessageCenter.kLine1, "Top: " + hold.getTop().hasBall());

                if (hold.getTop() instanceof DistanceSensor) {

                    mc.println(MessageCenter.kFifthScreen, MessageCenter.kLine1, "Top: " + ((DistanceSensor) hold.getTop()).getDistance());
                }


                mc.println(MessageCenter.kThirdScreen, MessageCenter.kLine2, "Entrance: " + hold.getEntrance().hasBall());

                if (hold.getEntrance() instanceof DistanceSensor) {

                    mc.println(MessageCenter.kFifthScreen, MessageCenter.kLine2, "Entrance: " + ((DistanceSensor) hold.getEntrance()).getDistance());
                }

                mc.println(MessageCenter.kThirdScreen, MessageCenter.kLine3, "Middle: " + hold.getMiddle().hasBall());

                if (hold.getMiddle() instanceof DistanceSensor) {

                    mc.println(MessageCenter.kFifthScreen, MessageCenter.kLine3, "Middle: " + ((DistanceSensor) hold.getMiddle()).getDistance());
                }

                mc.println(MessageCenter.kThirdScreen, MessageCenter.kLine4, "Bottom: " + hold.getBottom().hasBall());

                if (hold.getBottom() instanceof DistanceSensor) {

                    mc.println(MessageCenter.kFifthScreen, MessageCenter.kLine4, "Bottom: " + ((DistanceSensor) hold.getBottom()).getDistance());
                }

                mc.println(MessageCenter.kThirdScreen, MessageCenter.kLine5, "Numballs: " + hold.getNumBalls());

                mc.println(MessageCenter.kFourthScreen, MessageCenter.kLine1, "Offset: " + shooter.getTrackerMovement());
                mc.println(MessageCenter.kFourthScreen, MessageCenter.kLine2, "PartCount: " + shooter.getTrackerParticleCount());
                mc.println(MessageCenter.kFourthScreen, MessageCenter.kLine3, "Distance: " + shooter.getTrackerDistance());
                mc.println(MessageCenter.kFourthScreen, MessageCenter.kLine4, "CalcRPM: " + shooter.getTargetSpeed());
                mc.println(MessageCenter.kFourthScreen, MessageCenter.kLine5, "CalcPWM: " + shooter.getPWM());

                mc.println(MessageCenter.kSixthScreen, MessageCenter.kLine1, "ArmDown: " + arm.isDown());
                mc.println(MessageCenter.kSixthScreen, MessageCenter.kLine2, "ArmUp: " + arm.isUp());
                mc.println(MessageCenter.kSixthScreen, MessageCenter.kLine3, "Arm Pot: " + arm.getPotValue());
                mc.println(MessageCenter.kSixthScreen, MessageCenter.kLine4, "Tilt: " + arm.getTilt());
                mc.println(MessageCenter.kSixthScreen, MessageCenter.kLine5, "ShooterPot: " + shooter.getRotation());
                ;



                updateCounter = 0;
            }

            mc.display();
        }


    }

    private double process(double raw, double previous, double factor) {
        return previous + (raw - previous) * factor;
    }

    private void processJoystick(double movement, double rotation) {
        fly = process(movement, ply, accelFactor);
        frx = process(rotation, prx, turnFactor);

        ply = fly;
        prx = frx;
    }

    public void update(String param, String[] values) {
        if (param.equalsIgnoreCase("accelerationfactor=")) {
            if (values.length > 0) {
                accelFactor = Double.parseDouble(values[0]);
            }
        } else if (param.equalsIgnoreCase("turnfactor=")) {
            if (values.length > 0) {
                turnFactor = Double.parseDouble(values[0]);
            }
        } else if (param.equalsIgnoreCase("defaultscale=")) {
            if (values.length > 0) {
                DEFAULT_SCALE = Double.parseDouble(values[0]);
            }
        } else if (param.equalsIgnoreCase("nudgescale=")) {
            if (values.length > 0) {
                NUDGE_SCALE = Double.parseDouble(values[0]);
            }
        } else if (param.equalsIgnoreCase("dsoutput=")) {
            if (values.length > 0) {
                dsOutput = values[0].equalsIgnoreCase("true");
            }
        }
    }
}
