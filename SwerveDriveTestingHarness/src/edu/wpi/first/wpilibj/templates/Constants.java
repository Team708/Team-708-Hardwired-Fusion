/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Relay;

/**
 * This class will provide access to the channels of each
 * device on connected to the cRIO and the slots of cRIO modules.
 * @author 708
 */
public class Constants{
    
    public class crioModules{
        public static final int kModule1 = 1;
	public static final int kModule2 = 2;
    }
    
    public class PWMChannels{
        public static final int kFrontRightCIM = 0;
        public static final int kFrontLeftCIM = 1;
        public static final int kRearLeftCIM = 2;
        public static final int kRearRightCIM = 3;
    }
    
    public class RelayChannels{
        public static final int kFrontRightSpike = 0;
        public static final int kFrontLeftSpike = 1;
        public static final int kRearLeftSpike = 2;
        public static final int kRearRightSpike = 3;
    }
    
    public class DigitalIOChannels{
        public static final int kFrontRightEncoderA = 1;
        public static final int kFrontRightEncoderB = 2;
        public static final int kFrontLeftEncoderA = 3;
        public static final int kFrontLeftEncoderB = 4;
        public static final int kRearLeftEncoderA = 1;
        public static final int kRearLeftEncoderB = 2;
        public static final int kRearRightEncoderA = 3;
        public static final int kRearRightEncoderB = 4;
    }
    
    public class AnalogInputChannels{
        public static final int kFrontRightPot = 0;
        public static final int kFrontLeftPot = 1;
        public static final int kRearLeftPot = 2;
        public static final int kRearRightPot = 3;
    }
    
    public class SolenoidChannels{
	//add solenoids for tshirt cannon
    }
    
    public class Calibrations
    {
        public static final double kFrontRightPotLowVlts = 0.0;
        public static final double kFrontRightPotHighVlts = 1.0;
        public static final double kFrontLeftPotLowVlts = 0.0;
        public static final double kFrontLeftPotHighVlts = 1.0;
        public static final double kRearLeftPotLowVlts = 0.0;
        public static final double kRearLeftPotHighVlts = 1.0;
        public static final double kRearRightPotLowVlts = 0.0;
        public static final double kRearRightPotHighVlts = 1.0; 
    }
}
