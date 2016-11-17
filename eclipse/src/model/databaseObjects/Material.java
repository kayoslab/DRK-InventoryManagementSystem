package model.databaseObjects;

public class Material extends StockObject {
	public final int batchSize;
	public final int minimumStock;
	public final int quotaStock;
	
	public Material(int id, String title, String description, Boolean silencedWarnings, Type type,
					int batchSize, int minimumStock, int quotaStock) {
		super(id, title, description, silencedWarnings, type);
		// TODO Auto-generated constructor stub
		this.batchSize = batchSize;
		this.minimumStock = minimumStock;
		this.quotaStock = quotaStock;
	}

}
