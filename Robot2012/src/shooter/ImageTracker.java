/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shooter;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.NIVision.MeasurementType;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import scripting.Configurable;
import scripting.Configuration;

/**
 * This class provides an interface to the image processing
 * thread.
 * @author 708
 */
public class ImageTracker implements Configurable{

    private Processor thread;

    private static final double targetWidthIn = 24;
    private static double cameraFOVRads = Math.toRadians(43.5);
    private static double offsetpx = 0;

    //possible targets
    public static final int TOP_TARGET = 0;
    public static final int LEFT_TARGET = 1;
    public static final int RIGHT_TARGET = 2;
    public static final int BOTTOM_TARGET = 3;

    //target heights inches
    private static final double topBasketHeight = 98.0;         //verified
    private static final double middleBasketHeight = 61.0;      //verified
    private static final double bottomBasketHeight = 28.0;      //verified

    public ImageTracker(AxisCamera camera) {
        thread = new Processor(camera);

        Configuration.getInstance().register("ImageTracker", this);
    }

    /**
     * Get height of current tracked target.
     * Note: asynchronous access.
     * @return
     */
    public double getTargetHeight(){
        switch(thread.getTarget()){
            case TOP_TARGET:
                return topBasketHeight;
            case LEFT_TARGET:
            case RIGHT_TARGET:
                return middleBasketHeight;
            case BOTTOM_TARGET:
                return bottomBasketHeight;
            default:
                return topBasketHeight;
        }
    }

    public void setTarget(int target){
        thread.setTarget(target);
    }

    private class Processor extends Thread {

        private AxisCamera camera;
        private int mode = TOP_TARGET;
        private double movement = 0;  //left/right movement value (pixels)
        private double distance = 0;  // calculated distance to target
        private int numParticles = 0; //number of particles in image
        private boolean running = false;

        public Processor(AxisCamera camera) {
            this.camera = camera;
        }

        /**
         * Entry point for thread.
         */
        public void run() {
            //image processing loop
            while (true) {
                waitForRequests();
                processImage();
            }
        }

        public synchronized void setTarget(int target){
            mode = target;
        }

        public synchronized int getTarget(){
            return mode;
        }

        /**
         * Alerts the thread
         * @param r
         */
        public synchronized void setRunning(boolean r) {
            running = r;
            if (running) {
                this.notify();  //restart the processor thread if needed.
            }else{
                //reset measurements
                distance = 0.0;
                movement = 0.0;
            }
        }

        public synchronized boolean isRunning() {
            return running;
        }

        /**
         * If the processing thread does not need to be running,
         * this method blocks it.
         */
        private synchronized void waitForRequests() {
            if (!running) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * Process one image and store resulting data.
         */
        private void processImage() {
            //get an image from the camera
            try {
                //wait until the camera has a fresh image
                while (!camera.freshImage()){
                    Thread.sleep(10);
                }

                ColorImage image = camera.getImage();


                /*
                 * Apply following filters to find center
                 * of target:
                 * - Color Threshold (HSL, Hue: (Min: 118 Max: 155) Saturation: (Min: 0 Max: 205) Luminance: (Min: 161 Max: 255))     - Creates binary image
                 * - Convex Hull                                            - Repairs particles
                 * - Compactness (Min .75 Max: 1.0)                   - How rectangle - like is it?
                 * - Aspect Ratio (Min: .8 Max: 1.5)                      - Optimum Aspect Ratio of 1.33
                 * - Area/Image Area: (Min: 1.0 Max: 100)                 - Percentage of image area
                 */
                BinaryImage binImage = image.thresholdHSL(118,155,0,205,161,255);
                BinaryImage convexed = binImage.convexHull(true);

                CriteriaCollection filterOptions1 = new CriteriaCollection();
                filterOptions1.addCriteria(MeasurementType.IMAQ_MT_COMPACTNESS_FACTOR, .75f, 1.0f, false);

                BinaryImage filtered1 =  convexed.particleFilter(filterOptions1);

                CriteriaCollection filterOptions2 = new CriteriaCollection();
                //filterOptions2.addCriteria(MeasurementType.IMAQ_MT_RATIO_OF_EQUIVALENT_RECT_SIDES,.8f,1.5f, false);
                
                BinaryImage filtered2 =  filtered1.particleFilter(filterOptions2);
                
                CriteriaCollection filterOptions3 = new CriteriaCollection();
                filterOptions3.addCriteria(MeasurementType.IMAQ_MT_AREA_BY_IMAGE_AREA,1.0f,100f,false);
                
                BinaryImage filtered =  filtered2.particleFilter(filterOptions3);

                ParticleAnalysisReport[] particles = filtered.getOrderedParticleAnalysisReports();
                ParticleAnalysisReport target = null;
                int bestX = filtered.getWidth()/2;
                int bestY = filtered.getHeight()/2;

                //determine which particle is the target
                for(int i = 0; i<particles.length; i++){
                    switch(mode){
                        case TOP_TARGET:
                            //get particle with least y value
                            if(particles[i].center_mass_y < bestY){
                                target = particles[i];
                                bestY = target.center_mass_y;
                            }
                            break;
                        case LEFT_TARGET:
                            //get particle with least x value
                            if(particles[i].center_mass_x < bestX){
                                target = particles[i];
                                bestX = target.center_mass_x;
                            }
                            break;
                        case RIGHT_TARGET:
                            //get particle with greatest x value
                            if(particles[i].center_mass_x > bestX){
                                target = particles[i];
                                bestX = target.center_mass_x;
                            }
                            break;
                        case BOTTOM_TARGET:
                            //get particle with greatest y value
                            if(particles[i].center_mass_y > bestY){
                                target = particles[i];
                                bestY = target.center_mass_y;
                            }
                            break;
                        default:
                            break;
                    }
                }

                
                    //set number of particles found
                    numParticles = particles.length;


                    //default to 0.0
                    double calc_Movement = 0.0;
                    double calc_Distance = 0.0;
                
                    //only calculate if a target was detected
                    if (target != null) {
                            //measure target's x value and subtract from center - return decimal -1.0 - 1.0
                            calc_Movement = (double)((target.center_mass_x - ((filtered.getWidth() / 2) + offsetpx)) / (double)filtered.getWidth());
                            calc_Distance = (targetWidthIn * filtered.getWidth())/
                                    (target.boundingRectWidth * Math.tan(cameraFOVRads/2) * 2);
                    }

                    //only synchronize data access
                    synchronized(this){
                        movement = calc_Movement;
                        distance = calc_Distance;
                    }


                //free images used
                image.free();
                binImage.free();
                convexed.free();
                filtered1.free();
                filtered2.free();
                filtered.free();
                
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public synchronized int getNumParticles() {
            return numParticles;
        }

        public synchronized double getMovement() {
            return movement;
        }

        public synchronized double getDistance(){
            return distance;
        }
    }

    /**
     * Asks the processing thread for the most
     * recent particle count in the image.
     * @return
     */
    public int getMostRecentParticleCount() {
        return thread.getNumParticles();
    }

    /**
     * Asks the processing thread for its
     * most recent data.
     * @return
     */
    public double getMostRecentMovement() {
        return thread.getMovement();
    }

    /**
     * Asks the processing thread for its most
     * recent estimate of the distance to the target.
     * @return
     */
    public double getMostRecentDistance(){
        return thread.getDistance();
    }

    /**
     * Starts the processing thread
     * on its way.
     */
    public void start() {
        if (!thread.isAlive()) {
            thread.start();
        }
        thread.setRunning(true);
    }

    /**
     * Stops the processing thread from
     * processing images.
     */
    public void stop() {
        thread.setRunning(false);
    }

    public boolean isRunning(){
       return thread.isRunning();
    }

    public void update(String param, String[] values){
        if(param.equalsIgnoreCase("camerafov=")){
            if(values.length > 0){
                cameraFOVRads = Math.toRadians(Double.parseDouble(values[0]));
            }
        }else if(param.equalsIgnoreCase("offsetpx=")){
            if(values.length > 0){
                offsetpx = Double.parseDouble(values[0]);
            }
        }
    }
}
