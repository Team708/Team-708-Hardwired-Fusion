///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package edu.wpi.first.wpilibj.templates.commands.catapult;
//
//import edu.wpi.first.wpilibj.templates.commands.CommandBase;
//
///**
// *
// * @author Nam Tran
// */
//public class LaunchBall extends CommandBase {
//    
//    public LaunchBall() {
//        requires(catapult);
//        // Use requires() here to declare subsystem dependencies
//        // eg. requires(chassis);
//    }
//
//    // Called just before this Command runs the first time
//    protected void initialize() {
//    }
//
//    // Called repeatedly when this Command is scheduled to run
//    protected void execute() {
//        while(catapult.getAngle() < catapult.getMaxAngle()) {
//            catapult.setMotorSpeed(catapult.getArmMoveSpeed());
//        }
//        
//        catapult.stop();
//        
//        while(catapult.getAngle() > catapult.getMinAngle()) {
//            catapult.setMotorSpeed(-catapult.getArmMoveSpeed());
//        }
//        
//        catapult.stop();
//    }
//
//    // Make this return true when this Command no longer needs to run execute()
//    protected boolean isFinished() {
//        return false;
//    }
//
//    // Called once after isFinished returns true
//    protected void end() {
//        catapult.stop();
//    }
//
//    // Called when another command which requires one or more of the same
//    // subsystems is scheduled to run
//    protected void interrupted() {
//        end();
//    }
//}