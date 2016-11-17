package model.databaseObjects.stockObjects;
/*
 * Struct-like object Class for Groups
 */
public class MedicalMaterial extends StockObject {
	public final int batchSize;
	public final int minimumStock;
	public final int quotaStock;
	
	public MedicalMaterial(int id, String title, String description, Boolean silencedWarnings, Type type,
					int batchSize, int minimumStock, int quotaStock) {
		super(id, title, description, silencedWarnings, type);
		this.batchSize = batchSize;
		this.minimumStock = minimumStock;
		this.quotaStock = quotaStock;
	}

}
