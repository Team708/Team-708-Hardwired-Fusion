/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package distance;

/**
 *
 * @author 708
 */
public class IRSensor extends DistanceSensor{

    //infrareds
    public static final Model GP2Y0A02YK0F = new Model(0.4,2.55,0.00667,.05);
    public static final Model GP2Y0A21YK0F = new Model(0.4,3.0,.0125,0.143);

    public IRSensor(int channel,Model m){
        super(1,channel,m);
    }

    public IRSensor(int slot,int channel,Model m){
        super(slot,channel,m);
    }

     public double getDistance(){
         return 1/(((getVoltage() - model.getLowV()) * model.getScale() + model.getLowD()) * 2.54);
     }
}
