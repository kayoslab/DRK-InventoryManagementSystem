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
			User user = DatabaseReadManager.getUser("nforbrich");
			System.out.println(user.firstName);
			boolean test = DatabaseReadManager.userDidChangePassword("nforbrich");
			System.out.println(test);
		} else {
			// TODO: Show SetupPresenter
			System.out.println("Can't connect to database using the given credentials.");
		}
	}
}
