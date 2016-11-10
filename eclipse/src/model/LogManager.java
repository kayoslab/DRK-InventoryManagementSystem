package model;
import model.databaseCommunication.DatabaseValueManager;

public class LogManager {
	private DatabaseValueManager valueManager;
	
	public LogManager() {
		this.valueManager = new DatabaseValueManager();
	}
}
