package model.databaseObjects.stockValues;

public class MaterialValue extends StockObjectValue {
	public static String batchNumber;
	
	public MaterialValue(int id, int volume, String batchNumber) {
		super(id, volume);
		this.batchNumber = batchNumber;
	}

}
