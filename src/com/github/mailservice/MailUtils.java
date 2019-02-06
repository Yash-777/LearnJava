package com.github.mailservice;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class MailUtils {
	public static Properties getMailProperties( boolean addExteraProps ) {
		
		Properties props = new Properties();
		props.put("mail.transport.protocol", MailDomain.MAIL_TRNSPORT_PROTOCOL.getValue());
		props.put("mail.smtp.host", MailDomain.MAIL_SERVER_NAME.getValue());
		props.put("mail.smtp.port", MailDomain.MAIL_PORT.getValue());
		
		// Sending Email to the GMail SMTP server requires authentication and SSL.
		props.put("mail.smtp.auth", true);
		if( MailDomain.ENCRYPTION_METHOD.getValue().equals("STARTTLS") ) {
			props.put("mail.smtp.starttls.enable", true);
			props.put("mail.smtp.socketFactory.port", MailDomain.SMTP_STARTTLS_PORT.getValue()); // 587
		} else {
			props.put("mail.smtps.ssl.enable", true);
			props.put("mail.smtp.socketFactory.port", MailDomain.SMTP_SSL_PORT.getValue()); // 465
		}
		
		props.put("mail.smtp.socketFactory", MailDomain.SOCKETFACTORY_CLASS.getValue());
		
		if ( addExteraProps ) {
			props.put("mail.smtp.socketFactory.fallback", "false");
			props.put("mail.smtp.debug", "true");
			props.put("mail.smtp.quitwait", "false");
		}
		
		return props;
	}
	
	// https://stackoverflow.com/questions/13854037/send-mail-to-multiple-recipients-in-java
	public static void setRecipients(Message message, Object addresslist) throws AddressException, MessagingException {
		if ( addresslist instanceof String ) {
			System.out.println("CharSequence « String with collection of chars");
			// Comma separated address strings « http://jkorpela.fi/rfc/822addr.html
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse( (String) addresslist  ));
		} else if ( addresslist instanceof String[] ) {
			System.out.println("String[] « Array with collection of Strings");
			String[] toAddressList = (String[]) addresslist;
			InternetAddress[] mailAddress_TO = new InternetAddress[ toAddressList.length ];
			for (int i = 0; i < toAddressList.length; i++) {
				mailAddress_TO[i] = new InternetAddress( toAddressList[i] );
			}
			message.setRecipients(Message.RecipientType.TO, mailAddress_TO);
		}
	}
	
	public static Session getSessionObejct() {
		Properties props = getMailProperties( false );
		Session session = Session.getInstance(props, null);
		session.setDebug(true);
		return session;
	}
	
	public static Session getSessionObject(boolean authProps) {
		System.out.println("==- Outgoing Mail (SMTP) Server details like SMTP properties and Authenticate -==");
		Properties props = getMailProperties( true );
		
		//create Authenticator object to pass in Session.getInstance argument
		Authenticator authenticator = new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
			PasswordAuthentication passwordAuthentication = new PasswordAuthentication(
					MailDomain.USER_NAME.getValue(), MailDomain.PASSWORD.getValue());
			return passwordAuthentication;
			}
		};
		
		// com.sun.mail.smtp.SMTPSendFailedException: 530-5.5.1 Authentication Required.
		//Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		
		// Get the Session object by Authenticating Password.
		if ( authProps ){
			props.put("mail.smtp.user", MailDomain.USER_NAME.getValue());
			props.put("mail.smtp.password", MailDomain.PASSWORD.getValue());
			System.out.println("Session DefaultInstance");
			return Session.getDefaultInstance(props, null);
			//return Session.getInstance(props);
		}
		System.out.println("Authenticated the client connect");
		//return Session.getDefaultInstance(props, authenticator);
		return Session.getInstance(props, authenticator);
	}
	
	public static boolean getTransportSendMessage( Session mailSession, Message message ) {
		try {
			Transport transport = mailSession.getTransport( MailDomain.MAIL_TRNSPORT_PROTOCOL.getValue() );
			transport.connect(MailDomain.MAIL_SERVER_NAME.getValue(),
					Integer.valueOf(MailDomain.MAIL_PORT.getValue()),
					MailDomain.USER_NAME.getValue(), MailDomain.PASSWORD.getValue());
			message.saveChanges(); // don't forget this
			
			//transport.send(message);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("Sent message successfully.");
			return true;
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return false;
	}
}