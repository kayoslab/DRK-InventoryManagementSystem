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
