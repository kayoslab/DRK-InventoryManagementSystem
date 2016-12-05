package model;

import model.databaseObjects.DatabaseObject;
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
		Session.getSharedInstance().currentUser = null;
		Session.getSharedInstance().currentRights = null;
		Session.sharedInstance = null;
	}

	/**
	 *
	 * @param groupRight DatabaseObject.GroupRight
	 * @return Boolean
	 *
	 * Check if the current user has the right to acces a given GroupRight
	 */
	public static Boolean currentUserCanHandleGroupRight(DatabaseObject.GroupRight groupRight) {
		int groupRightID = groupRight.ordinal();
		for (GroupRight right : Session.getSharedInstance().currentRights) {
			if (right.id == groupRightID) {
				return true;
			}
		}
		return false;
	}

	public static Boolean currentUserCanAccessInventory() {
		Session instance = Session.getSharedInstance();
		if (instance.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.viewDevices) || instance.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.viewConsumableMaterials) || instance.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.viewMedicalMaterials)) {
			return true;
		}
		return false;
	}
}
