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
	public static String[] StockObjectTypeStrings = new String[]{
			"None",
			"Ger\u00e4te",
			"Medizinisches Material",
			"Versorgungsmaterial",
			"Fahrzeuge"
	};

	public enum StockValueMessage {
		empty,
		green,
		yellow,
		red
	}

	public enum ModificationType {
		deviceMenuItem,
		medicalMaterialMenuItem,
		consumableMaterialMenuItem,
		locationMenuItem,
		userMenuItem,
		groupMenuItem
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
