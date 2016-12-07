import model.databaseCommunication.DatabaseLoginManager;
import presenter.onboarding.LoginPresenter;
import presenter.onboarding.SetupPresenter;

public class main {

	public static void main(String[] args) {
		
		DatabaseLoginManager dbloginManager = new DatabaseLoginManager();
		if (dbloginManager.testDatabaseConnection()) {
			System.out.println("Database Connection established.");
			LoginPresenter loginPresenter = new LoginPresenter();
			loginPresenter.newScreen();
		} else {
			System.out.println("Can't connect to database using the given credentials.");
			SetupPresenter setupPresenter = new SetupPresenter();
			setupPresenter.newScreen();
		}
	}
}
