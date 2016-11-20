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
	 * @param String sql
	 */
	public ResultSet executeQuery(String sql) throws SQLException {
		Connection connection = this.manager.getDatabaseConnection();
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sql);
		// statement.close();
		// this.manager.releaseDatabaseConnection();
		return result;
	}
	
	/**
	 * @param String sql
	 */
	public int executeUpdate(String sql) throws SQLException {
		Connection connection = this.manager.getDatabaseConnection();
		Statement statement = connection.createStatement();
		int result = statement.executeUpdate(sql);
		statement.close();
		this.manager.releaseDatabaseConnection();
		return result;
	}
	
	/**
	 * @param String sql
	 */
	public Boolean execute(String sql) throws SQLException {
		Connection connection = this.manager.getDatabaseConnection();
		Statement statement = connection.createStatement();
		Boolean result = statement.execute(sql);
		statement.close();
		this.manager.releaseDatabaseConnection();
		return result;
	}
	
	public void releaseDatabaseConnection() throws SQLException {
		this.manager.releaseDatabaseConnection();
	}
}
