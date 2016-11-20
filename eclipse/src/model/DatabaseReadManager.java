package model;

import java.sql.ResultSet;
import java.sql.SQLException;

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
	 * @param String
	 *            username
	 * @return User
	 */
	public static User getUser(int id) {
		// get User from Database and return for id
		String sqlStatement = "SELECT `username`,`firstname`,`name`,`password`" 
				+ "FROM `User` WHERE id = " + id;
		// get Data from Database
		ResultSet rs = DatabaseReadManager.executeQuery(sqlStatement);
		// fill with reasonable Data
		try {
			return new User(id, rs.getString("username"), rs.getString("firstname"), rs.getString("name"),
					rs.getString("password"), rs.getBoolean("passwordChanged"));
		} catch (SQLException e) {
			// rs isNull or one or more attributes are missing
			// uncomment for debugging SQL-Statements
			// System.out.println(exception.getMessage());
			return null;
		}
	}

	/**
	 * @param String
	 *            username
	 * @return User
	 */
	public static User getUser(String username) {
		// get User from Database and return for username
		String sqlStatement = "SELECT `id`,`username`,`firstname`,`name`,`password`" + "FROM `User` WHERE username = "
				+ username;
		// get Data from Database
		ResultSet rs = DatabaseReadManager.executeQuery(sqlStatement);
		// fill with reasonable Data
		try {
			return new User(rs.getInt("id"), username, rs.getString("firstname"), rs.getString("name"),
					rs.getString("password"), rs.getBoolean("passwordChanged"));
		} catch (SQLException e) {
			// rs isNull or one or more attributes are missing
			// uncomment for debugging SQL-Statements
			// System.out.println(exception.getMessage());
			return null;
		}
	}

	/**
	 * @param String
	 *            username
	 * @return Boolean
	 * 
	 *         Check if a user has already changed his password since it was
	 *         modifies by an admin
	 */
	public static Boolean userDidChangePassword(String username) {
		// get current password hash from Database and return for username
		String sqlStatement = "SELECT `passwordChanged`" + "FROM `User` WHERE username = " + username;
		// get Data from Database
		ResultSet rs = DatabaseReadManager.executeQuery(sqlStatement);
		// fill with reasonable Data
		try{
			return (rs.getBoolean("passwordChanged"));
		}
		catch(SQLException e){
			// rs isNull
			// uncomment for debugging SQL-Statements
			// System.out.println(exception.getMessage());
			return(null);
		}	
	}

	/**
	 * @param
	 * @return Boolean
	 * 
	 *         Generates Inventory
	 */
	//public static StockObject[] generateInventory() {
		// get Inventory as Array
		/*String sqlStatement = "SELECT `id`, `title`, `description`, "
				+ "`silenceWarning`, `type_id`, `totalVolume` "
				+ "FROM `StockObject`";
		// get Data from Database
		ResultSet rs = DatabaseReadManager.executeQuery(sqlStatement);
		// fill with reasonable Data
		StockObject[] stockobject;
		int i;
		while (rs.next()) {
		    stockobject[i++] = new StockObject(rs.getInt("id"), rs.getString("title"),
		    		rs.getString("description"),rs.getBoolean("silenceWarning"),
		    		rs.getInt("type_id"), rs.getInt("totalVolume"));
		
		
		  }*/ 
		//return StockObject[0];
	//}

	/**
	 * @param String
	 *            sqlStatement
	 * @return Boolean
	 *
	 *         try to execute Query on DatabaseValueManager Returns a ResultSet,
	 *         which indicates the outcome.
	 *
	 */
	private static ResultSet executeQuery(String sqlStatement) {
		// Get a shared Instance of the DatabaseValueManager
		DatabaseValueManager valueManager = DatabaseReadManager.getValueManager();
		try {
			// execute Database Update
			ResultSet result = valueManager.executeQuery(sqlStatement);
			// returns either the ResultSet or null for SQL statements
			// that return nothing.
			return (result);
		} catch (SQLException exception) {
			// uncomment for debugging SQL-Statements
			// System.out.println(exception.getMessage());
			return null;
		}
	}
}