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
 * @author Simon
 *
 */

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
	private int warningIntervallDevice = 3;
	private int warningIntervallMedMat = 3;
	private int warningIntervallConsMat = 1;


	private ArrayList<StockObjectValue> deviceUpdateMailingList = new ArrayList<StockObjectValue>();
	private ArrayList<StockObjectValue> medicalMaterialUpdateMailingList = new ArrayList<StockObjectValue>();
	private ArrayList<StockObjectValue> consumableMaterialUpdateMailingList = new ArrayList<StockObjectValue>();

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
				Boolean edited = false;

				if (stockValue instanceof DeviceValue) {
					DeviceValue deviceValue = (DeviceValue) stockValue;
					StockObject stockObject = DatabaseReadManager.getStockObject(stockValue.stockObjectID);
					Device device = (Device) stockObject;

					/** Check deviceValue for Message State Changes **/
					if (deviceValue.mtkDate != null && deviceValue.stkDate != null) {
						/** Dynamic Time Intervalls for Devices **/
						Calendar mtkIntervallTime = Calendar.getInstance();
						mtkIntervallTime.setTime(deviceValue.mtkDate);
						mtkIntervallTime.add(Calendar.MONTH, device.mtkIntervall);
						Date nextMtkDate = new Date((mtkIntervallTime.getTime()).getTime());

						Calendar stkIntervallTime = Calendar.getInstance();
						stkIntervallTime.setTime(deviceValue.stkDate);
						stkIntervallTime.add(Calendar.MONTH, device.stkIntervall);
						Date nextStkDate = new Date((stkIntervallTime.getTime()).getTime());

						/** Dynamic Time Intervalls for Devices minus fixed number of months **/
						Calendar mtkSoftIntervallTime = Calendar.getInstance();
						mtkSoftIntervallTime.setTime(deviceValue.stkDate);
						mtkSoftIntervallTime.add(Calendar.MONTH, (device.mtkIntervall - warningIntervallDevice) );
						Date nextSoftMtkDate = new Date((mtkSoftIntervallTime.getTime()).getTime());

						Calendar stkSoftIntervallTime = Calendar.getInstance();
						stkSoftIntervallTime.setTime(deviceValue.stkDate);
						stkSoftIntervallTime.add(Calendar.MONTH, (device.stkIntervall - warningIntervallDevice));
						Date nextSoftStkDate = new Date((stkSoftIntervallTime.getTime()).getTime());

						if (sqlDate.after(nextMtkDate) || sqlDate.after(nextStkDate)) {
							edited = this.setStockObjectValueMessage(deviceValue, DatabaseObject.StockValueMessage.red);
						} else if ((deviceValue.mtkDate.before(nextSoftMtkDate)) || (deviceValue.stkDate.before(nextSoftStkDate))) {
							edited = this.setStockObjectValueMessage(deviceValue, DatabaseObject.StockValueMessage.yellow);
						} else {
							edited = this.setStockObjectValueMessage(deviceValue, DatabaseObject.StockValueMessage.green);
						}
					} else {
						if (deviceValue.stkDate != null) {
							/** Dynamic Time Intervalls for Devices **/
							Calendar stkIntervallTime = Calendar.getInstance();
							stkIntervallTime.setTime(deviceValue.stkDate);
							stkIntervallTime.add(Calendar.MONTH, device.stkIntervall);
							Date nextStkDate = new Date((stkIntervallTime.getTime()).getTime());

							/** Dynamic Time Intervalls for Devices minus fixed number of months **/
							Calendar stkSoftIntervallTime = Calendar.getInstance();
							stkSoftIntervallTime.setTime(deviceValue.stkDate);
							stkSoftIntervallTime.add(Calendar.MONTH, (device.stkIntervall - warningIntervallDevice));
							Date nextSoftStkDate = new Date((stkSoftIntervallTime.getTime()).getTime());

							if (sqlDate.after(nextStkDate)) {
								edited = this.setStockObjectValueMessage(deviceValue, DatabaseObject.StockValueMessage.red);
							} else if (deviceValue.stkDate.before(nextSoftStkDate)) {
								edited = this.setStockObjectValueMessage(deviceValue, DatabaseObject.StockValueMessage.yellow);
							} else {
								edited = this.setStockObjectValueMessage(deviceValue, DatabaseObject.StockValueMessage.green);
							}
						} else if (deviceValue.mtkDate != null) {
							/** Dynamic Time Intervalls for Devices **/
							Calendar mtkIntervallTime = Calendar.getInstance();
							mtkIntervallTime.setTime(deviceValue.mtkDate);
							mtkIntervallTime.add(Calendar.MONTH, device.mtkIntervall);
							Date nextMtkDate = new Date((mtkIntervallTime.getTime()).getTime());

							/** Dynamic Time Intervalls for Devices minus fixed number of months **/
							Calendar mtkSoftIntervallTime = Calendar.getInstance();
							mtkSoftIntervallTime.setTime(deviceValue.mtkDate);
							mtkSoftIntervallTime.add(Calendar.MONTH, (device.mtkIntervall - warningIntervallDevice) );
							Date nextSoftMtkDate = new Date((mtkSoftIntervallTime.getTime()).getTime());

							if (sqlDate.after(nextMtkDate)) {
								edited = this.setStockObjectValueMessage(deviceValue, DatabaseObject.StockValueMessage.red);
							} else if (deviceValue.mtkDate.before(nextSoftMtkDate)) {
								edited = this.setStockObjectValueMessage(deviceValue, DatabaseObject.StockValueMessage.yellow);
							} else {
								edited = this.setStockObjectValueMessage(deviceValue, DatabaseObject.StockValueMessage.green);
							}
						}
					}

				} else if (stockValue instanceof MedicalMaterialValue) {
					MedicalMaterialValue medicalMaterialValue = (MedicalMaterialValue) stockValue;
					StockObject stockObject = DatabaseReadManager.getStockObject(stockValue.stockObjectID);
					MedicalMaterial medicalMaterial = (MedicalMaterial) stockObject;
					/** Fixed number of Months Time Intervall **/
					Calendar currenttimeThreeMonths = Calendar.getInstance();
					currenttimeThreeMonths.add(Calendar.MONTH, warningIntervallMedMat);
					Date sqlDateThreeMonths = new Date((currenttimeThreeMonths.getTime()).getTime());
					/** Check medicalMaterialValue for Message State Changes **/

					if (medicalMaterialValue.date != null) {
						if (sqlDate.after(medicalMaterialValue.date)) {
							if (medicalMaterialValue.minimumStock > 0) {
								edited = this.setStockObjectValueMessage(medicalMaterialValue, DatabaseObject.StockValueMessage.red);
							} else if (medicalMaterialValue.quotaStock > 0) {
								edited = this.setStockObjectValueMessage(medicalMaterialValue, DatabaseObject.StockValueMessage.red);
							} else {
								// dont warn if a min/quota isnt reached by exiring the storability
								edited = this.setStockObjectValueMessage(medicalMaterialValue, DatabaseObject.StockValueMessage.green);
							}
						} else if (medicalMaterialValue.date.before(sqlDateThreeMonths)) {
							if (medicalMaterialValue.minimumStock > 0) {
								edited = this.setStockObjectValueMessage(medicalMaterialValue, DatabaseObject.StockValueMessage.yellow);
							} else if (medicalMaterialValue.quotaStock > 0) {
								edited = this.setStockObjectValueMessage(medicalMaterialValue, DatabaseObject.StockValueMessage.yellow);
							} else {
								// dont warn if a min/quota isnt reached by exiring the storability
								edited = this.setStockObjectValueMessage(medicalMaterialValue, DatabaseObject.StockValueMessage.green);
							}
						} else {
							int consolidatedVolumeForStockObject = 0;
							StockObjectValue[] consolidatedStockObjectValues = DatabaseReadManager.getStockObjectValues(medicalMaterialValue.stockObjectID);
							if (consolidatedStockObjectValues != null) {
								for (StockObjectValue consolidatedStockObjectValue : consolidatedStockObjectValues) {
									if (consolidatedStockObjectValue instanceof MedicalMaterialValue) {
										MedicalMaterialValue consolidatedMedicalMaterialValue = (MedicalMaterialValue) consolidatedStockObjectValue;
										if (consolidatedMedicalMaterialValue.locationID == medicalMaterialValue.locationID ) {
											consolidatedVolumeForStockObject += consolidatedStockObjectValue.volume;
										}
									}
								}
							}
							if (medicalMaterialValue.volume < medicalMaterialValue.minimumStock) {
								edited = this.setStockObjectValueMessage(medicalMaterialValue, DatabaseObject.StockValueMessage.red);
							} else if (medicalMaterialValue.volume < medicalMaterialValue.quotaStock) {
								edited = this.setStockObjectValueMessage(medicalMaterialValue, DatabaseObject.StockValueMessage.yellow);
							} else {
								// Everything is fine
								edited = this.setStockObjectValueMessage(medicalMaterialValue, DatabaseObject.StockValueMessage.green);
							}
						}
					}
				} else if (stockValue instanceof ConsumableMaterialValue) {
					ConsumableMaterialValue consumableMaterialValue = (ConsumableMaterialValue) stockValue;
					StockObject stockObject = DatabaseReadManager.getStockObject(stockValue.stockObjectID);
					ConsumableMaterial consumableMaterial = (ConsumableMaterial) stockObject;
					/** Fixed 3 Months Time Intervall **/
					Calendar currenttimeThreeMonths = Calendar.getInstance();
					currenttimeThreeMonths.add(Calendar.MONTH, warningIntervallConsMat);
					Date sqlDateThreeMonths = new Date((currenttimeThreeMonths.getTime()).getTime());
					/** Check consumableMaterialValue for Message State Changes **/
					if (consumableMaterialValue.date != null) {
						if (sqlDate.after(consumableMaterialValue.date)) {
							if (consumableMaterialValue.minimumStock > 0) {
								edited = this.setStockObjectValueMessage(consumableMaterialValue, DatabaseObject.StockValueMessage.red);
							} else if (consumableMaterialValue.quotaStock > 0) {
								edited = this.setStockObjectValueMessage(consumableMaterialValue, DatabaseObject.StockValueMessage.red);
							} else {
								// dont warn if a min/quota isnt reached by exiring the storability
								edited = this.setStockObjectValueMessage(consumableMaterialValue, DatabaseObject.StockValueMessage.green);
							}
						} else if (consumableMaterialValue.date.before(sqlDateThreeMonths)) {
							if (consumableMaterialValue.minimumStock > 0) {
								edited = this.setStockObjectValueMessage(consumableMaterialValue, DatabaseObject.StockValueMessage.yellow);
							} else if (consumableMaterialValue.quotaStock > 0) {
								edited = this.setStockObjectValueMessage(consumableMaterialValue, DatabaseObject.StockValueMessage.yellow);
							} else {
								// dont warn if a min/quota isnt reached by exiring the storability
								edited = this.setStockObjectValueMessage(consumableMaterialValue, DatabaseObject.StockValueMessage.green);
							}
						} else {
							int consolidatedVolumeForStockObject = 0;
							StockObjectValue[] consolidatedStockObjectValues = DatabaseReadManager.getStockObjectValues(consumableMaterialValue.stockObjectID);
							if (consolidatedStockObjectValues != null) {
								for (StockObjectValue consolidatedStockObjectValue : consolidatedStockObjectValues) {
									if (consolidatedStockObjectValue instanceof ConsumableMaterialValue) {
										ConsumableMaterialValue consolidatedConsumableMaterialValue = (ConsumableMaterialValue) consolidatedStockObjectValue;
										if (consolidatedConsumableMaterialValue.locationID == consumableMaterialValue.locationID) {
											consolidatedVolumeForStockObject += consolidatedStockObjectValue.volume;
										}
									}
								}
							}
							if (consumableMaterialValue.volume < consumableMaterialValue.minimumStock) {
								edited = this.setStockObjectValueMessage(consumableMaterialValue, DatabaseObject.StockValueMessage.red);
							} else if (consumableMaterialValue.volume < consumableMaterialValue.quotaStock) {
								edited = this.setStockObjectValueMessage(consumableMaterialValue, DatabaseObject.StockValueMessage.yellow);
							} else {
								// Everything is fine
								edited = this.setStockObjectValueMessage(consumableMaterialValue, DatabaseObject.StockValueMessage.green);
							}
						}
					}
				}
				if (edited) {
					System.out.println("Succesfully edited: id " + stockValue.id);
				}
			}
		}
	}

	private Boolean setStockObjectValueMessage(StockObjectValue stockObjectValue, DatabaseObject.StockValueMessage message) {
		// Only Update this Value if it changes.
		if (stockObjectValue.messageID != message.ordinal()) {
			// Check if the messageID is getting worse
			if (stockObjectValue.messageID < message.ordinal()) {
				stockObjectValue.messageID = message.ordinal();
				// Update the DatabaseObject
				if (stockObjectValue.editObject() == true) {
					// And add the stockObjectValue to the MailingList
					if (stockObjectValue instanceof DeviceValue) {
						this.deviceUpdateMailingList.add(stockObjectValue);
					} else if (stockObjectValue instanceof MedicalMaterialValue) {
						this.medicalMaterialUpdateMailingList.add(stockObjectValue);
					} else if (stockObjectValue instanceof ConsumableMaterialValue) {
						this.consumableMaterialUpdateMailingList.add(stockObjectValue);
					}
					return true;
				}
			} else {
				stockObjectValue.messageID = message.ordinal();
				// Update the DatabaseObject
				return stockObjectValue.editObject();
			}

		}
		return false;
	}

	private void sendMail() {
		if (this.deviceUpdateMailingList.size() > 0) {
			StockObjectValue[] stockObjectValues = this.deviceUpdateMailingList.toArray(new StockObjectValue[deviceUpdateMailingList.size()]);
			Sender.sendMailWithUpdateableDatabaseObjects(stockObjectValues, DatabaseObject.StockObjectType.device);
		}
		if (this.medicalMaterialUpdateMailingList.size() > 0) {
			StockObjectValue[] stockObjectValues = this.medicalMaterialUpdateMailingList.toArray(new StockObjectValue[medicalMaterialUpdateMailingList.size()]);
			Sender.sendMailWithUpdateableDatabaseObjects(stockObjectValues, DatabaseObject.StockObjectType.medicalMaterial);
		}
		if (this.consumableMaterialUpdateMailingList.size() > 0) {
			StockObjectValue[] stockObjectValues = this.consumableMaterialUpdateMailingList.toArray(new StockObjectValue[consumableMaterialUpdateMailingList.size()]);
			Sender.sendMailWithUpdateableDatabaseObjects(stockObjectValues, DatabaseObject.StockObjectType.consumableMaterial);
		}
	}
}
