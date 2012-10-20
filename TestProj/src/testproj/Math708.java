/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testproj;

/**
 *
 * @author 708
 */
public class Math708 {
    
    public static double round(double n,int dig)
    {
        int tens = (int)Math.pow(10,dig);
        return (double)((int)((n * tens) + .5)) / tens;
    }
}
