package model.databaseObjects.stockObjects;
import model.databaseObjects.DatabaseObject;

/*
 * Struct-like object Class for StockObjects 
 * Extends Abstract Class StockObject
 */
public class Device extends StockObject {
	public final String serailNumber;
	public final String inventoryNumber;
	public final String umdns;
	public final int mtkIntervall;
	public final int stkIntervall;
		
	public Device(int id, String title, String description, Boolean silencedWarnings, DatabaseObject.StockObjectType type,
				  String serialNumber, String inventoryNumber, String umdns, int mtkIntervall, int stkIntervall) { 
		super(id, title, description, silencedWarnings, type);
		
		this.serailNumber = serialNumber;
		this.inventoryNumber = inventoryNumber;
		this.umdns = umdns;
		this.mtkIntervall = mtkIntervall;
		this.stkIntervall = stkIntervall;
	}

}
