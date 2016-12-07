package model.databaseObjects.stockValues;
import java.util.Date;
/*
 * Struct-like object Class for ConsumableMaterial StockValues
 */
public class ConsumableMaterialValue extends MaterialValue {

	public ConsumableMaterialValue(int id, int volume, int stockObjectID, int locationID, int messageID, String batchNumber, Date date) {
		super(id, volume, stockObjectID, locationID, messageID, batchNumber, date);
	}

}
