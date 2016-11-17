package model;
import model.databaseCommunication.DatabaseValueManager;
import model.databaseObjects.accessControl.*;
import model.databaseObjects.stockObjects.*;
// import model.databaseObjects.stockValues.*;

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
	 * @return User
	 */
	public static User getUser(int id) {
		DatabaseValueManager valueManager = DatabaseReadManager.getValueManager();
		// get User from Database and return for id
		String sqlStatement = "";
		// fill with reasonable Data
		return new User(id, "", "", "", "", false);
	}
	
	/**
	 * @param String username
	 * @return User
	 */
	public static User getUser(String username) {
		DatabaseValueManager valueManager = DatabaseReadManager.getValueManager();
		// get User from Database and return for username
		String sqlStatement = "";
		// fill with reasonable Data
		return new User(0, username, "", "", "", false);
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
	
	/**
	 * @param 
	 * @return Boolean
	 * 
	 * Check if a user has already changed his password since
	 * it was modifies by an admin
	 */
	public static StockObject[] generateInventory() {
		DatabaseValueManager valueManager = DatabaseReadManager.getValueManager();
		// get current password hash from Database and return for username
		String sqlStatement = "";
		return new StockObject[0];
	}
}
