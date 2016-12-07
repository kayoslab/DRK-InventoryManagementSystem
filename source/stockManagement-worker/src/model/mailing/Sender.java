package model.mailing;

import model.DatabaseReadManager;
import model.databaseCommunication.DatabaseLoginManager;
import model.databaseObjects.DatabaseObject;
import model.databaseObjects.stockObjects.ConsumableMaterial;
import model.databaseObjects.stockObjects.Device;
import model.databaseObjects.stockObjects.MedicalMaterial;
import model.databaseObjects.stockValues.ConsumableMaterialValue;
import model.databaseObjects.stockValues.DeviceValue;
import model.databaseObjects.stockValues.MedicalMaterialValue;
import model.databaseObjects.stockValues.StockObjectValue;

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

	public static void sendMailWithUpdateableDatabaseObjects(StockObjectValue[] stockObjectValues, DatabaseObject.StockObjectType type) {
		DatabaseLoginManager dbloginManager = new DatabaseLoginManager();

		/** Authentication Properties **/
		Authenticator auth = getAuthenticator(dbloginManager.getSmtpUserName(), dbloginManager.getSmtpPassword());
		Properties properties = Sender.getProperties(dbloginManager.getSmtpHost(), dbloginManager.getSmtpPort());
		Session session = Session.getDefaultInstance(properties, auth);

		/** Message Object **/
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(dbloginManager.getSenderAddress(), "Stock Manager"));
			InternetAddress[] internetAddresses;
			switch (type) {
				case empty:
					return;
				case device:
					internetAddresses = new InternetAddress[] { new InternetAddress("nina.forbrich@drk-sennestadt.de") };
					message.setRecipients(RecipientType.BCC, internetAddresses);
					message.setRecipient(RecipientType.TO, new InternetAddress(dbloginManager.getSenderAddress()));
					message.setSubject("Geräte: " + stockObjectValues.length + " neue Meldungen");
					break;
				case medicalMaterial:
					internetAddresses = new InternetAddress[] { new InternetAddress("nina.forbrich@drk-sennestadt.de") };
					message.setRecipients(RecipientType.BCC, internetAddresses);
					message.setRecipient(RecipientType.TO, new InternetAddress(dbloginManager.getSenderAddress()));
					message.setSubject("MedMat: " + stockObjectValues.length + " neue Meldungen");
					break;
				case consumableMaterial:
					internetAddresses = new InternetAddress[] { new InternetAddress("nina.forbrich@drk-sennestadt.de") };
					message.setRecipients(RecipientType.BCC, internetAddresses);
					message.setRecipient(RecipientType.TO, new InternetAddress(dbloginManager.getSenderAddress()));
					message.setSubject("Versorgung: " + stockObjectValues.length + " neue Meldungen");
					break;
				case vehicle:
					return;
			}


			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(Sender.generateTextForStockObjectValues(stockObjectValues));
			message.setContent(multipart);
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

	private static BodyPart generateTextForStockObjectValues(StockObjectValue[] stockObjectValues) {
		BodyPart messageBodyPart = new MimeBodyPart();
		String messageBodyTextString = "<html><head></head><body><h2>" + stockObjectValues.length + " neue Warnung(en):</h2>\n\n\n<ul>";

		// Add relevant StockObjectValues to an E-Mail
		for (StockObjectValue stockObjectValue : stockObjectValues) {
			if (stockObjectValue instanceof DeviceValue) {
				DeviceValue deviceValue = (DeviceValue) stockObjectValue;
				Device device = (Device) DatabaseReadManager.getStockObject(deviceValue.stockObjectID);
				messageBodyTextString += "<li><b>" + device.title + ":</b>&nbsp;&nbsp;&nbsp;&nbsp;Menge: " + device.totalVolume + "</li>";
			} else if (stockObjectValue instanceof MedicalMaterialValue) {
				MedicalMaterialValue medicalMaterialValue = (MedicalMaterialValue) stockObjectValue;
				MedicalMaterial medicalMaterial = (MedicalMaterial) DatabaseReadManager.getStockObject(medicalMaterialValue.stockObjectID);
				messageBodyTextString += "<li><b>" + medicalMaterial.title + ":</b>&nbsp;&nbsp;&nbsp;&nbsp;Menge: " + medicalMaterial.totalVolume + "</li>";
			} else if (stockObjectValue instanceof ConsumableMaterialValue) {
				ConsumableMaterialValue consumableMaterialValue = (ConsumableMaterialValue) stockObjectValue;
				ConsumableMaterial consumableMaterial = (ConsumableMaterial) DatabaseReadManager.getStockObject(consumableMaterialValue.stockObjectID);
				messageBodyTextString += "<li><b>" + consumableMaterial.title + ":</b>&nbsp;&nbsp;&nbsp;&nbsp;Menge: " + consumableMaterial.totalVolume + "</li>";
			}
		}
		messageBodyTextString += "</ul></body></html>";
		// Add Text to the MessageBody
		try {
			// messageBodyPart.setText(messageBodyTextString);
			messageBodyPart.setContent(messageBodyTextString,  "text/html; charset=utf-8");
			return messageBodyPart;
		} catch (MessagingException me) {
			System.out.println(me);
			return null;
		}
	}
}
