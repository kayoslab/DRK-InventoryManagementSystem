package model.databaseObjects;
/*
 * Abstract Struct-like object Class for StockObjects
 *  
 */
public abstract class StockObject {
	public enum Type {
		consumableMaterial,
		medicalMaterial,
		device,
		vehicle
	}
	
	public final int id;
	public final String title;
	public final String description;
	public final int minimumStock;
	public final int quotaStock;
	public final Boolean silencedWarnings;
	public final Type type;

	public StockObject(int id, String title, String description, int minimumStock, int quotaStock, Boolean silencedWarnings, Type type) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.minimumStock = minimumStock;
		this.quotaStock = quotaStock;
		this.silencedWarnings = silencedWarnings;
		this.type = type;
	}
}
