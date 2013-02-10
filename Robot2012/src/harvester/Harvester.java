/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package harvester;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import io.IOConstants;

/**
 * This class represents the Harvester, the part
 * of the robot responsible for picking balls
 * up off of the floor and placing them in the
 * Hold.
 * @author 708
 */
public class Harvester{

    private Relay brush;
    private Hold hold;  //pointer to hold

    public final Relay.Value kForward = Relay.Value.kForward;     //push balls into hold
    public final Relay.Value kBackward = Relay.Value.kReverse;   //pushing balls out of hold
    private Relay.Value currentValue = kForward;

    private DigitalInput brushVertical;

    private boolean driverOverride = false;

    public Harvester(Hold hold){
        this.hold = hold;
        brush = new Relay(IOConstants.RelayChannels.kBrushSpike);
        brushVertical = new DigitalInput(IOConstants.DigitalIOChannels.kBrushVertical);
    }

    public void harvest(){

            //main loop
            hold.controlConveyors();


            //do not control brushes if controlled by joystick
            if(!driverOverride){
                synchronized(this){
                    /*
                     * Decide which direction to
                     * power brushes based on hold.
                     */
                    if(hold.canHarvest()){
                        currentValue = kForward;
                        brush.set(kForward);
                    }else{
                        //turn roller off
                        currentValue = Relay.Value.kOff;
                        brush.set(Relay.Value.kOff);
                    }
                }
            }
        }

    /**
     * Enables or disables driver override 
     * for brush speed control.
     * @param b
     */
    public synchronized void setOverride(boolean b){
        driverOverride = b;
    }

    public boolean isOverriden(){
        return driverOverride;
    }

    /**
     * Sets brushes to a speed only
     * if setOverride(true) is called first.
     * @param pow
     */
    public synchronized void setBrushes(Relay.Value val){
        if(driverOverride){
            currentValue = val;
            brush.set(val);
        }
    }

    public void reverseBrush(){
        if(currentValue == kForward){
            setBrushes(kBackward);
        }else{
            setBrushes(kForward);
        }
    }

    /**
     * Moves the brush forward until
     * the vertical switch is triggered.
     */
    public void setBrushVertical(){
        if(!brushVertical.get()){
            setBrushes(kForward);
        }else{
            setBrushes(Relay.Value.kOff);
        }
    }

    public boolean isBrushVertical(){
        return brushVertical.get();
    }
}