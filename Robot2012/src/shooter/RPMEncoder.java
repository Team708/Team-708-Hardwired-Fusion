package shooter;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;

/*
 * This class is here to provide an interface to the encoder for
 * the pid controller.
 */
public class RPMEncoder extends Encoder implements PIDSource {

    //pre-calculated the conversion factor
    private double scaleFactor;

    public RPMEncoder(int slotA, int channelA, int slotB, int channelB, double scaleFactor) {
        super(slotA, channelA, slotB, channelB, false, EncodingType.k1X);
        this.scaleFactor = scaleFactor;
    }

    //called by pid controller
    public double pidGet() {
        return getRPM();
    }

    /**
     * This method returns the current rate of
     * speed (in RPMs) that the encoder is sensing.
     * @return
     */
    public double getRPM() {
        //getRate() returns a value in distance per second
        return this.getRate() * scaleFactor;
    }
}
