package model.databaseObjects.stockObjects;
import model.databaseObjects.DatabaseObject;

/*
 * Struct-like object Class for Groups
 */
public class MedicalMaterial extends Material {
	
	public MedicalMaterial(int id, String title, String description, Boolean silencedWarnings, DatabaseObject.StockObjectType type, int totalVolume, int batchSize, int minimumStock, int quotaStock) {
		super(id, title, description, silencedWarnings, type, totalVolume, batchSize, minimumStock, quotaStock);
	}

}
