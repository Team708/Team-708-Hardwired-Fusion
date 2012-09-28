/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package io;

import edu.wpi.first.wpilibj.DriverStationLCD;

/**
 *
 * @author Carl Burger and Conner Davis - Team 708
 */
public class MessageCenter {

    private static MessageCenter m_instance = null;

    public final static int kFirstScreen            = 0;
    public final static int kSecondScreen           = 1;
    public final static int kThirdScreen            = 2;
    public final static int kFourthScreen           = 3;
    public final static int kFifthScreen            = 4;
    public final static int kSixthScreen            = 5;
    public final static int kSeventhScreen          = 6;
    private static final int kNumberOfScreens       = 6; // change to 7 to add another screen

    public final static int kLine1 = 0;
    public final static int kLine2 = 1;
    public final static int kLine3 = 2;
    public final static int kLine4 = 3;
    public final static int kLine5 = 4;
    public final static int kLine6 = 5;
    private static final int kNumberOfLines = 6;

    private final static int kLineLength = DriverStationLCD.kLineLength;
    
    // Driver Station Output - 21 characters per line
    DriverStationLCD driverStationMsgs;

    String[][] screens;

    private int currScreen;
    private boolean currScreenUpdated;    // only update driver station if there's a
				          // change to avoid unnecessary message traffic
    private boolean messageCenterVisible; // operator can toggle on/off

    private MessageCenter() {

        driverStationMsgs = DriverStationLCD.getInstance();

        screens = new String[kNumberOfScreens][kNumberOfLines];

        for (int screen = 0; screen < screens.length; screen++) {
            for (int line = 0; line < screens[screen].length; line++) {
                screens[screen][line] = "                     ";
            }
        }

        currScreen = kFirstScreen;
        currScreenUpdated    = true; // ensure the screen is displayed
        messageCenterVisible = true;
    }

    /**
     *  Get an instance of the MessageCenter
     */
    public static MessageCenter getInstance() {
        if (m_instance == null) {
            m_instance = new MessageCenter();
        }
        return m_instance;
    }

    public void println(int screen, int line, String text) {

        if (screen >= screens.length) {
            System.out.println("MessageCenter - Invalid screen number " + screen);
        }
        else if (line >= screens[screen].length) {
            System.out.println("MessageCenter - Invalid line number " + line);
        }
        else {
            int len = text.length();
            
            if (len <= DriverStationLCD.kLineLength) {

                StringBuffer dest = new StringBuffer(DriverStationLCD.kLineLength);
                dest.append(text);

                for (int character = len; character < DriverStationLCD.kLineLength; character++) {
                    dest.append(" ");
                }
                screens[screen][line] = dest.toString();
            }
            else {
                screens[screen][line] = text;
                
                //this is really annoying
                //System.out.println("MessageCenter - Line too long " + text);
            }

            if (screen == currScreen) {
                currScreenUpdated = true;
            }

        }
    }

    public void display() {

        if ((currScreenUpdated == true) && (messageCenterVisible == true)) {
            
            driverStationMsgs.println(DriverStationLCD.Line.kMain6, 1, screens[currScreen][kLine1]);
            driverStationMsgs.println(DriverStationLCD.Line.kUser2, 1, screens[currScreen][kLine2]);
            driverStationMsgs.println(DriverStationLCD.Line.kUser3, 1, screens[currScreen][kLine3]);
            driverStationMsgs.println(DriverStationLCD.Line.kUser4, 1, screens[currScreen][kLine4]);
            driverStationMsgs.println(DriverStationLCD.Line.kUser5, 1, screens[currScreen][kLine5]);
            driverStationMsgs.println(DriverStationLCD.Line.kUser6, 1, screens[currScreen][kLine6]);

            driverStationMsgs.updateLCD();
            
            currScreenUpdated = false;
        }
    }

    public void toggleScreenControl() {

        messageCenterVisible = !messageCenterVisible;  // toggle current state
    }

    public void nextScreenNumber() {

        currScreen = ++currScreen % kNumberOfScreens;

        currScreenUpdated = true; // so that screen is updated
    }

    public void prevScreenNumber() {

        if (currScreen > 0) {
            --currScreen;
        }
        else {
            currScreen = kNumberOfScreens - 1;
        }

        currScreenUpdated = true; // so that screen is updated
    }
    
    public int getMaxLineLength(){
	return kLineLength;
    }

    public static String formatDouble(double number, int length) {

        String formattedNumber = Double.toString(number);

        if (formattedNumber.length() <= length) {
            return formattedNumber;
        }
        else {
            return formattedNumber.substring(0,length);
        }
    }

}