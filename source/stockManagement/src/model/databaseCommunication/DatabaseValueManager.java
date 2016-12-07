package model.databaseCommunication;
import java.sql.*;


public class DatabaseValueManager {
	private DatabaseConnectionManager manager;
	
	/**
	 * Constructor
	 */
	public DatabaseValueManager() {
		this.manager = DatabaseConnectionManager.getSharedInstance();
	}
	
	/**
	 * @param sql String
	 */
	public ResultSet executeQuery(String sql) throws SQLException {
		Connection connection = this.manager.getDatabaseConnection();
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sql);
		// manually close these on executeQuerry
		// statement.close();
		// this.manager.releaseDatabaseConnection();
		return result;
	}
	
	/**
	 * @param sql String
	 */
	public int executeUpdate(String sql) throws SQLException {
		Connection connection = this.manager.getDatabaseConnection();
		Statement statement = connection.createStatement();
		int result = statement.executeUpdate(sql);
		statement.close();
		this.manager.releaseDatabaseConnection();
		return result;
	}

	public void releaseDatabaseConnection() throws SQLException {
		this.manager.releaseDatabaseConnection();
	}
}
