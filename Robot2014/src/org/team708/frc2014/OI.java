

package org.team708.frc2014;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
//import org.team708.frc2014.commands.LEDs.ToggleSED;
import org.team708.frc2014.commands.LEDs.ToggleLED;
import org.team708.frc2014.commands.drivetrain.DriveForwardToTargetUltrasonic;
import org.team708.frc2014.commands.drivetrain.ToggleSwagSpeed;
import org.team708.frc2014.commands.intake.DeployIntake;
import org.team708.frc2014.commands.intake.ManualDispense;
import org.team708.frc2014.commands.intake.ManualIntake;
import org.team708.frc2014.commands.intake.PassBall;
import org.team708.frc2014.commands.intake.RetractIntake;
import org.team708.frc2014.commands.launcher.LauncherGoalShot;
import org.team708.frc2014.commands.launcher.LauncherMoveToDefaultBall;
import org.team708.frc2014.commands.launcher.LauncherTrussShot;
import org.team708.util.Gamepad;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    /*
     * Define Button Constants
     */
    
    // Driver
//    private static final int holdToFollowBallButtonNumber = Gamepad.button_B;
    private static final int holdToSwagSpeedButtonNumber = Gamepad.button_R_Shoulder;
    private static final int activateAutoPassButtonNumber = Gamepad.button_Y;
    private static final int activateAutoShootButtonNumber = Gamepad.button_A;
    private static final int toggleLEDButtonNumber = Gamepad.button_X;
    private static final int allOnSEDButtonNumber = Gamepad.button_B;
    
    // Operator
//    private static final int holdToManualIntakeButtonNumber = Gamepad.button_A;
//    private static final int holdToManualDispenseButtonNumber = Gamepad.button_B;
//    private static final int toggleIntakeButtonNumber = Gamepad.button_Y;
    private static final int deployIntakeButtonNumber = Gamepad.button_B;
    private static final int retractIntakeButtonNumber = Gamepad.button_A;
    private static final int passBallButtonNumber = Gamepad.button_L_Shoulder;
    private static final int launchBallButtonNumber = Gamepad.button_R_Shoulder;
    private static final int trussShotButtonNumber = Gamepad.button_X;
    private static final int armMoveHasBallButtonNumber = Gamepad.button_Y;
    
    //initialize Gamepads
    public static final Gamepad driverGamepad = new Gamepad(RobotMap.driverGamepad);
    public static final Gamepad operatorGamepad = new Gamepad(RobotMap.operatorGamepad);
    
    /*
     * Driver Buttons
     */
    
    //Switch to swag speed
    public static final Button holdToSwagSpeed = new JoystickButton(driverGamepad, holdToSwagSpeedButtonNumber);
    
    //Activates auto pass distance finding
    public static final Button activateAutoPass = new JoystickButton(driverGamepad, activateAutoPassButtonNumber);
    
    // Activates auto shoot distance finding
    public static final Button activateAutoShoot = new JoystickButton(driverGamepad, activateAutoShootButtonNumber);
    
    // Toggles the state of the LEDs
    public static final Button toggleLED = new JoystickButton(driverGamepad, toggleLEDButtonNumber);
    
    public static final Button allOnSED = new JoystickButton(driverGamepad, allOnSEDButtonNumber);
    
//    //Follow the ball while this button is held
//    public static final Button holdToFollowBallButton = new JoystickButton(driverGamepad,holdToFollowBallButtonNumber);
    
    /*
     * Operator Buttons
     */
    
//    //Turns on motor for intake and stops when released
//    public static final Button holdToManualIntake = new JoystickButton(operatorGamepad, holdToManualIntakeButtonNumber);
//    
//    // Turns on motor for dispensing ball and stops when released
//    public static final Button holdToManualDispense = new JoystickButton(operatorGamepad, holdToManualDispenseButtonNumber);
    
    // Deploys the intake
    public static final Button deployIntake = new JoystickButton(operatorGamepad, deployIntakeButtonNumber);
    
    // Retracts the intake
    public static final Button retractIntake = new JoystickButton(operatorGamepad, retractIntakeButtonNumber);
    
    // Automatically closes the intake and passes the ball
    public static final Button passBall = new JoystickButton(operatorGamepad, passBallButtonNumber);
    
    // Cycles through the launch sequence
    public static final Button launchBall = new JoystickButton(operatorGamepad, launchBallButtonNumber);
    
//    // Moves the arm to the truss shot area
//    public static final Button trussShot = new JoystickButton(operatorGamepad, trussShotButtonNumber);
//    
//    public static final Button armMoveHasBall = new JoystickButton(operatorGamepad, armMoveHasBallButtonNumber);
    
//    // Toggles Intake
//    public static final Button toggleIntake = new JoystickButton(operatorGamepad, toggleIntakeButtonNumber);
    
    public OI() 
    {
        // Driver
        holdToSwagSpeed.whileHeld(new ToggleSwagSpeed());
//        activateAutoPass.whenPressed(new DriveForwardToTargetUltrasonic(1));
        activateAutoShoot.whenPressed(new DriveForwardToTargetUltrasonic(0));
        toggleLED.whenPressed(new ToggleLED());
//        allOnSED.whenPressed(new ToggleSED());
//        holdToFollowBallButton.whileHeld(new FollowBall());
        
        // Operator
//        holdToManualIntake.whileHeld(new ManualIntake());
//        holdToManualDispense.whileHeld(new ManualDispense());
        deployIntake.whenPressed(new DeployIntake());
        retractIntake.whenPressed(new RetractIntake());
//        passBall.whenPressed(new PassBall());
//        launchBall.whenPressed(new LauncherGoalShot());
//        trussShot.whenPressed(new LauncherTrussShot());
//        armMoveHasBall.whenPressed(new LauncherMoveToDefaultBall());
    }
    
        //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // Another type of button you can create is a DigitalIOButton, which is
    // a button or switch hooked up to the cypress module. These are useful if
    // you want to build a customized operator interface.
    //Button button = new DigitalIOButton(1);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}
