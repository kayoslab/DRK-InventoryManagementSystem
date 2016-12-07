package model.databaseObjects.stockValues;
import java.util.Date;

/*
 * Struct-like object Class for MedicalMaterial StockValues
 */
public class MedicalMaterialValue extends MaterialValue {

	public MedicalMaterialValue(int id, int volume, int stockObjectID, int locationID, int messageID, String batchNumber, Date date) {
		super(id, volume, stockObjectID, locationID, messageID, batchNumber, date);
	}

}
