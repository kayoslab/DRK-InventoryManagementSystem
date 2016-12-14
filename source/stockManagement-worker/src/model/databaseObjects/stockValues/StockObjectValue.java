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
