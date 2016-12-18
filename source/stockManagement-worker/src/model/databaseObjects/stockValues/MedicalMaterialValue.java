/*
 * Copyright (c) - All Rights Reserved
 *
 * Unauthorized copying of these files, via any medium is
 * strictly prohibited Proprietary and confidential
 *
 * NOTICE:
 * All information contained in this project is, and remains the property of the owner and its suppliers, if any.
 * The intellectual and technical concepts contained herein are proprietary to the owner and its suppliers and
 * are protected by trade secret or copyright law. Dissemination of this information or reproduction of this
 * material is strictly forbidden unless prior written permission is obtained by the owner.
 *
 * @author Chris
 *
 */

package model.databaseObjects.stockValues;
import java.util.Date;
/*
 * Struct-like object Class for MedicalMaterial StockValues
 */
public class MedicalMaterialValue extends MaterialValue {

	public MedicalMaterialValue(int id, int volume, Boolean silencedWarnings, int stockObjectID, int locationID, int messageID, String batchNumber, Date date, int minimumStock, int quotaStock) {
		super(id, volume, silencedWarnings, stockObjectID, locationID, messageID, batchNumber, date, minimumStock, quotaStock);
	}

}
