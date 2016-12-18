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
 * @author Chris
 *
 */

package model.databaseObjects.stockObjects;
import model.databaseObjects.DatabaseObject;
/*
 * Abstract Struct-like object Class for StockObjects
 */
public abstract class StockObject extends DatabaseObject {

	public String title;
	public String description;

	public DatabaseObject.StockObjectType type;
	public int totalVolume;

	public StockObject(int id, String title, String description, DatabaseObject.StockObjectType type, int totalVolume) {
		super(id);
		this.title = title;
		this.description = description;
		this.type = type;
		this.totalVolume = totalVolume;
	}
}
