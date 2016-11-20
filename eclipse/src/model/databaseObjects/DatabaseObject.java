package model.databaseObjects;
/*
 * Abstract Struct-like object Class for all Values 
 * which can be saved in the database.
 */
public abstract class DatabaseObject {
	public enum StockObjectType {
		empty,
		device,
		medicalMaterial,
		consumableMaterial,
		vehicle
	}
	
	public enum StockValueMessage {
		green,
		yellow,
		orange,
		red
	}
	
	public final int id;
	public DatabaseObject(int id) {
		this.id = id;
	}

}
