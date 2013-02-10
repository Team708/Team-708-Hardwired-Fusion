/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package distance;

import edu.wpi.first.wpilibj.AnalogChannel;
import harvester.BallSensor;

/**
 * This class represents a sensor that can
 * determine distance.
 * @author 708
 */
public abstract class DistanceSensor implements BallSensor{
    
    private AnalogChannel sensor;   //channel to read voltages from
    protected Model model;

    private static final double noBallMin = 7.0;
    private static final double noBallMax = 13.0;
    
    protected static class Model{
        private double lowV,highV,lowD,highD;
        private double vrange,drange,scale;

        protected Model(double lowV, double highV, double lowD, double highD) {
            this.lowV = lowV;
            this.highV = highV;
            this.lowD = lowD;
            this.highD = highD;

            vrange = highV - lowV;
            drange = highD - lowD;

            scale = drange/vrange;
        }

        protected Model(double voltsPerInch){
            this(0,0,0,0);  //not known
            scale = 1/voltsPerInch;
        }

        public double getDrange() {
            return drange;
        }

        public double getHighD() {
            return highD;
        }

        public double getHighV() {
            return highV;
        }

        public double getLowD() {
            return lowD;
        }

        public double getLowV() {
            return lowV;
        }

        public double getVrange() {
            return vrange;
        }

        public double getScale(){
            return scale;
        }
     }

    public DistanceSensor(int slot,int channel,Model m){
        sensor = new AnalogChannel(slot,channel);
        model = m;
    }

    public DistanceSensor(int channel,Model m){
        this(1,channel,m);
    }

    /**
     * Returns the distance read by the sensor.
     * @return
     */
    public abstract double getDistance();

    public double getVoltage(){
        return sensor.getVoltage();
    }

    public boolean hasBall(){
        return !(getDistance() > noBallMin && getDistance() < noBallMax);
    }

}
