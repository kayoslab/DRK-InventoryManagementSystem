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
/*
 * Struct-like object Class for Device StockValues
 */
public class DeviceValue extends StockObjectValue {
	public Date mtkDate;
	public Date stkDate;
	public String serialNumber;
	public String inventoryNumber;
	public String umdns;
	
	public DeviceValue(int id, int volume, Boolean silencedWarnings, Date mtkDate, Date stkDate, int stockObjectID, int locationID, int messageID,
			String serialNumber, String inventoryNumber, String umdns) {
		super(id, volume, silencedWarnings, stockObjectID, locationID, messageID);
		this.mtkDate = mtkDate;
		this.stkDate = stkDate;
		this.serialNumber = serialNumber;
		this.inventoryNumber = inventoryNumber;
		this.umdns = umdns;
	}

}
