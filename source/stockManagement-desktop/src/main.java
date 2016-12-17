/*
 * Copyright (c) - All Rights Reserved
 *
 * Unauthorized copying of these files, via any medium is
 * strictly prohibited Proprietary and confidential
 *
 * NOTICE:
 * All information contained in this project is, and remains the property of the owner and its suppliers, if any.
 * The intellectual and technical concepts contained herein are proprietary to the owner and its suppliers and
 * are protected by trade secret or copyright law. Dissemination of this information or reproduction of this
 * material is strictly forbidden unless prior written permission is obtained by the owner.
 *
 * @author
 *
 */

import model.databaseCommunication.DatabaseLoginManager;
import presenter.onboarding.LoginPresenter;
import presenter.onboarding.SetupPresenter;

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
