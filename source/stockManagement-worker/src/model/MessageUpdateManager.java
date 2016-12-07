package model;

import model.databaseObjects.DatabaseObject;
import model.databaseObjects.stockObjects.*;
import model.databaseObjects.stockValues.ConsumableMaterialValue;
import model.databaseObjects.stockValues.DeviceValue;
import model.databaseObjects.stockValues.MedicalMaterialValue;
import model.databaseObjects.stockValues.StockObjectValue;
import model.mailing.Sender;

import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;

public class MessageUpdateManager {
	private ArrayList<StockObjectValue> deviceUpdateList = new ArrayList<StockObjectValue>();
	private ArrayList<StockObjectValue> medicalMaterialUpdateList = new ArrayList<StockObjectValue>();
	private ArrayList<StockObjectValue> consumableMaterialUpdateList = new ArrayList<StockObjectValue>();
	// private ArrayList<StockObjectValue> vehicleUpdateList = new ArrayList<StockObjectValue>();

	public void updateAll() {
		StockObjectValue[] greenValues = DatabaseReadManager.getStockObjectValues(DatabaseObject.StockValueMessage.green);
		StockObjectValue[] yellowValues = DatabaseReadManager.getStockObjectValues(DatabaseObject.StockValueMessage.yellow);
		StockObjectValue[] redValues = DatabaseReadManager.getStockObjectValues(DatabaseObject.StockValueMessage.red);

		this.updateList(greenValues);
		this.updateList(yellowValues);
		this.updateList(redValues);
		this.sendMail();
	}

	private void updateList(StockObjectValue[] stockObjectValues) {
		if (stockObjectValues != null) {
			for (StockObjectValue stockValue : stockObjectValues) {
				Calendar currenttime = Calendar.getInstance();
				Date sqlDate = new Date((currenttime.getTime()).getTime());
				Calendar currenttimeThreeMonths = Calendar.getInstance();
				currenttimeThreeMonths.add(Calendar.MONTH, 3);
				Date sqlDateThreeMonths = new Date((currenttimeThreeMonths.getTime()).getTime());
				Boolean edited = false;
				if (stockValue instanceof DeviceValue) {
					DeviceValue deviceValue = (DeviceValue) stockValue;

					// Check deviceValue for Message State Changes
					if (sqlDate.after(deviceValue.mtkDate) || sqlDate.after(deviceValue.stkDate)) {
						edited = this.setStockObjectValueMessage(deviceValue, DatabaseObject.StockValueMessage.red);
					} else if (deviceValue.mtkDate.after(sqlDate) && ((deviceValue.mtkDate.before(sqlDateThreeMonths))
							|| (deviceValue.stkDate.before(sqlDateThreeMonths)))) {
						edited = this.setStockObjectValueMessage(deviceValue, DatabaseObject.StockValueMessage.yellow);
					} else {
						edited = this.setStockObjectValueMessage(deviceValue, DatabaseObject.StockValueMessage.green);
					}
				} else if (stockValue instanceof MedicalMaterialValue) {
					MedicalMaterialValue medicalMaterialValue = (MedicalMaterialValue) stockValue;
					StockObject stockObject = DatabaseReadManager.getStockObject(stockValue.stockObjectID);
					MedicalMaterial medicalMaterial = (MedicalMaterial) stockObject;

					// Check medicalMaterialValue for Message State Changes
					if ((sqlDate.after(medicalMaterialValue.date) || medicalMaterial.totalVolume < medicalMaterial.minimumStock)) {
						edited = this.setStockObjectValueMessage(medicalMaterialValue, DatabaseObject.StockValueMessage.red);
					} else if ((medicalMaterialValue.date.after(sqlDate) && medicalMaterialValue.date.before(sqlDateThreeMonths))
							|| (medicalMaterial.totalVolume < medicalMaterial.quotaStock)) {
						edited = this.setStockObjectValueMessage(medicalMaterialValue, DatabaseObject.StockValueMessage.yellow);
					} else {
						edited = this.setStockObjectValueMessage(medicalMaterialValue, DatabaseObject.StockValueMessage.green);
					}
				} else if (stockValue instanceof ConsumableMaterialValue) {
					ConsumableMaterialValue consumableMaterialValue = (ConsumableMaterialValue) stockValue;
					StockObject stockObject = DatabaseReadManager.getStockObject(stockValue.stockObjectID);
					ConsumableMaterial consumableMaterial = (ConsumableMaterial) stockObject;

					// Check consumableMaterialValue for Message State Changes
					if ((sqlDate.after(consumableMaterialValue.date) || consumableMaterial.totalVolume < consumableMaterial.minimumStock)) {
						edited = this.setStockObjectValueMessage(consumableMaterialValue, DatabaseObject.StockValueMessage.red);
					} else if ((consumableMaterialValue.date.after(sqlDate) && consumableMaterialValue.date.before(sqlDateThreeMonths))
							|| (consumableMaterial.totalVolume < consumableMaterial.quotaStock)) {
						edited = this.setStockObjectValueMessage(consumableMaterialValue, DatabaseObject.StockValueMessage.yellow);
					} else {
						edited = this.setStockObjectValueMessage(consumableMaterialValue, DatabaseObject.StockValueMessage.green);
					}
				}
				if (edited) {
					System.out.println("Succesfully edited: id " + stockValue.stockObjectID);
				}
			}
		}
	}

	private Boolean setStockObjectValueMessage(StockObjectValue stockObjectValue, DatabaseObject.StockValueMessage message) {
		// Only Update this Value if it changes.
		if (stockObjectValue.messageID != message.ordinal()) {
			stockObjectValue.messageID = message.ordinal();
			// Update the DatabaseObject
			if (stockObjectValue.editObject() == true) {
				if (stockObjectValue instanceof DeviceValue) {
					this.deviceUpdateList.add(stockObjectValue);
				} else if (stockObjectValue instanceof MedicalMaterialValue) {
					this.medicalMaterialUpdateList.add(stockObjectValue);
				} else if (stockObjectValue instanceof ConsumableMaterialValue) {
					this.consumableMaterialUpdateList.add(stockObjectValue);
				}

				return true;
			}
		}
		return false;
	}

	private void sendMail() {
		if (this.deviceUpdateList.size() > 0) {
			Sender.sendMailWithUpdateableDatabaseObjects(this.deviceUpdateList.toArray(new StockObjectValue[deviceUpdateList.size()]), DatabaseObject.StockObjectType.device);
		}
		if (this.medicalMaterialUpdateList.size() > 0) {
			Sender.sendMailWithUpdateableDatabaseObjects(this.medicalMaterialUpdateList.toArray(new StockObjectValue[medicalMaterialUpdateList.size()]), DatabaseObject.StockObjectType.medicalMaterial);
		}
		if (this.consumableMaterialUpdateList.size() > 0) {
			Sender.sendMailWithUpdateableDatabaseObjects(this.consumableMaterialUpdateList.toArray(new StockObjectValue[consumableMaterialUpdateList.size()]), DatabaseObject.StockObjectType.consumableMaterial);
		}
	}
}
