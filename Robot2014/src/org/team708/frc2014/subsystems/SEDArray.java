/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.frc2014.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team708.frc2014.RobotMap;
import org.team708.frc2014.commands.SEDs.RandomSEDColors;

/**
 *
 * @author Nam Tran, Matt Foley
 */
public class SEDArray extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    private Solenoid red, blue, green,power;
    
    public static final int BLACK = 0;   //000
    public static final int RED = 1;     //001
    public static final int GREEN = 2;   //010
    public static final int BLUE = 4;    //100
    public static final int YELLOW = 3;  //011
    public static final int VIOLET = 5;  //101
    public static final int TEAL = 6;    //110
    public static final int WHITE = 7;   //111
    
    private final DriverStation.Alliance redAlliance = DriverStation.Alliance.kRed;
    private final DriverStation.Alliance blueAlliance = DriverStation.Alliance.kBlue;
    
    public SEDArray() {
        red = new Solenoid(RobotMap.redSED);
        blue = new Solenoid(RobotMap.blueSED);
        green = new Solenoid(RobotMap.greenSED);
        power = new Solenoid(RobotMap.powerSED);
        power.set(true);
        setColor(WHITE);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new RandomSEDColors());
    }
    
    public void setColor(int color)
    {
        red.set((color & 1) > 0);
        green.set((color & 2) > 0);
        blue.set((color & 4) > 0);
    }
    
    public void setToAllianceColour() {
//        if (DriverStation.getAlliance().equals(redAlliance)) {
//            
//        }
    }
}
