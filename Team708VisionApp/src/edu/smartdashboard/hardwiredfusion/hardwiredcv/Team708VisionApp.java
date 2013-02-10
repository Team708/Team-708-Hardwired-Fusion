package edu.smartdashboard.hardwiredfusion.hardwiredcv;


import edu.wpi.first.smartdashboard.camera.WPICameraExtension;
import edu.wpi.first.smartdashboard.robot.Robot;
import edu.wpi.first.wpijavacv.WPIColor;
import edu.wpi.first.wpijavacv.WPIColorImage;
import edu.wpi.first.wpijavacv.WPIImage;
import edu.wpi.first.wpijavacv.WPIPoint;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This SmartDashboard extension accepts data being output by the
 * RoboRealm vision processing program and displays it as a
 * box around the acquired target.
 * 
 * p1 = top left corner of box
 * p2 = top right
 * p3 = bottom left
 * p4 = bottom right
 * @author Connor Willison
 */
public class Team708VisionApp extends WPICameraExtension{
    
    private int p1x,p1y,p2x,p3y;
//    private boolean written = false;
    private boolean initialized = false;
    private ITable robot;
    private WPIPoint rp1,rp2,rp3,rp4;
    private int reticuleRadius = 5;
    
    public WPIImage processImage(WPIColorImage rawImage) {
        
        if(!initialized)
        {
            rp1 = new WPIPoint(rawImage.getWidth()/2,rawImage.getHeight()/2 - reticuleRadius);
            rp2 = new WPIPoint(rawImage.getWidth()/2,rawImage.getHeight()/2 + reticuleRadius);
            
            rp3 = new WPIPoint(rawImage.getWidth()/2 - reticuleRadius,rawImage.getHeight()/2);
            rp4 = new WPIPoint(rawImage.getWidth()/2 + reticuleRadius,rawImage.getHeight()/2);
            
            initialized = true;
        }
//        
	try
	{
            robot = Robot.getTable("vision");
            
            p1x = (int)robot.getNumber("p1x");
            p1y = rawImage.getHeight() - (int)robot.getNumber("p1y");
            p2x = (int)robot.getNumber("p2x");
            p3y = rawImage.getHeight() - (int)robot.getNumber("p3y");
            
            //draw a box around the acquired target
            rawImage.drawRect(p1x,p1y,p2x - p1x, p3y - p1y, WPIColor.GREEN,1);
	}
	catch (TableKeyNotDefinedException exp)
        {
//            if(!written)
//            {
//                try{
//                    File f = new File("C:/Users/Robotics/Desktop/dash_error.txt");
//                    if(!f.exists()) f.createNewFile();
//                    PrintStream stream = new PrintStream(f);
//                    exp.printStackTrace(stream);
//                    stream.close();
//                    
//                }catch(IOException e)
//                {
//                    
//                }
//                
//                written = true;
//            }
	}
        
        //draw a reticule in the center of the screen for easier aiming
        rawImage.drawLine(rp1,rp2, WPIColor.GREEN, 1);
        rawImage.drawLine(rp3,rp4, WPIColor.GREEN, 1);

        return rawImage;
    }
    
}
