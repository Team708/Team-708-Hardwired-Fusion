/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testproj;

/**
 * Miscellaneous math functions.
 * @author 708
 */
public class Math708 {
    
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
        double tens = Math.pow(10,dig);
        return ((int)((n * tens) + .5)) / tens;
    }
}
