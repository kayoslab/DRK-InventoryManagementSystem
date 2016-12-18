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
 * @author Chris
 *
 */

package model.databaseCommunication;
import java.sql.*;

class DatabaseConnectionManager {
	private static DatabaseConnectionManager sharedInstance;
	private final String databaseUsername;
	private final String databasePassword;
	private final String databaseURL;
	private Connection connection;
	private int connectionCounter;
	
	/**
	 * Private Constructor
	 */
	private DatabaseConnectionManager() {
		DatabaseLoginManager loginManager = new DatabaseLoginManager();
		databaseUsername = loginManager.getUsername();
		databasePassword = loginManager.getPassword();
		databaseURL = loginManager.getURL();
		connectionCounter = 0;
	}
	
	private DatabaseConnectionManager(DatabaseLoginManager loginManager) {
		databaseUsername = loginManager.getUsername();
		databasePassword = loginManager.getPassword();
		databaseURL = loginManager.getURL();
		connectionCounter = 0;
	}
	
	/**
	 * Singleton
	 */
	static DatabaseConnectionManager getSharedInstance() {
		if (DatabaseConnectionManager.sharedInstance == null) {
			DatabaseConnectionManager.sharedInstance = new DatabaseConnectionManager();
		}
		
		return DatabaseConnectionManager.sharedInstance;
	}
	
	static DatabaseConnectionManager getSharedInstance(DatabaseLoginManager loginManager) {
		if (DatabaseConnectionManager.sharedInstance == null) {
			DatabaseConnectionManager.sharedInstance = new DatabaseConnectionManager(loginManager);
		}
		
		return DatabaseConnectionManager.sharedInstance;
	}
	
	/****************** Counter Management **************************/
	
	/**
	 * Private counter increment
	 */
	private void incrementCounter() {
		this.connectionCounter++;
	}
	
	/**
	 * Private counter decrement
	 */
	private void decrementCounter() {
		if (this.connectionCounter > 0) {
			this.connectionCounter --;
		}
	}
	
	/****************** Database Connection **************************/
	
	/**
	 * Privately open new Database Connections
	 */
	private void openDatabaseConnection() throws SQLException {
		if (this.connection == null) {
			this.connection = DriverManager.getConnection(this.databaseURL, this.databaseUsername, this.databasePassword);
		}
	}
	
	/**
	 * Privately close existing Database Connections
	 */
	private void closeDatabaseConnection() throws SQLException {
		if (this.connection != null) {
			this.connection.close();
			this.connection = null;
		}
	}
	
	/********************* Public Accessory ****************************/
	
	/**
	 * Getter for existing Database Connections
	 * @throws SQLException
	 */
	public Connection getDatabaseConnection() throws SQLException {
		if (this.connectionCounter == 0) {
			this.openDatabaseConnection();
		}
		this.incrementCounter();
		return this.connection;
	}
	
	/**
	 * Getter for Database Connection release
	 * @throws SQLException
	 */
	public void releaseDatabaseConnection() throws SQLException {
		this.decrementCounter();
		if (this.connectionCounter == 0) {
			this.closeDatabaseConnection();
		}
	}
}
