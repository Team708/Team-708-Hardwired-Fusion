

package org.team708.frc2014;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.team708.frc2014.commands.LEDs.ToggleLED;
import org.team708.frc2014.commands.drivetrain.DriveForwardToTargetUltrasonic;
import org.team708.frc2014.commands.drivetrain.Stop;
import org.team708.frc2014.commands.drivetrain.ToggleSwagSpeed;
import org.team708.frc2014.commands.intake.DeployIntake;
import org.team708.frc2014.commands.intake.RetractIntake;
import org.team708.frc2014.commands.launcher.LauncherGoalShot;
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
    private static final int overrideAutoButtonNumber = Gamepad.button_L_Shoulder;
    private static final int holdToSwagSpeedButtonNumber = Gamepad.button_R_Shoulder;
    private static final int activateAutoShootButtonNumber = Gamepad.button_A;
    private static final int activateAutoPassButtonNumber = Gamepad.button_B;
    private static final int toggleLEDButtonNumber = Gamepad.button_X;
    
    // Operator
    private static final int deployIntakeButtonNumber = Gamepad.button_B;
    private static final int retractIntakeButtonNumber = Gamepad.button_A;
    private static final int goalShotButtonNumber = Gamepad.button_L_Shoulder;
    
    //initialize Gamepads
    public static final Gamepad driverGamepad = new Gamepad(RobotMap.driverGamepad);
    public static final Gamepad operatorGamepad = new Gamepad(RobotMap.operatorGamepad);
    
    /*
     * Driver Buttons
     */
    
    //Overrides any auto commands
    public static final Button overrideAuto = new JoystickButton(driverGamepad, overrideAutoButtonNumber);
    
    //Switch to swag speed
    public static final Button holdToSwagSpeed = new JoystickButton(driverGamepad, holdToSwagSpeedButtonNumber);
    
    // Activates auto shoot distance finding
    public static final Button activateAutoShoot = new JoystickButton(driverGamepad, activateAutoShootButtonNumber);
    
    // Activates auto pass distance finding
    public static final Button activateAutoPass = new JoystickButton(driverGamepad, activateAutoPassButtonNumber);
    
    // Toggles the state of the LEDs
    public static final Button toggleLED = new JoystickButton(driverGamepad, toggleLEDButtonNumber);
    
//    //Follow the ball while this button is held
//    public static final Button holdToFollowBallButton = new JoystickButton(driverGamepad,holdToFollowBallButtonNumber);
    
    /*
     * Operator Buttons
     */
    
//  // Deploys the intake
    public static final Button deployIntake = new JoystickButton(operatorGamepad, deployIntakeButtonNumber);
    
    // Retracts the intake
    public static final Button retractIntake = new JoystickButton(operatorGamepad, retractIntakeButtonNumber);
    
    // Cycles through the launch sequence
    public static final Button goalShot = new JoystickButton(operatorGamepad, goalShotButtonNumber);
    
    public OI() 
    {
        // Driver
        overrideAuto.whenPressed(new Stop());
        holdToSwagSpeed.whileHeld(new ToggleSwagSpeed());
        activateAutoShoot.whenPressed(new DriveForwardToTargetUltrasonic(2));
        activateAutoPass.whenPressed(new DriveForwardToTargetUltrasonic(1));
        toggleLED.whenPressed(new ToggleLED());
        
        // Operator
        deployIntake.whenPressed(new DeployIntake());
        retractIntake.whenPressed(new RetractIntake());
        goalShot.whenPressed(new LauncherGoalShot());
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
