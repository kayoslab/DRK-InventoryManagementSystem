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

package model.mailing;
import model.DatabaseReadManager;
import model.databaseCommunication.DatabaseLoginManager;
import model.databaseObjects.DatabaseObject;
import model.databaseObjects.accessControl.Group;
import model.databaseObjects.accessControl.User;
import model.databaseObjects.stockObjects.ConsumableMaterial;
import model.databaseObjects.stockObjects.Device;
import model.databaseObjects.stockObjects.MedicalMaterial;
import model.databaseObjects.stockValues.ConsumableMaterialValue;
import model.databaseObjects.stockValues.DeviceValue;
import model.databaseObjects.stockValues.MedicalMaterialValue;
import model.databaseObjects.stockValues.StockObjectValue;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Sender {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	/** Fixed IDs for Responsible Groups **/
	private static final int deviceResponsibleGroupID = 3;
	private static final int medicalMaterialResponsibleGroupID = 4;
	private static final int consumableMaterialResponsibleGroupID = 5;

	public static void sendMailWithUpdateableDatabaseObjects(StockObjectValue[] stockObjectValues, DatabaseObject.StockObjectType type) {
		/** Sort the StockObjectValues by Message **/
		DatabaseLoginManager dbloginManager = new DatabaseLoginManager();
		StockObjectValue[] sortedStockObjectValues = stockObjectValues;
		Arrays.sort(sortedStockObjectValues, Comparator.comparingInt(a -> a.stockObjectID));
		Arrays.sort(sortedStockObjectValues, Comparator.comparingInt(a -> a.messageID));
		/** Authentication Properties **/
		Authenticator auth = getAuthenticator(dbloginManager.getSmtpUserName(), dbloginManager.getSmtpPassword());
		Properties properties = Sender.getProperties(dbloginManager.getSmtpHost(), dbloginManager.getSmtpPort());
		Session session = Session.getDefaultInstance(properties, auth);
		/** Message Object **/
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(dbloginManager.getSenderAddress(), "Stock Manager"));
			Group group = null;
			switch (type) {
				case empty:
					break;
				case device:
					group = DatabaseReadManager.getGroup(Sender.deviceResponsibleGroupID);
					message.setSubject("Geräte: " + stockObjectValues.length + " neue Meldung(en)");
					break;
				case medicalMaterial:
					group = DatabaseReadManager.getGroup(Sender.medicalMaterialResponsibleGroupID);
					message.setSubject("MedMat: " + stockObjectValues.length + " neue Meldung(en)");
					break;
				case consumableMaterial:
					group = DatabaseReadManager.getGroup(Sender.consumableMaterialResponsibleGroupID);
					message.setSubject("Versorgung: " + stockObjectValues.length + " neue Meldung(en)");
					break;
				case vehicle:
					break;
			}

			if (group != null) {
				User[] users = DatabaseReadManager.getUsers(group);
				InternetAddress[] internetAddresses = new InternetAddress[users.length];
				int counter = 0;
				for (User user : users) {
					internetAddresses[counter] = new InternetAddress(user.mail , user.firstName + " " + user.name);
					counter ++;
				}
				message.setRecipients(RecipientType.CC, internetAddresses);
				message.setRecipient(RecipientType.TO, new InternetAddress(dbloginManager.getSenderAddress()));
			}

			/** Get Mail Content **/
			Multipart multipart = new MimeMultipart();
			message.setContent(Sender.generateMultipartForStockObjectValues(sortedStockObjectValues, type));
			message.setSentDate(new Date());
			/** Send **/
			Transport.send(message);
			System.out.println("Message send: " + type.name());
		}  catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	private static Properties getProperties(String smtpHost, String smtpPort) {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", smtpHost);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", smtpPort);
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.starttls.enable", "true");
		return properties;
	}

	private static Authenticator getAuthenticator(String user, String password) {
		try {
			Authenticator auth = new Authenticator() {
				/**
				 * @return PasswordAuthentication Object
				 * @see javax.mail.Authenticator#getPasswordAuthentication()
				 */
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
				}
			};
			return auth;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Multipart generateMultipartForStockObjectValues(StockObjectValue[] stockObjectValues, DatabaseObject.StockObjectType type) {
		String plainMessageString = stockObjectValues.length + " neue Meldung(en) \n\n";
		String messageHTMLString = "<html><head>" +
				"<style type=\"text/css\">" +
				"  tr:nth-child(2n) {" +
				"    background-color: #BBBBBB;" +
				"  }" +
				"</style>" +
				"</head>" +
				"<body>" +
				"<h2>" + stockObjectValues.length + " neue Meldung(en):</h2>" +
				"\n\n\n" +
				"<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"1\">" +
				"<colgroup>\n" +
				"    <col width=\"20%\" />\n" +
				"    <col width=\"20%\" />\n" +
				"    <col width=\"20%\" />\n" +
				"    <col width=\"20%\" />\n" +
				"    <col width=\"20%\" />\n" +
				"</colgroup>";
		switch (type) {
			case empty:
				return null;
			case device:
				messageHTMLString += "<tbody>" +
						"<tr>" +
						"<th align=\"center\">Titel</th>" +
						"<th align=\"center\">Letzte MTK</th>" +
						"<th align=\"center\">MTK Intervall</th>" +
						"<th align=\"center\">Letzte STK</th>" +
						"<th align=\"center\">STK Intervall</th>" +
						"</tr>";
				for (StockObjectValue stockObjectValue : stockObjectValues) {
					if (stockObjectValue instanceof DeviceValue) {
						DeviceValue deviceValue = (DeviceValue) stockObjectValue;
						Device device = (Device) DatabaseReadManager.getStockObject(deviceValue.stockObjectID);

						String mtkDate = "";
						String stkDate = "";
						if (deviceValue.stkDate != null) {
							mtkDate = Sender.sdf.format(deviceValue.mtkDate);
						}
						if (deviceValue.stkDate != null) {
							stkDate = Sender.sdf.format(deviceValue.stkDate);
						}

						plainMessageString += "Titel: " + device.title + ", " +
								"Letzte MTK: " + mtkDate + ", "+
								"MTK Intervall: " + device.mtkIntervall + ", "+
								"Letzte STK: " + stkDate + ", "+
								"STK Intervall: " + device.stkIntervall +
								" \n\n";
						messageHTMLString += "<tr>" +
								"<td align=\"center\">" + device.title + "</td>" +
								"<td align=\"center\">" + mtkDate + "</td>" +
								"<td align=\"center\">" + device.mtkIntervall + "</td>" +
								"<td align=\"center\">" + stkDate + "</td>" +
								"<td align=\"center\">" + device.stkIntervall + "</td>" +
								"</tr>";
					}
				}
				messageHTMLString += "</tbody>";
				break;
			case medicalMaterial:
				messageHTMLString += "<tbody>" +
						"<tr>" +
						"<th align=\"center\">Titel</th>" +
						"<th align=\"center\">Lagerort</th>" +
						"<th align=\"center\">Lagerbestand</th>" +
						"<th align=\"center\">Sollbestand</th>" +
						"<th align=\"center\">Bestellmenge</th>" +
						"<th align=\"center\">Ablaufdatum</th>" +
						"</tr>";
				for (StockObjectValue stockObjectValue : stockObjectValues) {
					if (stockObjectValue instanceof MedicalMaterialValue) {
						MedicalMaterialValue medicalMaterialValue = (MedicalMaterialValue) stockObjectValue;
						MedicalMaterial medicalMaterial = (MedicalMaterial) DatabaseReadManager.getStockObject(medicalMaterialValue.stockObjectID);

						String date = "";
						if (medicalMaterialValue.date != null) {
							date = Sender.sdf.format(medicalMaterialValue.date);
						}

						int consolidatedVolumeForStockObject = 0;
						StockObjectValue[] consolidatedStockObjectValues = DatabaseReadManager.getStockObjectValues(medicalMaterialValue.stockObjectID);
						if (consolidatedStockObjectValues != null) {
							for (StockObjectValue consolidatedStockObjectValue : consolidatedStockObjectValues) {
								if (consolidatedStockObjectValue instanceof MedicalMaterialValue) {
									MedicalMaterialValue consolidatedMedicalMaterialValue = (MedicalMaterialValue) consolidatedStockObjectValue;
									if (consolidatedMedicalMaterialValue.locationID == medicalMaterialValue.locationID
											&& consolidatedMedicalMaterialValue.minimumStock == medicalMaterialValue.minimumStock
											&& consolidatedMedicalMaterialValue.quotaStock == medicalMaterialValue.quotaStock) {
										consolidatedVolumeForStockObject += consolidatedStockObjectValue.volume;
									}
								}
							}
						}
						int orderQuantity = 0;
						int quotaQuantity = medicalMaterialValue.quotaStock - consolidatedVolumeForStockObject;
						int minimumQuantity = medicalMaterialValue.minimumStock - consolidatedVolumeForStockObject;
						// Date exceeded
						if (quotaQuantity <= 0 && minimumQuantity <= 0) {
							orderQuantity = medicalMaterialValue.quotaStock;
						} else if (quotaQuantity > 0) {
							orderQuantity = quotaQuantity;
						} else if (minimumQuantity > 0) {
							orderQuantity = quotaQuantity;
						} else {
							orderQuantity = 0;
						}
						String locationTitle = DatabaseReadManager.getLocation(medicalMaterialValue.locationID).title;
						plainMessageString += "Titel: " + medicalMaterial.title + ", " +
								"Lagerort: " + locationTitle + ", "+
								"Lagerbestand: " + medicalMaterialValue.volume + ", "+
								"Sollbestand: " + medicalMaterialValue.quotaStock + ", "+
								"Bestellmenge: " + orderQuantity + ", "+
								"Ablaufdatum: " + date +
								" \n\n";
						messageHTMLString += "<tr>" +
								"<td align=\"center\">" + medicalMaterial.title + "</td>" +
								"<td align=\"center\">" + locationTitle + "</td>" +
								"<td align=\"center\">" + medicalMaterialValue.volume + "</td>" +
								"<td align=\"center\">" + medicalMaterialValue.quotaStock + "</td>" +
								"<td align=\"center\">" + orderQuantity + "</td>" +
								"<td align=\"center\">" + date + "</td>" +
								"</tr>";
					}
				}
				messageHTMLString += "</tbody>";
				break;
			case consumableMaterial:
				messageHTMLString += "<tbody>" +
						"<tr>" +
						"<th align=\"center\">Titel</th>" +
						"<th align=\"center\">Lagerort</th>" +
						"<th align=\"center\">Lagerbestand</th>" +
						"<th align=\"center\">Sollbestand</th>" +
						"<th align=\"center\">Bestellmenge</th>" +
						"<th align=\"center\">Ablaufdatum</th>" +
						"</tr>";
				for (StockObjectValue stockObjectValue : stockObjectValues) {
					if (stockObjectValue instanceof ConsumableMaterialValue) {
						ConsumableMaterialValue consumableMaterialValue = (ConsumableMaterialValue) stockObjectValue;
						ConsumableMaterial consumableMaterial = (ConsumableMaterial) DatabaseReadManager.getStockObject(consumableMaterialValue.stockObjectID);

						String date = "";
						if (consumableMaterialValue.date != null) {
							date = Sender.sdf.format(consumableMaterialValue.date);
						}


						int consolidatedVolumeForStockObject = 0;
						StockObjectValue[] consolidatedStockObjectValues = DatabaseReadManager.getStockObjectValues(consumableMaterialValue.stockObjectID);
						if (consolidatedStockObjectValues != null) {
							for (StockObjectValue consolidatedStockObjectValue : consolidatedStockObjectValues) {
								if (consolidatedStockObjectValue instanceof ConsumableMaterialValue) {
									ConsumableMaterialValue consolidatedConsumableMaterialValue = (ConsumableMaterialValue) consolidatedStockObjectValue;
									if (consolidatedConsumableMaterialValue.locationID == consumableMaterialValue.locationID
											&& consolidatedConsumableMaterialValue.minimumStock == consumableMaterialValue.minimumStock
											&& consolidatedConsumableMaterialValue.quotaStock == consumableMaterialValue.quotaStock) {
										consolidatedVolumeForStockObject += consolidatedStockObjectValue.volume;
									}
								}
							}
						}
						int orderQuantity = 0;
						int quotaQuantity = consumableMaterialValue.quotaStock - consolidatedVolumeForStockObject;
						int minimumQuantity = consumableMaterialValue.minimumStock - consolidatedVolumeForStockObject;
						// Date exceeded
						if (quotaQuantity <= 0 && minimumQuantity <= 0) {
							orderQuantity = consumableMaterialValue.quotaStock;
						} else if (quotaQuantity > 0) {
							orderQuantity = quotaQuantity;
						} else if (minimumQuantity > 0) {
							orderQuantity = quotaQuantity;
						} else {
							orderQuantity = 0;
						}
						String locationTitle = DatabaseReadManager.getLocation(consumableMaterialValue.locationID).title;
						plainMessageString += "Titel: " + consumableMaterial.title + ", " +
								"Lagerort: " + locationTitle + ", "+
								"Lagerbestand: " + consumableMaterialValue.volume + ", "+
								"Sollbestand: " + consumableMaterialValue.quotaStock + ", "+
								"Bestellmenge: " + orderQuantity + ", "+
								"Ablaufdatum: " + date +
								" \n\n";
						messageHTMLString += "<tr>" +
								"<td align=\"center\">" + consumableMaterial.title + "</td>" +
								"<td align=\"center\">" + locationTitle + "</td>" +
								"<td align=\"center\">" + consumableMaterialValue.volume + "</td>" +
								"<td align=\"center\">" + consumableMaterialValue.quotaStock + "</td>" +
								"<td align=\"center\">" + orderQuantity + "</td>" +
								"<td align=\"center\">" + date + "</td>" +
								"</tr>";
					}
				}
				messageHTMLString += "</tbody>";
				break;
			case vehicle:
				return null;
		}

		messageHTMLString += "</table></body></html>";
		// Add HTML Text to the MessageBody
		BodyPart plainMessageBodyPart = new MimeBodyPart();
		BodyPart htmlMessageBodyPart = new MimeBodyPart();
		try {
			// messageBodyPart.setText(messageHTMLString);
			plainMessageBodyPart.setContent(plainMessageString,  "text/plain; charset=ISO-8859-1");
			htmlMessageBodyPart.setContent(messageHTMLString,  "text/html; charset=ISO-8859-1");
			// return messageBodyPart;

			Multipart mp = new MimeMultipart("alternative");
			mp.addBodyPart(plainMessageBodyPart);
			mp.addBodyPart(htmlMessageBodyPart);
			return mp;
		} catch (MessagingException me) {
			me.printStackTrace();
			return null;
		}

	}
}
