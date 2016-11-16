package model;
import model.databaseCommunication.DatabaseValueManager;

public final class DatabaseReadManager {
	
	private DatabaseReadManager() {
		// Do nothing here -> Static implementation
	}
	
	private static DatabaseValueManager getValueManager() {
		return new DatabaseValueManager();
	}
	
	// MARK: - User Management
	
	/**
	 * @param String username
	 * @return String
	 */
	public static String getPasswordFromDatabase(String username) {
		DatabaseValueManager valueManager = DatabaseReadManager.getValueManager();
		// get current password hash from Database and return for username
		String sqlStatement = "";
		return "";
	}
	
	/**
	 * @param String username
	 * @return Boolean
	 * 
	 * Check if a user has already changed his password since
	 * it was modifies by an admin
	 */
	public static Boolean userDidChangePassword(String username) {
		DatabaseValueManager valueManager = DatabaseReadManager.getValueManager();
		// get current password hash from Database and return for username
		String sqlStatement = "";
		return false;
	}
	
}
