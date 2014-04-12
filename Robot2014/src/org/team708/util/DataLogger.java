/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.util;

import com.sun.squawk.io.BufferedWriter;
import com.sun.squawk.microedition.io.FileConnection;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import javax.microedition.io.Connector;

/**
 * This class is used to output .csv (Comma-Separated Value) files which can be
 * read in excel. It is helpful for data analysis. The format of the output is:
 *
 * @author Connor Willison
 */
public class DataLogger {

    private final String m_defaultPath = "/ni-rt/datalogs/";
    private final String m_defaultFilename = "datalog";
    private String m_filename;
    private Vector m_data;                 //vector (dynamic array) for storing string data.
    private Vector m_headings;             //list of table headings (in order left to right) 
    private Random m_random;
    private long m_prevLogTime;
    private long m_granularity;
    private final long m_defaultGranularity = 1; //1 msec minimum time between timestamps
    private final String m_noData = "0";
    private final int m_capacity = 500;    //max amount of entries to buffer before writing.
    private boolean m_logging;             //whether or not the log file has been started.

    public DataLogger() {
        m_logging = false;
        m_data = new Vector();
        m_granularity = -1;
        m_prevLogTime = Long.MIN_VALUE;
        m_random = new Random(System.currentTimeMillis());
    }

    /**
     * Start a new log using the given filename.
     *
     * @param path
     */
    public void startLog(String filename) {
        m_logging = true;
        m_filename = filename;
        if (m_granularity < 0) {
            m_granularity = m_defaultGranularity;
        }
    }

    /**
     * Start a new log using the default filename. NOTE: Retrieve logs from
     */
    public void startLog() {
        startLog(m_defaultFilename);
    }

    /**
     * Ends and writes the current log to flash memory at the default location,
     * /ni-rt/datalogs/ If the file exists, the current data is appended.
     */
    public void endLog() {
        m_logging = false;
        write(true);
    }

    /*
     * Logging functions for various data types.
     * If the heading is not defined, it is created.
     */
    public void log(String heading, double x) {
        log(heading, String.valueOf(x));
    }

    public void log(String heading, int x) {
        log(heading, String.valueOf(x));
    }

    public void log(String heading, boolean x) {
        log(heading, String.valueOf(x));
    }

    public void log(String heading, String x) {
        if (m_logging) {
            if (!m_headings.contains(heading)) {
                m_headings.addElement(heading);
            }

            LogEntry current;

            if (System.currentTimeMillis() - m_prevLogTime > m_granularity) {
                current = makeNewEntry();
            } else {
                //use the previous log entry (overwrite or fill in new data)
                if (m_data.lastElement() != null) {
                    current = (LogEntry) m_data.lastElement();
                } else {
                    //there is no previous entry
                    current = makeNewEntry();
                }
            }

            current.put(heading, x); //store the data
        }
    }

    private LogEntry makeNewEntry() {
        //create a new log entry
        LogEntry current = new LogEntry();

        if (m_data.lastElement() != null) {
            LogEntry prevEntry = (LogEntry) m_data.lastElement();
            //fill in values from last entry
            for (int i = 0; i < m_headings.size(); i++) {

                String key = (String) m_headings.elementAt(i);

                //check if the previous entry contains a value for this heading
                if (prevEntry.containsKey(key)) {
                    //fill it in
                    current.put(key, prevEntry.get(key));  //this ensures that if a value becomes untracked
                    //or is written at a different rate, it retains previous value
                } else {
                    //fill in no data symbol
                    current.put(key, m_noData);
                }
            }
        }

        //add to log
        checkCapacity();
        m_data.addElement(current);

        return current;
    }

    /**
     * Helper method for writing the log.
     *
     * Timestamp,Heading1,Heading2....\n time,data1,data2...\n .... ....
     *
     */
    private void write(boolean delimiter) {
        try {
            checkDirectory();

            FileConnection file = (FileConnection) Connector.open(m_defaultPath + m_filename + ".csv", Connector.WRITE);

            //ensure that the file exists
            if (!file.exists()) {
                file.create();
            }

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(file.openOutputStream()));

            //write delimiter to separate table from others
            if (delimiter) {
                writer.write("Begin Log: " + makeHash());
                writer.newLine();
            }

            //write headings
            writer.write("Timestamp");

            for (int i = 0; i < m_headings.size(); i++) {
                writer.write((String) m_headings.elementAt(i));

                if (i < m_headings.size() - 1) {
                    writer.write(",");
                }


            }
            writer.newLine();

            //write data
            for (int i = 0; i < m_data.size(); i++) {
                LogEntry e = (LogEntry) m_data.elementAt(i);
                writer.write((String) e.get("ts"));
                for (int j = 0; j < m_headings.size(); j++) {
                    writer.write(",");

                    String key = (String) m_headings.elementAt(i);

                    if (e.containsKey(key)) {
                        writer.write((String) e.get(m_headings.elementAt(j)));
                    } else {
                        //write no data symbol
                        writer.write(m_noData);
                    }
                }

                writer.newLine();
            }

            //close stream
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ensure the directory exists.
     */
    private void checkDirectory() throws IOException {
        FileConnection file = (FileConnection) Connector.open(m_defaultPath, Connector.WRITE);

        if (!file.exists()) {
            file.create();
        }
    }

    /**
     * Ensures that the data log never goes over capacity by writing the
     * buffered data.
     */
    private void checkCapacity() {
        if (m_data.size() >= m_capacity) {
            write(false);
            m_data.removeAllElements();
        }
    }

    /**
     * Ensure that the directory exists.
     *
     * @param msec
     */
    public void setGranularity(long msec) {
        if (!m_logging) {
            m_granularity = msec;
        }
    }

    /**
     * Make a 10-character hash string.
     *
     * @return
     */
    private String makeHash() {
        String h = "";
        int x;

        for (int i = 0; i < 10; i++) {
            x = m_random.nextInt(36);
            if (x < 26) {
                h += (x + 'a');
            } else {
                h += ((x - 26) + '0');
            }
        }

        return h;
    }

    private class LogEntry extends Hashtable {

        public LogEntry() {
            this.put("ts", String.valueOf(System.currentTimeMillis()));
        }
    };
}
