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

package model;
import model.databaseObjects.DatabaseObject;
import model.databaseObjects.accessControl.GroupRight;
import model.databaseObjects.accessControl.User;

public class Session {
	private static Session sharedInstance;
	public static User currentUser;
	public static model.databaseObjects.accessControl.GroupRight[] currentRights;

	public enum PossibleGroupRight {
		empty,
		login,
		editSelf,
		createUser,
		deleteUser,
		editUser,
		viewUsers,
		createGroup,
		deleteGroup,
		editGroup,
		viewGroups,
		createDevice,
		deleteDevice,
		editDevice,
		viewDevices,
		deviceIncrease,
		deviceDecrease,
		deviceCorrection,
		createMedicalMaterial,
		deleteMedicalMaterial,
		editMedicalMaterial,
		viewMedicalMaterials,
		medicalMaterialIncrease,
		medicalMaterialDecrease,
		medicalMaterialCorrection,
		createConsumableMaterial,
		deleteConsumableMaterial,
		editConsumableMaterial,
		viewConsumableMaterials,
		consumableMaterialIncrease,
		consumableMaterialDecrease,
		consumableMaterialCorrection,
		createLocation,
		deleteLocation,
		editLocation,
		viewLocations
	}

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
	 * @param possibleGroupRight PossibleGroupRight
	 * @return Boolean
	 *
	 * Check if the current user has the right to acces a given GroupRight
	 */
	public static Boolean currentUserCanHandleGroupRight(Session.PossibleGroupRight possibleGroupRight) {
		int groupRightID = possibleGroupRight.ordinal();
		for (GroupRight right : Session.getSharedInstance().currentRights) {
			if (right.id == groupRightID) {
				return true;
			}
		}
		return false;
	}

	public static Boolean currentUserCanAccessInventory() {
		Session instance = Session.getSharedInstance();
		if (instance.currentUserCanHandleGroupRight(Session.PossibleGroupRight.viewDevices)
				|| instance.currentUserCanHandleGroupRight(Session.PossibleGroupRight.viewConsumableMaterials)
				|| instance.currentUserCanHandleGroupRight(Session.PossibleGroupRight.viewMedicalMaterials)) {
			return true;
		}
		return false;
	}

	public static Boolean currentUserCanAddData() {
		Session instance = Session.getSharedInstance();
		if (instance.currentUserCanHandleGroupRight(Session.PossibleGroupRight.createDevice)
				|| instance.currentUserCanHandleGroupRight(Session.PossibleGroupRight.createConsumableMaterial)
				|| instance.currentUserCanHandleGroupRight(Session.PossibleGroupRight.createMedicalMaterial)
				|| instance.currentUserCanHandleGroupRight(Session.PossibleGroupRight.createLocation)
				|| instance.currentUserCanHandleGroupRight(Session.PossibleGroupRight.createGroup)
				|| instance.currentUserCanHandleGroupRight(Session.PossibleGroupRight.createUser) ) {
			return true;
		}
		return false;
	}

	public static Boolean currentUserCanEditData() {
		Session instance = Session.getSharedInstance();
		if (instance.currentUserCanHandleGroupRight(Session.PossibleGroupRight.editDevice)
				|| instance.currentUserCanHandleGroupRight(Session.PossibleGroupRight.editConsumableMaterial)
				|| instance.currentUserCanHandleGroupRight(Session.PossibleGroupRight.editMedicalMaterial)
				|| instance.currentUserCanHandleGroupRight(Session.PossibleGroupRight.editLocation)
				|| instance.currentUserCanHandleGroupRight(Session.PossibleGroupRight.editGroup)
				|| instance.currentUserCanHandleGroupRight(Session.PossibleGroupRight.editUser) ) {
			return true;
		}
		return false;
	}

	public static Boolean currentUserCanDeleteData() {
		Session instance = Session.getSharedInstance();
		if (instance.currentUserCanHandleGroupRight(Session.PossibleGroupRight.deleteDevice)
				|| instance.currentUserCanHandleGroupRight(Session.PossibleGroupRight.deleteConsumableMaterial)
				|| instance.currentUserCanHandleGroupRight(Session.PossibleGroupRight.deleteMedicalMaterial)
				|| instance.currentUserCanHandleGroupRight(Session.PossibleGroupRight.deleteLocation)
				|| instance.currentUserCanHandleGroupRight(Session.PossibleGroupRight.deleteGroup)
				|| instance.currentUserCanHandleGroupRight(Session.PossibleGroupRight.deleteUser) ) {
			return true;
		}
		return false;
	}

	public static Boolean currentUserCanChangeDeviceStock() {
		Session instance = Session.getSharedInstance();
		if (instance.currentUserCanHandleGroupRight(PossibleGroupRight.deviceIncrease)
				|| instance.currentUserCanHandleGroupRight(PossibleGroupRight.deviceDecrease)
				|| instance.currentUserCanHandleGroupRight(PossibleGroupRight.deviceCorrection) ) {
			return true;
		}
		return false;
	}

	public static Boolean currentUserCanChangeMedicalMaterialStock() {
		Session instance = Session.getSharedInstance();
		if (instance.currentUserCanHandleGroupRight(PossibleGroupRight.medicalMaterialIncrease)
				|| instance.currentUserCanHandleGroupRight(PossibleGroupRight.medicalMaterialDecrease)
				|| instance.currentUserCanHandleGroupRight(PossibleGroupRight.medicalMaterialCorrection) ) {
			return true;
		}
		return false;
	}

	public static Boolean currentUserCanChangeConsumableMaterialStock() {
		Session instance = Session.getSharedInstance();
		if (instance.currentUserCanHandleGroupRight(PossibleGroupRight.consumableMaterialIncrease)
				|| instance.currentUserCanHandleGroupRight(PossibleGroupRight.consumableMaterialDecrease)
				|| instance.currentUserCanHandleGroupRight(PossibleGroupRight.consumableMaterialCorrection) ) {
			return true;
		}
		return false;
	}
}
