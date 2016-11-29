package model.databaseObjects.stockObjects;
import model.databaseObjects.DatabaseObject;

/*
 * Struct-like object Class for StockObjects 
 * Extends Abstract Class StockObject
 */
public class Device extends StockObject {
	
	public int mtkIntervall;
	public int stkIntervall;
		
	public Device(int id, String title, String description, Boolean silencedWarnings, DatabaseObject.StockObjectType type, int totalVolume,
				  int mtkIntervall, int stkIntervall) { 
		super(id, title, description, silencedWarnings, type, totalVolume);
		
		this.mtkIntervall = mtkIntervall;
		this.stkIntervall = stkIntervall;
	}

}
