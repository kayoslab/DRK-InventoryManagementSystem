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
 * @author Nina
 * @author Simon
 *
 */

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
				+ " VALUES(" +
				"0" +
				", " + "'" + user.username + "'" +
				", " + "'" + user.firstName +"'" +
				", " + "'" + user.name + "'" +
				", " + "'" + user.mail + "'" +
				", " + "'" + DatabaseWriteManager.sdf.format(timestamp) + "'" +
				", " + "'"	+ user.passwordHash + "'" +
				"," + " false" +
				");";
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
		String sqlStatement = "UPDATE `User` SET " +
				"`username` = " + "'" + user.username + "'" +
				", " + "`firstname` = " + "'" + user.firstName + "'" +
				", " + "name = " + "'" + user.name +"'" +
				", " + "mail = " + "'" + user.mail + "'" +
				", " + "`password` = " + "'" + user.passwordHash + "'" +
				", " + "`passwordChanged` = " + user.passwordChanged +
				" WHERE `id` = " + user.id +";";

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
		String sqlDelete = "DELETE FROM `UserIsMemberOfGroup` WHERE `user` = " + user.id + ";";
		DatabaseWriteManager.executeUpdate(sqlDelete);
		if (groups.length > 0) {
			if (groups != null && user != null) {
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
		} else {
			return true;
		}
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
				+ group.id + ";";
		DatabaseWriteManager.executeUpdate(sqlDelete);
		if (groupRights.length > 0) {
			String sqlStatement = "INSERT INTO `GroupHasRights` VALUES";
			for(GroupRight groupRight:groupRights){
				sqlStatement += "(" + group.id + "," + groupRight.id + "),";
			}
			if (sqlStatement.charAt(sqlStatement.length()-1) == ',' ){
				sqlStatement = sqlStatement.substring(0, sqlStatement.length()-1);
			}
			sqlStatement += ";";
			return DatabaseWriteManager.executeUpdate(sqlStatement);
		} else {
			return true;
		}
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
			sqlStatement = "INSERT INTO `StockObject` " +
					"VALUES(" +
						"0" + // id
						",'" + device.title + "'" + // titel
						"," + "'" + device.description + "'" + // description
						", 1" + // batchSize
						", 0" + // totalVolume
						"," + device.mtkIntervall + // mtkIntervall
						", " + device.stkIntervall + // stkIntervall
						", " + "'" + DatabaseWriteManager.sdf.format(timestamp) + "'" + // creation
						", " + DatabaseObject.StockObjectType.device.ordinal() + // typeID
					");";
		} else if (stockObject instanceof Material) {
			if (stockObject instanceof MedicalMaterial) {
				MedicalMaterial medmat = (MedicalMaterial) stockObject;
				//Type ID 2 = MedicalMaterial
				sqlStatement = "INSERT INTO `StockObject` " +
						"VALUES(" +
							"0" + // id
							", " + "'" + medmat.title + "'" + // titel
							", " + "'" + medmat.description + "'" + // description
							", " + medmat.batchSize + // batchSize
							", 0" + // totalVolume
							", NULL" + // mtkIntervall
							", NULL" + // stkIntervall
							"," + "'" + DatabaseWriteManager.sdf.format(timestamp) + "'" + // creation
							", " + DatabaseObject.StockObjectType.medicalMaterial.ordinal() + // typeID
						");";
			} else if (stockObject instanceof ConsumableMaterial) {
				ConsumableMaterial consmat = (ConsumableMaterial) stockObject;
				//Type ID 3 = ConsumableMaterial
				sqlStatement = "INSERT INTO `StockObject` " +
						"VALUES(" +
							"0" + // id
							", " + "'" + consmat.title + "'" + // titel
							", " + "'" + consmat.description + "'" + // description
							", " + consmat.batchSize + // batchSize
							", 0" + // totalVolume
							", NULL" + // mtkIntervall
							", NULL" + // stkIntervall
							", " + "'" + DatabaseWriteManager.sdf.format(timestamp) + "'" + // creation
							", " + DatabaseObject.StockObjectType.consumableMaterial.ordinal() + // typeID
						");";
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
			sqlStatement = "DELETE FROM `StockObject` " +
					"WHERE `id` = " + stockObject.id +
					";";
		} else if (stockObject instanceof Material) {
			if (stockObject instanceof MedicalMaterial) {
				sqlStatement = "DELETE FROM `StockObject` " +
						"WHERE `id` = " + stockObject.id +
						";";
			} else if (stockObject instanceof ConsumableMaterial) {
				sqlStatement = "DELETE FROM `StockObject` " +
						"WHERE `id` = " + stockObject.id + ";";
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
			sqlStatement = "UPDATE `StockObject` " +
					"SET `title` = " + "'" + device.title + "'" +
					", `description` = " + "'" + device.description + "'" +
					", `mtkIntervall` = " + device.mtkIntervall +
					", `stkIntervall` = " + device.stkIntervall +
					" WHERE `id` = " + stockObject.id + ";";
		} else if (stockObject instanceof Material) {
			if (stockObject instanceof MedicalMaterial) {
				MedicalMaterial medmat = (MedicalMaterial) stockObject;
				//Type ID 2 = MedicalMaterial
				sqlStatement = "UPDATE `StockObject` " +
						"SET `title` = " + "'" + medmat.title + "'" +
						", `description` = " + "'" + medmat.description + "'" +
						", `batchSize` = " + medmat.batchSize +
						", `totalVolume` = " + medmat.totalVolume
						+ " WHERE `id` = " + stockObject.id + ";";
			} else if (stockObject instanceof ConsumableMaterial) {
				ConsumableMaterial consmat = (ConsumableMaterial) stockObject;
				//Type ID 3 = ConsumableMaterial
				sqlStatement = "UPDATE `StockObject` " +
						"SET `title` = " + "'" + consmat.title + "'" +
						", `description` = " + "'" + consmat.description + "'" +
						",  `batchSize` = " + consmat.batchSize +
						", `totalVolume` = " + consmat.totalVolume +
						" WHERE `id` = " + stockObject.id + ";";
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
		StockObjectValue[] existingStock = DatabaseReadManager.existingStockObjectValueFor(stockObjectValue);
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
					String mtkDate = "null";
					String stkDate = "null";
					if (deviceValue.mtkDate != null) {
						mtkDate = "'" +  DatabaseWriteManager.sdf.format(deviceValue.mtkDate) + "'";
					}
					if (deviceValue.stkDate != null) {
						stkDate = "'" + DatabaseWriteManager.sdf.format(deviceValue.stkDate) + "'";
					}

					sqlStatement = "INSERT INTO `StockValue` " +
							"VALUES(" +
								"0" + // id
								", " + stockObjectValue.volume + // volume
								", NULL" + // date
								", " + mtkDate + // mtkdate
								", " + stkDate + // stkdate
								", '" + deviceValue.inventoryNumber + // inventory
								"', '" + deviceValue.serialNumber + // serialNummer
								"', '" + deviceValue.umdns + // umdns
								"', NULL" + // batchNumber
								", 0" + // minimumStock
								", 0 " + // quotaStock
								", " + deviceValue.silencedWarnings + // silencedWarnings
								", '" + DatabaseWriteManager.sdf.format(timestamp) + "'" + // creation
								", " + stockObjectValue.stockObjectID +
								", " + stockObjectValue.locationID +
								", " + stockObjectValue.messageID +
							");";
				} else if (stockObjectValue instanceof MaterialValue) {
					if (stockObjectValue instanceof MedicalMaterialValue) {
						MedicalMaterialValue medicalValue = (MedicalMaterialValue) stockObjectValue;
						String date = "null";
						if (medicalValue.date != null) {
							date =  "'" + DatabaseWriteManager.sdf.format(medicalValue.date) + "'";
						}
						sqlStatement = "INSERT INTO `StockValue` " +
								"VALUES(" +
									"0" + // id
									", " + stockObjectValue.volume + // volume
									", " + date + // date
									", NULL" + // mtkdate
									", NULL" + // stkdate
									", NULL" + // inventory
									", NULL" + // serialNumber
									", NULL" + // umdns
									", '" + medicalValue.batchNumber + // batchNumber
									"', "  + medicalValue.minimumStock+ // minimumStock
									",  " + medicalValue.quotaStock + // quotaStock
									", " + medicalValue.silencedWarnings + // silencedWarnings
									", '" + DatabaseWriteManager.sdf.format(timestamp) + "'" + // creation
									", " + stockObjectValue.stockObjectID +
									", " + stockObjectValue.locationID +
									", " + stockObjectValue.messageID +
								");";
					} else if (stockObjectValue instanceof ConsumableMaterialValue) {
						ConsumableMaterialValue consumableValue = (ConsumableMaterialValue) stockObjectValue;
						String date = "null";
						if (consumableValue.date != null) {
							date = "'" + DatabaseWriteManager.sdf.format(consumableValue.date) + "'";
						}
						sqlStatement = "INSERT INTO `StockValue` " +
								"VALUES(" +
									"0" + // id
									", " + stockObjectValue.volume + // volume
									", " + date + // date
									", NULL" + // mtkdate
									", NULL" + // stkdate
									", NULL" + // inventory
									", NULL" + // serialNumber
									", NULL" + // umdns
									", '" + consumableValue.batchNumber + // batchNumber
									"', "  + consumableValue.minimumStock+ // minimumStock
									",  " + consumableValue.quotaStock + // quotaStock
									", " + consumableValue.silencedWarnings + // silencedWarnings
									", '" + DatabaseWriteManager.sdf.format(timestamp) + "'" +  // creation
									", " + stockObjectValue.stockObjectID +
									", " + stockObjectValue.locationID +
									", " + stockObjectValue.messageID +
								");";
					} else {
						return false;
					}
				} else {
					return false;
				}
				if (DatabaseWriteManager.executeUpdate(sqlStatement)) {
					sqlStatement = "UPDATE `StockObject` " +
							"SET " +
							"`totalVolume` = `totalVolume` + " + stockObjectValue.volume +
							" WHERE `id` = " + stockObjectValue.stockObjectID +
							";";
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
			sqlStatement = "DELETE FROM `StockValue` " +
					"WHERE `id` = " + stockObjectValue.id +
					";";
		} else if (stockObjectValue instanceof MaterialValue) {
			if (stockObjectValue instanceof MedicalMaterialValue) {
				sqlStatement = "DELETE FROM `StockValue` " +
						"WHERE `id` = " + stockObjectValue.id +
						";";
			} else if (stockObjectValue instanceof ConsumableMaterialValue) {
				sqlStatement = "DELETE FROM `StockValue` " +
						"WHERE `id` = " + stockObjectValue.id +
						";";
			} else {
				return false;
			}
			if (DatabaseWriteManager.executeUpdate(sqlStatement)) {
				StockObject stockObject = DatabaseReadManager.getStockObject(stockObjectValue.stockObjectID);
				if (stockObject != null) {
					StockObjectValue[] stockObjectValues = DatabaseReadManager.getStockObjectValues(stockObject);
					int totalVolume = 0;
					if (stockObjectValues != null) {
						for (StockObjectValue iteratedStockObjectValue : stockObjectValues) {
							totalVolume += iteratedStockObjectValue.volume;
						}
					}
					sqlStatement = "UPDATE `StockObject` SET `totalVolume` = " + totalVolume + " WHERE `id` = " + stockObjectValue.stockObjectID + ";";
					return DatabaseWriteManager.executeUpdate(sqlStatement);
				}
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
		if (stockObjectValue instanceof DeviceValue) {
			DeviceValue mergedDeviceValue = (DeviceValue) stockObjectValue;
			String mtkDate = "null";
			String stkDate = "null";
			if (mergedDeviceValue.mtkDate != null) {
				mtkDate = "'" +  DatabaseWriteManager.sdf.format(mergedDeviceValue.mtkDate) + "'";
			}
			if (mergedDeviceValue.stkDate != null) {
				stkDate = "'" + DatabaseWriteManager.sdf.format(mergedDeviceValue.stkDate) + "'";
			}
			sqlStatement = "UPDATE `StockValue` " +
					"SET " +
					"`volume` = " + stockObjectValue.volume +
					", " + "`mtkDate` = " + mtkDate +
					", " + "`stkDate` = " + stkDate +
					", " + "`inventoryNumber` = '" + mergedDeviceValue.inventoryNumber + "'" +
					", " + "`serialNumber` = '" + mergedDeviceValue.serialNumber + "'" +
					", " + "`umdns` = '" + mergedDeviceValue.umdns + "'" +
					", " + "`locationId` = " + stockObjectValue.locationID +
					", " + "`messageId` = " + stockObjectValue.messageID +
					" WHERE `id` = " + stockObjectValue.id + ";";
			return DatabaseWriteManager.executeUpdate(sqlStatement);
		} else if (stockObjectValue instanceof MaterialValue) {
			if (stockObjectValue instanceof MedicalMaterialValue) {
				MedicalMaterialValue mergedMedicalValue = (MedicalMaterialValue) stockObjectValue;
				String date = "null";
				if (mergedMedicalValue.date != null) {
					date =  "'" + DatabaseWriteManager.sdf.format(mergedMedicalValue.date) + "'";
				}
				sqlStatement = "UPDATE `StockValue` " +
						"SET " +
						"`volume` = " + stockObjectValue.volume +
						", " + "`date` = " + date +
						", " + "`batchNumber` = '" + mergedMedicalValue.batchNumber + "'" +
						", " + "`minimumStock` = " + mergedMedicalValue.minimumStock +
						", " + "`quotaStock` = " + mergedMedicalValue.quotaStock +
						", " + "`locationId` = " + stockObjectValue.locationID +
						", " + "`messageId` = " + stockObjectValue.messageID +
						" WHERE `id` = " + stockObjectValue.id +
						";";
			} else if (stockObjectValue instanceof ConsumableMaterialValue) {
				ConsumableMaterialValue mergedConsumableValue = (ConsumableMaterialValue) stockObjectValue;
				String date = "null";
				if (mergedConsumableValue.date != null) {
					date = "'" + DatabaseWriteManager.sdf.format(mergedConsumableValue.date) + "'";
				}
				sqlStatement = "UPDATE `StockValue` SET " +
						"`volume` = " + stockObjectValue.volume +
						", " + "`date` = " + date +
						", " + "`batchNumber` = '" + mergedConsumableValue.batchNumber + "'" +
						", " + "`minimumStock` = " + mergedConsumableValue.minimumStock +
						", " + "`quotaStock` = " + mergedConsumableValue.quotaStock +
						", " + "`locationId` = " + stockObjectValue.locationID +
						", " + "`messageId` = " + stockObjectValue.messageID +
						" WHERE `id` = " + stockObjectValue.id +
						";";
			} else {
				return false;
			}
			if (DatabaseWriteManager.executeUpdate(sqlStatement)) {
				StockObject stockObject = DatabaseReadManager.getStockObject(stockObjectValue.stockObjectID);
				if (stockObject != null) {
					StockObjectValue[] stockObjectValues = DatabaseReadManager.getStockObjectValues(stockObject);
					int totalVolume = 0;
					if (stockObjectValues != null) {
						for (StockObjectValue iteratedStockObjectValue : stockObjectValues) {
							totalVolume += iteratedStockObjectValue.volume;
						}
					}
					sqlStatement = "UPDATE `StockObject` SET " +
							"`totalVolume` = " + totalVolume +
							" WHERE `id` = " + stockObjectValue.stockObjectID +
							";";
					return DatabaseWriteManager.executeUpdate(sqlStatement);
				}
			}
		}
		return false;
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