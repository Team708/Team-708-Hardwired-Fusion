/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scripting;

import com.sun.squawk.io.BufferedReader;
import com.sun.squawk.microedition.io.FileConnection;
import com.sun.squawk.util.StringTokenizer;
import io.MessageCenter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.Hashtable;
import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.io.Connector;

/**
 * The Configuration class provides a mechanism to read a downloaded
 * configuration file and update configuration parameters without
 * the need to modify the values in the source code.  Objects register
 * with the Configuration object to be notified of any configuration
 * changes.
 *
 * The configuration file syntax is:
 *   parameter_name <argument(s)>
 *
 *   where parameter_name is key.name
 *       key is the class identifier or top-level identifier used for
 *           registration
 *       name is a string that identifies the parameter
 *
 * One or more parameter arguments must be provided, separated by
 * blanks
 *
 * Each parameter specification must fit on a single line
 *
 * Objects register by key
 *  etc.
 * @author Mentors, Connor Willison - Team 708
 */

public class Configuration {

    private static Configuration m_instance;
    private static final String	configFilePath = "file:////ni-rt/startup/config.cfg";
    private Hashtable		 registrations;

    public static Configuration getInstance() {
	if (m_instance == null) {
	    m_instance = new Configuration();
	}
        
	return m_instance;
    }

    private Configuration() {
	registrations = new Hashtable();
    }

    /**
     * This method reads the config file currently indicated
     * by this Configuration object's configFilePath field.
     */
    public void readConfig() {

	try {
	    FileConnection fcConfigFile = (FileConnection) Connector.open(configFilePath,
		    Connector.READ);
	    InputStream ist = fcConfigFile.openInputStream();
	    BufferedReader bufStream = new BufferedReader(new InputStreamReader(ist));

	    System.out.println("Configuration: file opened");

	    short configPass = 0;
	    short configFail = 0;
	    StringTokenizer st;

	    String paramName;
	    String[] paramValues;

	    String line = bufStream.readLine(); // read the first line in the text file

	    while (line != null) // while more lines in the file
	    {
		//ignore comments starting with "#" and whitespace
		if (line.startsWith("#")) {
		    line = bufStream.readLine();
		    continue;
		}
		
		st = new StringTokenizer(line); // break the line into separate strings

                //skip whitespace
                if(st.hasMoreTokens()){

		int numtokens = st.countTokens();
		if (numtokens > 1) // parameter name and values required
		{
		    //get the name of the parameter
		    paramName = st.nextToken();

		    //parse the hash key from the param name
		    int endindex = paramName.indexOf(".");
		    String key = paramName.substring(0, endindex);
		    paramName = paramName.substring(endindex + 1, paramName.length());

		    //check the hashtable for a receiver object
		    if (registrations.containsKey(key)) {

			//to store the parameter values
			paramValues = new String[numtokens - 1];

			//accumulate the values
			for (int i = 0; i < paramValues.length; i++) {
			    paramValues[i] = st.nextToken();
			}

			Configurable obj = (Configurable) registrations.get(key);
			obj.update(paramName, paramValues);

			System.out.println("Configuration: param name read: " + key);
			configPass++;
		    } else {
			System.out.println("Configuration: unknown param name: " + key);
			configFail++;
		    }

		} else {
		    System.out.println("Configuration: parameters not found on line: " + line);
                    configFail++;
		}
            }else{
                //skip whitespace
                System.out.println("Skipping whitespace...");
            }

		line = bufStream.readLine();	//read the next line
	    }

	    System.out.println("Configuration: pass/fail: " + configPass + "/" + configFail);

	    ist.close();

	} catch (ConnectionNotFoundException cfnf) {

	    System.out.println("Configuration: " + cfnf.getMessage());
	} catch (IOException ioe) {

	    System.out.println("Configuration: " + ioe.getMessage());
	
	//these get generated during parsing of the lines
	//if the user forgot "."'s, etc.
	} catch (StringIndexOutOfBoundsException siobe) {

	    System.out.println("Configuration: syntax error" + siobe.getMessage());
	}

    }

    /**
     * This method associates a config file parameter name with an object.
     * This object will receive all data read from the config file that begins
     * with the associated parameter name.
     * @param name
     * @param obj
     */
    public void register(String name, Configurable obj) {
	registrations.put(name, obj);	//map the name to the object in the hashtable
    }

    /**
     * This method clears all Configuration registrations
     */
    public void reset() {
	registrations.clear();
    }
}