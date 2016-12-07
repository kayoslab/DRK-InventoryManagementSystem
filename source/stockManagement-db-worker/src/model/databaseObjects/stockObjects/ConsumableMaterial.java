package model.databaseObjects.stockObjects;
import model.databaseObjects.DatabaseObject;

/*
 * Struct-like object Class for Groups
 */
public class ConsumableMaterial extends Material {
	
	public ConsumableMaterial(int id, String title, String description, Boolean silencedWarnings, DatabaseObject.StockObjectType type, int totalVolume, int batchSize, int minimumStock, int quotaStock) {
		super(id, title, description, silencedWarnings, type, totalVolume, batchSize, minimumStock, quotaStock);
	}

}
