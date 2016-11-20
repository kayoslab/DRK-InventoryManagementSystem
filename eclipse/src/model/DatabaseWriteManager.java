package model;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import model.databaseCommunication.DatabaseValueManager;
import model.databaseObjects.*;
import model.databaseObjects.accessControl.*;
import model.databaseObjects.environment.*;
import model.databaseObjects.stockObjects.*;
import model.databaseObjects.stockValues.*;

public final class DatabaseWriteManager {
	static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private DatabaseWriteManager() {
		// Do nothing here -> Static implementation
	}

	private static DatabaseValueManager getValueManager() {
		return new DatabaseValueManager();
	}
	
	/**
	 * @param databaseObjects.DatabaseObject object
	 * @return Boolean
	 * 
	 * Try to create a new Database Entry with a given DatabaseObject Struct.
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	public static Boolean createObject(DatabaseObject object) {
		if (object instanceof User) {
			return DatabaseWriteManager.createUser((User) object);
		} else if (object instanceof Group) {
			return DatabaseWriteManager.createGroup((Group) object);
		} else if (object  instanceof Location) {
			return DatabaseWriteManager.createLocation((Location) object);
		} else if (object instanceof StockObject) {
			return DatabaseWriteManager.createStockObject((StockObject) object);
		} else if (object instanceof StockObjectValue) {
			return DatabaseWriteManager.createStockObjectValue((StockObjectValue) object);
		}
		return false;
	}
	
	/**
	 * @param DatabaseObject object
	 * @return Boolean
	 * 
	 * Try to delete an existing Database Entry with a given DatabaseObject Struct.
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	public static Boolean deleteObject(DatabaseObject object) {
		if (object instanceof User) {
			return DatabaseWriteManager.deleteUser((User) object);
		} else if (object instanceof Group) {
			return DatabaseWriteManager.deleteGroup((Group) object);
		} else if (object  instanceof Location) {
			return DatabaseWriteManager.deleteLocation((Location) object);
		} else if (object instanceof StockObject) {
			return DatabaseWriteManager.deleteStockObject((StockObject) object);
		} else if (object instanceof StockObjectValue) {
			return DatabaseWriteManager.deleteStockObjectValue((StockObjectValue) object);
		}
		return false;
	}
	
	/**
	 * @param DatabaseObject object
	 * @return Boolean
	 * 
	 * Try to update an existing Database Entry with a given DatabaseObject Struct.
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	public static Boolean editObject(DatabaseObject object) {
		if (object instanceof User) {
			return DatabaseWriteManager.editUser((User) object);
		} else if (object instanceof Group) {
			return DatabaseWriteManager.editGroup((Group) object);
		} else if (object  instanceof Location) {
			return DatabaseWriteManager.editLocation((Location) object);
		} else if (object instanceof StockObject) {
			return DatabaseWriteManager.editStockObject((StockObject) object);
		} else if (object instanceof StockObjectValue) {
			return DatabaseWriteManager.editStockObjectValue((StockObjectValue) object);
		}
		return false;
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
	private static Boolean createUser(User user) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		String sqlStatement = "INSERT INTO `User`"
				+ " VALUES(0, '" + user.username + "', '" 
				+ user.firstName +"', '" + user.name + "', '" + sdf.format(timestamp) 
				+ "', '"	+ user.passwordHash + "',false);";
		return DatabaseWriteManager.executeUpdate(sqlStatement);
	}
	
	/**
	 * @param String username
	 * @return Boolean
	 * 
	 * Try to delete an existing User with given User Struct.
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	private static Boolean deleteUser(User user) {
		String sqlStatement = "DELETE FROM `User` WHERE `id` = " + user.id + ";";
		return DatabaseWriteManager.executeUpdate(sqlStatement);
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
	private static Boolean editUser(User user) {
		String sqlStatement = "UPDATE `User` SET `username` = '" 
				+ user.username + "', `firstname` = '" + user.firstName 
				+"', name = '" + user.name + "', `password` = '" + user.passwordHash 
				+ "', `passwordChanged` = " + user.passwordChanged 
				+ " WHERE `id` = " + user.id +";";

		return DatabaseWriteManager.executeUpdate(sqlStatement);
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
		String sqlStatement = "UPDATE `User` SET `password` = '" 
				+ user.passwordHash + "' WHERE `id` = " + user.id + ";";
		return DatabaseWriteManager.executeUpdate(sqlStatement);
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
		String sqlStatement = "";
		return DatabaseWriteManager.executeUpdate(sqlStatement);
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
	private static Boolean createGroup(Group group) {
		String sqlStatement = "INSERT INTO `Group`"
				+ "VALUES(NULL, '" + group.title + "', " + group.isActive + ");";
		return DatabaseWriteManager.executeUpdate(sqlStatement);
	}
	
	/**
	 * @param Group group
	 * @return Boolean
	 * 
	 * Try to delete an existing Group with a given Group Struct.
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	private static Boolean deleteGroup(Group group) {
		String sqlStatement = "DELETE FROM `Group` WHERE `id` = "
				+ group.id + ";";
		return DatabaseWriteManager.executeUpdate(sqlStatement);
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
	private static Boolean editGroup(Group group) {
		String sqlStatement = "UPDATE `Group` SET `title` = '"
				+ group.title + "', `isActive` = " + group.isActive
				+ " WHERE `id` = " + group.id + ";";

		return DatabaseWriteManager.executeUpdate(sqlStatement);
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
		String sqlStatement = "";

		return DatabaseWriteManager.executeUpdate(sqlStatement);
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
	private static Boolean createLocation(Location location) {
		String sqlStatement = "INSERT INTO `Location`"
				+ "VALUES(NULL,'" + location.title + "');";
		return DatabaseWriteManager.executeUpdate(sqlStatement);
	}
	
	/**
	 * @param Location location
	 * @return Boolean
	 * 
	 * Try to delete an existing Location with a given Location Struct.
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	private static Boolean deleteLocation(Location location) {
		String sqlStatement = "DELETE FROM `Location` WHERE id = " + location.id + ";";
		return DatabaseWriteManager.executeUpdate(sqlStatement);
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
	private static Boolean editLocation(Location location) {
		String sqlStatement = "UPDATE `Location` SET `title` = '" 
				+ location.title + "' WHERE `id` = " + location.id + ";";
		return DatabaseWriteManager.executeUpdate(sqlStatement);
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
	private static Boolean createStockObject(StockObject stockObject) {
		String sqlStatement;
		// Switch between different extended StockObject Types
		if (stockObject instanceof Device) {
			sqlStatement = "";
		} else if (stockObject instanceof Material) {
			if (stockObject instanceof MedicalMaterial) {
				sqlStatement = "";
			} else if (stockObject instanceof ConsumableMaterial) {
				sqlStatement = "";
			} else {
				return false;
			}
		} else {
			return false;
		}

		return DatabaseWriteManager.executeUpdate(sqlStatement);
	}
	
	/**
	 * @param StockObject stockObject
	 * @return Boolean
	 * 
	 * Try to delete a StockObject with a given extended StockObject Struct.
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	private static Boolean deleteStockObject(StockObject stockObject) {
		String sqlStatement;
		// Switch between different extended StockObject Types
		if (stockObject instanceof Device) {
			sqlStatement = "";
		} else if (stockObject instanceof Material) {
			if (stockObject instanceof MedicalMaterial) {
				sqlStatement = "";
			} else if (stockObject instanceof ConsumableMaterial) {
				sqlStatement = "";
			} else {
				return false;
			}
		} else {
			return false;
		}

		return DatabaseWriteManager.executeUpdate(sqlStatement);
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
	private static Boolean editStockObject(StockObject stockObject) {
		String sqlStatement;
		// Switch between different extended StockObject Types
		if (stockObject instanceof Device) {
			sqlStatement = "";
		} else if (stockObject instanceof Material) {
			if (stockObject instanceof MedicalMaterial) {
				sqlStatement = "";
			} else if (stockObject instanceof ConsumableMaterial) {
				sqlStatement = "";
			} else {
				return false;
			}
		} else {
			return false;
		}

		return DatabaseWriteManager.executeUpdate(sqlStatement);
	}
	
	//================================================================================
	// endregion StockObject
	// region StockValue
	//================================================================================
	
	/**
	 * @param StockObjectValue stockObjectValue
	 * @return Boolean
	 * 
	 * Try to create a new StockObjectValue with a given extended StockObjectValue Struct.
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	private static Boolean createStockObjectValue(StockObjectValue stockObjectValue) {
		String sqlStatement;
		// Switch between different extended StockObjectValue Types
		if (stockObjectValue instanceof DeviceValue) {
			sqlStatement = "";
		} else if (stockObjectValue instanceof MaterialValue) {
			if (stockObjectValue instanceof MedicalMaterialValue) {
				sqlStatement = "";
			} else if (stockObjectValue instanceof ConsumableMaterialValue) {
				sqlStatement = "";
			} else {
				return false;
			}
		} else {
			return false;
		}

		return DatabaseWriteManager.executeUpdate(sqlStatement);
	}
	
	/**
	 * @param StockObjectValue stockObjectValue
	 * @return Boolean
	 * 
	 * Try to delete a StockObjectValue with a given extended StockObjectValue Struct.
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	private static Boolean deleteStockObjectValue(StockObjectValue stockObjectValue) {
		String sqlStatement;
		// Switch between different extended StockObjectValue Types
		if (stockObjectValue instanceof DeviceValue) {
			sqlStatement = "";
		} else if (stockObjectValue instanceof MaterialValue) {
			if (stockObjectValue instanceof MedicalMaterialValue) {
				sqlStatement = "";
			} else if (stockObjectValue instanceof ConsumableMaterialValue) {
				sqlStatement = "";
			} else {
				return false;
			}
		} else {
			return false;
		}

		return DatabaseWriteManager.executeUpdate(sqlStatement);
	}
	
	/**
	 * @param StockObjectValue stockObjectValue
	 * @return Boolean
	 * 
	 * Try to edit an existing StockObjectValue with a given extended StockObjectValue Struct.
	 * Fetch the StockObjectValue by its stockObjectValue.id
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	private static Boolean editStockObjectValue(StockObjectValue stockObjectValue) {
		String sqlStatement;
		// Switch between different extended StockObjectValue Types
		if (stockObjectValue instanceof DeviceValue) {
			sqlStatement = "";
		} else if (stockObjectValue instanceof MaterialValue) {
			if (stockObjectValue instanceof MedicalMaterialValue) {
				sqlStatement = "";
			} else if (stockObjectValue instanceof ConsumableMaterialValue) {
				sqlStatement = "";
			} else {
				return false;
			}
		} else {
			return false;
		}

		return DatabaseWriteManager.executeUpdate(sqlStatement);
	}
	
	//================================================================================
	// endregion StockValue
	//================================================================================

	/**
	 * @param String sqlStatement
	 * @return Boolean
	 *
	 * try to execute Update on DatabaseValueManager
	 * Returns a boolean Value, which indicates the outcome.
	 *
	 */
	private static Boolean executeUpdate(String sqlStatement) {
		// Get a shared Instance of the DatabaseValueManager
		DatabaseValueManager valueManager = DatabaseWriteManager.getValueManager();
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
			System.out.println(exception.getMessage());
			return false;
		}
		return false;
	}
}