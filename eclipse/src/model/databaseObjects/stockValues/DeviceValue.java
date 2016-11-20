package model.databaseObjects.stockValues;
import java.util.Date;
/*
 * Struct-like object Class for Device StockValues
 */
public class DeviceValue extends StockObjectValue {
	public final Date mtkDate;
	public final Date stkDate;
	public final String serialNumber;
	public final String inventoryNumber;
	public final String umdns;
	
	public DeviceValue(int id, int volume, Date mtkDate, Date stkDate, int stockObjectID, int locationID, int messageID,
			String serialNumber, String inventoryNumber, String umdns) {
		super(id, volume, stockObjectID, locationID, messageID);
		this.mtkDate = mtkDate;
		this.stkDate = stkDate;
		this.serialNumber = serialNumber;
		this.inventoryNumber = inventoryNumber;
		this.umdns = umdns;
	}

}
