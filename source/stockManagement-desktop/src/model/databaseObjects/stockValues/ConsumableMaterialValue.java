package model.databaseObjects.stockValues;
import java.util.Date;
/*
 * Struct-like object Class for ConsumableMaterial StockValues
 */
public class ConsumableMaterialValue extends MaterialValue {

	public ConsumableMaterialValue(int id, int volume, Boolean silencedWarnings, int stockObjectID, int locationID, int messageID, String batchNumber, Date date, int minimumStock, int quotaStock) {
		super(id, volume, silencedWarnings, stockObjectID, locationID, messageID, batchNumber, date, minimumStock, quotaStock);
	}

}
