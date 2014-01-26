package sdp.ggj14.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * Static class that handles the loading and saving of .properties files.
 * These files are then cached, so various parts of the program can access the data via this class.
 * 
 * There are two main areas that we use .properties files. 
 * The first is to store the program's constants, so that they can be tweaked externally by the end user.
 * Constants such as the min and max parameters for a game, and the colours that the program renders for various purposes.
 * The second use are presets for the Match generator.
 * 
 * @author Michael D'Andrea
 * @author Devindra Payment
 */
public abstract class PropertiesLoader {
	/** All the game's properties, mapped to the paths to their files. */
	private static HashMap<String, Properties> propertiesMap = new HashMap<String, Properties>();
	
	/**
	 * Retrieve the contents of a .properties file.
	 * This function will either get the Properties object from memory, 
	 * or load it from the file if it hasn't already been cached.
	 * 
	 * @param filePath the path to and name of the .properties file.
	 * @return the Properties object that represents the requested file.
	 */
	public static Properties get(String filePath) {
		if (! filePath.endsWith(".properties")) {
			filePath += ".properties";
		}
		
		if (propertiesMap.containsKey(filePath)) { // Check if the file is cached.
			// If so, return the cached version of the file.
			return propertiesMap.get(filePath);
		} else {
			try {
				InputStream input = new FileInputStream(filePath); // Open the requested file
				Properties properties = new Properties(); // Create a new Properties object.
				properties.load(input); // Populate the properties object with the input file.
				input.close(); // Close the requested file.
				
				propertiesMap.put(filePath, properties); // Cache the file, so that it can be quickly loaded in the future.
				
				return properties; // Return our new properties object.
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null; // If the try/catch block fails, then return null. The properties could not be loaded.
	}
	
	public static String getString(String property) {
		Properties properties = PropertiesLoader.get(System.getProperty("user.dir")+"/dat/settings/Game");
		
		System.out.println(properties.get(property).toString());
		return properties.get(property).toString();
	}
	
	public static int getInt(String property) {
		return Integer.parseInt(getString(property));
	}
	
	public static double getDouble(String property) {
		return Double.parseDouble(getString(property));
	}
	
	public static boolean getBoolean(String property) {
		return Boolean.parseBoolean(getString(property));
	}
	
}
