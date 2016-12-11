import model.MessageUpdateManager;
import model.databaseCommunication.DatabaseLoginManager;

public class main {
	public static void main(String[] args) {
		DatabaseLoginManager dbloginManager = new DatabaseLoginManager();
		if (dbloginManager.testDatabaseConnection()) {
			System.out.println("Connected to MySQL Server at: " + dbloginManager.getURL());
			MessageUpdateManager msgupdateManager = new MessageUpdateManager();
			msgupdateManager.updateAll();
		} else {
			System.out.println("Can't connect to MySQL Server.");
		}
	}
}