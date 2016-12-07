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
		if (instance.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.viewDevices)
				|| instance.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.viewConsumableMaterials)
				|| instance.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.viewMedicalMaterials)) {
			return true;
		}
		return false;
	}

	public static Boolean currentUserCanAddData() {
		Session instance = Session.getSharedInstance();
		if (instance.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.createDevice)
				|| instance.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.createConsumableMaterial)
				|| instance.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.createMedicalMaterial)
				|| instance.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.createLocation)
				|| instance.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.createGroup)
				|| instance.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.createUser) ) {
			return true;
		}
		return false;
	}

	public static Boolean currentUserCanEditData() {
		Session instance = Session.getSharedInstance();
		if (instance.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.editDevice)
				|| instance.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.editConsumableMaterial)
				|| instance.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.editMedicalMaterial)
				|| instance.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.editLocation)
				|| instance.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.editGroup)
				|| instance.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.editUser) ) {
			return true;
		}
		return false;
	}

	public static Boolean currentUserCanDeleteData() {
		Session instance = Session.getSharedInstance();
		if (instance.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.deleteDevice)
				|| instance.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.deleteConsumableMaterial)
				|| instance.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.deleteMedicalMaterial)
				|| instance.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.deleteLocation)
				|| instance.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.deleteGroup)
				|| instance.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.deleteUser) ) {
			return true;
		}
		return false;
	}
}
