package model.databaseObjects.stockObjects;
import model.databaseObjects.DatabaseObject;

/*
 * Struct-like object Class for Groups
 */
public class ConsumableMaterial extends Material {

	public ConsumableMaterial(int id, String title, String description, DatabaseObject.StockObjectType type, int totalVolume, int batchSize) {
		super(id, title, description, type, totalVolume, batchSize);
	}

}
