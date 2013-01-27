/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpijavacv;

import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_imgproc;

/**
 *
 * @author Robotics
 */
public class CVUtils {
    
    public static IplImage getIplImage(WPIImage image)
    {
        return image.image;
    }
    
    public static void modifyRawImage(WPIImage rawImage,IplImage processed)
    {
        opencv_imgproc.cvCvtColor(rawImage.image,rawImage.image,opencv_imgproc.CV_BGR2HSV);
    }
}

