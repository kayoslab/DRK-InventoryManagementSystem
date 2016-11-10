package model;
import model.databaseCommunication.DatabaseValueManager;

public class DatabaseReadManager {
	private DatabaseValueManager valueManager;
	
	public DatabaseReadManager() {
		 this.valueManager = new DatabaseValueManager();
	}
}
