
package edu.wpi.first.wpilibj.templates;

import utilclasses.Gamepad;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.templates.commands.AbortCommands;
import edu.wpi.first.wpilibj.templates.commands.Climbing.ClimbPyramid;
import edu.wpi.first.wpilibj.templates.commands.Climbing.ExtendArms;
import edu.wpi.first.wpilibj.templates.commands.Climbing.GoHome;
import edu.wpi.first.wpilibj.templates.commands.Climbing.RetractArms;
import edu.wpi.first.wpilibj.templates.commands.Climbing.ToggleLiftRobot;
import edu.wpi.first.wpilibj.templates.commands.Climbing.ToggleTipRobot;
import edu.wpi.first.wpilibj.templates.commands.Driving.Shift;
import edu.wpi.first.wpilibj.templates.commands.Shooting.DecreaseShooterSpeed;
import edu.wpi.first.wpilibj.templates.commands.Shooting.IncreaseShooterSpeed;
import edu.wpi.first.wpilibj.templates.commands.Shooting.ManualFeed;
import edu.wpi.first.wpilibj.templates.commands.Shooting.RetractFeeder;
import edu.wpi.first.wpilibj.templates.commands.Shooting.TeleopPrepareToShoot;

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
    private static final int shiftButtonNumber = Gamepad.button_R_Shoulder;
//    private static final int climberOverrideButtonNumber = Gamepad.button_L_Shoulder;
//    private static final int shooterOverrideButtonNumber = Gamepad.button_A;
//    private static final int shooterJoystickControlButtonNumber = Gamepad.button_X;
    private static final int prepareToShootButtonNumber = Gamepad.button_L_Shoulder;
    
    public OI()
    {
        driverGamepad = new Gamepad(RobotMap.driverGamepad);
        operatorGamepad = new Gamepad(RobotMap.operatorGamepad);
        
//        Button tankDriveButton = new JoystickButton(driverGamepad,Gamepad.button_Start);
//        tankDriveButton.whenPressed(new TankDrive());
//        
//        Button arcadeDriveButton = new JoystickButton(driverGamepad,Gamepad.button_Back);
//        arcadeDriveButton.whenPressed(new ArcadeDrive());
        
        //Driver Controls
        Button shiftButton = new JoystickButton(driverGamepad,shiftButtonNumber);
        shiftButton.whenPressed(new Shift());
        
        Button driverAbortButton = new JoystickButton(driverGamepad,Gamepad.button_Back);
        driverAbortButton.whenPressed(new AbortCommands());

//        Button moveToButton =  new JoystickButton(driverGamepad,Gamepad.button_RightStick);
//        moveToButton.whenPressed(new MoveTo(900));
        
        //Operator Controls       
        Button climbUpwardStrokeButton = new JoystickButton(operatorGamepad,Gamepad.button_LeftStick);
        climbUpwardStrokeButton.whenPressed(new ExtendArms());

        Button climbDownwardStrokeButton = new JoystickButton(operatorGamepad,Gamepad.button_RightStick);
        climbDownwardStrokeButton.whenPressed(new RetractArms());
        
        Button climbHomeButton = new JoystickButton(operatorGamepad,Gamepad.button_Start);
        climbHomeButton.whenPressed(new GoHome());
        
        Button climbPyramidButton = new JoystickButton(operatorGamepad,Gamepad.button_A);
        climbPyramidButton.whenPressed(new ClimbPyramid());
        
        Button liftRobot = new JoystickButton(operatorGamepad,Gamepad.button_X);
        liftRobot.whenPressed(new ToggleLiftRobot());
//        liftRobot.whenPressed(new LiftRobot());
//        liftRobot.whenReleased(new LowerRobot());
        
        Button tipRobot = new JoystickButton(operatorGamepad, Gamepad.button_Y);
        tipRobot.whenPressed(new ToggleTipRobot());
        
//        Button extendClimber = new JoystickButton(operatorGamepad,Gamepad.button_Y);
////        extendClimber.whenPressed(new ExtendClimber());
////        extendClimber.whenReleased(new RetractClimber());
//        extendClimber.whenPressed(new ToggleExtendClimber());
        
//        Button manualClimbButton = new JoystickButton(operatorGamepad,climberOverrideButtonNumber);
//        manualClimbButton.whenPressed(new ManualClimb());
        
        //uses vision processing to aim and spin up
        Button prepareToShootButton = new JoystickButton(operatorGamepad,prepareToShootButtonNumber);
        prepareToShootButton.whenPressed(new TeleopPrepareToShoot());
        
        Button manualFeedButton = new JoystickButton(operatorGamepad,Gamepad.button_R_Shoulder);
        manualFeedButton.whenPressed(new ManualFeed());
        manualFeedButton.whenReleased(new RetractFeeder());
        
        Button operatorAbortButton = new JoystickButton(operatorGamepad,Gamepad.button_Back);
        operatorAbortButton.whenPressed(new AbortCommands());
        
        Button increaseShooterSpeedButton = new AxisButton(operatorGamepad,Gamepad.dpadAxis,true);
        increaseShooterSpeedButton.whenPressed(new IncreaseShooterSpeed());
        
        Button decreaseShooterSpeedButton = new AxisButton(operatorGamepad,Gamepad.dpadAxis,false);
        decreaseShooterSpeedButton.whenPressed(new DecreaseShooterSpeed());

//        Button manualSpinUpButton = new JoystickButton(operatorGamepad,shooterOverrideButtonNumber);
//        manualSpinUpButton.whenPressed(new ManualSpinUp());
        
//        Button shooterJoystickButton = new JoystickButton(operatorGamepad,shooterJoystickControlButtonNumber);
//        shooterJoystickButton.whenPressed(new JoystickShooterControl());
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
        return driverGamepad.getAxis(Gamepad.leftStick_Y);
    }
    
    public double getArcadeRotationAxis()
    {
        return driverGamepad.getAxis(Gamepad.rightStick_X);
    }
    
    public double getTankLeftAxis()
    {
        return driverGamepad.getAxis(Gamepad.leftStick_Y);
    }
    
    public double getTankRightAxis()
    {
        return driverGamepad.getAxis(Gamepad.rightStick_Y);
    }

    public boolean isShiftButtonHeld()
    {
	return driverGamepad.getButton(shiftButtonNumber);
    }
    
//    public boolean isClimberOverrideButtonHeld()
//    {
//	return driverGamepad.getButton(climberOverrideButtonNumber);
//    }
    
    public boolean isShooterOverrideButtonHeld()
    {
//	return operatorGamepad.getButton(shooterOverrideButtonNumber);
        return false;
    }
    
    public boolean isShooterJoystickControlButtonHeld()
    {
//        return operatorGamepad.getButton(shooterJoystickControlButtonNumber);
        return false;
    }
    
    public double getShooterControlAxis()
    {
        return operatorGamepad.getAxis(Gamepad.leftStick_Y);
    }
    
    public boolean isNextStageButtonPressed()
    {
        return operatorGamepad.getButton(Gamepad.button_B);
    }
    
    public boolean isPrepareToShootButtonHeld()
    {
        return operatorGamepad.getButton(prepareToShootButtonNumber);
    }
    
//    private boolean climberIndependentButtonState = false;
//    private boolean hasBeenFalse = false;
//    
//    public boolean getClimberIndependentButtonState()
//    {
//        if(driverGamepad.getButton(Gamepad.button_Start))
//        {
//            if(hasBeenFalse)
//            {
//                climberIndependentButtonState = !climberIndependentButtonState;
//            }
//        }
//    }
    
    //may need to read an axis on operator stick to set shooter speed
    
    public double getClimbingLeftAxis()
    {
        return operatorGamepad.getAxis(Gamepad.leftStick_Y);
    }
    
    public double getClimbingRightAxis()
    {
        return operatorGamepad.getAxis(Gamepad.rightStick_Y);
    }
    
    public double getClimbingUpDownAxis()
    {
        return operatorGamepad.getAxis(Gamepad.leftStick_Y);
    }
    
    public double getClimbingLeftRightAxis()
    {
        return operatorGamepad.getAxis(Gamepad.rightStick_X);
    }
    
}

