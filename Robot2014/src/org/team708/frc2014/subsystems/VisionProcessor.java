// /*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.team708.frc2014.subsystems;
//
//import edu.wpi.first.wpilibj.command.Subsystem;
//import edu.wpi.first.wpilibj.networktables.NetworkTable;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import org.team708.frc2014.commands.vision.ProcessVisionData;
//
///**
// * This class contains an algorithm that receives
// * data from the RoboRealm vision program and performs
// * calculations to:
// * 1) Identify the target(s) in the image.
// * 2) Provide information to code that reacts to the target(s).
// * @author Connor Willison
// */
//public class VisionProcessor extends Subsystem{
//   
//    //Raw hot goal data
//    private int hot_center_x = 0;
//    
//    //Processed hot goal data
//    private int hotCenterDiffPx = 0;
//    // Numbers used to do vision processing
//    private static final int imageWidthPx = 320;
//    private static final int imageCenterPx = imageWidthPx / 2;
//    private static final int hotCenterBoundPx = 50;
//    
//    
//    //daisy says to set this to 43.5 deg
////    private final double cameraFOVRads = Math.toRadians(47);
////    private double cameraFOVRads = Math.toRadians(43.5);
//    
//    /**
//     * Constructor
//     */
//    public VisionProcessor()
//    {
//        super("Vision Processor");
//    }
//
//    /**
//     * Sets the default command for the subsystem.
//     */
//    protected void initDefaultCommand() {
//        setDefaultCommand(new ProcessVisionData());
//    }
//
//    private int getHotCenterDiffPx() {
//        return hotCenterDiffPx;
//    }
//    
//    public boolean isHotGoalLeft()
//    {
//        return getHotCenterDiffPx() < -hotCenterBoundPx;
//    }
//    
//    public boolean isHotGoalRight()
//    {
//        return getHotCenterDiffPx() >= hotCenterBoundPx;
//    }
//    
//    public boolean isHotGoalCentered()
//    {
//        return Math.abs(getHotCenterDiffPx()) < hotCenterBoundPx;
//    }
//    
//    /**
//     * Processes the data from the network table to determine various things.
//     * It sets if the goal is lit and if a ball is found. It also can calculate
//     * various distances from things and find the differences between pixels.
//     */
//    public void processData()
//    {
//        try{
//            /*
//             * Receive data from RoboRealm program.
//             */
//            NetworkTable table = NetworkTable.getTable("vision");
//            hot_center_x = (int) table.getNumber("hcx");
//            
//            //subtract from center
//            hotCenterDiffPx = hot_center_x - imageCenterPx;
//            
//        }catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//    
//    /**
//     * Sends data to the dumb-dashboard.
//     */
//    public void sendToDash()
//    {
//        SmartDashboard.putNumber("Raw Hot Goal X:",hot_center_x);
//        SmartDashboard.putNumber("Processed Hot Goal X:", hotCenterDiffPx);
//    }
//}
