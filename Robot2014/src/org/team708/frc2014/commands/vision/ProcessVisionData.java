///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.team708.frc2014.commands.vision;
//
//import edu.wpi.first.wpilibj.Timer;
//import org.team708.frc2014.commands.CommandBase;
//
///**
// *
// * @author Robotics
// */
//public class ProcessVisionData extends CommandBase {
//    
//    private Timer timer;
//    private static final double processIntervalSec = .1; //process data every 10th of a second (?)
//    
//    public ProcessVisionData() {
//        timer = new Timer();
//        // Use requires() here to declare subsystem dependencies
//        requires(visionProcessor);
//    }
//
//    // Called just before this Command runs the first time
//    protected void initialize() {
//        timer.start();
//    }
//
//    // Called repeatedly when this Command is scheduled to run
//    protected void execute() {
//        if(timer.get() >= processIntervalSec)
//        {  
//            visionProcessor.processData();
//            timer.reset();
//        }
//    }
//
//    // Make this return true when this Command no longer needs to run execute()
//    protected boolean isFinished() {
//        return false;
//    }
//
//    // Called once after isFinished returns true
//    protected void end() {
//    }
//
//    // Called when another command which requires one or more of the same
//    // subsystems is scheduled to run
//    protected void interrupted() {
//    }
//}
