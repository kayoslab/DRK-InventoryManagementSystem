import model.databaseCommunication.DatabaseLoginManager;
import model.databaseObjects.accessControl.User;
import model.DatabaseReadManager;
import model.DatabaseWriteManager;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DatabaseLoginManager dbloginManager = new DatabaseLoginManager();
		if (dbloginManager.testDatabaseConnection()) {
			// TODO: Show LoginPresenter
			System.out.println("Database-Connection established.");
			User user = DatabaseReadManager.getUser(1);
			System.out.println(user.firstName);
		} else {
			// TODO: Show SetupPresenter
			System.out.println("Can't connect to database using the given credentials.");
		}
	}
}
