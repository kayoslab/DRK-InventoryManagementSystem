/*
 * Copyright (c) - All Rights Reserved
 *
 * Unauthorized copying of these files, via any medium is
 * strictly prohibited Proprietary and confidential
 *
 * NOTICE:
 * All information contained in this project is, and remains the property of the owner and its suppliers, if any.
 * The intellectual and technical concepts contained herein are proprietary to the owner and its suppliers and
 * are protected by trade secret or copyright law. Dissemination of this information or reproduction of this
 * material is strictly forbidden unless prior written permission is obtained by the owner.
 *
 * @author
 *
 */

package model.databaseCommunication;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseLoginManager {
	private String databaseUsername;
	private String databasePassword;
	private String databaseURL;
	private String senderAddress = "";
	private String smtpUserName = "";
	private String smtpPassword = "";
	private String smtpHost = "";
	private String smtpPort = "";
	private String configLocation = System.getProperty("user.home") + File.separator + ".stockManager" + File.separator + "config";
	/**
	 * Constructor
	 */
	public DatabaseLoginManager() {
		this.loadDatabaseLoginCredentials();
	}
	
	/**
	 * @param username String
	 * @param password  String
	 * @param url String
	 * @return Boolean
	 */
	public DatabaseLoginManager(String username, String password, String url) {
		File filePathInstance = new File(configLocation);
		if (filePathInstance.exists()) {
		    // File already exists
			filePathInstance.delete();
		}
		this.databaseUsername = username;
		this.databasePassword = password;
		this.databaseURL = url;
		this.storeDatabaseLoginCredentials();
	}
	
	/**
	 * @param username String
	 */
	public void setDatabaseUsername(String username) {
		this.databaseUsername = username;
		this.storeDatabaseLoginCredentials();
	}
	
	/**
	 * @param password String
	 */
	public void setPassword(String password) {
		this.databasePassword = password;
		this.storeDatabaseLoginCredentials();
	}
	
	/**
	 * @param url String
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

	public String getSenderAddress() {
		return senderAddress;
	}

	public String getSmtpUserName() {
		return smtpUserName;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	/**
	 * Test credentials for Login
	 */
	public Boolean testDatabaseConnection() {
		if (this.databaseUsername != null && this.databasePassword != null && this.databaseURL != null) {
			try {
				DriverManager.setLoginTimeout(5);
				Connection connection = DriverManager.getConnection(this.databaseURL, this.databaseUsername, this.databasePassword);
				if(!connection.isClosed() && connection != null){
		            return true;
		        } else {
					return false;
				}
			} catch (SQLException exception) {
				return false;
			}
		}
		System.out.println("Can't access Database Login Credentials");
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
								case "senderAddress":
									this.senderAddress = content;
									break;
								case "smtpUsername":
									this.smtpUserName = content;
									break;
								case "smtpPassword":
									this.smtpPassword = content;
									break;
								case "smtpHost":
									this.smtpHost = content;
									break;
								case "smtpPort":
									this.smtpPort = content;
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
	 * @param filePathInstance File
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