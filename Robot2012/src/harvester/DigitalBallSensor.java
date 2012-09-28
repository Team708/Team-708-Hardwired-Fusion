/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package harvester;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 *
 * @author 708
 */
public class DigitalBallSensor implements BallSensor{

    private DigitalInput sensor;

    public DigitalBallSensor(int channel){
        sensor = new DigitalInput(channel);
    }
    
    public boolean hasBall(){
        return sensor.get();
    }

}
