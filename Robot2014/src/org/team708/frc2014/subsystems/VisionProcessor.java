 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Hashtable;
import org.team708.frc2014.commands.vision.ProcessVisionData;
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
   // private final double highGoalAspectRatio = 3.73; 
    
    //Raw ball data from network table
    private double ball_center_x = 0;
    private double ball_radius = 0;
    private boolean hasBall = false;
    
    //Processed ball data
    private double ballDistanceIn = 0.0;
    private int ballCenterDiffPx = 0;
    
    //Ball constants
    private final double ballDiameterIn = 24;
    
    //Raw hot goal data
    private double hot_center_x = 0;
    private boolean hasHotGoal = false;
    
    //Processed hot goal data
    private double hotCenterDiffPx = 0;
    
    //General roborealm data
    private double blob_count = 0;
    
    // Numbers used to do vision processing
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
        setDefaultCommand(new ProcessVisionData());
    }

    public boolean hasBall() {
        return hasBall;
    }

    public int getBallCenterDiffPx() {
        return ballCenterDiffPx;
    }

    public boolean hasHotGoal() {
        return hasHotGoal;
    }

    public double getHotCenterDiffPx() {
        return hotCenterDiffPx;
    }
    
    /**
     * Returns the distance to the target.
     * @return
     */
    public double getBallDistanceIn()
    {
        return ballDistanceIn;
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
            ball_center_x = (double) table.getNumber("bcx");
            ball_radius = (double) table.getNumber("r");
            hasBall = table.getNumber("ball") > 0;
            hot_center_x = (double) table.getNumber("hcx");
            hasHotGoal = table.getNumber("goal") > 0;
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
            
            ballDistanceIn = (ballDiameterIn * imageWidthPx)/
                                    ((2* radius) * Math.tan(cameraFOVRads/2) * 2);
            
            //calculate difference between center of target and center of screen
            ballCenterDiffPx = (int)(center_x - (imageWidthPx / 2.0));
            
        }catch(Exception e)
        {
//            e.printStackTrace();
        }
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
