/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard708;

import edu.wpi.first.smartdashboard.camera.WPICameraExtension;
import edu.wpi.first.wpijavacv.WPIColorImage;
import edu.wpi.first.wpijavacv.WPIImage;

/**
 *
 * @author Willisons
 */
public class Team708CameraApp extends WPICameraExtension{
    
    public WPIImage processImage(WPIColorImage rawImage)
    {
        /**
         * Image processing code
         */
        double movement,distance; //output values from image processing
        
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
        
        return rawImage;
    }
}
