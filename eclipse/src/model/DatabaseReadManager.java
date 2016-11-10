package model;
import model.databaseCommunication.DatabaseValueManager;

public final class DatabaseReadManager {
	
	private DatabaseReadManager() {
		// Do nothing here -> Static implementation
	}
	
	private static DatabaseValueManager getValueManager() {
		return new DatabaseValueManager();
	}
	
	public static void func() {
		DatabaseValueManager valueManager = DatabaseReadManager.getValueManager();
	}
}
