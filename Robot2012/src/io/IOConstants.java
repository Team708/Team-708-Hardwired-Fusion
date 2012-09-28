/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

/**
 * This class will provide access to the channels of each
 * device on connected to the cRIO and the slots of cRIO modules.
 * @author 708
 */
public class IOConstants{
    
    public class crioModules{
        public static final int kModule1 = 1;
	public static final int kModule2 = 2;
    }

    public class Driverstation{
        public static final int driver_Gamepad = 1;
        public static final int operator_Gamepad = 2;
    }
    
    public class PWMChannels{
	public static final int kLeftMotor = 1;
	public static final int kRightMotor = 2;
        public static final int kFlyWheelMotor = 3;
        public static final int kRotationMotor = 4;
        public static final int kArmMotor = 5;
    }
    
    public class RelayChannels{
        public static final int kLowerMotor = 1;
        public static final int kUpperMotor = 5;
        public static final int kBrushSpike = 3;
        public static final int kArmSpike = 4;
    }
    
    public class DigitalIOChannels{
        public static final int kLeftEncoderA = 1;
        public static final int kLeftEncoderB = 2;
        public static final int kRightEncoderA = 3;
        public static final int kRightEncoderB = 4;
        public static final int kShooterEncoderA = 5;
        public static final int kShooterEncoderB = 6;
        public static final int kLeftLimit = 7;
        public static final int kRightLimit = 8;
        public static final int kArmUp = 9;
        public static final int kArmDown = 10;
        public static final int kBrushVertical = 11;
    }
    
    public class AnalogInputChannels{
        public static final int kGyro = 1;
        public static final int kTopBallSensor = 2;
        public static final int kEntranceSensor = 3;
        public static final int kMiddleBallSensor = 4;
        public static final int kBottomBallSensor = 5;
        public static final int kShooterPot = 6;
        public static final int kArmPot = 7;
    }
    
    public class SolenoidChannels{
	public static final int kGatePiston = 1;
    }

    public class Network{
        public static final String cameraIP = "10.7.8.11";
    }
}
