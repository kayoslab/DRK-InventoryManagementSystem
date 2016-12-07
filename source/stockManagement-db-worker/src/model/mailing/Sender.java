package model.mailing;

import model.databaseCommunication.DatabaseLoginManager;
import model.databaseObjects.DatabaseObject;
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
		Properties properties = new Properties();
		properties.put("mail.smtp.host", dbloginManager.getSmtpHost());
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", dbloginManager.getSmtpPort());
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.starttls.enable", "true");
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
					message.setSubject("Geräte: " + stockObjectValues.length + " neue Meldungen");
					break;
				case medicalMaterial:
					internetAddresses = new InternetAddress[] { new InternetAddress("nina.forbrich@drk-sennestadt.de") };
					message.setRecipients(RecipientType.BCC, internetAddresses);
					message.setSubject("MedMat: " + stockObjectValues.length + " neue Meldungen");
					break;
				case consumableMaterial:
					internetAddresses = new InternetAddress[] { new InternetAddress("nina.forbrich@drk-sennestadt.de") };
					message.setRecipients(RecipientType.BCC, internetAddresses);
					message.setSubject("Versorgung: " + stockObjectValues.length + " neue Meldungen");
					break;
				case vehicle:
					return;
			}


			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(Sender.generateTextForStockObjectValues(stockObjectValues));
			message.setContent(multipart);
			Transport.send(message);
		}  catch (Exception e) {
			return;
		}
	}

	private static BodyPart generateTextForStockObjectValues(StockObjectValue[] stockObjectValues) {
		BodyPart messageBodyPart = new MimeBodyPart();
		String messageBodyTextString = "";

		// Add relevant StockObjectValues to an E-Mail
		for (StockObjectValue stockObjectValue : stockObjectValues) {
			if (stockObjectValue instanceof DeviceValue) {

			} else if (stockObjectValue instanceof MedicalMaterialValue) {

			} else if (stockObjectValue instanceof ConsumableMaterialValue) {

			}
		}

		// Add Text to the MessageBody
		try {
			messageBodyPart.setText(messageBodyTextString);
			return messageBodyPart;
		} catch (MessagingException me) {
			return null;
		}
	}

	/** *************************************************  ************************************************* **/


	private static void sendMail(String smtpHost, String username, String password, String senderAddress, String recipientsAddress, String subject, String text) {
		Authenticator auth = getAuthenticator(username, password);
		Properties properties = getProperties();
		// Hier wird mit den Properties und dem Authenticator eine Session erzeugt
		Session session = Session.getDefaultInstance(properties, auth);
		// Message senden
		sendMessage(session, senderAddress, recipientsAddress, subject);
	}

	/**
	 * Nachricht wird gesendet
	 *
	 * @param session
	 * @param senderAddress
	 * @param recipientsAddress
	 * @param subject
	 */
	private static void sendMessage(Session session, String senderAddress, String recipientsAddress, String subject) {
		try {
			// Eine neue Message erzeugen
			Message msg = new MimeMessage(session);
			// Hier werden die Absender- und Empf�ngeradressen gesetzt
			msg.setFrom(new InternetAddress(senderAddress, "XXX"));
			// msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientsAddress));
			msg.setRecipient(RecipientType.TO, new InternetAddress(recipientsAddress));
			// Der Betreff wird gesetzt
			msg.setSubject(subject);
			// multipart message erstellen, in dem Text und Attachment gepuffert werden
			Multipart multipart = new MimeMultipart();
			// Der Text wird gesetzt
			multipart.addBodyPart(getText());
			// Text zur Nachricht hinzufügen
			msg.setContent(multipart);
			// Senden der Nachricht
			Transport.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Nachrichtentext wird gesetzt
	 *
	 * @return
	 */
	private static BodyPart getText() {
		try {
			// message part erstellen
			BodyPart messageBodyPart = new MimeBodyPart();
			// Mailtext
			String text = "Hallo das ist ein Test mit Javamail!\nWenn Sie den Text lesen koennen hat es funktioniert.";
			messageBodyPart.setText(text);
			// Daten
			return messageBodyPart;
		} catch (MessagingException me) {
			me.printStackTrace();
			return null;
		}

	}

	/**
	 * Eigenschaften des Mail-Servers werden gesetzt
	 *
	 * @return
	 */
	private static Properties getProperties() {
		Properties properties = new Properties();
		// Den Properties wird die ServerAdresse hinzugefuegt
		properties.put("mail.smtp.host", "smtp.web.de");
		// !!Wichtig!! Falls der SMTP-Server eine Authentifizierung verlangt
		// muss an dieser Stelle die Property auf "true" gesetzt
		// werden
		properties.put("mail.smtp.auth", "true");
		// Port setzen
		properties.put("mail.smtp.port", "587");
		// Protokoll festlegen
		properties.put("mail.transport.protocol", "smtp");
		// Verschlüsselung festlegen
		properties.put("mail.smtp.starttls.enable", "true");
		return properties;
	}

	/**
	 * Die Methode erzeugt ein MailAuthenticator Objekt aus den beiden
	 * Parametern user und passwort des Mailaccounts.
	 *
	 * @param user
	 * @param password
	 */
	private static Authenticator getAuthenticator(String user, String password) {
		try {
			Authenticator auth = new Authenticator() {
				/**
				 * Diese Methode gibt ein neues PasswortAuthentication Objekt
				 * zurueck.
				 *
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
}
