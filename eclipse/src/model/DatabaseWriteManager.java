package model;
import java.sql.SQLException;

import model.databaseCommunication.DatabaseValueManager;

public final class DatabaseWriteManager {
	
	private DatabaseWriteManager() {
		// Do nothing here -> Static implementation
	}

	private static DatabaseValueManager getValueManager() {
		return new DatabaseValueManager();
	}
	
	// MARK: - User Management

	/**
	 * @param String firstname, String name, String username, String password
	 * @return Boolean
	 * 
	 * Try to create a new User with given userdata and initial password.
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	public static Boolean createUser(String firstname, String name, String username, String password) {
		DatabaseValueManager valueManager = DatabaseWriteManager.getValueManager();
		String sqlStatement = "";
		try {
			// execute Database Update
			int updateResult = valueManager.executeUpdate(sqlStatement);
			// returns either the row count for SQL Data Manipulation Language (DML) statements 
			// or 0 for SQL statements that return nothing.
			if (updateResult > 0) {
				return true;
			}
		} catch (SQLException exception) {
			// uncomment for debugging SQL-Statements
			// System.out.println(exception.getMessage());
			return false;
		}
		return false;
	}
	
	/**
	 * @param String username, String password, Boolean initialized
	 * @return Boolean
	 * 
	 * Try to set a new Password into the Users DB Entry.
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 * Use the initialized attribute to indicate whether a user has to change
	 * his password on the next login or not.
	 * 
	 */
	public static Boolean setPassword(String username, String password, Boolean initialized) {
		DatabaseValueManager valueManager = DatabaseWriteManager.getValueManager();
		String sqlStatement = "";
		try {
			// execute Database Update
			int updateResult = valueManager.executeUpdate(sqlStatement);
			// returns either the row count for SQL Data Manipulation Language (DML) statements 
			// or 0 for SQL statements that return nothing.
			if (updateResult > 0) {
				return true;
			}
		} catch (SQLException exception) {
			// uncomment for debugging SQL-Statements
			// System.out.println(exception.getMessage());
			return false;
		}
		return false;
	}
	
	// MARK: - Group Management
	
	/**
	 * @param String groupname
	 * @return Boolean
	 * 
	 * Try to create a new Group with a given name.
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	public static Boolean createGroup(String groupname) {
		// Get a shared Instance of the DatabaseValueManager
		DatabaseValueManager valueManager = DatabaseWriteManager.getValueManager();
		String sqlStatement = "";
		try {
			// execute Database Update
			int updateResult = valueManager.executeUpdate(sqlStatement);
			// returns either the row count for SQL Data Manipulation Language (DML) statements 
			// or 0 for SQL statements that return nothing.
			if (updateResult > 0) {
				return true;
			}
		} catch (SQLException exception) {
			// uncomment for debugging SQL-Statements
			// System.out.println(exception.getMessage());
			return false;
		}
		return false;
	}
	
	/**
	 * @param String groupname
	 * @return Boolean
	 * 
	 * Try to delete an existing Group with a given name.
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	public static Boolean deleteGroup(String groupname) {
		// Get a shared Instance of the DatabaseValueManager
		DatabaseValueManager valueManager = DatabaseWriteManager.getValueManager();
		String sqlStatement = "";
		try {
			// execute Database Update
			int updateResult = valueManager.executeUpdate(sqlStatement);
			// returns either the row count for SQL Data Manipulation Language (DML) statements 
			// or 0 for SQL statements that return nothing.
			if (updateResult > 0) {
				return true;
			}
		} catch (SQLException exception) {
			// uncomment for debugging SQL-Statements
			// System.out.println(exception.getMessage());
			return false;
		}
		return false;
	}
	
	
}