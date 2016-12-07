package model;

import model.databaseObjects.DatabaseObject;
import model.databaseObjects.stockObjects.*;
import model.databaseObjects.stockValues.ConsumableMaterialValue;
import model.databaseObjects.stockValues.DeviceValue;
import model.databaseObjects.stockValues.MedicalMaterialValue;
import model.databaseObjects.stockValues.StockObjectValue;
import java.util.Calendar;
import java.sql.Date;

public class MessageUpdateManager {

	public void updateAll(){
		StockObjectValue[] greenValues = DatabaseReadManager.getStockObjectValues(DatabaseObject.StockValueMessage.green);
		StockObjectValue[] yellowValues = DatabaseReadManager.getStockObjectValues(DatabaseObject.StockValueMessage.yellow);
		StockObjectValue[] redValues = DatabaseReadManager.getStockObjectValues(DatabaseObject.StockValueMessage.red);

		this.compare(greenValues);
		this.compare(yellowValues);
		this.compare(redValues);
	}

	private void compare(StockObjectValue[] stockObjectValues) {
		if (stockObjectValues != null) {
			for (StockObjectValue stockValue:stockObjectValues) {
				Calendar currenttime = Calendar.getInstance();
				Date sqlDate = new Date((currenttime.getTime()).getTime());
				Calendar currenttimeThreeMonths = Calendar.getInstance();
				currenttimeThreeMonths.add(Calendar.MONTH,3);
				Date sqlDateThreeMonths = new Date((currenttimeThreeMonths.getTime()).getTime());
				Boolean edited = false;
				if (stockValue instanceof DeviceValue) {
					DeviceValue deviceValue = (DeviceValue) stockValue;

					if (sqlDate.after(deviceValue.mtkDate) || sqlDate.after(deviceValue.stkDate)){
						// Only Update this Value if it changes.
						if (deviceValue.messageID != DatabaseObject.StockValueMessage.red.ordinal()) {
							deviceValue.messageID = DatabaseObject.StockValueMessage.red.ordinal();
							edited = deviceValue.editObject();
						}
					} else if(deviceValue.mtkDate.after(sqlDate) && ((deviceValue.mtkDate.before(sqlDateThreeMonths))
							|| (deviceValue.stkDate.before(sqlDateThreeMonths)))){
						// Only Update this Value if it changes.
						if (deviceValue.messageID != DatabaseObject.StockValueMessage.yellow.ordinal()) {
							deviceValue.messageID = DatabaseObject.StockValueMessage.yellow.ordinal();
							edited = deviceValue.editObject();
						}
					} else {
						// Only Update this Value if it changes.
						if (deviceValue.messageID != DatabaseObject.StockValueMessage.green.ordinal()) {
							deviceValue.messageID = DatabaseObject.StockValueMessage.green.ordinal();
							edited = deviceValue.editObject();
						}
					}
				} else if (stockValue instanceof MedicalMaterialValue) {
					MedicalMaterialValue medMatValue = (MedicalMaterialValue) stockValue;
					StockObject stockobj = DatabaseReadManager.getStockObject(stockValue.stockObjectID);
					MedicalMaterial medmat = (MedicalMaterial) stockobj;
					if ((sqlDate.after(medMatValue.date) || medmat.totalVolume < medmat.minimumStock)){
						// Only Update this Value if it changes.
						if (medMatValue.messageID != DatabaseObject.StockValueMessage.red.ordinal()) {
							medMatValue.messageID = DatabaseObject.StockValueMessage.red.ordinal();
							edited = medMatValue.editObject();
						}
					} else if ((medMatValue.date.after(sqlDate) && medMatValue.date.before(sqlDateThreeMonths))
							|| (medmat.totalVolume < medmat.quotaStock)){
						// Only Update this Value if it changes.
						if (medMatValue.messageID != DatabaseObject.StockValueMessage.yellow.ordinal()) {
							medMatValue.messageID = DatabaseObject.StockValueMessage.yellow.ordinal();
							edited = medMatValue.editObject();
						}
					} else {
						// Only Update this Value if it changes.
						if (medMatValue.messageID != DatabaseObject.StockValueMessage.green.ordinal()) {
							medMatValue.messageID = DatabaseObject.StockValueMessage.green.ordinal();
							edited = medMatValue.editObject();
						}
					}
				} else if (stockValue instanceof ConsumableMaterialValue) {
					ConsumableMaterialValue consMatValue = (ConsumableMaterialValue) stockValue;
					StockObject stockobj = DatabaseReadManager.getStockObject(stockValue.stockObjectID);
					ConsumableMaterial consmat = (ConsumableMaterial) stockobj;
					if ((sqlDate.after(consMatValue.date) || consmat.totalVolume < consmat.minimumStock)){
						// Only Update this Value if it changes.
						if (consMatValue.messageID != DatabaseObject.StockValueMessage.red.ordinal()) {
							consMatValue.messageID = DatabaseObject.StockValueMessage.red.ordinal();
							edited = consMatValue.editObject();
						}
					} else if ((consMatValue.date.after(sqlDate) && consMatValue.date.before(sqlDateThreeMonths))
							|| (consmat.totalVolume < consmat.quotaStock)){
						// Only Update this Value if it changes.
						if (consMatValue.messageID != DatabaseObject.StockValueMessage.yellow.ordinal()) {
							consMatValue.messageID = DatabaseObject.StockValueMessage.yellow.ordinal();
							edited = consMatValue.editObject();
						}
					} else {
						// Only Update this Value if it changes.
						if (consMatValue.messageID != DatabaseObject.StockValueMessage.green.ordinal()) {
							consMatValue.messageID = DatabaseObject.StockValueMessage.green.ordinal();
							edited = consMatValue.editObject();
						}
					}
				}
				if (edited) {
					System.out.println("Succesfully edited: id "+ stockValue.stockObjectID);
				}
			}
		}
	}
}
