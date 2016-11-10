package model;
import model.databaseCommunication.DatabaseValueManager;

public final class DatabaseWriteManager {
	
	private DatabaseWriteManager() {
		// Do nothing here -> Static implementation
	}

	private static DatabaseValueManager getValueManager() {
		return new DatabaseValueManager();
	}
}
