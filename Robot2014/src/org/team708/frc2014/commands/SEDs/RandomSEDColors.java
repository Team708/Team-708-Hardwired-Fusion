/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.commands.SEDs;

import edu.wpi.first.wpilibj.Timer;
import java.util.Random;
import org.team708.frc2014.commands.CommandBase;

/**
 *
 * @author Connor Willison
 */
public class RandomSEDColors extends CommandBase{

    private Timer randomTimer;
    private double randomInterval = 5.0;
    private Random random;
    
    public RandomSEDColors()
    {
        requires(sedArray);
    }
    
    protected void initialize() {
        random = new Random();
        randomTimer = new Timer();
        randomTimer.start();
    }

    protected void execute() {
        if(randomTimer.get() >= randomInterval)
        {
            sedArray.setColor(random.nextInt() % 8);
            randomTimer.reset();
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
