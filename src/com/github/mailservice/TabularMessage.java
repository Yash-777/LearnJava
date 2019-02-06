package com.github.mailservice;

import java.nio.file.Files;
import java.nio.file.Paths;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * <a href="https://templates.mailchimp.com/resources/email-client-css-support/">
 * Email Client CSS Support</a>
 * 
 * WRITE:
 * https://stackoverflow.com/questions/40108157/how-to-send-dynamic-html-table-in-email-body-using-java
 * 
 * @author yashwanth.m
 *
 */
public class TabularMessage extends MailSenderSMTPGmail_Client {
	public static void main(String[] args) {
		Session mailSession = MailUtils.getSessionObject(true);
		//String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		
		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage( mailSession );
			message.setFrom( new InternetAddress( MailDomain.USER_NAME.getValue() ) );
			
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(TO_ADDRESS));
			//message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(TO_ADDRESS));
			
			String subject = "REG: Tabular report.";
			message.setSubject( subject  );
			
			String htmlData = 
					new String(Files.readAllBytes(Paths.get("./html/Mail_Templates/ReportsMail.html")));
					// "<h1>This is actual message embedded in HTML tags</h1>";
			
			System.out.println("HTML:"+htmlData);
			
			Multipart multipart = new MimeMultipart();
			
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(htmlData, "text/html");
			multipart.addBodyPart(htmlPart);
			message.setContent( multipart );
			
			// Send message
			MailUtils.getTransportSendMessage(mailSession, message);
		} catch (Exception e) {
		}
	}
}
