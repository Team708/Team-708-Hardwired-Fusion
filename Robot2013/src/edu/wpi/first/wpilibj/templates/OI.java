
package edu.wpi.first.wpilibj.templates;

import utilclasses.Gamepad;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.templates.commands.Climbing.DownwardStroke;
import edu.wpi.first.wpilibj.templates.commands.Climbing.Home;
import edu.wpi.first.wpilibj.templates.commands.Climbing.ManualClimb;
import edu.wpi.first.wpilibj.templates.commands.Climbing.UpwardStroke;
import edu.wpi.first.wpilibj.templates.commands.Driving.Shift;
import edu.wpi.first.wpilibj.templates.commands.Shooting.ManualFeed;
import edu.wpi.first.wpilibj.templates.commands.Shooting.ManualSpinUp;
import edu.wpi.first.wpilibj.templates.commands.Shooting.RetractFeeder;

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
    // Button button = new DigitalIOButton(1);
    
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
    
    private Gamepad driverGamepad;
    private Gamepad operatorGamepad;
	
	//gamepad button assignments
    private static final int shiftButtonNumber = Gamepad.button_L_Shoulder;
    private static final int climberOverrideButtonNumber = Gamepad.button_L_Shoulder;
    private static final int shooterOverrideButtonNumber = Gamepad.button_A;
    
    public OI()
    {
        driverGamepad = new Gamepad(RobotMap.driverGamepad);
        operatorGamepad = new Gamepad(RobotMap.operatorGamepad);
        
//        Button tankDriveButton = new JoystickButton(driverGamepad,Gamepad.button_Start);
//        tankDriveButton.whenPressed(new TankDrive());
//        
//        Button arcadeDriveButton = new JoystickButton(driverGamepad,Gamepad.button_Back);
//        arcadeDriveButton.whenPressed(new ArcadeDrive());
        
        Button shiftButton = new JoystickButton(driverGamepad,shiftButtonNumber);
        shiftButton.whenPressed(new Shift());
        
        Button climbUpwardStrokeButton = new JoystickButton(driverGamepad,Gamepad.button_Y);
        climbUpwardStrokeButton.whenPressed(new UpwardStroke());
        
        Button climbDownwardStrokeButton = new JoystickButton(driverGamepad,Gamepad.button_A);
        climbDownwardStrokeButton.whenPressed(new DownwardStroke());
        
        Button climbHomeButton = new JoystickButton(driverGamepad,Gamepad.button_B);
        climbHomeButton.whenPressed(new Home());
        
        Button manualClimbButton = new JoystickButton(driverGamepad,climberOverrideButtonNumber);
        manualClimbButton.whenPressed(new ManualClimb());
        
        Button manualSpinUpButton = new JoystickButton(operatorGamepad,shooterOverrideButtonNumber);
        manualSpinUpButton.whenPressed(new ManualSpinUp());
        
        Button manualFeedButton = new JoystickButton(operatorGamepad,Gamepad.button_R_Shoulder);
        manualFeedButton.whenPressed(new ManualFeed());
        manualFeedButton.whenReleased(new RetractFeeder());
    }
    
    public void sendGamepads()
    {
        driverGamepad.sendAxesToDashboard();
        operatorGamepad.sendAxesToDashboard();
    }
    
    public void sendGamepad()
    {
        driverGamepad.sendAxesToDashboard();
    }
    
    public double getArcadeMovementAxis()
    {
        return -driverGamepad.getAxis(Gamepad.leftStick_Y);
    }
    
    public double getArcadeRotationAxis()
    {
        return driverGamepad.getAxis(Gamepad.rightStick_X);
    }
    
    public double getTankLeftAxis()
    {
        return -driverGamepad.getAxis(Gamepad.leftStick_Y);
    }
    
    public double getTankRightAxis()
    {
        return -driverGamepad.getAxis(Gamepad.rightStick_Y);
    }

    public boolean isShiftButtonHeld()
    {
	return driverGamepad.getButton(shiftButtonNumber);
    }
    
    public boolean isClimberOverrideButtonHeld()
    {
	return driverGamepad.getButton(climberOverrideButtonNumber);
    }
    
    public boolean isShooterOverrideButtonHeld()
    {
	return operatorGamepad.getButton(shooterOverrideButtonNumber);
    }
    
    //may need to read an axis on operator stick to set shooter speed
    
    public double getClimbingLeftAxis()
    {
        return driverGamepad.getAxis(Gamepad.leftStick_Y);
    }
    
    public double getClimbingRightAxis()
    {
        return driverGamepad.getAxis(Gamepad.rightStick_Y);
    }
    
}

