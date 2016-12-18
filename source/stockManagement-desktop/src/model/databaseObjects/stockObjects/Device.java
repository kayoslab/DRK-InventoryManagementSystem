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

package model.databaseObjects.stockObjects;
import model.databaseObjects.DatabaseObject;

/*
 * Struct-like object Class for StockObjects 
 * Extends Abstract Class StockObject
 */
public class Device extends StockObject {
	
	public int mtkIntervall;
	public int stkIntervall;
		
	public Device(int id, String title, String description, DatabaseObject.StockObjectType type, int totalVolume,
				  int mtkIntervall, int stkIntervall) { 
		super(id, title, description, type, totalVolume);
		
		this.mtkIntervall = mtkIntervall;
		this.stkIntervall = stkIntervall;
	}

}
