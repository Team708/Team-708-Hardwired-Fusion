
package org.team708.frc2014;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.team708.frc2014.commands.drivetrain.FollowBall;
import org.team708.frc2014.commands.drivetrain.ToggleAntiswagSpeed;
import org.team708.frc2014.commands.drivetrain.ToggleSwagSpeed;
import org.team708.frc2014.commands.intake.DeployIntake;
import org.team708.frc2014.commands.intake.ManualIntake;
import org.team708.frc2014.commands.launcher.ManualBackward;
import org.team708.frc2014.commands.launcher.ManualForward;
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
//    private static final int toggleDriveModeButtonNumber = Gamepad.button_A;
    private static final int holdToFollowBallButtonNumber = Gamepad.button_B;
    private static final int holdToSwagSpeedButtonNumber = Gamepad.button_R_Shoulder;
    private static final int holdToAntiswagSpeedButtonNumber = Gamepad.button_L_Shoulder;
    
    // Operator
    private static final int manualForwardButtonNumber = Gamepad.button_R_Shoulder;
    private static final int manualBackwardButtonNumber = Gamepad.button_L_Shoulder;
    private static final int holdToManualIntakeButtonNumber = Gamepad.button_A;
    private static final int deployIntakeButtonNumber = Gamepad.button_B;
    
    //initialize Gamepads
    public static final Gamepad driverGamepad = new Gamepad(RobotMap.driverGamepad);
    public static final Gamepad operatorGamepad = new Gamepad(RobotMap.operatorGamepad);
    
    /*
     * Driver Buttons
     */
    
    //Switch to swag speed
    public static final Button holdToSwagSpeed = new JoystickButton(driverGamepad, holdToSwagSpeedButtonNumber);
    
    //Switch to crawl speed
    public static final Button holdToAntiswagSpeed = new JoystickButton(driverGamepad, holdToAntiswagSpeedButtonNumber);
    
    //Follow the ball while this button is held
    public static final Button holdToFollowBallButton = new JoystickButton(driverGamepad,holdToFollowBallButtonNumber);
    
//    //Initialize drivetrain switch button
//    public static final Button toggleDriveMode = new JoystickButton(driverGamepad, toggleDriveModeButtonNumber);
    
    /*
     * Operator Buttons
     */
    
    //Makes arm go forward or stop
    public static final Button manualForward = new JoystickButton(operatorGamepad, manualForwardButtonNumber);
    
    //Makes the arm go backward or stop
    public static final Button manualBackward = new JoystickButton(operatorGamepad, manualBackwardButtonNumber);
    
    //Turns on motor for intake and stops when released
    public static final Button holdToManualIntake = new JoystickButton(operatorGamepad, holdToManualIntakeButtonNumber);
    
    //Deploys intake or retracts it back in
    public static final Button deployIntake = new JoystickButton(operatorGamepad, deployIntakeButtonNumber);
    
    public OI() 
    {
        // Driver
//        toggleDriveMode.whenPressed(new ToggleDriveMode());
        holdToSwagSpeed.whileHeld(new ToggleSwagSpeed());
        holdToAntiswagSpeed.whileHeld(new ToggleAntiswagSpeed());
        holdToFollowBallButton.whileHeld(new FollowBall());
        
        // Operator
        manualForward.whenPressed(new ManualForward());
        manualBackward.whenPressed(new ManualBackward());
        holdToManualIntake.whileHeld(new ManualIntake());
        deployIntake.whenPressed(new DeployIntake());
    }
    
    //Is Button Pressed Query Methods
    
//    public static boolean isHoldToFollowButtonPressed()
//    {
//        return driverGamepad.getButton(holdToFollowBallButtonNumber);
//    }
//    
//    public static boolean isHoldToSwagSpeedButtonPressed() {
//        return driverGamepad.getButton(holdToSwagSpeedButtonNumber);
//    }
//    
    public static boolean isHoldToAntiswagSpeedButtonPressed() {
        return driverGamepad.getButton(holdToAntiswagSpeedButtonNumber);
    }
//    
//    public static boolean isHoldToManualIntakeButtonPressed() {
//        return operatorGamepad.getButton(holdToManualIntakeButtonNumber);
//    }
    
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
