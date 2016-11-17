package model.databaseObjects.stockValues;
import model.databaseObjects.DatabaseObject;
/*
 * Abstract Struct-like object Class for StockObject StockValues
 */
public abstract class StockObjectValue extends DatabaseObject {
	public final int volume;
	
	
	public StockObjectValue(int id, int volume) {
		super(id);
		this.volume = volume;
	}

}
