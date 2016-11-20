import model.databaseCommunication.DatabaseLoginManager;
import model.databaseObjects.environment.Location;
import model.databaseObjects.accessControl.*;
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
			//User user = DatabaseReadManager.getUser("nforbrich");
			//System.out.println(user.firstName);
			GroupRight[] groupRights = new GroupRight[2];
			groupRights[0] = new GroupRight(1,"Admin");
			groupRights[1] = new GroupRight(2,"User");
			boolean test = DatabaseWriteManager.setGroupRights(new Group(4,"",true), 
						groupRights);
			System.out.println(test);
		} else {
			// TODO: Show SetupPresenter
			System.out.println("Can't connect to database using the given credentials.");
		}
	}
}
