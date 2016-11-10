package model;
import model.databaseCommunication.DatabaseValueManager;

public final class LogManager {
	
	private LogManager() {
		// Do nothing here -> Static implementation
	}

	private static DatabaseValueManager getValueManager() {
		return new DatabaseValueManager();
	}
}
