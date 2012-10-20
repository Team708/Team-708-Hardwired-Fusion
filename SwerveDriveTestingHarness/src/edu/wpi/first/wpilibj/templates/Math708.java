/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.util.MathUtils;

/**
 *
 * @author 708
 */
public class Math708 {
    
    public static double round(double n,int dig)
    {
        int tens = (int)MathUtils.pow(10,dig);
        return (double)((int)((n * tens) + .5)) / tens;
    }
}
