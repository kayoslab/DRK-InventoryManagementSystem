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
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private DatabaseWriteManager() {
		// Do nothing here -> Static implementation
	}

	private static DatabaseValueManager getValueManager() {
		return new DatabaseValueManager();
	}

	/**
	 * @param  object databaseObjects.DatabaseObject
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
	 * @param  object DatabaseObject
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
	 * @param  object DatabaseObject
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
	 * @param  user User
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
				+ user.firstName +"', '" + user.name + "', '" + DatabaseWriteManager.sdf.format(timestamp)
				+ "', '"	+ user.passwordHash + "',false);";
		return DatabaseWriteManager.executeUpdate(sqlStatement);
	}

	/**
	 * @param  user User
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
	 * @param  user User
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
	 * @param  user User
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
	 * @param user User
	 * @param groups Group[]
	 * @return Boolean
	 *
	 * Try to set Groups for an existing User with a User Struct.
	 * This function iterates over a given Group Array and adds them to the User.
	 * It should also remove all existing Groups before adding them.
	 * Returns a boolean Value, which indicates the outcome.
	 *
	 */
	public static Boolean setGroupsForUser(User user, Group[] groups) {
		String sqlDelete = "DELETE FROM `UserIsMemberOfGroup` WHERE `user` = "
				+ user.id + "; ";
		DatabaseWriteManager.executeUpdate(sqlDelete);
		String sqlStatement = "INSERT INTO `UserIsMemberOfGroup` VALUES";
		for(Group group:groups){
			sqlStatement += "(" + user.id + "," + group.id + "),";
		}
		if (sqlStatement.charAt(sqlStatement.length()-1) == ',' ){
			sqlStatement = sqlStatement.substring(0, sqlStatement.length()-1);
		}
		sqlStatement += ";";
		return DatabaseWriteManager.executeUpdate(sqlStatement);
	}

	//================================================================================
	// endregion User Management
	// region Group Management
	//================================================================================

	/**
	 * @param group Group
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
	 * @param group Group
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
	 * @param group Group
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
	 * @param group Group
	 * @param groupRights GroupRight[]
	 * @return Boolean
	 *
	 * Try to set GroupRights for an existing Group with a given Group Struct.
	 * This function iterates over a given GroupRight Array and adds them to the Group.
	 * It should also remove all existing Group rights before adding them.
	 * Returns a boolean Value, which indicates the outcome.
	 *
	 */
	public static Boolean setGroupRights(Group group, GroupRight[] groupRights) {
		String sqlDelete = "DELETE FROM `GroupHasRights` WHERE `group` = "
				+ group.id + "; ";
		DatabaseWriteManager.executeUpdate(sqlDelete);
		String sqlStatement = "INSERT INTO `GroupHasRights` VALUES";
		for(GroupRight groupRight:groupRights){
			sqlStatement += "(" + group.id + "," + groupRight.id + "),";
		}
		if (sqlStatement.charAt(sqlStatement.length()-1) == ',' ){
			sqlStatement = sqlStatement.substring(0, sqlStatement.length()-1);
		}
		sqlStatement += ";";
		return DatabaseWriteManager.executeUpdate(sqlStatement);
	}

	//================================================================================
	// endregion Group Management
	// region Location
	//================================================================================

	/**
	 * @param location Location
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
	 * @param location Location
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
	 * @param location Location
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
	 * @param stockObject StockObject
	 * @return Boolean
	 *
	 * Try to create a new StockObject with a given extended StockObject Struct.
	 * Returns a boolean Value, which indicates the outcome.
	 *
	 */
	private static Boolean createStockObject(StockObject stockObject) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String sqlStatement;
		// Switch between different extended StockObject Types
		if (stockObject instanceof Device) {
			Device device = (Device) stockObject;
			//Type ID 1 = Device
			sqlStatement = "INSERT INTO `StockObject` VALUES(0,'"
					+ device.title + "','" + device.description + "', NULL, NULL, NULL, 0,"
					+ device.mtkIntervall + ", " + device.stkIntervall
					+ ", '" + DatabaseWriteManager.sdf.format(timestamp) + "', " + device.silencedWarnings + ", "
					+ DatabaseObject.StockObjectType.device.ordinal() + ");";
		} else if (stockObject instanceof Material) {
			if (stockObject instanceof MedicalMaterial) {
				MedicalMaterial medmat = (MedicalMaterial) stockObject;
				//Type ID 2 = MedicalMaterial
				sqlStatement = "INSERT INTO `StockObject` VALUES(0,'"
						+ medmat.title + "','" + medmat.description + "', "
						+ medmat.minimumStock + ", " + medmat.quotaStock + ", "
						+ medmat.batchSize + ", 0, NULL, NULL, '"
						+ DatabaseWriteManager.sdf.format(timestamp) + "', " + medmat.silencedWarnings + ", "
						+ DatabaseObject.StockObjectType.medicalMaterial.ordinal() + ");";
			} else if (stockObject instanceof ConsumableMaterial) {
				ConsumableMaterial consmat = (ConsumableMaterial) stockObject;
				//Type ID 3 = ConsumableMaterial
				sqlStatement = "INSERT INTO `StockObject` VALUES(0,'"
						+ consmat.title + "','" + consmat.description + "', "
						+ consmat.minimumStock + ", " + consmat.quotaStock + ", "
						+ consmat.batchSize + ", 0, NULL, NULL, '"
						+ DatabaseWriteManager.sdf.format(timestamp) + "', " + consmat.silencedWarnings + ", "
						+ DatabaseObject.StockObjectType.consumableMaterial.ordinal() + ");";
			} else {
				return false;
			}
		} else {
			return false;
		}

		return DatabaseWriteManager.executeUpdate(sqlStatement);
	}

	/**
	 * @param stockObject StockObject
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
			sqlStatement = "DELETE FROM `StockObject` WHERE `id` = "
					+ stockObject.id + ";";
		} else if (stockObject instanceof Material) {
			if (stockObject instanceof MedicalMaterial) {
				sqlStatement = "DELETE FROM `StockObject` WHERE `id` = "
						+ stockObject.id + ";";
			} else if (stockObject instanceof ConsumableMaterial) {
				sqlStatement = "DELETE FROM `StockObject` WHERE `id` = "
						+ stockObject.id + ";";
			} else {
				return false;
			}
		} else {
			return false;
		}

		return DatabaseWriteManager.executeUpdate(sqlStatement);
	}

	/**
	 * @param stockObject StockObject
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
			Device device = (Device) stockObject;
			//Type ID 1 = Device
			sqlStatement = "UPDATE `StockObject` SET `title` = '" + device.title
					+ "', `description` = '" + device.description + "', `mtkIntervall` = "
					+ device.mtkIntervall + ", `stkIntervall` = " + device.stkIntervall
					+ ", silenceWarning = "	+ device.silencedWarnings + " WHERE `id` = "
					+ stockObject.id + ";";
		} else if (stockObject instanceof Material) {
			if (stockObject instanceof MedicalMaterial) {
				MedicalMaterial medmat = (MedicalMaterial) stockObject;
				//Type ID 2 = MedicalMaterial
				sqlStatement = "UPDATE `StockObject` SET `title` = '" + medmat.title
						+ "', `description` = '" + medmat.description + "', `minimumStock` = "
						+ medmat.minimumStock + ", `quotaStock` = " + medmat.quotaStock
						+ ", `batchSize` = " + medmat.batchSize
						+ ", `totalVolume` = " + medmat.totalVolume
						+ ", silenceWarning = "	+ medmat.silencedWarnings + " WHERE `id` = "
						+ stockObject.id + ";";
			} else if (stockObject instanceof ConsumableMaterial) {
				ConsumableMaterial consmat = (ConsumableMaterial) stockObject;
				//Type ID 3 = ConsumableMaterial
				sqlStatement = "UPDATE `StockObject` SET `title` = '" + consmat.title
						+ "', `description` = '" + consmat.description + "', `minimumStock` = "
						+ consmat.minimumStock + ", `quotaStock` = " + consmat.quotaStock
						+ ", `batchSize` = " + consmat.batchSize
						+ ", `totalVolume` = " + consmat.totalVolume
						+ ", silenceWarning = "	+ consmat.silencedWarnings + " WHERE `id` = "
						+ stockObject.id + ";";
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
	 * @param stockObjectValue StockObjectValue
	 * @return Boolean
	 *
	 * Try to create a new StockObjectValue with a given extended StockObjectValue Struct.
	 * Returns a boolean Value, which indicates the outcome.
	 *
	 */
	private static Boolean createStockObjectValue(StockObjectValue stockObjectValue) {
		// Check for allready existing Stock Entries with this particular identifiers (e.g. Location/Message/Date).
		StockObjectValue[] existingStock = DatabaseReadManager.existingStockFor(stockObjectValue);
		// switch over the number of objects with the same identifier.
		assert existingStock != null : false;
		switch (existingStock.length) {
			case 0:
				// Default case for creation, there is no existing entry.
				String sqlStatement;
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				// Switch between different extended StockObjectValue Types
				if (stockObjectValue instanceof DeviceValue) {
					DeviceValue deviceValue = (DeviceValue) stockObjectValue;
					sqlStatement = "INSERT INTO `Stock` VALUES(0,"
							+ stockObjectValue.volume + ", NULL, '" + DatabaseWriteManager.sdf.format(deviceValue.mtkDate)
							+ "', '" + sdf.format(deviceValue.stkDate) + "', '" + deviceValue.inventoryNumber
							+ "', '" + deviceValue.serialNumber + "', '" + deviceValue.umdns + "', NULL, '"
							+ DatabaseWriteManager.sdf.format(timestamp) + "', 0, " + stockObjectValue.stockObjectID + ", "
							+ stockObjectValue.locationID + ", " + stockObjectValue.messageID + ");";
				} else if (stockObjectValue instanceof MaterialValue) {
					if (stockObjectValue instanceof MedicalMaterialValue) {
						MedicalMaterialValue medicalValue = (MedicalMaterialValue) stockObjectValue;
						sqlStatement = "INSERT INTO `Stock` VALUES(0,"
								+ stockObjectValue.volume + ", '"
								+ DatabaseWriteManager.sdf.format(medicalValue.date) + "', NULL, NULL, NULL, NULL, NULL, '"
								+ medicalValue.batchNumber + "', '"
								+ DatabaseWriteManager.sdf.format(timestamp) + "', 0, "
								+ stockObjectValue.stockObjectID + ", "
								+ stockObjectValue.locationID + ", "
								+ stockObjectValue.messageID + ");";
					} else if (stockObjectValue instanceof ConsumableMaterialValue) {
						ConsumableMaterialValue consumableValue = (ConsumableMaterialValue) stockObjectValue;
						sqlStatement = "INSERT INTO `Stock` VALUES(0,"
								+ stockObjectValue.volume + ", '"
								+ DatabaseWriteManager.sdf.format(consumableValue.date) + "', NULL, NULL, NULL, NULL, NULL, '"
								+ consumableValue.batchNumber + "', '"
								+ DatabaseWriteManager.sdf.format(timestamp) + "', 0, "
								+ stockObjectValue.stockObjectID + ", "
								+ stockObjectValue.locationID + ", "
								+ stockObjectValue.messageID + ");";
					} else {
						return false;
					}
				} else {
					return false;
				}
				if (DatabaseWriteManager.executeUpdate(sqlStatement)) {
					sqlStatement = "UPDATE `StockObject` SET `totalVolume` = `totalVolume` + "
							+ stockObjectValue.volume + " WHERE `id` = " + stockObjectValue.stockObjectID + ";";
					return DatabaseWriteManager.executeUpdate(sqlStatement);
				}
			break;
			case 1:
				StockObjectValue mergedStockObject = DatabaseWriteManager.mergeStockObjectValues(existingStock[0], stockObjectValue);
				return DatabaseWriteManager.editStockObjectValue(mergedStockObject);
			default:
				// Data inconsistency - by merging existing StockObjects this state is not covered.
				// The only way this could happen if a user is manually adding StockObjectValues to the db.
			break;
		}
		return false;
	}

	/**
	 * @param existingStockObjectValue StockObjectValue
	 * @param newStockObjectValue StockObjectValue
	 * @return Boolean
	 *
	 * Try to a new StockObjectValue with an existing StockObjectValue before putting it into the
	 * Database. This is a function to safely call the create function, even if another stockObjectValue
	 * exists.
	 *
	 */
	private static StockObjectValue mergeStockObjectValues(StockObjectValue existingStockObjectValue,
	                                                       StockObjectValue newStockObjectValue) {
		// Switch between different extended StockObjectValue Types
		if (existingStockObjectValue instanceof DeviceValue && newStockObjectValue instanceof DeviceValue) {
			DeviceValue existingDeviceValue = (DeviceValue) existingStockObjectValue;
			DeviceValue newDeviceValue = (DeviceValue) newStockObjectValue;

			existingDeviceValue.volume += newDeviceValue.volume;

			return  existingDeviceValue;
		} else if (existingStockObjectValue instanceof MaterialValue  && newStockObjectValue instanceof MaterialValue) {
			if (existingStockObjectValue instanceof MedicalMaterialValue  && newStockObjectValue instanceof MedicalMaterialValue) {
				MedicalMaterialValue existingMedicalValue = (MedicalMaterialValue) existingStockObjectValue;
				MedicalMaterialValue newMedicalValue = (MedicalMaterialValue) newStockObjectValue;

				existingMedicalValue.volume += newMedicalValue.volume;

				return existingMedicalValue;
			} else if (existingStockObjectValue instanceof ConsumableMaterialValue  && newStockObjectValue instanceof ConsumableMaterialValue) {
				ConsumableMaterialValue existingConsumableValue = (ConsumableMaterialValue) existingStockObjectValue;
				ConsumableMaterialValue newConsumableValue = (ConsumableMaterialValue) newStockObjectValue;

				existingConsumableValue.volume += newConsumableValue.volume;

				return existingConsumableValue;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * @param stockObjectValue StockObjectValue
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
			sqlStatement = "DELETE FROM `Stock` WHERE `id` = " + stockObjectValue.id + ";";
		} else if (stockObjectValue instanceof MaterialValue) {
			if (stockObjectValue instanceof MedicalMaterialValue) {
				sqlStatement = "DELETE FROM `Stock` WHERE `id` = " + stockObjectValue.id + ";";
			} else if (stockObjectValue instanceof ConsumableMaterialValue) {
				sqlStatement = "DELETE FROM `Stock` WHERE `id` = " + stockObjectValue.id + ";";
			} else {
				return false;
			}
		} else {
			return false;
		}
		return DatabaseWriteManager.executeUpdate(sqlStatement);
	}
	
	/**
	 * @param stockObjectValue StockObjectValue
	 * @return Boolean
	 * 
	 * Try to edit an existing StockObjectValue with a given extended StockObjectValue Struct.
	 * Fetch the StockObjectValue by its stockObjectValue.id
	 * Returns a boolean Value, which indicates the outcome.
	 * 
	 */
	private static Boolean editStockObjectValue(StockObjectValue stockObjectValue) {
		String sqlStatement = "";
		// Check for allready existing Stock Entries with this particular identifiers (e.g. Location/Message/Date).
		StockObjectValue[] existingStock = DatabaseReadManager.existingStockFor(stockObjectValue);
		// switch over the number of objects with the same identifier.
		assert existingStock != null : false;
		switch (existingStock.length) {
			case 0:
				return false;
			case 1:
				StockObjectValue mergedValue = DatabaseWriteManager.mergeStockObjectValues(stockObjectValue, existingStock[0]);
				// Switch between different extended StockObjectValue Types
				if (mergedValue instanceof DeviceValue) {
					DeviceValue mergedDeviceValue = (DeviceValue) mergedValue;
					sqlStatement = "UPDATE `Stock` SET `volume` = " + mergedValue.volume
							+ ", `mtk` = '" + DatabaseWriteManager.sdf.format(mergedDeviceValue.mtkDate)
							+ "', `stk` = '" + DatabaseWriteManager.sdf.format(mergedDeviceValue.stkDate)
							+ "', `inventarNo` = '" + mergedDeviceValue.inventoryNumber
							+ "', `serialNo` = '" + mergedDeviceValue.serialNumber
							+ "', `umdns` = '" + mergedDeviceValue.umdns
							+ "',`location_id` = " + mergedValue.locationID
							+ ",`message_id` = " + mergedValue.messageID
							+ " WHERE `id` = " + mergedValue.id + ";";
				} else if (mergedValue instanceof MaterialValue) {
					if (mergedValue instanceof MedicalMaterialValue) {
						MedicalMaterialValue mergedMedicalValue = (MedicalMaterialValue) mergedValue;
						sqlStatement = "UPDATE `Stock` SET `volume` = " + mergedValue.volume
							+ ", `date` = '" + DatabaseWriteManager.sdf.format(mergedMedicalValue.date)
							+ "', `batchNo` = '" + mergedMedicalValue.batchNumber
								+ "',`location_id` = " + mergedValue.locationID
								+ ",`message_id` = " + mergedValue.messageID
								+ " WHERE `id` = " + mergedValue.id + ";";
					} else if (mergedValue instanceof ConsumableMaterialValue) {
						ConsumableMaterialValue mergedConsumableValue = (ConsumableMaterialValue) mergedValue;
						sqlStatement = "UPDATE `Stock` SET `volume` = " + mergedValue.volume
							+ ", `date` = '" + DatabaseWriteManager.sdf.format(mergedConsumableValue.date)
							+ "', `batchNo` = '" + mergedConsumableValue.batchNumber
								+ "',`location_id` = " + mergedValue.locationID
								+ ",`message_id` = " + mergedValue.messageID
								+ " WHERE `id` = " + mergedValue.id + ";";
					} else {
						return false;
					}
				} else {
					return false;
				}
				break;
		}
		return DatabaseWriteManager.executeUpdate(sqlStatement);
	}
	
	//================================================================================
	// endregion StockValue
	//================================================================================

	/**
	 * @param sqlStatement String
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