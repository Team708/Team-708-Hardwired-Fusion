/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shooter;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import scripting.Configurable;
import scripting.Configuration;

/**
 * This class is meant to provide the ability
 * to set a motor (SpeedController) to a certain
 * number of RPMs (revolutions per minute). Since
 * a SpeedController object is used, it is
 * compatible with both Victors and Jaguars.
 * The EncoderMotor is a SpeedController object,
 * so it can be used with a RobotDrive object.
 * 
 * Operation:
 * When an RPM value is specified, it
 * is passed to a pid controller as a setpoint.
 * The pid controller will then ramp up (or down)
 * the motor until the specified rpm value is
 * reached.
 * @author Connor Willison
 */
public class EncoderMotor implements SpeedController,Configurable {

    private RPMEncoder encoder;
    private SpeedController motor;
    private PIDController controller;
    //instance variable to avoid asynchronous access
    private double goalRPM = 0.0;
    //0.0014
    private double P = 0.0015;
    private double I = 0.0013;
    private double D = 0.0006;
    //percentage error accepted by the pid controller as OK.
    private double pidTolerance = 4;
    //we should figure these next two out experimentally - robot dependent
    private static final double numPulsesPerRotation = 250;  //verified
    //calculate distance for later use
    private double distancePerPulse;
    //the maximum # of RPMs expected by the pid controller (must be set to a reasonable value, could affect calculations)
    private double maxRPMs;
    private boolean enabled;

    public EncoderMotor(int encoderChannelA, int encoderChannelB, SpeedController motor, double maxRPMs, double wheelCircumference) {
        this(encoderChannelA, 1, encoderChannelB, 1, motor, maxRPMs, wheelCircumference);
    }

    public EncoderMotor(int encoderChannelA, int slotA, int encoderChannelB, int slotB,
            SpeedController motor, double maxRPMs, double wheelCircumference) {

        this.motor = motor;
        this.maxRPMs = maxRPMs;
        distancePerPulse = wheelCircumference / numPulsesPerRotation;

        encoder = new RPMEncoder(slotA, encoderChannelA, slotB, encoderChannelB, 60.0 / wheelCircumference);
        encoder.setDistancePerPulse(distancePerPulse); //for use in rpm calculations

        //set up the pid controller - input to pid is the encoder reading (rpms), output is motor pwm value.
        controller = new PIDController(P, I, D, encoder, motor);
        controller.setTolerance(pidTolerance);   //must set these values so that the pid has a frame of reference.
        controller.setInputRange(-maxRPMs, maxRPMs);
        controller.setOutputRange(-1, 1);

        enabled = false;
        Configuration.getInstance().register("EncoderMotor", this);

    }

    /**
     * Stops the PID Controller.
     */
    public void disable() {
        controller.disable();
        enabled = false;
    }

    /**
     * Starts the PID Controller.
     */
    public void enable() {
        controller.enable();
        enabled = true;
    }

    /**
     * Accesses instance variable in EncoderMotor class
     * to avoid asynchronous access of PIDController.
     * @return
     */
    public boolean isEnabled(){
        return enabled;
    }

    /**
     * This method returns the current output value to
     * the motor (-1 to 1).
     * @return 
     */
    public double get() {
        return motor.get();
    }

    /**
     * This method returns the current RPM value
     * of the motor as indicated by the encoder.
     * @return 
     */
    public double getRPM() {
        return encoder.getRPM();
    }

    /**
     * Sets the goal rpm to the argument.
     * A pid controller will be used to change the
     * motor output until the specified value is reached.
     * @param rpm 
     */
    public void setRPM(double rpm) {
        goalRPM = rpm;

        //clear pulse count and start encoder
        encoder.reset();
        encoder.start();

        //reset and start the pid controller
        controller.reset();
        controller.setSetpoint(rpm);
        controller.enable();
    }

    /**
     * This method returns whether
     * or not this motor has attained
     * its goal speed.
     */
    public boolean isAtSpeed() {
        return controller.onTarget();
    }

    public double getP() {
        return P;
    }

    public double getI() {
        return I;
    }

    public double getD() {
        return D;
    }

    public void setPID(double p, double i, double d) {
        P = p;
        I = i;
        D = d;
        controller.setPID(p, i, d);
    }

    public double getGoalRPM() {
        return goalRPM;
    }

    /**
     * This method will be called by RobotDrive objects
     * with speed as a PWM value. This method will transform
     * that value into an RPM value, then pass it to setRPM().
     * @param speed
     * @param syncGroup 
     */
    public void set(double speed, byte syncGroup) {
        set(speed);
    }

    /**
     * This method will be called by RobotDrive objects
     * with speed as a PWM value. This method will transform
     * that value into an RPM value, then pass it to setRPM().
     * @param speed
     * @param syncGroup 
     */
    public void set(double speed) {
        setRPM(speed * maxRPMs);    //scale speed into an RPM value (preserves wheel speed ratios found by mecanumDrive())
    }

    /**
     * Calls set(double speed)
     * @param output 
     */
    public void pidWrite(double output) {
        set(output);
    }

    /**
     * This method returns a message of the form:
     * "Goal RPM: <pid setpoint> Current RPM: <rpm> Current PWM value: <motor output val>
     * Encoder count: <count> PID Error (RPMs): <error>"
     * @return 
     */
    public String getStatusMessage() {
        return "Goal RPM: " + controller.getSetpoint()
                + " Current RPM: " + encoder.getRPM()
                + "PID Error (RPMs): " + controller.getError()
                + " Current PWM value: " + motor.get();

    }

    public void update(String param,String[] values){
        if(param.equalsIgnoreCase("PID=")){
            if(values.length >= 3){
                P = Double.parseDouble(values[0]);
                I = Double.parseDouble(values[1]);
                D = Double.parseDouble(values[2]);
            }

            controller.setPID(P, I, D);
        }else if(param.equalsIgnoreCase("tolerance=")){
            if(values.length >= 1){
               pidTolerance= Double.parseDouble(values[0]);
            }

            controller.setTolerance(pidTolerance);
        }
    }
}
