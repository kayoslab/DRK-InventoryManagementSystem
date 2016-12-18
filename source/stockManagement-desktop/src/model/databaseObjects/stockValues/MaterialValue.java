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

package model.databaseObjects.stockValues;
import java.util.Date;

public class MaterialValue extends StockObjectValue {
	public Date date;
	public String batchNumber;
	public int minimumStock;
	public int quotaStock;
	
	public MaterialValue(int id, int volume, Boolean silencedWarnings, int stockObjectID, int locationID, int messageID, String batchNumber, Date date, int minimumStock, int quotaStock) {
		super(id, volume, silencedWarnings, stockObjectID, locationID, messageID);
		this.date = date;
		this.batchNumber = batchNumber;
		this.minimumStock = minimumStock;
		this.quotaStock = quotaStock;
	}

}
