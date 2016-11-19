import model.databaseCommunication.DatabaseLoginManager;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DatabaseLoginManager dbloginManager = new DatabaseLoginManager();
		if (dbloginManager.testDatabaseConnection()) {
			// TODO: Show LoginPresenter
			System.out.println("Database-Connection established.");
		} else {
			// TODO: Show SetupPresenter baehm
			System.out.println("Can't connect to database using the given credentials.");
		}
	}
}
