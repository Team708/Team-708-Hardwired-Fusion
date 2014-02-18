/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team708.frc2014.sensors;

import org.team708.util.*;

/**
 *
 * @author 708
 */
public class UltrasonicSensor extends DistanceSensor{

    //ultrasonics
    public static final Model MB1010 = new Model(.009766);
    public static final Model MB1340 = new Model(.012446);

    public UltrasonicSensor(int channel, Model m) {
        super(1,channel,m);
    }

    public UltrasonicSensor(int slot, int channel, Model m) {
        super(slot,channel,m);
    }

    public double getRawDistance(){
        return (getVoltage() - model.getLowV()) * model.getScale() + model.getLowD();
    }

    public double getDistance() {
        return (getAverageVoltage() - model.getLowV()) * model.getScale() + model.getLowD();
    }
}
