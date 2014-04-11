/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team708.util;

import com.sun.squawk.io.BufferedWriter;
import com.sun.squawk.microedition.io.FileConnection;
import edu.wpi.first.wpilibj.networktables2.util.List;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.io.Connector;

/**
 * This class is used to output .csv (Comma-Separated Value) files which can be
 * read in excel. It is helpful for data analysis. The format of the output is:
 *
 * @author Connor Willison
 */
public class DataLogging {

    private final String m_defaultPath = "/ni-rt/datalogs/";
    private final String m_defaultFilename = "datalog";
    private String m_filename;
    private Vector m_data;                 //vector (dynamic array) for storing string data.
    private Vector m_headings;             //list of table headings (in order left to right) 
    private long m_prevLogTime;
    private long m_granularity;
    private final long m_defaultGranularity = 1; //1 msec minimum time between timestamps
    private final String m_noData = "0";
    private final int m_capacity = 500;    //max amount of entries to buffer before writing.
    private boolean m_logging;             //whether or not the log file has been started.

    public DataLogging() {
        m_logging = false;
        m_data = new Vector();
        m_granularity = -1;
        m_prevLogTime = Long.MIN_VALUE;
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
     * /ni-rt/datalogs/
     * If the file exists, the current data is appended.
     */
    public void endLog() {
        m_logging = false;
        write();
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
            }else
            {
                //there is no previous entry
                current = makeNewEntry();
            }
        }

        current.put(heading, x); //store the data
    }

    private LogEntry makeNewEntry() {
        //create a new log entry
        LogEntry current = new LogEntry();

        if (m_data.lastElement() != null) {
            //fill in values from last entry
            for (int i = 0; i < m_headings.size(); i++) {
                LogEntry prevEntry = (LogEntry) m_data.lastElement();
                String key = (String) m_headings.elementAt(i);

                //check if the previous entry contains a value for this heading
                if (prevEntry.containsKey(key)) {
                    //fill it in
                    current.put(key, prevEntry.get(key));
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
     * Timestamp,Heading1,Heading2....\n
     * time,data1,data2...\n
     * ....
     * ....
     * 
     */
    private void write()
    {
        try
        {
            FileConnection file = (FileConnection) Connector.open(m_defaultPath + m_filename + ".csv",Connector.WRITE);
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(file.openOutputStream()));
            
            //write delimiter
            
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }   
    
    /**
     * Ensures that the data log never goes over capacity by
     * writing the buffered data.
     */
    private void checkCapacity()
    {
        if(m_data.size() >= m_capacity)
        {
            write();
            m_data.removeAllElements();
        }
    }

    public void setGranularity(long msec) {
        m_granularity = msec;
    }

    private class LogEntry extends Hashtable {

        private long m_timestamp;

        public LogEntry() {
            m_timestamp = System.currentTimeMillis();
            this.put("ts", String.valueOf(m_timestamp));
        }

        public long getTimestamp() {
            return m_timestamp;
        }
    };
}
