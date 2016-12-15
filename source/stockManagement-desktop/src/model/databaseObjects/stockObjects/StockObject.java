package model.databaseObjects.stockObjects;
import model.databaseObjects.DatabaseObject;
/*
 * Abstract Struct-like object Class for StockObjects
 */
public abstract class StockObject extends DatabaseObject {
	
	public String title;
	public String description;

	public DatabaseObject.StockObjectType type;
	public int totalVolume;

	public StockObject(int id, String title, String description, DatabaseObject.StockObjectType type, int totalVolume) {
		super(id);
		this.title = title;
		this.description = description;
		this.type = type;
		this.totalVolume = totalVolume;
	}
}
