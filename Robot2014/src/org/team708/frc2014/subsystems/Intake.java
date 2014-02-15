package org.team708.frc2014.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team708.frc2014.RobotMap;
import org.team708.frc2014.commands.intake.JoystickMotorControl;
import org.team708.frc2014.sensors.IRSensor;

/**
 *
 * @author Nam Tran, Pat Walls
 */
public class Intake extends Subsystem {
    
    // Solenoid for the intake
    private final DoubleSolenoid intakeSolenoid;
    // Solenoid values are not primitive types, so this makes it easy to read
    // Solenoid value for extended intake
    private final DoubleSolenoid.Value EXTENDED = DoubleSolenoid.Value.kReverse;
    // Solenoid value for retracted intake
    private final DoubleSolenoid.Value RETRACTED = DoubleSolenoid.Value.kForward;
    
    //Motor Controller
    private final Talon intakeMotor;
    // Speeds for the intake motor
    private final double INTAKE_SPEED = 1.0;
    private final double DISPENSE_SPEED = -1.0;
    
    // Booleans to keep track of the state of the intake system
    private boolean isExtended = false;
    
    private final IRSensor intakeIR; // IR Sensor to check for ball
    
    private final double lowHasBallDistance = 0.0; // Lower threshold for having the ball
    private final double highHasBallDistance = 4.0; // Upper threshold for having the ball
    
    public Intake() {
        // Creates the solenoid for the intake piston
        intakeSolenoid = new DoubleSolenoid(RobotMap.intakeSolenoidA, RobotMap.intakeSolenoidB);
        intakeMotor = new Talon(RobotMap.intakeMotor); // Initialises intake motor
        // Creates the IR sensor for the intake system
        intakeIR = new IRSensor(RobotMap.intakeIRSensor, IRSensor.GP2Y0A21YK0F);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new JoystickMotorControl());
    }
    
    // Extends the intake system and sets its state to extended
    public void extendIntake() {
        intakeSolenoid.set(EXTENDED);
        isExtended = true;
    }
    
    // Retracts the intake system and sets its state to not extended
    public void retractIntake() {
        intakeSolenoid.set(RETRACTED);
        isExtended = false;
    }

    // Checks to see if it has the ball
    public boolean checkIfBall() {
        return (lowHasBallDistance <= intakeIR.getDistance() && intakeIR.getDistance() <= highHasBallDistance);
    }
    
    // Spins to intake the ball
    public void intakeBall() {
        intakeMotor.set(-INTAKE_SPEED);
    }
    
    // Spins to dispense the ball (in case the wrong colour is picked up)
    public void dispenseBall() {
        intakeMotor.set(-DISPENSE_SPEED);
    }
    
    // Stops the intake motor
    public void stopIntake() {
        intakeMotor.set(0.0);
    }
    /**
     *  Controls the motors for the intake using the joystick
     * @param axis 
     */
    public void joystickMotorControl(double axis) {
        if (axis > 0) {
            intakeBall();
        } else if (axis < 0) {
            dispenseBall();
        } else {
            stopIntake();
        }
    }
    
    public boolean isExtended() {
        return isExtended;
    }
    
    public boolean isDeployed() {
        return intakeSolenoid.get().equals(EXTENDED);
    }
    
    public void sendToDash() {
        SmartDashboard.putBoolean("Has Ball", checkIfBall());
        SmartDashboard.putNumber("IR Sensor", intakeIR.getDistance());
        SmartDashboard.putBoolean("Is Extended", isExtended);
        SmartDashboard.putBoolean("Is Deployed", isDeployed());
    }
}