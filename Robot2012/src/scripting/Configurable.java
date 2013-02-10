/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package scripting;

/**
 *
 * @author Connor Willison- Team 708
 */
public interface Configurable {

    //accept the name of the parameter and its values- must be parsed within the object
    public void update(String name, String[] values);
}
