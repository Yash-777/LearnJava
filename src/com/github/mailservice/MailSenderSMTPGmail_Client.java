package com.github.mailservice;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * MailService class is to send mail using <a href=
 * "https://cloud.google.com/appengine/docs/standard/java/mail/sending-mail-with-mail-api">JavaMail API</a>.
 * <br />
 * Exception: javax.mail.AuthenticationFailedException: <a href= 
 * "https://accounts.google.com/signin/continue?sarp=1&scc=1&plt=AKgnsbui">534-5.7.14</a><br />
 * <p>Solution: GMail <a href="https://myaccount.google.com/lesssecureapps">Less secure apps</a> access.
 * <br />
 * Sending Mail with the<a href="https://cloud.google.com/appengine/docs/standard/java/mail/sending-mail-with-mail-api">
 *  Mail API</a>
 * </p>
 * 
 * @author yashwanth.m
 *
 */
public class MailSenderSMTPGmail_Client {
	
	static String TO_ADDRESS = "yashwanth2357@gmail.com";
	
	public static void main(String[] args) {
		
		String subject = "Test Subject";
		String electronic_Message = "MIME message Body - Test Mail using JavaMailAPI";
		String contentType = "HTML"; // plain | HTML | Attached File | Inline File
		
		//String toAddressList = MailDomain.USER_NAME.getValue();
		String[] toAddressList = { MailDomain.USER_NAME.getValue() };
		
		if( sendMail(subject, contentType, electronic_Message, toAddressList) ) {
			System.out.println("Sent message successfully.");
		} else {
			System.out.println("message failed.");
		}
	}
	
	public static void sendMail( String subject, String msg, Object recipients ) {
		try {
			Properties props = MailUtils.getMailProperties( false );
			Session mailSession = Session.getDefaultInstance(props, null);
			Message message = new MimeMessage( mailSession );
			message.setFrom( new InternetAddress( MailDomain.USER_NAME.getValue() ) );
			
			MailUtils.setRecipients(message, recipients);
			
			message.setSubject( subject );
			
			//String htmlData = "<h1>This is actual message embedded in HTML tags</h1>";
			
			message.setContent( msg, "text/html");
			MailUtils.getTransportSendMessage(mailSession, message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean sendMail(String subject, String contentType, String msg, Object recipients) {
		
		Session mailSession = MailUtils.getSessionObject(true);
		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage( mailSession );
			message.setFrom( new InternetAddress( MailDomain.USER_NAME.getValue() ) );
			
			MailUtils.setRecipients(message, recipients);
			
			message.setSubject( subject );
			
			// Multipart Message - [Attached File, In-line Images]
			if( contentType.equalsIgnoreCase("Attached File") ) {
				Multipart multipart = new MimeMultipart();
				
				// Create a mime body and Set Text
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setText( msg );
				multipart.addBodyPart( messageBodyPart );

				String filename = "./books.xml";
				// Part two is attachment
				BodyPart  fileBodyPart = new MimeBodyPart();
				DataSource source = new javax.activation.FileDataSource( filename );
				fileBodyPart.setDataHandler( new DataHandler( source ) );
				fileBodyPart.setFileName( filename );
				multipart.addBodyPart( fileBodyPart );

				//http://www.oracle.com/technetwork/java/javamail/faq/index.html#overridefiletype
				// Send the complete message parts
				message.setContent( multipart, Part.ATTACHMENT ); // "multipart/*"
			} else if( contentType.equalsIgnoreCase("Inline File") ) {
				Multipart multipart = new MimeMultipart();
				
				// Create a mime body and Set Text
				BodyPart messageBodyPart = new MimeBodyPart();
				String htmlText = "<H1>Hello</H1><img src=\"cid:image\">";// Get from Header
				messageBodyPart.setContent(htmlText, "text/html");
				multipart.addBodyPart( messageBodyPart );

				String filename = "./Wiki_Images/MailMesageBody.png";
				// Part two is attachment
				BodyPart  fileBodyPart = new MimeBodyPart();
				DataSource source = new javax.activation.FileDataSource( filename );
				fileBodyPart.setDataHandler( new DataHandler( source ) );
				String header_name = "Content-ID", header_value = "<image>";
				fileBodyPart.setHeader(header_name, header_value);
				multipart.addBodyPart( fileBodyPart );

				// Send the complete message parts
				message.setContent( multipart, Part.INLINE );
			} else if ( contentType.equalsIgnoreCase("HTML") ) {
				String htmlData = "<h1>This is actual message embedded in HTML tags</h1>";
				message.setContent( htmlData, "text/html");
			} else { //Simple Message - [Plain Text | HTML Content]
				String header_name = "Content-ID", header_value = "<b>";
				message.addHeader( header_name, header_value );
				message.setContent( msg, "text/plain" ); 
				//(OR) message.setText( body );
			}
			
			return MailUtils.getTransportSendMessage(mailSession, message);
		} catch (AddressException ex) {
			System.out.println(ex);
		} catch (MessagingException ex) {
			System.out.println(ex);
		} catch(Exception ex) {
			System.out.println(ex);
		}
		return false;
	}
	
}