/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package harvester;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import io.IOConstants;

/**
 *
 * @author 708
 */
public class Conveyor {

    private Relay lowerSection,upperSection;

    public static final Value kUp = Value.kForward;
    public static final Value kDown = Value.kReverse;
    public static final Value kStopped = Value.kOff;

    public Conveyor(){
        lowerSection = new Relay(IOConstants.RelayChannels.kLowerMotor);
        upperSection = new Relay(IOConstants.RelayChannels.kUpperMotor);
    }

    public void setLower(Value mode){
        lowerSection.set(mode);
    }

    public void setUpper(Value mode){
        upperSection.set(mode);
    }
}
