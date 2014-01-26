/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilclasses;

import com.sun.squawk.util.MathUtils;

/**
 * Miscellaneous math functions.
 * @author Connor Willison
 */
public class Math708 {
    
    /**
     * Performs linear interpolation between the points (x1,y1) and (x2,y2)
     * and returns the interpolated value of the function at x_result.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return 
     */
    public static double lerp(double x1,double y1, double x2, double y2,double x_result)
    {
        return ((x_result - x1) * (y2 - y1) / (x2 - x1)) + y1;
    }
    
    /**
     * Used to round a floating point value to
     * a given decimal place.
     * @param n The floating point value.
     * @param dig The decimal place to round to (use negative
     * numbers for places > 0.
     * @return 
     */
    public static double round(double n,int dig)
    {
        double tens = MathUtils.pow(10,dig);
        return ((int)((n * tens) + .5)) / tens;
    }
    
    /**
     * Returns the length of a vector with the specified
     * components.
     * @param x
     * @param y
     * @return 
     */
    public static double length(double x,double y)
    {
        return Math.sqrt(lengthSquared(x,y));
    }
    
    public static double lengthSquared(double x,double y)
    {
        return x * x + y * y;
    }
}
