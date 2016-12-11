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
			System.out.println(e);
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
			System.out.println(e);
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
						plainMessageString += "Titel: " + device.title + ", " +
								"Letzte MTK: " + Sender.sdf.format(deviceValue.mtkDate) + ", "+
								"MTK Intervall: " + device.mtkIntervall + ", "+
								"Letzte STK: " + Sender.sdf.format(deviceValue.stkDate) + ", "+
								"STK Intervall: " + device.stkIntervall +
								" \n\n";
						messageHTMLString += "<tr>" +
								"<td align=\"center\">" + device.title + "</td>" +
								"<td align=\"center\">" + Sender.sdf.format(deviceValue.mtkDate) + "</td>" +
								"<td align=\"center\">" + device.mtkIntervall + "</td>" +
								"<td align=\"center\">" + Sender.sdf.format(deviceValue.stkDate) + "</td>" +
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
						"<th align=\"center\">Mindestbestand</th>" +
						"<th align=\"center\">Sollbestand</th>" +
						"<th align=\"center\">Lagerbestand</th>" +
						"<th align=\"center\">Ablaufdatum</th>" +
						"</tr>";
				for (StockObjectValue stockObjectValue : stockObjectValues) {
					if (stockObjectValue instanceof MedicalMaterialValue) {
						MedicalMaterialValue medicalMaterialValue = (MedicalMaterialValue) stockObjectValue;
						MedicalMaterial medicalMaterial = (MedicalMaterial) DatabaseReadManager.getStockObject(medicalMaterialValue.stockObjectID);
						plainMessageString += "Titel: " + medicalMaterial.title + ", " +
								"Mindestbestand: " + medicalMaterial.minimumStock + ", "+
								"Sollbestand: " + medicalMaterial.quotaStock + ", "+
								"Lagerbestand: " + medicalMaterialValue.volume + ", "+
								"Ablaufdatum: " + Sender.sdf.format(medicalMaterialValue.date) +
								" \n\n";
						messageHTMLString += "<tr>" +
								"<td align=\"center\">" + medicalMaterial.title + "</td>" +
								"<td align=\"center\">" + medicalMaterial.minimumStock + "</td>" +
								"<td align=\"center\">" + medicalMaterial.quotaStock + "</td>" +
								"<td align=\"center\">" + medicalMaterialValue.volume + "</td>" +
								"<td align=\"center\">" + Sender.sdf.format(medicalMaterialValue.date) + "</td>" +
								"</tr>";
					}
				}
				messageHTMLString += "</tbody>";
				break;
			case consumableMaterial:
				messageHTMLString += "<tbody>" +
						"<tr>" +
						"<th align=\"center\">Titel</th>" +
						"<th align=\"center\">Mindestbestand</th>" +
						"<th align=\"center\">Sollbestand</th>" +
						"<th align=\"center\">Lagerbestand</th>" +
						"<th align=\"center\">Ablaufdatum</th>" +
						"</tr>";
				for (StockObjectValue stockObjectValue : stockObjectValues) {
					if (stockObjectValue instanceof ConsumableMaterialValue) {
						ConsumableMaterialValue consumableMaterialValue = (ConsumableMaterialValue) stockObjectValue;
						ConsumableMaterial consumableMaterial = (ConsumableMaterial) DatabaseReadManager.getStockObject(consumableMaterialValue.stockObjectID);
						plainMessageString += "Titel: " + consumableMaterial.title + ", " +
								"Mindestbestand: " + consumableMaterial.minimumStock + ", "+
								"Sollbestand: " + consumableMaterial.quotaStock + ", "+
								"Lagerbestand: " + consumableMaterialValue.volume + ", "+
								"Ablaufdatum: " + Sender.sdf.format(consumableMaterialValue.date) +
								" \n\n";
						messageHTMLString += "<tr>" +
								"<td align=\"center\">" + consumableMaterial.title + "</td>" +
								"<td align=\"center\">" + consumableMaterial.minimumStock + "</td>" +
								"<td align=\"center\">" + consumableMaterial.quotaStock + "</td>" +
								"<td align=\"center\">" + consumableMaterialValue.volume + "</td>" +
								"<td align=\"center\">" + Sender.sdf.format(consumableMaterialValue.date) + "</td>" +
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
			System.out.println(me);
			return null;
		}

	}
}
