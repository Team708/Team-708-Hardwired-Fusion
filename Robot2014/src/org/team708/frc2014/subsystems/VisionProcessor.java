 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Hashtable;
import org.team708.util.Math708;

/**
 * This class contains an algorithm that receives
 * data from the RoboRealm vision program and performs
 * calculations to:
 * 1) Identify the target(s) in the image.
 * 2) Provide information to code that reacts to the target(s).
 * @author Connor Willison
 */
public class VisionProcessor extends Subsystem{
    // High goal aspect ratio (11ft6in/3ft1in) in inches (3.729 repeating)
    private final double highGoalAspectRatio = 3.73; 
    
    // Distance related measurements from the network table
    private double distanceToTarget = 0.0;
    private int differencePx = 0;
    private final double ballDiameterIn = 24;
    
    // Data sent from the network table
    private double currentAspectRatio = 0.0;
    private double upper_left_x = 0;
    private double upper_left_y = 0;
    private double upper_right_x = 0;
    private double upper_right_y = 0;
    private double lower_left_x = 0;
    private double lower_left_y = 0;
    private double center_x = 0;
    private double radius = 0;
    private double blob_count = 0;
    
    // Information statuses for the image
    private boolean hasBall = false;
    private boolean isHighGoal = false;
    
    // Numbers used to do vision processing
    private static final double aspectRatioTolerance = .4;
    private final int imageWidthPx = 320;
    
    //daisy says to set this to 43.5 deg
    private final double cameraFOVRads = Math.toRadians(47);
//    private double cameraFOVRads = Math.toRadians(43.5);
    
    /**
     * Constructor
     */
    public VisionProcessor()
    {
        super("Vision Processor");
    }

    /**
     * Sets the default command for the subsystem.
     */
    protected void initDefaultCommand() {
        //no default command
    }
    
    /**
     * Returns if the camera has found the target object.
     * @return
     */
    public boolean hasTarget()
    {
        return hasBall;
    }
    
    /**
     * Returns if the goal is lit.
     * @return
     */
    public boolean isGoalLit() {
        return isHighGoal;
    }
    
    /**
     * Processes the data from the network table to determine various things.
     * It sets if the goal is lit and if a ball is found. It also can calculate
     * various distances from things and find the differences between pixels.
     */
    public void processData()
    {
        try{
            /*
             * Receive data from RoboRealm program.
             */
            NetworkTable table = NetworkTable.getTable("vision");
            upper_left_x = (double) table.getNumber("p1x");
            upper_left_y = (double) table.getNumber("p1y");
            upper_right_x = (double)table.getNumber("p2x");
            upper_right_y = (double)table.getNumber("p2y");
            lower_left_x = (double) table.getNumber("p3x");
            lower_left_y = (double) table.getNumber("p3y");
            center_x = (double) table.getNumber("cx");
            radius = (double) table.getNumber("r");
            hasBall = table.getNumber("ball") > 0;
            blob_count = (double) table.getNumber("count");
            
            
            //calculate aspect ratio of observed target
            currentAspectRatio = (upper_right_x - upper_left_x) / (upper_left_y - lower_left_y);
            
            //make sure that target aspect ratio is within tolerance
            //if it isn't, we really don't have a target
            if(Math.abs(currentAspectRatio - highGoalAspectRatio) > aspectRatioTolerance || blob_count < 1) {
                isHighGoal = false;
            } else {
                isHighGoal = true;
            }
            
            distanceToTarget = (ballDiameterIn * imageWidthPx)/
                                    ((2* radius) * Math.tan(cameraFOVRads/2) * 2);
            
            //calculate difference between center of target and center of screen
            differencePx = (int)(center_x - (imageWidthPx / 2.0));
            
        }catch(Exception e)
        {
//            e.printStackTrace();
        }
    }
    
    /**
     * Returns how many blobs are found by the camera.
     * @return 
     */
    public int getBlobCount()
    {
        return (int)blob_count;
    }
    
    /**
     * Returns the difference of pixels.
     * @return
     */
    public int getDifferencePx()
    {
        return differencePx;
    }
    
    /**
     * Returns the distance to the target.
     * @return
     */
    public double getDistanceToTarget()
    {
        return distanceToTarget;
    }
    
    /**
     * Sends data to the dumb-dashboard.
     */
    public void sendToDash()
    {
        SmartDashboard.putNumber("Distance To Target:", this.getDistanceToTarget());
        SmartDashboard.putNumber("Pixel Difference:", this.getDifferencePx());
        SmartDashboard.putNumber("Blob Count:", this.getBlobCount());
        SmartDashboard.putNumber("center x:",center_x);
        SmartDashboard.putNumber("radius:",radius);
        SmartDashboard.putBoolean("ball:",hasBall);
    }
}
