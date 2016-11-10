package model.databaseCommunication;

public class DatabaseLoginManager {
	private String databaseUsername;
	private String databasePassword;
	private String databaseURL;
	
	/**
	 * Constructor
	 */
	public DatabaseLoginManager() {
		this.loadDatabaseLoginCredentials();
	}
	
	/**
	 * @param String usename, String password, String url
	 * @return Boolean
	 */
	public DatabaseLoginManager(String username, String password, String url) {
		this.databaseUsername = username;
		this.databasePassword = password;
		this.databaseURL = url;
		this.storeDatabaseLoginCredentials();
	}
	
	/**
	 * @param String username
	 */
	public void setDatabaseUsername(String username) {
		this.databaseUsername = username;
		this.storeDatabaseLoginCredentials();
	}
	
	/**
	 * @param String password
	 */
	public void setPassword(String password) {
		this.databasePassword = password;
		this.storeDatabaseLoginCredentials();
	}
	
	/**
	 * @param String url
	 */
	public void setURL(String url) {
		this.databaseURL = url;
		this.storeDatabaseLoginCredentials();
	}
	
	/**
	 * @return String username
	 */
	public String getUsername() {
		if (this.databaseUsername == null) {
			this.loadDatabaseLoginCredentials();
		}
		return this.databaseUsername;
	}
	
	/**
	 * @return String password
	 */
	public String getPassword() {
		if (this.databasePassword == null) {
			this.loadDatabaseLoginCredentials();
		}
		return this.databasePassword;
	}
	
	/**
	 * @return String URL
	 */
	public String getURL() {
		if (this.databaseURL == null) {
			this.loadDatabaseLoginCredentials();
		}
		return this.databaseURL;
	}
	
	/**
	 * Test credentials for Login
	 */
	public Boolean testDatabaseConnection() {
		if (this.databaseUsername != null && this.databasePassword != null && this.databaseURL != null) {
			// TODO: do connection test
			return true;
		}
		return false;
	}
	
	/**
	 * Store the given Credentials into local file
	 */
	private void storeDatabaseLoginCredentials() {
		// TODO: Store Credentials into File.
	}
	
	/**
	 * Read the Credentials from a local file
	 */
	private void loadDatabaseLoginCredentials() {
		// TODO: Load Credentials from File.
	}
	
}