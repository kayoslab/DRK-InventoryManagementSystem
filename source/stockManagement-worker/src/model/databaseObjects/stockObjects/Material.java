package model.databaseObjects.stockObjects;

import model.databaseObjects.DatabaseObject;

public class Material extends StockObject {
	public int batchSize;


	public Material(int id, String title, String description, DatabaseObject.StockObjectType type, int totalVolume, int batchSize) {
		super(id, title, description, type, totalVolume);
		this.batchSize = batchSize;
	}

}
