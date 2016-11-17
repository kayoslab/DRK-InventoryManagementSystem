package model.databaseObjects;
/*
 * Struct-like object Class for StockObjects 
 * Extends Abstract Class StockObject
 *  
 */
public class Device extends StockObject {
	public final String serailNumber;
	public final String inventoryNumber;
	public final String umdns;
	public final int mtkIntervall;
	public final int stkIntervall;
	// public final Date mtkDate;
	// public final Date stkDate;
		
	public Device(int id, String title, String description, Boolean silencedWarnings, Type type,
				  String serialNumber, String inventoryNumber, String umdns, int mtkIntervall, int stkIntervall) { // Date mtkDate, Date stkDate) {
		// call super Constructor
		super(id, title, description, silencedWarnings, type);
		
		this.serailNumber = serialNumber;
		this.inventoryNumber = inventoryNumber;
		this.umdns = umdns;
		this.mtkIntervall = mtkIntervall;
		this.stkIntervall = stkIntervall;
		// this.mtkDate = mtkDate
		// this.stkDate = stkDate
	}

}
