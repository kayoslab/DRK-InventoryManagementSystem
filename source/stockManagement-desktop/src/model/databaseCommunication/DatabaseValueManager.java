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

public class DatabaseValueManager {
	private DatabaseConnectionManager manager;
	
	/**
	 * Constructor
	 */
	public DatabaseValueManager() {
		this.manager = DatabaseConnectionManager.getSharedInstance();
	}
	
	/**
	 * @param sql String
	 */
	public ResultSet executeQuery(String sql) throws SQLException {
		Connection connection = this.manager.getDatabaseConnection();
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sql);
		// manually close these on executeQuerry
		// statement.close();
		// this.manager.releaseDatabaseConnection();
		return result;
	}
	
	/**
	 * @param sql String
	 */
	public int executeUpdate(String sql) throws SQLException {
		Connection connection = this.manager.getDatabaseConnection();
		Statement statement = connection.createStatement();
		int result = statement.executeUpdate(sql);
		statement.close();
		this.manager.releaseDatabaseConnection();
		return result;
	}

	public void releaseDatabaseConnection() throws SQLException {
		this.manager.releaseDatabaseConnection();
	}
}
