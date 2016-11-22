package model;

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

public class SendMailExample {

	public void sendMail(String smtpHost, String username, String password,
			String senderAddress, String recipientsAddress, String subject,
			String text) {

		Authenticator auth = getAuthenticator(username, password);
		Properties properties = getProperties();

		
		// Hier wird mit den Properties und dem Authenticator eine Session erzeugt
		Session session = Session.getDefaultInstance(properties, auth);

		// Message senden
		sendMessage(session, senderAddress, recipientsAddress, subject);

	}
	
	/**
	 * Nachricht wird gesendet
	 * @param session
	 * @param senderAddress
	 * @param recipientsAddress
	 * @param subject
	 */
	public void sendMessage(Session session, String senderAddress,
			String recipientsAddress, String subject) {
		try {
			// Eine neue Message erzeugen
			Message msg = new MimeMessage(session);

			// Hier werden die Absender- und Empf�ngeradressen gesetzt
			msg.setFrom(new InternetAddress(senderAddress, "XXX"));

			// msg.addRecipient(Message.RecipientType.TO, new
			// InternetAddress(recipientsAddress));
			msg.setRecipient(RecipientType.TO, new InternetAddress(
					recipientsAddress));

			// Der Betreff wird gesetzt
			msg.setSubject(subject);

			// multipart message erstellen, in dem Text und Attachment gepuffert
			// werden
			Multipart multipart = new MimeMultipart();

			// Der Text wird gesetzt
			multipart.addBodyPart(getText());

			
			// Text zur Nachricht hinzuf�gen
			msg.setContent(multipart);

			// Senden der Nachricht
			Transport.send(msg);

		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Nachrichtentext wird gesetzt
	 * @return
	 */
	public BodyPart getText() {
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
	 * @return
	 */
	public Properties getProperties() {
		Properties properties = new Properties();
		// Den Properties wird die ServerAdresse hinzugef�gt
		properties.put("mail.smtp.host", "smtp.web.de");
		// !!Wichtig!! Falls der SMTP-Server eine Authentifizierung verlangt
		// muss an dieser Stelle die Property auf "true" gesetzt
		// werden
		properties.put("mail.smtp.auth", "true");
		// Port setzen
		properties.put("mail.smtp.port", "587");
		// Protokoll festlegen
		properties.put("mail.transport.protocol", "smtp");
		// Verschl�sselung festlegen
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
	public Authenticator getAuthenticator(String user, String password) {
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

	public static void main(String[] args) {

		String username = "XXX";
		String password = "XXX";
		String senderAddress = "XXX";// someone@web.de
		String recipientsAddress = "XXX"; // somereceiver@web.de
		String subject = "Testnachricht JAVA";
		String text = "Hallo das ist ein Test mit Javamail!\nWenn Sie den Text lesen koennen hat es funktioniert.";
		String smtpHost = "smtp.web.de";

		new SendMailExample().sendMail(smtpHost, username, password,
				senderAddress, recipientsAddress, subject, text);

	}
}
