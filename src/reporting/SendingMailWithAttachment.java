package reporting;

import java.io.IOException;
import java.util.Date;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import smtp.msg.info.SmtpMsgInfo;

public class SendingMailWithAttachment {

	public static void sendEmailWithAttachments(String host, String port, final String userName, final String password,
			String toAddress, String subject, String message, String[] attachFiles)
			throws AddressException, MessagingException {

		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};
		Session session = Session.getInstance(SmtpMsgInfo.properties, auth);

		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(userName));
		InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject);
		msg.setSentDate(new Date());

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");
		
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// adds attachments
		if (attachFiles != null && attachFiles.length > 0) {
			for (String filePath : attachFiles) {
				MimeBodyPart attachPart = new MimeBodyPart();

				try {
					attachPart.attachFile(filePath);
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				multipart.addBodyPart(attachPart);
			}
		}

		msg.setContent(multipart);
		Transport.send(msg);
	}

	public static void main(String[] args) {
		try {
			SmtpMsgInfo.setProperties();
			sendEmailWithAttachments(SmtpMsgInfo.host, SmtpMsgInfo.port, SmtpMsgInfo.mailFrom, SmtpMsgInfo.password,
					SmtpMsgInfo.mailTo, SmtpMsgInfo.subject, SmtpMsgInfo.message, SmtpMsgInfo.getAttachments());
			System.out.println("Email sent.");
		} catch (Exception ex) {
			System.out.println("Could not send email.");
			ex.printStackTrace();
		}
	}
}
