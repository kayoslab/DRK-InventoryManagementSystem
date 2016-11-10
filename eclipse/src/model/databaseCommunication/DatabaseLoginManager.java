package model.databaseCommunication;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class DatabaseLoginManager {
	private String databaseUsername;
	private String databasePassword;
	private String databaseURL;
	public String configLocation = System.getProperty("user.home") + File.separator + ".DRKstockMngmt" + File.separator + "config";
	/**
	 * Constructor
	 */
	public DatabaseLoginManager() {
		this.loadDatabaseLoginCredentials();
	}
	
	/**
	 * @param String usename, String password, String url
	 * @return Boolean
	 */
	public DatabaseLoginManager(String username, String password, String url) {
		this.databaseUsername = username;
		this.databasePassword = password;
		this.databaseURL = url;
		this.storeDatabaseLoginCredentials();
	}
	
	/**
	 * @param String username
	 */
	public void setDatabaseUsername(String username) {
		this.databaseUsername = username;
		this.storeDatabaseLoginCredentials();
	}
	
	/**
	 * @param String password
	 */
	public void setPassword(String password) {
		this.databasePassword = password;
		this.storeDatabaseLoginCredentials();
	}
	
	/**
	 * @param String url
	 */
	public void setURL(String url) {
		this.databaseURL = url;
		this.storeDatabaseLoginCredentials();
	}
	
	/**
	 * @return String username
	 */
	public String getUsername() {
		if (this.databaseUsername == null) {
			this.loadDatabaseLoginCredentials();
		}
		return this.databaseUsername;
	}
	
	/**
	 * @return String password
	 */
	public String getPassword() {
		if (this.databasePassword == null) {
			this.loadDatabaseLoginCredentials();
		}
		return this.databasePassword;
	}
	
	/**
	 * @return String URL
	 */
	public String getURL() {
		if (this.databaseURL == null) {
			this.loadDatabaseLoginCredentials();
		}
		return this.databaseURL;
	}
	
	/**
	 * Test credentials for Login
	 */
	public Boolean testDatabaseConnection() {
		if (this.databaseUsername != null && this.databasePassword != null && this.databaseURL != null) {
			// TODO: Implement connection test
			return true;
		}
		return false;
	}
	
	/**
	 * Store the given Credentials into local file
	 */
	private void storeDatabaseLoginCredentials() {
		if (this.databaseUsername != null && this.databasePassword != null && this.databaseURL != null) {
			File filePathInstance = new File(configLocation);
			if (filePathInstance.getParentFile().exists()) {
			    // Directory already exists
				this.writeConfigFile(filePathInstance);
			} else if (filePathInstance.getParentFile().mkdirs()) {
			    // Directory created
				this.writeConfigFile(filePathInstance);
			} else {
			    // Can't create Directory operate in SingleSignOn Mode
				DatabaseConnectionManager.getSharedInstance(this);
			}
		} else {
			// Class not initialized
		}
	}
	
	/**
	 * Read the Credentials from a local file
	 */
	private void loadDatabaseLoginCredentials() {
		// Store config commands into this Array
		ArrayList<String> configCommands = new ArrayList<String>();
		File filePathInstance = new File(configLocation);
		if (filePathInstance.exists()) {
		    // Directory exists, try to read the file
			try {
				FileReader fileReader = new FileReader(this.configLocation);
				try(BufferedReader br = new BufferedReader(fileReader)) {
					String line = br.readLine();
					while (line != null) {
						// remove leading and trailing spaces
						line = line.trim();
						// allow comments, then do stuff with the line
						if (!line.startsWith("#") && line.length() != 0) {
							configCommands.add(line);
						}
						line = br.readLine();
					}
					// iterate over loaded commands
					for (String command : configCommands) {
						// execute command
						String[] syntax = command.split(":", 2);
						if (syntax.length == 2) {
							String variable = syntax[0];
							String content = syntax[1];
							
							switch (variable) {
							case "databaseUsername":
								this.databaseUsername = content;
								break;
							case "databasePassword":
								this.databasePassword = content;
								break;
							case "databaseURL":
								this.databaseURL = content;
								break;
							}
						}						
					}
				} catch (IOException exception) {
					// can't read a Line in the configuration File, throws IOException
					this.storeDatabaseLoginCredentials();
				}
			} catch (FileNotFoundException fileNotFoundException){
				// File does not exist, throws FileNotFoundException 
				this.storeDatabaseLoginCredentials();
			}
		} else {
			// Directory does not exist
		    this.storeDatabaseLoginCredentials();
		}
	}
	
	/**
	 * @param File filePathInstance
	 * 
	 * Writes default config File
	 */
	private void writeConfigFile(File filePathInstance) {
		try {
			if (filePathInstance.createNewFile()) {
				FileOutputStream outputStream = new FileOutputStream(this.configLocation);
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "utf-8");
				try (BufferedWriter writer = new BufferedWriter(outputStreamWriter)) {
					writer.write("#######################################");
					writer.newLine();
					writer.write("#######################################");
					writer.newLine();
					writer.write("### DRK Stock Management Configfile ###");
					writer.newLine();
					writer.write("###           Version 1.0           ###");
					writer.newLine();
					writer.write("#######################################");
					writer.newLine();
					writer.write("#######################################");
					writer.newLine();
					writer.newLine();
					writer.newLine();
					writer.write("databaseUsername:"+this.databaseUsername);
					writer.newLine();
					writer.write("databasePassword:"+this.databasePassword);
					writer.newLine();
					writer.write("databaseURL:"+this.databaseURL);
					writer.newLine();
					writer.newLine();
					writer.write("#######################################");
					writer.newLine();
				}
				// catch exception below
			} else {
				// Can't create File operate in SingleSignOn Mode
				DatabaseConnectionManager.getSharedInstance(this);
			}
		} catch (IOException exception) {
			// Exception while creating the File, operate in SingleSignOn Mode
			DatabaseConnectionManager.getSharedInstance(this);
		}
	}
}