import model.PDFtest;
import model.databaseCommunication.DatabaseLoginManager;
import presenter.onboarding.LoginPresenter;
import presenter.onboarding.SetupPresenter;

import java.io.IOException;

public class main {

	/**
	 * main Function:
	 * tests Database Connection.
	 * Shows Login- or Setup-Presenter
	 *
	 */
	public static void main(String[] args) {
		DatabaseLoginManager dbloginManager = new DatabaseLoginManager();
		if (dbloginManager.testDatabaseConnection()) {
			PDFtest pdFtest = new PDFtest();
			try {
				pdFtest.generatePDF();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

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
