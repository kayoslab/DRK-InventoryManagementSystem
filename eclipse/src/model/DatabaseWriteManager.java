package model;
import java.sql.SQLException;

import model.databaseCommunication.DatabaseValueManager;
import model.databaseObjects.*;

public final class DatabaseWriteManager {
	
	private DatabaseWriteManager() {
		// Do nothing here -> Static implementation
	}

	private static DatabaseValueManager getValueManager() {
		return new DatabaseValueManager();
	}
	
	//================================================================================
	// region User Management
	//================================================================================
	
	/**
	 * @param User user
	 * @return Boolean
	 * 
	 * Try to create a new User with a given User Struct.
	 * The user.id will be 0 and should be ignored. 
	 * The id field in the User Table is auto incremented.
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	public static Boolean createUser(User user) {
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
	 * @param String username
	 * @return Boolean
	 * 
	 * Try to delete an existing User with given User Struct.
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	public static Boolean deleteUser(User user) {
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
	 * @param User user
	 * @return Boolean
	 * 
	 * Try to set a new Password into the Users DB Entry.
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 * Use the initialized attribute to indicate whether a user has to change
	 * his password on the next login or not.
	 * 
	 */
	public static Boolean setPassword(User user) {
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
	 * @param User user
	 * @return Boolean
	 * 
	 * Try to edit Data for an existing User with given User struct.
	 * Change the data for user with user.id, because the username could be changed.
	 * Returns a boolean Value, which indicates the outcome.
	 *
	 */
	public static Boolean editUser(User user) {
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
	 * @param User user, Group[] groups 
	 * @return Boolean
	 * 
	 * Try to set Groups for an existing User with a User Struct.
	 * This function iterates over a given Group Array and adds them to the User. 
	 * It should also remove all existing Groups before adding them.
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	public static Boolean setGroupsForUser(User user, Group[] groups) {
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
	
	//================================================================================
	// endregion User Management
	// region Group Management
	//================================================================================
	
	/**
	 * @param Group group
	 * @return Boolean
	 * 
	 * Try to create a new Group with a given Group Struct.
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	public static Boolean createGroup(Group group) {
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
	 * @param Group group
	 * @return Boolean
	 * 
	 * Try to delete an existing Group with a given Group Struct.
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	public static Boolean deleteGroup(Group group) {
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
	 * @param Group group
	 * @return Boolean
	 * 
	 * Try to edit an existing Group with a given name.
	 * Fetch the Group by its group.id because the groupname could be changed
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	public static Boolean editGroup(Group group) {
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
	 * @param Group group, GroupRight[] groupRights 
	 * @return Boolean
	 * 
	 * Try to set GroupRights for an existing Group with a given Group Struct.
	 * This function iterates over a given GroupRight Array and adds them to the Group.
	 * It should also remove all existing Group rights before adding them.
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	public static Boolean setGroupRights(Group group, GroupRight[] groupRights) {
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
	
	//================================================================================
	// endregion Group Management
	// region Location
	//================================================================================
	
	/**
	 * @param Location location
	 * @return Boolean
	 * 
	 * Try to create a new Location with a given Location Struct.
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	public static Boolean createLocation(Location location) {
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
	 * @param Location location
	 * @return Boolean
	 * 
	 * Try to delete an existing Location with a given Location Struct.
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	public static Boolean deleteLocation(Location location) {
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
	 * @param Location location
	 * @return Boolean
	 * 
	 * Try to edit an existing Location with a given Location Struct.
	 * Fetch the Location by its location.id because the locationname could be changed
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	public static Boolean editLocation(Location location) {
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
	
	//================================================================================
	// endregion Location
	// region StockObject
	//================================================================================
	
	/**
	 * @param StockObject stockObject
	 * @return Boolean
	 * 
	 * Try to create a new StockObject with a given extended StockObject Struct.
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	public static Boolean createStockObject(StockObject stockObject) {
		// Get a shared Instance of the DatabaseValueManager
		DatabaseValueManager valueManager = DatabaseWriteManager.getValueManager();
		String sqlStatement;
		// Switch between different extended StockObject Types
		if (stockObject.getClass() == Device.class) {
			sqlStatement = "";
		} else if (stockObject.getClass() == MedicalMaterial.class) {
			sqlStatement = "";
		} else if (stockObject.getClass() == ConsumableMaterial.class) {
			sqlStatement = "";
		} else {
			return false;
		}
		
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
	 * @param StockObject stockObject
	 * @return Boolean
	 * 
	 * Try to delete a StockObject with a given extended StockObject Struct.
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	public static Boolean deleteStockObject(StockObject stockObject) {
		// Get a shared Instance of the DatabaseValueManager
		DatabaseValueManager valueManager = DatabaseWriteManager.getValueManager();
		String sqlStatement;
		// Switch between different extended StockObject Types
		if (stockObject.getClass() == Device.class) {
			sqlStatement = "";
		} else if (stockObject.getClass() == MedicalMaterial.class) {
			sqlStatement = "";
		} else if (stockObject.getClass() == ConsumableMaterial.class) {
			sqlStatement = "";
		} else {
			return false;
		}
		
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
	 * @param StockObject stockObject
	 * @return Boolean
	 * 
	 * Try to edit an existing StockObject with a given extended StockObject Struct.
	 * Fetch the StockObject by its stockObject.id because the objectname could be changed
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	public static Boolean editStockObject(StockObject stockObject) {
		// Get a shared Instance of the DatabaseValueManager
		DatabaseValueManager valueManager = DatabaseWriteManager.getValueManager();
		String sqlStatement;
		// Switch between different extended StockObject Types
		if (stockObject.getClass() == Device.class) {
			sqlStatement = "";
		} else if (stockObject.getClass() == MedicalMaterial.class) {
			sqlStatement = "";
		} else if (stockObject.getClass() == ConsumableMaterial.class) {
			sqlStatement = "";
		} else {
			return false;
		}
		
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
	
	//================================================================================
	// endregion StockObject
	// region Stock
	//================================================================================
	
	
	
	//================================================================================
	// endregion Stock
	//================================================================================
}