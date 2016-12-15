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

package model.databaseObjects.stockValues;
import model.databaseObjects.DatabaseObject;
/*
 * Abstract Struct-like object Class for StockObject StockValues
 */
public abstract class StockObjectValue extends DatabaseObject {
	// not final for merging multiple Objects
	public int volume;
	public Boolean silencedWarnings;
	public int stockObjectID;
	public int locationID;
	public int messageID;

	public StockObjectValue(int id, int volume, Boolean silencedWarnings, int stockObjectID, int locationID, int messageID) {
		super(id);
		this.volume = volume;
		this.silencedWarnings = silencedWarnings;
		this.stockObjectID = stockObjectID;
		this.locationID = locationID;
		this.messageID = messageID;
	}

}
