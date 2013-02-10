/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import bridge.Arm;
import harvester.Harvester;
import harvester.Hold;
import java.util.Timer;
import java.util.TimerTask;
import shooter.Shooter;

/**
 *
 * @author 708
 */
public class Manipulator {

    private Timer timer;
    private boolean running = false;
//    private boolean prepareToShootPressed = false, shootPressed = false;
    private Shooter shooter;
    private Harvester harvester;
    private Arm arm;
    private Hold hold;

    public Manipulator(Shooter shooter, Harvester harvester,Hold hold,Arm arm) {
        //schedule task to run @50 hz
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Task(), 0, 20);

        this.shooter = shooter;
        this.harvester = harvester;
        this.hold = hold;
        this.arm = arm;
    }

    private class Task extends TimerTask {

        public void run() {
                //control shooter
                shooter.control();

                //control harvester
                harvester.harvest();

                //control arm
                arm.control();

        }
    }

    public void enable() {
        running = true;

//        synchronized (this) {
//            //wake up manipulator thread
//            this.notify();
//        }
    }

    public void disable() {
        running = false;
    }

    public boolean isEnabled() {
        return running;
    }
}
