/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * This class contains an algorithm that receives
 * data from the RoboRealm vision program and performs
 * calculations to:
 * 1) Determine the distance to the target
 * 2) Determine if the robot needs to rotate to line up with the target.
 * @author Connor Willison
 */
public class VisionProcessor extends Subsystem{
    
    private static final int HIGH_TARGET = 0;
    private static final int MIDDLE_TARGET = 1;
    private static final int LOW_TARGET = 2;
    private static final int PRACTICE_TARGET = 3;
    private static final int UNKNOWN_TARGET = 4;
   
    private String[] targetNames = {"High Target","Middle Target","Low Target","Practice Target","???"};
//    private double[] aspectRatios = {3.1,2.12,1.16,1.12,0};
    private double[] aspectRatios = {3.1,2.12,100.0,1.12,0};
    private double[] targetWidths = {62,62,37,24,0};
    
    private int currentTargetType = UNKNOWN_TARGET;
    private String currentTargetName = "???";
    private double distanceToTarget = 0.0;
    private int differencePx = 0;
    
    private double currentAspectRatio = 0.0;
    private double upper_left_x = 0;
    private double upper_left_y = 0;
    private double upper_right_x = 0;
    private double upper_right_y = 0;
    private double lower_left_x = 0;
    private double lower_left_y = 0;
    private double blob_count = 0;
    
    private static final double aspectRatioTolerance = .2;
    private int imageWidthPx = 320;
    
    //daisy says to set this to 43.5 deg
    private double cameraFOVRads = Math.toRadians(47);
//    private double cameraFOVRads = Math.toRadians(43.5);

    protected void initDefaultCommand() {
        //no default command
    }
    
    public boolean hasTarget()
    {
        return currentTargetType != UNKNOWN_TARGET;
    }
    
    public void processData()
    {
        try{
            NetworkTable table = NetworkTable.getTable("vision");
            upper_left_x = (double) table.getNumber("p1x");
            upper_left_y = (double) table.getNumber("p1y");
            upper_right_x = (double)table.getNumber("p2x");
            upper_right_y = (double)table.getNumber("p2y");
            lower_left_x = (double) table.getNumber("p3x");
            lower_left_y = (double) table.getNumber("p3y");
            blob_count = (double) table.getNumber("count");
            
            //calculate aspect ratio of observed target
            currentAspectRatio = (upper_right_x - upper_left_x) / (upper_left_y - lower_left_y);
            
            double smallest_diff = Double.MAX_VALUE;
            double diff = 0.0;
            
            //compare the calculated aspect ratio to all known aspect ratios,
            //choosing the target type that has the closest aspect ratio.
            for(int i = 0; i < aspectRatios.length; i++)
            {
                diff = Math.abs(currentAspectRatio - aspectRatios[i]);
                if(diff < smallest_diff){
                    smallest_diff = diff;
                    currentTargetType = i;
                }
            }
            
            //make sure that target aspect ratio is within tolerance
            //if it isn't, we really don't have a target
            if(Math.abs(currentAspectRatio - aspectRatios[currentTargetType]) > aspectRatioTolerance || blob_count < 1)
            {
                currentTargetType = UNKNOWN_TARGET;
            }
            
            //determine target name
            currentTargetName = targetNames[currentTargetType];
            
            //calculate distance to the target
            distanceToTarget = (targetWidths[currentTargetType] * imageWidthPx)/
                                    ((upper_right_x - upper_left_x) * Math.tan(cameraFOVRads/2) * 2);
            
            //calculate difference between center of target and center of screen
            differencePx = (int)(((upper_right_x + upper_left_x)/2.0) - (imageWidthPx / 2.0));
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns which target the vision processor thinks it is seeing.
     * Valid target types:
     * 1) HIGH   (3pts) dims 62in x 20in     aspect 3.1
     * 2) MIDDLE (2pts) dims 62in x 29in     aspect 2.12
     * 3) LOW    (1pt)  dims 37in x 32in     aspect 1.16
     * 4) PRACTICE      dims 24in x 21.5in   aspect 1.08
     * @return 
     */
    public String getTargetType()
    {
        return currentTargetName;
    }
    
    public int getBlobCount()
    {
        return (int)blob_count;
    }
    
    public int getDifferencePx()
    {
        return differencePx;
    }
    
    public double getDistanceToTarget()
    {
        return distanceToTarget;
    }
    
    public double getAspectRatio()
    {
        return currentAspectRatio;
    }
}
