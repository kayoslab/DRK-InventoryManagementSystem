import model.MessageUpdateManager;
import model.databaseCommunication.DatabaseLoginManager;

public class main {

	public static void main(String[] args) {
		String username = "worker";
		String password = "";
		String url = "localhost";
		DatabaseLoginManager dbloginManager = new DatabaseLoginManager(username, password, url);
		if (dbloginManager.testDatabaseConnection()) {
			MessageUpdateManager msgupdateManager = new MessageUpdateManager();
			msgupdateManager.updateAll();
		} else {
			System.out.println("Can't connect to MySQL Server.");
		}
	}
}