package edu.smartdashboard.hardwiredfusion.hardwiredcv;


import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_core.CvSize;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_imgproc;
import edu.wpi.first.smartdashboard.camera.WPICameraExtension;
import edu.wpi.first.wpijavacv.CVUtils;
import edu.wpi.first.wpijavacv.WPIColor;
import edu.wpi.first.wpijavacv.WPIColorImage;
import edu.wpi.first.wpijavacv.WPIImage;
import edu.wpi.first.wpijavacv.WPIPoint;
import java.awt.image.Raster;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Robotics
 */
public class Team708VisionApp extends WPICameraExtension{

    private IplImage rawCVFormat;
    private CvSize imageDims = opencv_core.cvSize(320,240);
    private IplImage hsvImage = IplImage.create(imageDims,8,3);
    private IplImage hue = IplImage.create(imageDims,8,1);
    private IplImage saturation = IplImage.create(imageDims,8,1);
    private IplImage value = IplImage.create(imageDims, 8, 1);
    private IplImage lowerSide = IplImage.create(imageDims, 8,1);
    private IplImage upperSide = IplImage.create(imageDims, 8,1);
    private Raster r;
    private int[] pixel = new int[1];
    
    //color filtering constants
    private final int hueLow = 95;
    private final int hueHigh = 151;
    private final int satLow = 205;
    private final int valLow = 205;
    
    @Override
    public WPIImage processImage(WPIColorImage rawImage) {
        rawCVFormat = CVUtils.getIplImage(rawImage);
        
        // convert raw image to HSV color space
        opencv_imgproc.cvCvtColor(rawCVFormat,hsvImage, opencv_imgproc.CV_BGR2HSV);
        
        //split each HSV channel into a separate image
        opencv_core.cvSplit(hsvImage,hue,saturation,value, null);
        
        //filter out all pixels except those orange colored (retroreflective tape)
        //that is, between 23 and 62 on hue channel.
        opencv_imgproc.cvThreshold(hue,upperSide,hueLow,255,opencv_imgproc.CV_THRESH_BINARY);
        opencv_imgproc.cvThreshold(hue,lowerSide,hueHigh,255,opencv_imgproc.CV_THRESH_BINARY_INV);
        
        //filter out saturation values that are too low
        opencv_imgproc.cvThreshold(saturation,saturation,satLow,255,opencv_imgproc.CV_THRESH_BINARY);
        
        //filter out saturation values that are too low
        opencv_imgproc.cvThreshold(value,value,valLow,255,opencv_imgproc.CV_THRESH_BINARY);
        
        //combine the images containing the pixels that we want
        opencv_core.cvAnd(hue,lowerSide,hue,null);
        opencv_core.cvAnd(hue,upperSide,hue,null);
        opencv_core.cvAnd(hue,saturation,hue,null);
        opencv_core.cvAnd(hue,value,hue,null);
        
        r = hue.getBufferedImage().getData();
        
        for(int x = 0; x < r.getWidth(); x++)
        {
            for(int y = 0; y < r.getHeight(); y++)
            {
                r.getPixel(x,y,pixel);
                if(pixel[0] > 0)
                {
                    rawImage.drawPoint(new WPIPoint(x,y), WPIColor.GREEN,1);
                    
                }
            }
        }
        
        return rawImage;
    }
    
}
