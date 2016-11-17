package model.databaseObjects.stockObjects;
import model.databaseObjects.DatabaseObject;
/*
 * Abstract Struct-like object Class for StockObjects
 */
public abstract class StockObject extends DatabaseObject {
	
	public final String title;
	public final String description;
	public final Boolean silencedWarnings;
	public final DatabaseObject.StockObjectType type;
	public final int totalVolume;

	public StockObject(int id, String title, String description, Boolean silencedWarnings, DatabaseObject.StockObjectType type, int totalVolume) {
		super(id);
		this.title = title;
		this.description = description;
		this.silencedWarnings = silencedWarnings;
		this.type = type;
		this.totalVolume = totalVolume;
	}
}
