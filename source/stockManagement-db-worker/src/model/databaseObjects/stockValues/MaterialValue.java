package model.databaseObjects.stockValues;
import java.util.Date;

public class MaterialValue extends StockObjectValue {
	public String batchNumber;
	public Date date;
	
	public MaterialValue(int id, int volume, int stockObjectID, int locationID, int messageID, String batchNumber, Date date) {
		super(id, volume, stockObjectID, locationID, messageID);
		this.batchNumber = batchNumber;
		this.date = date;
	}

}
