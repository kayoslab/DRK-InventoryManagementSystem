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
