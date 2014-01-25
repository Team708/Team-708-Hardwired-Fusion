
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.templates.commands.catapult.LaunchBall;
import edu.wpi.first.wpilibj.templates.commands.drivetrain.ToggleDriveMode;
import edu.wpi.first.wpilibj.templates.commands.drivetrain.ToggleOverdrive;
import utilclasses.Gamepad;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
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
    
    //initialize Gamepad
    public static final Gamepad driverGamepad = new Gamepad(RobotMap.driverGamepad);
    public static final Gamepad operatorGamepad = new Gamepad(RobotMap.operatorGamepad);
    
    /*
     * Driver buttons
     */
    
    //Initialises drivetrain mode switch button
    public static final Button toggleDriveMode = new JoystickButton(driverGamepad, Gamepad.button_A);
    
    // Initialises drivetrain overdrive toggle button
    public static final Button toggleOverdrive = new JoystickButton(driverGamepad, Gamepad.button_R_Shoulder);
    
    /*
     * Operator buttons
     */
    
    //Initialises button that launches ball from catapult
    public static final Button launchBall = new JoystickButton(operatorGamepad, Gamepad.button_R_Shoulder);
    
    public OI() 
    {
        // Driver button actions
        toggleDriveMode.whenPressed(new ToggleDriveMode());
        toggleOverdrive.whenPressed(new ToggleOverdrive());
        
        // Operator button actions
        launchBall.whenPressed(new LaunchBall());
    }
}
