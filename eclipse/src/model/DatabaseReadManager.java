package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.databaseCommunication.DatabaseValueManager;
import model.databaseObjects.DatabaseObject;
import model.databaseObjects.accessControl.*;
import model.databaseObjects.environment.*;
import model.databaseObjects.stockObjects.*;
import model.databaseObjects.stockValues.*;

public final class DatabaseReadManager {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private DatabaseReadManager() {
		// Do nothing here -> Static implementation
	}

	private static DatabaseValueManager getValueManager() {
		return new DatabaseValueManager();
	}

	//================================================================================
	// region User
	//================================================================================

	/**
	 * @return User[]
	 */
	public static User[] getUser() {
		String sqlStatement = "SELECT `username`,`firstname`,`name`,`mail`,`password`,`passwordChanged`"
				+ " FROM `User` ;";
		ResultSet rs = null;
		try {
			// get Data from Database
			rs = DatabaseReadManager.executeQuery(sqlStatement);
			if (rs.last()) {
				User[] user = new User[rs.getRow()];
				int i = 0;
				// fill with reasonable Data
				while (rs.next())
                {
                    user[i] = new User(rs.getInt("id"), rs.getString("username"), rs.getString("firstname"), rs.getString("name"),
    						rs.getString("mail"), rs.getString("password"), rs.getBoolean("passwordChanged"));
                    i++;
                }   
				return null;
			}
			return null;
		} catch (SQLException e) {
			// rs isNull or one or more attributes are missing
			// uncomment for debugging SQL-Statements
			System.out.println(e.getMessage());
			try {
				DatabaseReadManager.close(rs);
			} catch (SQLException e1) {
				// nothing to do here, return not necessary
				return null;
			}
			return null;
		}
	}


	/**
	 * @param id int user.id
	 * @return User
	 */
	public static User getUser(int id) {
		// get User from Database and return for id
		String sqlStatement = "SELECT `username`,`firstname`,`name`,`mail`,`password`,`passwordChanged`"
				+ " FROM `User` WHERE id = " + id + ";";
		ResultSet rs = null;
		try {
			// get Data from Database
			rs = DatabaseReadManager.executeQuery(sqlStatement);
			if (rs.first()) {
				// fill with reasonable Data
				User user = new User(id, rs.getString("username"), rs.getString("firstname"), rs.getString("name"),
						rs.getString("mail"), rs.getString("password"), rs.getBoolean("passwordChanged"));
				DatabaseReadManager.close(rs);
				return user;
			}
			return null;
		} catch (SQLException e) {
			// rs isNull or one or more attributes are missing
			// uncomment for debugging SQL-Statements
			// System.out.println(e.getMessage());
			try {
				DatabaseReadManager.close(rs);
			} catch (SQLException e1) {
				// nothing to do here, return not necessary
				return null;
			}
			return null;
		}
	}

	/**
	 * @param username String
	 * @return User
	 */
	public static User getUser(String username) {
		// get User from Database and return for id
		String sqlStatement = "SELECT `id`,`username`,`firstname`,`name`,`mail`,`password`,`passwordChanged`"
				+ " FROM `User` WHERE `username` = '" + username + "'";
		ResultSet rs = null;
		try {
			// get Data from Database
			rs = DatabaseReadManager.executeQuery(sqlStatement);
			if (rs.first()) {
				// fill with reasonable Data
				User user = new User(rs.getInt("id"), username, rs.getString("firstname"), rs.getString("name"),
						rs.getString("mail"),rs.getString("password"), rs.getBoolean("passwordChanged"));
				DatabaseReadManager.close(rs);
				return user;
			}
			return null;
		} catch (SQLException e) {
			// rs isNull or one or more attributes are missing
			// uncomment for debugging SQL-Statements
			System.out.println(e.getMessage());
			try {
				DatabaseReadManager.close(rs);
			} catch (SQLException e1) {
				// nothing to do here, return not necessary
				return null;
			}
			return null;
		}
	}

	/**
	 * @param username String
	 * @return Boolean
	 *
	 * Check if a user has already changed his password since it was
	 * modifies by an admin
	 */
	public static Boolean userDidChangePassword(String username) {
		// check if password has already been changed
		String sqlStatement = "SELECT `passwordChanged`" + "FROM `User` WHERE `username` = '"
				+ username + "'";
		ResultSet rs = null;
		try {
			// get Data from Database
			rs = DatabaseReadManager.executeQuery(sqlStatement);
			if (rs.first()) {
				// fill with reasonable Data
				Boolean changed = rs.getBoolean("passwordChanged");
				DatabaseReadManager.close(rs);
				return changed;
			}
			return null;
		} catch (SQLException e) {
			// rs isNull or one or more attributes are missing
			// uncomment for debugging SQL-Statements
			// System.out.println(e.getMessage());
			try {
				DatabaseReadManager.close(rs);
			} catch (SQLException e1) {
				// nothing to do here, return not necessary
				return null;
			}
			return null;
		}
	}

	//================================================================================
	// endregion User
	// region Group
	//================================================================================

	/**
	 * @return Group[]
	 */
	public static Group[] getGroup() {
		String sqlStatement = "";
		ResultSet rs = null;
		try {
			// get Data from Database
			rs = DatabaseReadManager.executeQuery(sqlStatement);
			if (rs.first()) {
				// fill with reasonable Data
				return null;
			}
			return null;
		} catch (SQLException e) {
			// rs isNull or one or more attributes are missing
			// uncomment for debugging SQL-Statements
			System.out.println(e.getMessage());
			try {
				DatabaseReadManager.close(rs);
			} catch (SQLException e1) {
				// nothing to do here, return not necessary
				return null;
			}
			return null;
		}
	}

	/**
	 * @param id int group.id
	 * @return Group
	 */
	public static Group getGroup(int id) {
		String sqlStatement = "";
		ResultSet rs = null;
		try {
			// get Data from Database
			rs = DatabaseReadManager.executeQuery(sqlStatement);
			if (rs.first()) {
				// fill with reasonable Data
				return null;
			}
			return null;
		} catch (SQLException e) {
			// rs isNull or one or more attributes are missing
			// uncomment for debugging SQL-Statements
			System.out.println(e.getMessage());
			try {
				DatabaseReadManager.close(rs);
			} catch (SQLException e1) {
				// nothing to do here, return not necessary
				return null;
			}
			return null;
		}
	}

	//================================================================================
	// endregion Group
	// region GroupRight
	//================================================================================

	/**
	 * @return GroupRight[]
	 */
	public static GroupRight[] getGroupRights() {
		String sqlStatement = "";
		ResultSet rs = null;
		try {
			// get Data from Database
			rs = DatabaseReadManager.executeQuery(sqlStatement);
			if (rs.first()) {
				// fill with reasonable Data
				return null;
			}
			return null;
		} catch (SQLException e) {
			// rs isNull or one or more attributes are missing
			// uncomment for debugging SQL-Statements
			System.out.println(e.getMessage());
			try {
				DatabaseReadManager.close(rs);
			} catch (SQLException e1) {
				// nothing to do here, return not necessary
				return null;
			}
			return null;
		}
	}
	/**
	 * @param id int user.id
	 * @return GroupRight[]
	 */
	public static GroupRight[] getGroupRights(int id) {
		String sqlStatement = "";
		ResultSet rs = null;
		try {
			// get Data from Database
			rs = DatabaseReadManager.executeQuery(sqlStatement);
			if (rs.first()) {
				// fill with reasonable Data
				return null;
			}
			return null;
		} catch (SQLException e) {
			// rs isNull or one or more attributes are missing
			// uncomment for debugging SQL-Statements
			System.out.println(e.getMessage());
			try {
				DatabaseReadManager.close(rs);
			} catch (SQLException e1) {
				// nothing to do here, return not necessary
				return null;
			}
			return null;
		}
	}

	//================================================================================
	// endregion GroupRight
	// region Location
	//================================================================================

	/**
	 * @return Location[]
	 */
	public static Location[] getLocations() {
		String sqlStatement = "";
		ResultSet rs = null;
		try {
			// get Data from Database
			rs = DatabaseReadManager.executeQuery(sqlStatement);
			if (rs.first()) {
				// fill with reasonable Data
				return null;
			}
			return null;
		} catch (SQLException e) {
			// rs isNull or one or more attributes are missing
			// uncomment for debugging SQL-Statements
			System.out.println(e.getMessage());
			try {
				DatabaseReadManager.close(rs);
			} catch (SQLException e1) {
				// nothing to do here, return not necessary
				return null;
			}
			return null;
		}
	}

	/**
	 * @param id int user.id
	 * @return Location[]
	 */
	public static Location[] getLocation(int id) {
		String sqlStatement = "";
		ResultSet rs = null;
		try {
			// get Data from Database
			rs = DatabaseReadManager.executeQuery(sqlStatement);
			if (rs.first()) {
				// fill with reasonable Data
				return null;
			}
			return null;
		} catch (SQLException e) {
			// rs isNull or one or more attributes are missing
			// uncomment for debugging SQL-Statements
			System.out.println(e.getMessage());
			try {
				DatabaseReadManager.close(rs);
			} catch (SQLException e1) {
				// nothing to do here, return not necessary
				return null;
			}
			return null;
		}
	}

	/**
	 * @param stockObject StockObject
	 * @return Location[]
	 */
	public static Location[] getLocation(StockObject stockObject) {
		String sqlStatement = "";
		ResultSet rs = null;
		try {
			// get Data from Database
			rs = DatabaseReadManager.executeQuery(sqlStatement);
			if (rs.first()) {
				// fill with reasonable Data
				return null;
			}
			return null;
		} catch (SQLException e) {
			// rs isNull or one or more attributes are missing
			// uncomment for debugging SQL-Statements
			System.out.println(e.getMessage());
			try {
				DatabaseReadManager.close(rs);
			} catch (SQLException e1) {
				// nothing to do here, return not necessary
				return null;
			}
			return null;
		}
	}

	//================================================================================
	// endregion Location
	// region StockObject
	//================================================================================

	/**
	 * @param id int stockObject.id
	 * @return StockObject
	 */
	public static StockObject getStockObject(int id) {
		String sqlStatement = "";
		ResultSet rs = null;
		try {
			// get Data from Database
			rs = DatabaseReadManager.executeQuery(sqlStatement);
			if (rs.first()) {
				// fill with reasonable Data
				return null;
			}
			return null;
		} catch (SQLException e) {
			// rs isNull or one or more attributes are missing
			// uncomment for debugging SQL-Statements
			System.out.println(e.getMessage());
			try {
				DatabaseReadManager.close(rs);
			} catch (SQLException e1) {
				// nothing to do here, return not necessary
				return null;
			}
			return null;
		}
	}

	/**
	 * @return StockObject[]
	 */
	public static StockObject[] getStockObjects() {
		String sqlStatement = "";
		ResultSet rs = null;
		try {
			// get Data from Database
			rs = DatabaseReadManager.executeQuery(sqlStatement);
			if (rs.first()) {
				// fill with reasonable Data
				return null;
			}
			return null;
		} catch (SQLException e) {
			// rs isNull or one or more attributes are missing
			// uncomment for debugging SQL-Statements
			System.out.println(e.getMessage());
			try {
				DatabaseReadManager.close(rs);
			} catch (SQLException e1) {
				// nothing to do here, return not necessary
				return null;
			}
			return null;
		}
	}

	/**
	 * @param type DatabaseObject.StockObjectType
	 * @return StockObject[]
	 */
	public static StockObject[] getStockObjects(DatabaseObject.StockObjectType type) {
		String sqlStatement = "";
		ResultSet rs = null;
		try {
			// get Data from Database
			rs = DatabaseReadManager.executeQuery(sqlStatement);
			if (rs.first()) {
				// fill with reasonable Data
				return null;
			}
			return null;
		} catch (SQLException e) {
			// rs isNull or one or more attributes are missing
			// uncomment for debugging SQL-Statements
			System.out.println(e.getMessage());
			try {
				DatabaseReadManager.close(rs);
			} catch (SQLException e1) {
				// nothing to do here, return not necessary
				return null;
			}
			return null;
		}
	}

	//================================================================================
	// endregion StockObject
	// region StockObjectValue
	//================================================================================

	/**
	 * @param stockObject StockObject
	 * @return StockObjectValue[]
	 */
	public static StockObjectValue[] getStockObjectValues(StockObject stockObject) {
		String sqlStatement = "";
		ResultSet rs = null;
		try {
			// get Data from Database
			rs = DatabaseReadManager.executeQuery(sqlStatement);
			if (rs.first()) {
				// fill with reasonable Data
				return null;
			}
			return null;
		} catch (SQLException e) {
			// rs isNull or one or more attributes are missing
			// uncomment for debugging SQL-Statements
			System.out.println(e.getMessage());
			try {
				DatabaseReadManager.close(rs);
			} catch (SQLException e1) {
				// nothing to do here, return not necessary
				return null;
			}
			return null;
		}
	}

	/**
	 * @param id int stockObjectValue.id
	 * @return StockObjectValue[]
	 */
	public static StockObjectValue[] getStockObjectValues(int id) {
		String sqlStatement = "";
		ResultSet rs = null;
		try {
			// get Data from Database
			rs = DatabaseReadManager.executeQuery(sqlStatement);
			if (rs.first()) {
				// fill with reasonable Data
				return null;
			}
			return null;
		} catch (SQLException e) {
			// rs isNull or one or more attributes are missing
			// uncomment for debugging SQL-Statements
			System.out.println(e.getMessage());
			try {
				DatabaseReadManager.close(rs);
			} catch (SQLException e1) {
				// nothing to do here, return not necessary
				return null;
			}
			return null;
		}
	}

	/**
	 * @param location Location
	 * @return StockObjectValue[]
	 */
	public static StockObjectValue[] getStockObjectValues(Location location) {
		String sqlStatement = "";
		ResultSet rs = null;
		try {
			// get Data from Database
			rs = DatabaseReadManager.executeQuery(sqlStatement);
			if (rs.first()) {
				// fill with reasonable Data
				return null;
			}
			return null;
		} catch (SQLException e) {
			// rs isNull or one or more attributes are missing
			// uncomment for debugging SQL-Statements
			System.out.println(e.getMessage());
			try {
				DatabaseReadManager.close(rs);
			} catch (SQLException e1) {
				// nothing to do here, return not necessary
				return null;
			}
			return null;
		}
	}

	/**
	 * @param type DatabaseObject.StockObjectType
	 * @param message DatabaseObject.StockValueMessage
	 * @return StockObjectValue[]
	 */
	public static StockObjectValue[] getStockObjectValues(DatabaseObject.StockObjectType type, DatabaseObject.StockValueMessage message) {
		String sqlStatement = "";
		ResultSet rs = null;
		try {
			// get Data from Database
			rs = DatabaseReadManager.executeQuery(sqlStatement);
			if (rs.first()) {
				// fill with reasonable Data
				return null;
			}
			return null;
		} catch (SQLException e) {
			// rs isNull or one or more attributes are missing
			// uncomment for debugging SQL-Statements
			System.out.println(e.getMessage());
			try {
				DatabaseReadManager.close(rs);
			} catch (SQLException e1) {
				// nothing to do here, return not necessary
				return null;
			}
			return null;
		}
	}

	/**
	 * @param stockObjectValue StockObjectValue
	 * @return StockObjectValue
	 *
	 * Check if the stock already contains an unique object for a given StockObjectValue
	 * This fucntion returns an emtpy Array of StockObjectValue[] if the Stock is empty.
	 *
	 */
	public static StockObjectValue[] existingStockObjectValueFor(StockObjectValue stockObjectValue) {
		String sqlStatement = "SELECT * FROM `Stock` WHERE `stockObject_id` = " + stockObjectValue.stockObjectID
				+ " AND `location_id` = " + stockObjectValue.locationID;
		// Switch between different extended StockObjectValue Types
		if (stockObjectValue instanceof DeviceValue) {
			DeviceValue deviceValue = (DeviceValue) stockObjectValue;
			sqlStatement += " AND `inventarNo` = '" + deviceValue.inventoryNumber
					+ "' AND `serialNo` = '" + deviceValue.serialNumber
					+ "' AND `mtk` = '" + DatabaseReadManager.sdf.format(deviceValue.mtkDate)
					+ "' AND `stk` = '" + DatabaseReadManager.sdf.format(deviceValue.stkDate) + "'";
		} else if (stockObjectValue instanceof MaterialValue) {
			if (stockObjectValue instanceof MedicalMaterialValue) {
				MedicalMaterialValue medicalValue = (MedicalMaterialValue) stockObjectValue;
				sqlStatement += " AND `batchNo` = '" + medicalValue.batchNumber + "'";
			} else if (stockObjectValue instanceof ConsumableMaterialValue) {
				ConsumableMaterialValue consumableValue = (ConsumableMaterialValue) stockObjectValue;
				sqlStatement += " AND `batchNo` = '" + consumableValue.batchNumber + "'";
			} else {
				return new StockObjectValue[0];
			}
		} else {
			return new StockObjectValue[0];
		}
		sqlStatement += ";";
		ResultSet rs = null;
		// Execute the processed SQL Statement and return an Array of Objects
		try {
			// get Data from Database
			rs = DatabaseReadManager.executeQuery(sqlStatement);
			ArrayList<StockObjectValue> existingObjects = new ArrayList<StockObjectValue>();
			while (rs.next()) {
				// fill the ArrayList with reasonable Data
				if (stockObjectValue instanceof DeviceValue) {
					existingObjects.add(new DeviceValue(rs.getInt("id"), rs.getInt("volume"),
							rs.getDate("mtk"), rs.getDate("stk"), rs.getInt("stockObject_id"),
							rs.getInt("location_id"), rs.getInt("message_id"), rs.getString("serialNo"),
							rs.getString("inventarNo"), rs.getString("umdns")));
				} else if (stockObjectValue instanceof MaterialValue) {
					if (stockObjectValue instanceof MedicalMaterialValue) {
						existingObjects.add(new MedicalMaterialValue(rs.getInt("id"), rs.getInt("volume"),
								rs.getInt("stockObject_id"), rs.getInt("location_id"), rs.getInt("message_id"),
								rs.getString("batchNo"), rs.getDate("date")));
					} else if (stockObjectValue instanceof ConsumableMaterialValue) {
						existingObjects.add(new ConsumableMaterialValue(rs.getInt("id"), rs.getInt("volume"),
								rs.getInt("stockObject_id"), rs.getInt("location_id"), rs.getInt("message_id"),
								rs.getString("batchNo"), rs.getDate("date")));
					} else {
						return new StockObjectValue[0];
					}
				} else {
					return new StockObjectValue[0];
				}
			}
			DatabaseReadManager.close(rs);
			return existingObjects.toArray(new StockObjectValue[existingObjects.size()]);
		} catch (SQLException e) {
			// rs isNull or one or more attributes are missing
			// uncomment for debugging SQL-Statements
			System.out.println(e.getMessage());
			try {
				DatabaseReadManager.close(rs);
			} catch (SQLException e1) {
				// nothing to do here, return not necessary
				return new StockObjectValue[0];
			}
			return new StockObjectValue[0];
		}
	}

	//================================================================================
	// endregion StockObjectValue
	//================================================================================

	/**
	 * @param stockObjectType DatabaseObject.StockObjectType
	 * @return StockObject[]
	 *
	 * Generates Inventorylist for a given StockObjectType
	 */
	public static StockObject[] generateInventory(DatabaseObject.StockObjectType stockObjectType) {
		// get Inventory as Array
		String sqlStatement = "SELECT `id`, `title`, `description`, `silenceWarning`, `type_id`, `totalVolume` FROM `StockObject`";
		// get Data from Database
		ResultSet rs = null;
		// Execute the processed SQL Statement and return an Array of Objects
		try {
			// get Data from Database
			rs = DatabaseReadManager.executeQuery(sqlStatement);
			ArrayList<StockObject> inventoryList = new ArrayList<StockObject>();
			while (rs.next()) {
				// fill the ArrayList with reasonable Data
				switch (stockObjectType) {
					case device:
						inventoryList.add(new Device(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
								rs.getBoolean("silenceWarning"), stockObjectType, rs.getInt("totalVolume"),
								rs.getInt("mtkIntervall"), rs.getInt("stkIntervall")));
						break;
					case medicalMaterial:
						inventoryList.add(new MedicalMaterial(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
								rs.getBoolean("silenceWarning"), stockObjectType, rs.getInt("totalVolume"),
								rs.getInt("batchSize"), rs.getInt("minimumStock"), rs.getInt("quotaStock")));
						break;
					case consumableMaterial:
						inventoryList.add(new ConsumableMaterial(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
								rs.getBoolean("silenceWarning"), stockObjectType, rs.getInt("totalVolume"),
								rs.getInt("batchSize"), rs.getInt("minimumStock"), rs.getInt("quotaStock")));
						break;
					case vehicle:
						break;
				}
			}
			DatabaseReadManager.close(rs);
			return (StockObject[]) inventoryList.toArray();
		} catch (SQLException e) {
			// rs isNull or one or more attributes are missing
			// uncomment for debugging SQL-Statements
			// System.out.println(e.getMessage());
			try {
				DatabaseReadManager.close(rs);
			} catch (SQLException e1) {
				// nothing to do here, return not necessary
				return new StockObject[0];
			}
			return new StockObject[0];
		}
	}

	/**
	 * @param sqlStatement String
	 * @return Boolean
	 *
	 * try to execute Query on DatabaseValueManager Returns a ResultSet,
	 * which indicates the outcome.
	 *
	 */
	private static ResultSet executeQuery(String sqlStatement) throws SQLException {
		// Get a shared Instance of the DatabaseValueManager
		DatabaseValueManager valueManager = DatabaseReadManager.getValueManager();
		// execute Database Update
		ResultSet result = valueManager.executeQuery(sqlStatement);
		// returns either the ResultSet or null for SQL statements
		// that return nothing.
		return (result);
	}
	
	private static void close(ResultSet resultSet) throws SQLException {
		if (resultSet != null) {
			resultSet.getStatement().close();
			resultSet.close();
			DatabaseReadManager.getValueManager().releaseDatabaseConnection();
		}
	}
}