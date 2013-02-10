/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shooter;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import harvester.Hold;
import io.IOConstants;
import edu.wpi.first.wpilibj.Timer;
import scripting.Configurable;
import scripting.Configuration;

/**
 * This class represents the Shooter, the
 * part of the robot that removes balls from the
 * hold and fires them into the basket.
 * @author 708
 */
public class Shooter implements Configurable {

    private Hold hold;
    private ImageTracker tracker;
    private AnalogChannel rotationSensor;
    private Victor rotationMotor;
    private EncoderMotor flywheel;
    private SpeedController rawFlywheel;
    private DigitalInput leftLimit;
    private DigitalInput rightLimit;
    private Timer samplingTimer;
    private Timer spinUpTimer;
    //shooter constants
    private static double shooterAngle = Math.toRadians(50.0);  //not verified - degrees
    private static double flyWheelDiameter = 4.0;         //not verified - inches
    private static double shooterHeight = 60.0;           //not verified
    private static double gravity = 386.4;                //verified - inches/sec/sec
    //pre-computed expressions
    private static double cosSqrTerm = 2 * Math.cos(shooterAngle) * Math.cos(shooterAngle);
    private static double piD = Math.PI * flyWheelDiameter;
    private static double tanAngle = Math.tan(shooterAngle);
    //shooter states
    private int shooterState = kIDLE;
    private static final int kIDLE = 0;
    private static final int kROTATING = 1;
    private static final int kSPINNING_UP = 2;
    private static final int kWAITING_FOR_BALL = 3;
    private static final int kREADY_TO_SHOOT = 4;

    private int centerPositionAvg = 912;
    private int rotationToleranceAvg = 0;
    private static double maxRotationSpeed = .13;
    private static double rotationTolerance = .03;        //-1.0 - 1.0r
    private static final double maxRPMs = 3500.0;
    private static final double distanceSampleRateSec = 1.0;
    private double targetSpeed = 0.0; //rpms
    private double pwm = 0.0; //pwm signal

    private boolean startedSpinning = false;

    //driver override vars
    private boolean manualAim = false;
    private boolean autoAim = false;
    private boolean rotatingLeft = false;
    private boolean rotatingRight = false;
    private boolean shooting = false;

    private double flywheelSpeed = 1.0;

    public Shooter(Hold hold) {

        this.hold = hold;

        //setup camera
        AxisCamera camera = AxisCamera.getInstance();
        camera.writeResolution(AxisCamera.ResolutionT.k320x240);
        camera.writeColorLevel(100);
        camera.writeBrightness(50);
        camera.writeCompression(30);

        tracker = new ImageTracker(camera);

        rotationSensor = new AnalogChannel(IOConstants.AnalogInputChannels.kShooterPot);

        rotationMotor = new Victor(IOConstants.PWMChannels.kRotationMotor);
        rotationMotor.setExpiration(2.0);

        rawFlywheel = new Victor(IOConstants.PWMChannels.kFlyWheelMotor);
        flywheel = new EncoderMotor(IOConstants.DigitalIOChannels.kShooterEncoderA, IOConstants.DigitalIOChannels.kShooterEncoderB,
                rawFlywheel, maxRPMs, Math.PI * flyWheelDiameter);

        tracker = new ImageTracker(camera);
        tracker.start();

        leftLimit = new DigitalInput(IOConstants.DigitalIOChannels.kLeftLimit);
        rightLimit = new DigitalInput(IOConstants.DigitalIOChannels.kRightLimit);

        samplingTimer = new Timer();
        samplingTimer.start();

        spinUpTimer = new Timer();
        spinUpTimer.start();

        //register config file
        Configuration.getInstance().register("Shooter", this);

        //calculate values for physics equation
        preCalculate();
    }

    /**
     * Called by manipulator thread to control shooter.
     */
    public void control(){
//        getShooterPWM();

        if(manualAim){
            //set flywheel to full power
            rawFlywheel.set(flywheelSpeed);

//            rawFlywheel.set(getShooterPWM());

            //turn on image processing
//            if(!tracker.isRunning()){
//                tracker.start();
//            }

            //rotate left or right
            if(rotatingLeft && !leftLimit.get()){
//            if(rotatingLeft){
                rotationMotor.set(maxRotationSpeed);
            }else if(rotatingRight && !rightLimit.get()){
//                }else if(rotatingRight){
                rotationMotor.set(-maxRotationSpeed);
            }else{
                rotationMotor.set(0.0);
            }

            if(shooting){
                shoot();
            }else{
                //make sure that harvester control is returned - bring balls up
                if(!hold.isUnderHarvesterControl()){
                    hold.setUnderHarvesterControl(true);
                }
            }

            //manual shooter control
        }else if(autoAim){
//            //turn on image processing
//            if(!tracker.isRunning()){
//                tracker.start();
//            }

            prepareToShoot();
            
            if(shooting){
                shoot();
            }

        }else{
            reset();
        }
    }

    /**
     * Use the auto-aim feature of shooter:
     *
     * Requirements:
     * 1) Aim shooter towards basket
     * 2) Estimate distance to target
     * 3) Spin up flywheels to required speed
     * 4)
     */
    public void prepareToShoot() {
        /*
         * Shooter state
         */
        switch (shooterState) {
            case kIDLE:
                /*
                 * Assume shooter is not aimed and
                 * flywheel is stopped or at low speed.
                 */

                //start rotating to face target
                shooterState = kROTATING;

                break;
            case kROTATING:

                //rotate left/right based on target data
                double offset = tracker.getMostRecentMovement();

                if (offset > rotationTolerance) {
                    //must rotate left
                    if(!leftLimit.get()){
                        rotationMotor.set(maxRotationSpeed);
                    }else{
                        //left limit switch was pressed
                        rotationMotor.set(0.0);
                    }
//                    rotationMotor.set(offset * maxRotationSpeed); //rotate at speed proportional to offset
                } else if (offset < -rotationTolerance) {
                    //must rotate right
                    if(!rightLimit.get()){
                        rotationMotor.set(-maxRotationSpeed);
                    }else{
                        //right limit switch was pressed
                        rotationMotor.set(0.0);
                    }
//                    rotationMotor.set(offset * maxRotationSpeed);
                } else {
                    //offset is within tolerance - done aiming
                    rotationMotor.set(0.0);

                    //start spinning up
                    shooterState = kSPINNING_UP;
                }

                break;
            case kSPINNING_UP:

//                //enable the flywheel if not already
//                if (!flywheel.isEnabled()) {
//                    flywheel.enable();
//                }

//                //if flywheel isn't up to speed, set the setpoint (every sec. or so to avoid PID noise)
//                if (!flywheel.isAtSpeed()) {
//
//                    //check if wait time has passed before adjusting pid controller again
//                    if (samplingTimer.get() >= distanceSampleRateSec) {
//                        //calculate speed based on distance to target and target height
//                        flywheel.setRPM(getTargetSpeed(tracker.getMostRecentDistance(), tracker.getTargetHeight()));
//
//                        //reset timer
//                        samplingTimer.reset();
//                    }
//                } else {
//                    //flywheel is at speed
//                    shooterState = kWAITING_FOR_BALL;
//                }

                //do not use pid control - temporary b/c no encoder

//                if(!startedSpinning){
//                    rawFlywheel.set(1.0);
//                    spinUpTimer.reset();
//                    startedSpinning = true;
//                }
//                if(spinUpTimer.get() > .5){
//                    startedSpinning = false;
//                    shooterState = kWAITING_FOR_BALL;
//                }

//                rawFlywheel.set(getShooterPWM());

                rawFlywheel.set(flywheelSpeed);
                shooterState = kWAITING_FOR_BALL;

                break;

            case kWAITING_FOR_BALL:
                //wait until the harvester loads a ball in the top slot.
                if (hold.canShoot()) {
                    shooterState = kREADY_TO_SHOOT;
                }

                break;
        }
    }

    /**
     * Control shooter manually.
     * @param override
     * @param moveLeft
     * @param moveRight
     * @param shoot
     */
    public void setFlags(boolean manual,boolean auto,boolean moveLeft,boolean moveRight, boolean shoot){
        manualAim = manual;
        autoAim = auto;
        rotatingLeft = moveLeft;
        rotatingRight = moveRight;
        shooting = shoot;
    }

     public boolean isDoneShooting(){
        return hold.getNumBalls() == 0 && !hold.getEntrance().hasBall();
    }

    /**
     * Fires a ball from the shooter
     * if it is ready to shoot. Returns whether
     * a ball has been fired from the shoooter.
     * @return
     */
    public void shoot() {
        //shoot only if prepareToShoot() has been called periodically (all shooting conditions are satisfied)
        if (isReadyToShoot()) {

            if (hold.feedBall()) {
                //ball was fired, so must prepare for next
                shooterState = kSPINNING_UP;
            }

            //ball has not reached shooter, so still ready to fire
        }

        //do nothing if ball is not ready to shoot
    }

    /**
     * Calculates speed of shooter based on distance
     * to target.
     * @return
     */
    private double getShooterPWM(){
        return calcPWM(calcTargetSpeed(tracker.getMostRecentDistance(),tracker.getTargetHeight()));
    }

    private double calcTargetSpeed(double distance, double targetHeight) {

        targetSpeed = (Math.sqrt(gravity * distance * distance /
                (cosSqrTerm * (tanAngle * distance - targetHeight + shooterHeight))) / piD) *60/.85;

        return targetSpeed;
    }

    /**
     * Uses interpolant function to calculate
     * pwm from rpm.
     * @param rpm
     * @return
     */
    private double calcPWM(double rpm){

        if(rpm < 0) pwm = 0.0;
        else{
            pwm = Math.min(1.0, 2E-11 * rpm * rpm * rpm
                - 1E-7 * rpm * rpm
                + .0003 * rpm);
        }

        return pwm;
    }

    public double getTargetSpeed(){
        return targetSpeed;
    }

    public double getPWM(){
        return pwm;
    }

    /**
     * Tells whether the shooter is ready to fire
     * based on 3 flags:
     * - Shooter is aimed at target
     * - flywheel is at speed
     * - hold has a ball loaded in the top slot
     * @return
     */
    public boolean isReadyToShoot() {
        return shooterState == kREADY_TO_SHOOT || manualAim;
    }

    public double getRotation(){
        return rotationSensor.getAverageValue();
    }

    /**
     * Stops the shooter from shooting or preparing to
     * shoot. Will stop the flywheel, any rotation,
     * and image tracking. Also causes the shooter to return
     * to the center position.
     */
    public void reset() {
        shooterState = kIDLE;
        if(flywheel.isEnabled()){
            flywheel.disable();
        }
        rawFlywheel.set(0.0);

        //make sure that harvester control is returned - avoid getting stuck in shooting state
        if(!hold.isUnderHarvesterControl()){
            hold.setUnderHarvesterControl(true);
        }

//        //turn off image processing
//        tracker.stop();

        //also start shooter moving to center position
        //pot - shooter left = increase, shooter right = decrease
        if(rotationSensor.getAverageValue() - centerPositionAvg > rotationToleranceAvg){
            //must move right
            rotationMotor.set(maxRotationSpeed);
        }else if(rotationSensor.getAverageValue() - centerPositionAvg < -rotationToleranceAvg){
            //must move left
            rotationMotor.set(-maxRotationSpeed);
        }else{
            //stop rotation
            rotationMotor.set(0.0);
        }
    }

    public void update(String param, String[] values) {
        if (param.equalsIgnoreCase("angle=")) {
            if (values.length > 0) {
                shooterAngle = Math.toRadians(Double.parseDouble(values[0]));
            }
        } else if (param.equalsIgnoreCase("flywheeldiameter=")) {
            if (values.length > 0) {
                flyWheelDiameter = Double.parseDouble(values[0]);
            }
        } else if (param.equalsIgnoreCase("shooterheight=")) {
            if (values.length > 0) {
                shooterHeight = Double.parseDouble(values[0]);
            }
        }else if (param.equalsIgnoreCase("rotationspeed=")) {
            if (values.length > 0) {
                maxRotationSpeed = Double.parseDouble(values[0]);
            }
        }else if (param.equalsIgnoreCase("rotationtolerance=")) {
            if (values.length > 0) {
                rotationTolerance = Double.parseDouble(values[0]);
            }
        }else if(param.equalsIgnoreCase("maxspeed=")){
            if(values.length > 0){
                flywheelSpeed = Double.parseDouble(values[0]);
            }
        }else if(param.equalsIgnoreCase("centerpos=")){
            if(values.length > 0){
                centerPositionAvg = (int)Double.parseDouble(values[0]);
            }
        }

            //use the new parameters to calculate terms
        preCalculate();

    }

    private void preCalculate() {
        cosSqrTerm = 2 * Math.cos(shooterAngle) * Math.cos(shooterAngle);
        piD = Math.PI * flyWheelDiameter;
        tanAngle = Math.tan(shooterAngle);
    }

    public double getTrackerMovement(){
        return tracker.getMostRecentMovement();
    }

    public double getTrackerParticleCount(){
        return tracker.getMostRecentParticleCount();
    }

    public double getTrackerDistance(){
        return tracker.getMostRecentDistance();
    }
}
