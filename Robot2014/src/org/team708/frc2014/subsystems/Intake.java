package org.team708.frc2014.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team708.frc2014.RobotMap;
import org.team708.frc2014.commands.intake.ManualIntake;

/**
 *
 * @author Nam Tran
 */
public class Intake extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    //Pneumatic Related Things
    private final DoubleSolenoid intakeSolenoid;
    private final DoubleSolenoid.Value EXTENDED = DoubleSolenoid.Value.kReverse;
    private final DoubleSolenoid.Value RETRACTED = DoubleSolenoid.Value.kForward;
    
    //Motor Controller
    private final Relay intakeMotor;
    private final Relay.Value INTAKE_SPEED = Relay.Value.kForward;
    private final Relay.Value DISPENSE_SPEED = Relay.Value.kReverse;
    private final Relay.Value STOP = Relay.Value.kOff;
    
    // Booleans to keep track of the state of the intake system
    private boolean isExtended = false;
    private boolean hasBall = false;
    
    public Intake() {
        // Creates the solenoid for the intake piston
        intakeSolenoid = new DoubleSolenoid(RobotMap.intakeSolenoidA, RobotMap.intakeSolenoidB);
        // Creates the motor for the intake system
        intakeMotor = new Relay(RobotMap.intakeMotor);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ManualIntake());
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
    
    // Checks the state of the intake and extends/retracts the intake
    public void deployIntake() {
        if (isExtended == false) {
            this.extendIntake();
        } else {
            this.retractIntake();
        }
    }
    
    // Spins to intake the ball
    public void intakeBall() {
        intakeMotor.set(INTAKE_SPEED);
    }
    
    // Spins to dispense the ball (in case the wrong colour is picked up)
    public void dispenseBall() {
        intakeMotor.set(DISPENSE_SPEED);
    }
    
    // Stops the intake motor
    public void stopIntake() {
        intakeMotor.set(STOP);
    }
}