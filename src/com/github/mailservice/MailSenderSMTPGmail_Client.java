package com.github.mailservice;

import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * MailService class is to send mail using JavaMail API.
 * 
 * Exception:
 * javax.mail.AuthenticationFailedException: 534-5.7.14 
 * <https://accounts.google.com/signin/continue?sarp=1&scc=1&plt=AKgnsbui
 * 
 * https://support.google.com/mail/answer/78754%20b68sm1935299pfa.127
 * 
 * Solution:
 * GMail « https://myaccount.google.com/lesssecureapps
 * @author yashwanth.m
 *
 */
public class MailSenderSMTPGmail_Client {
	
	private static String TO_ADDRESS = "XXXXXXXXXX@gmail.com";
	
	public static void main(String[] args) {
		
		String subject = "Test Subject";
		String electronic_Message = "MIME message Body - Test Mail using JavaMailAPI";
		String contentType = "HTML"; // plain | HTML | Attached File | Inline File
		
		if( sendMail(subject, contentType, electronic_Message, TO_ADDRESS) ) {
			System.out.println("Sent message successfully.");
		} else {
			System.out.println("message failed.");
		}
	}
	
	public static boolean sendMail(String subject, String contentType, String body, String recipients) {
		
		String mailhost = "smtp.gmail.com", mailport = "587"; // 587 | 465
		// mail.domain.com [587]
		Session mailSession = getSessionObject(mailhost, mailport, true);
		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage( mailSession );
			message.setFrom( new InternetAddress( MailDomain.USER_NAME.getValue() ) );
			
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(TO_ADDRESS));
			
			message.setSubject( subject );
			
			// Multipart Message - [Attached File, In-line Images]
			if( contentType.equalsIgnoreCase("Attached File") ) {
				Multipart multipart = new MimeMultipart();
				
				// Create a mime body and Set Text
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setText( body );
				multipart.addBodyPart( messageBodyPart );

				String filename = "D:\\Test.js";
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

				String filename = "H:\\MailMesageBody.png";
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
				message.setContent( body, "text/plain" ); 
				//(OR) message.setText( body );
			}
			
			// Send message
			Transport transport = mailSession.getTransport("smtps");
			transport.connect(mailhost, MailDomain.USER_NAME.getValue(), MailDomain.PASSWORD.getValue());
			message.saveChanges();
			//transport.send(message);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("Sent message successfully.");
			return true;
		} catch (AddressException ex) {
			System.out.println(ex);
		} catch (MessagingException ex) {
			System.out.println(ex);
		} catch(Exception ex) {
			System.out.println(ex);
		}
		return false;
	}
	
	@SuppressWarnings("restriction")
	public static Session getSessionObject(String mailhost, String mailport, boolean authProps) {
		System.out.println("==- Outgoing Mail (SMTP) Server details like SMTP properties and Authenticate -==");
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", mailhost);
		props.put("mail.smtp.port", mailport);
		
		props.put("mail.smtp.auth", "true");
		if( mailport.equals("587") ) {
			props.put("mail.smtp.starttls.enable", "true");
		} else {
			props.put("mail.smtps.ssl.enable", "true");
		}
		props.put("mail.smtp.socketFactory", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.quitwait", "false");
		
		
		//create Authenticator object to pass in Session.getInstance argument
		Authenticator authenticator = new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
			PasswordAuthentication passwordAuthentication = new PasswordAuthentication(
					MailDomain.USER_NAME.getValue(), MailDomain.USER_NAME.getValue());
			return passwordAuthentication;
			}
		};
		
		// com.sun.mail.smtp.SMTPSendFailedException: 530-5.5.1 Authentication Required.
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		
		// Get the Session object by Authenticating Password.
		if ( authProps ){
			props.put("mail.smtp.user", MailDomain.USER_NAME.getValue());
			props.put("mail.smtp.password", MailDomain.PASSWORD.getValue());
			System.out.println("Session DefaultInstance");
			return Session.getDefaultInstance(props);
			//return Session.getInstance(props);
		}
		System.out.println("Session Instance with Authentication");
		//return Session.getDefaultInstance(props, authenticator);
		return Session.getInstance(props, authenticator);
	}
}