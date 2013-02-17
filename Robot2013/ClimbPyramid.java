/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.templates.commands.Climbing.ExtendArms;
import edu.wpi.first.wpilibj.templates.commands.Climbing.Home;
import edu.wpi.first.wpilibj.templates.commands.Climbing.RetractArms;

/**
 *
 * @author Connor
 */
public class ClimbPyramid extends CommandGroup {
    
    public ClimbPyramid() {
        for(int i = 0; i < 5; i++)
        {
            addSequential(new ExtendArms());
            addSequential(new RetractArms());
        }
        
        addSequential(new Home());
    }
}
