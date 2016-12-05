package model.databaseObjects;

import model.DatabaseWriteManager;

/*
 * Abstract Struct-like object Class for all Values 
 * which can be saved in the database.
 */
public abstract class DatabaseObject {
	public enum StockObjectType {
		empty,
		device,
		medicalMaterial,
		consumableMaterial,
		vehicle
	}
	
	public enum StockValueMessage {
		empty,
		green,
		yellow,
		red
	}

	public enum GroupRight {
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
	
	public final int id;
	public DatabaseObject(int id) {
		this.id = id;
	}

	/**
	 *
	 * Call these instance functions to simply modify the
	 * database objects.
	 */

	public Boolean createObject() {
		return DatabaseWriteManager.createObject(this);
	}

	public Boolean deleteObject() {
		return DatabaseWriteManager.deleteObject(this);
	}

	public Boolean editObject() {
		return DatabaseWriteManager.editObject(this);
	}
}
