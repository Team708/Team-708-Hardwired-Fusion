/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harvester;

import distance.IRSensor;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Solenoid;
import io.IOConstants;

/**
 * This class represents the hold of the robot,
 * which contains balls that have been picked up
 * by the harvester and are on their way to the
 * shooter. This class is thread-safe, as the
 * harvester and shooter may run on separate
 * threads.
 * @author 708
 */
public class Hold {

    private BallSensor top, middle, bottom;
    private BallSensor entrance;
    private Conveyor conveyor;
    //used by shooter thread
    //used by harvester thread
    private boolean underHarvesterControl = true;
    private boolean hasBall = false;

    public Hold() {
        top = new IRSensor(IOConstants.AnalogInputChannels.kTopBallSensor, IRSensor.GP2Y0A21YK0F);
        middle = new IRSensor(IOConstants.AnalogInputChannels.kMiddleBallSensor, IRSensor.GP2Y0A21YK0F);
        bottom = new IRSensor(IOConstants.AnalogInputChannels.kBottomBallSensor, IRSensor.GP2Y0A21YK0F);

        entrance = new IRSensor(IOConstants.AnalogInputChannels.kEntranceSensor, IRSensor.GP2Y0A21YK0F);
        conveyor = new Conveyor();

    }

    public int getNumBalls() {
        return ((top.hasBall()) ? 1 : 0) + ((middle.hasBall()) ? 1 : 0) + ((bottom.hasBall()) ? 1 : 0);
    }

    public void controlConveyors() {

        /*
         * If the shooter is using the conveyor to
         * load a ball, harvester thread should tend
         * to the brushes until it finishes.
         */
        if (!underHarvesterControl) {
            return;
        }

//            if (getNumBalls() > 0) {
            if (getNumBalls() < 3) {
                if (top.hasBall()) {
                    //stop top conveyor - ball is in top
                    conveyor.setUpper(Conveyor.kStopped);

                    //secondary objective - keep bottom slot open
                    if (bottom.hasBall() || hasBall) {

                        if (!middle.hasBall()) {
                            conveyor.setLower(Conveyor.kUp);
                            hasBall = true; //move ball to middle
                        } else {
                            hasBall = false; // middle has ball
                            conveyor.setLower(Conveyor.kStopped);
                        }

                    } else {
                        if (bottom.hasBall() && middle.hasBall()) {
                            conveyor.setLower(Conveyor.kStopped);
                        } else {
                            conveyor.setLower(Conveyor.kUp);  // used to be kStopped with no no if-then-else
                        }
                    }

                } else {
                    //ball is in hold and needs to be moved to top slot
                    loadTopSlot();
                }
            } else {
                //hold is full, stop conveyor
                conveyor.setUpper(Conveyor.kStopped);

                // gotta load a ball into bottom slot
                conveyor.setLower(Conveyor.kStopped);
            }
    }

    /**
     * Starts the conveyors moving a ball out of
     * the hold and into the shooter. Returns whether
     * or not a ball left the hold.
     * @return
     */
    public boolean feedBall() {
        if (underHarvesterControl) {
            //pass control to shooter
            underHarvesterControl = false;
        }

        if (top.hasBall()) {
            //power top conveyor to push ball up
            conveyor.setUpper(Conveyor.kUp);

            //shooter needs to call again
            return false;
        } else {
            //stop the conveyor
            conveyor.setUpper(Conveyor.kStopped);

            //pass control to harvester
            underHarvesterControl = true;

            //tell shooter not to call again
            return true;
        }

    }

    /**
     * Called iteratively to load ball into top slot.
     * Returns whether ball has been loaded.
     */
    private void loadTopSlot() {
        if (top.hasBall()) {
            //ball has reached the top, stop the  top conveyor
            conveyor.setUpper(Conveyor.kStopped);


        } else {

            if (!entrance.hasBall()) {

                if (middle.hasBall() || bottom.hasBall()) {
                    hasBall = true;
                    conveyor.setUpper(Conveyor.kUp);
                    conveyor.setLower(Conveyor.kUp);
                } else if(hasBall){
                    //might still have a ball - is between sensors
                        conveyor.setUpper(Conveyor.kUp);
                        conveyor.setLower(Conveyor.kUp);
                }else{
                    //does not have balls - prepare to catch from brush
                    conveyor.setLower(Conveyor.kUp);
                    conveyor.setUpper(Conveyor.kStopped);
                }


            } else {
                //entrance has a ball
                hasBall = false;

                conveyor.setLower(Conveyor.kStopped);
                conveyor.setUpper(Conveyor.kUp);
            }
            //ball hasn't reached top yet
        }
    }

    /**
     * Check if the hold contains enough ammunition to
     * shoot. (ball count is greater than 0 and top slot
     * has a ball.)
     * @return
     */
    public boolean canShoot() {
//        return getNumBalls() > 0 && top.hasBall();
        return top.hasBall();
    }

    /**
     * Check if the hold has space for more balls.
     * (total ball count is less than 3 and the bottom slot
     * is empty.)
     * @return
     */
    public boolean canHarvest() {
//        return !bottom.hasBall();
        return getNumBalls() < 3 && !bottom.hasBall();
    }

    public void stopConveyor(){
        conveyor.setLower(Conveyor.kStopped);
        conveyor.setUpper(Conveyor.kStopped);
    }

    public BallSensor getBottom() {
        return bottom;
    }

    public BallSensor getEntrance() {
        return entrance;
    }

    public BallSensor getMiddle() {
        return middle;
    }

    public BallSensor getTop() {
        return top;
    }

    public boolean isUnderHarvesterControl(){
        return underHarvesterControl;
    }

    public void setUnderHarvesterControl(boolean b){
        underHarvesterControl = b;
    }
    
}
