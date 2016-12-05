package model.databaseCommunication;

import model.databaseObjects.accessControl.GroupRight;
import model.databaseObjects.accessControl.User;

public class Session {
	private static Session sharedInstance;
	public static User currentUser;
	public static GroupRight[] currentRights;
	private Session() {

	}

	public static Session getSharedInstance() {
		if (Session.sharedInstance == null) {
			Session.sharedInstance = new Session();
		}
		return Session.sharedInstance;
	}

	public static void invalidateSession() {
		Session.sharedInstance.currentUser = null;
		Session.sharedInstance.currentRights = null;
		Session.sharedInstance = null;
	}
}
