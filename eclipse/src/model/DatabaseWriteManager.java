package model;
import model.databaseCommunication.DatabaseValueManager;

public class DatabaseWriteManager {
	private DatabaseValueManager valueManager;
	
	public DatabaseWriteManager() {
		 this.valueManager = new DatabaseValueManager();
	}
}
