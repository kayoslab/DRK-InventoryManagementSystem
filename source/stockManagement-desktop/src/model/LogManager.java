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
 *
 */

package model;
import model.databaseCommunication.DatabaseValueManager;
import model.databaseObjects.stockObjects.StockObject;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public final class LogManager {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static DatabaseValueManager getValueManager() {
		return new DatabaseValueManager();
	}
	private static Session getCurrentSession() {
		return Session.getSharedInstance();
	}
	public enum Operation {
		empty,
		login,
		editSelf,
		createUser,
		deleteUser,
		editUser,
		viewUsers,
		createGroup,
		deleteGroup,
		editGroup,
		viewGroups,
		createDevice,
		deleteDevice,
		editDevice,
		viewDevices,
		deviceIncrease,
		deviceDecrease,
		deviceCorrection,
		createMedicalMaterial,
		deleteMedicalMaterial,
		editMedicalMaterial,
		viewMedicalMaterials,
		medicalMaterialIncrease,
		medicalMaterialDecrease,
		medicalMaterialCorrection,
		createConsumableMaterial,
		deleteConsumableMaterial,
		editConsumableMaterial,
		viewConsumableMaterials,
		consumableMaterialIncrease,
		consumableMaterialDecrease,
		consumableMaterialCorrection,
		createLocation,
		deleteLocation,
		editLocation,
		viewLocations
	}

	/** ******************************************* **/

	private LogManager() {
		// Do nothing here -> Static implementation
	}

	public static Boolean writeLogMessage(LogManager.Operation operation) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String sqlString = "INSERT INTO `Logbook` VALUES" +
				"( 0 "
				+ "," + "'" + LogManager.sdf.format(timestamp) + "'" 
				+ "," + LogManager.getCurrentSession().currentUser.id
				+ "," + "NULL"
				+ "," + operation.ordinal() + ");";
		return LogManager.executeUpdate(sqlString);
	}
	
	public static Boolean writeLogMessage(LogManager.Operation operation, StockObject stockObject) { 
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String sqlString = "INSERT INTO `Logbook` VALUES" +
				"( 0 "
				+ "," + "'" + LogManager.sdf.format(timestamp) + "'" 
				+ "," + LogManager.getCurrentSession().currentUser.id 
				+ "," + stockObject.id 
				+ "," + operation.ordinal() + ");";
		return LogManager.executeUpdate(sqlString);
	}

	/** ******************************************* **/

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
		DatabaseValueManager valueManager = LogManager.getValueManager();
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
