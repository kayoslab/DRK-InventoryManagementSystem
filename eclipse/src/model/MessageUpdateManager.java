package model;

import model.databaseObjects.DatabaseObject;
import model.databaseObjects.stockObjects.ConsumableMaterial;
import model.databaseObjects.stockObjects.MedicalMaterial;
import model.databaseObjects.stockObjects.StockObject;
import model.databaseObjects.stockValues.ConsumableMaterialValue;
import model.databaseObjects.stockValues.DeviceValue;
import model.databaseObjects.stockValues.MedicalMaterialValue;
import model.databaseObjects.stockValues.StockObjectValue;
import java.util.Calendar;
import java.sql.Date;

public class MessageUpdateManager {
	
	public void updateAll(){
		StockObjectValue[] greenDeviceValues = DatabaseReadManager.getStockObjectValues(DatabaseObject.StockObjectType.device, DatabaseObject.StockValueMessage.green);
		StockObjectValue[] greenMedMatValues = DatabaseReadManager.getStockObjectValues(DatabaseObject.StockObjectType.medicalMaterial, DatabaseObject.StockValueMessage.green);
		StockObjectValue[] greenConsMatValues = DatabaseReadManager.getStockObjectValues(DatabaseObject.StockObjectType.consumableMaterial, DatabaseObject.StockValueMessage.green);
		StockObjectValue[] yellowDeviceValues = DatabaseReadManager.getStockObjectValues(DatabaseObject.StockObjectType.device, DatabaseObject.StockValueMessage.yellow);
		StockObjectValue[] yellowMedMatValues = DatabaseReadManager.getStockObjectValues(DatabaseObject.StockObjectType.medicalMaterial, DatabaseObject.StockValueMessage.yellow);
		StockObjectValue[] yellowConsMatValues = DatabaseReadManager.getStockObjectValues(DatabaseObject.StockObjectType.consumableMaterial, DatabaseObject.StockValueMessage.yellow);
		StockObjectValue[] redDeviceValues = DatabaseReadManager.getStockObjectValues(DatabaseObject.StockObjectType.device, DatabaseObject.StockValueMessage.red);
		StockObjectValue[] redMedMatValues = DatabaseReadManager.getStockObjectValues(DatabaseObject.StockObjectType.medicalMaterial, DatabaseObject.StockValueMessage.red);
		StockObjectValue[] redConsMatValues = DatabaseReadManager.getStockObjectValues(DatabaseObject.StockObjectType.consumableMaterial, DatabaseObject.StockValueMessage.red);
				
		this.compare(greenDeviceValues);
		this.compare(greenMedMatValues);
		this.compare(greenConsMatValues);
		this.compare(yellowDeviceValues);
		this.compare(yellowMedMatValues);
		this.compare(yellowConsMatValues);
		this.compare(redDeviceValues);
		this.compare(redMedMatValues);
		this.compare(redConsMatValues);
	}
	
	private void compare(StockObjectValue[] stockObjectValues) {
		for (StockObjectValue stockValue:stockObjectValues) {
			
			Calendar currenttime = Calendar.getInstance();
		    Date sqlDate = new Date((currenttime.getTime()).getTime());
			Calendar currenttimeThreeMonths = Calendar.getInstance();
			currenttimeThreeMonths.add(Calendar.MONTH,3);
			Date sqlDateThreeMonths = new Date((currenttimeThreeMonths.getTime()).getTime());
			
			if (stockValue instanceof DeviceValue) {
				DeviceValue deviceValue = (DeviceValue) stockValue; 	
				
				if (sqlDate.after(deviceValue.mtkDate) || sqlDate.after(deviceValue.stkDate)){    
					deviceValue.messageID = DatabaseObject.StockValueMessage.red.ordinal();
					deviceValue.editObject();					
				} else if(deviceValue.mtkDate.after(sqlDate) 
						&& ((deviceValue.mtkDate.before(sqlDateThreeMonths)) || (deviceValue.stkDate.before(sqlDateThreeMonths)))){
					deviceValue.messageID = DatabaseObject.StockValueMessage.yellow.ordinal();
					deviceValue.editObject();		
				} else {
					deviceValue.messageID = DatabaseObject.StockValueMessage.green.ordinal();
					deviceValue.editObject();					
				}
			} else if (stockValue instanceof MedicalMaterialValue) {
				MedicalMaterialValue medMatValue = (MedicalMaterialValue) stockValue;  
				StockObject stockobj = DatabaseReadManager.getStockObject(stockValue.stockObjectID);
				MedicalMaterial medmat = (MedicalMaterial) stockobj;
				
				if ((sqlDate.after(medMatValue.date) || medmat.totalVolume < medmat.minimumStock)){    
					medMatValue.messageID = DatabaseObject.StockValueMessage.red.ordinal();
					medMatValue.editObject();					
				} else if ((medMatValue.date.after(sqlDate) && medMatValue.date.before(sqlDateThreeMonths))
						|| (medmat.totalVolume < medmat.quotaStock)){
					medMatValue.messageID = DatabaseObject.StockValueMessage.yellow.ordinal();
					medMatValue.editObject();		
				} else {
					medMatValue.messageID = DatabaseObject.StockValueMessage.green.ordinal();
					medMatValue.editObject();					
				}				
			} else if (stockValue instanceof ConsumableMaterialValue) {
				ConsumableMaterialValue consMatValue = (ConsumableMaterialValue) stockValue;  
				StockObject stockobj = DatabaseReadManager.getStockObject(stockValue.stockObjectID);
				ConsumableMaterial consmat = (ConsumableMaterial) stockobj;
				
				if ((sqlDate.after(consMatValue.date) || consmat.totalVolume < consmat.minimumStock)){    
					consMatValue.messageID = DatabaseObject.StockValueMessage.red.ordinal();
					consMatValue.editObject();					
				} else if ((consMatValue.date.after(sqlDate) && consMatValue.date.before(sqlDateThreeMonths))
						|| (consmat.totalVolume < consmat.quotaStock)){
					consMatValue.messageID = DatabaseObject.StockValueMessage.yellow.ordinal();
					consMatValue.editObject();		
				} else {
					consMatValue.messageID = DatabaseObject.StockValueMessage.green.ordinal();
					consMatValue.editObject();					
				}				
			}
		}
	}
}
