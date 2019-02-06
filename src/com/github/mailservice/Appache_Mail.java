package com.github.mailservice;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class Appache_Mail {
	public static void main(String[] args) throws Exception {
		sendSimpleMail();
	}
	public static void sendSimpleMail() throws Exception {
		Email email = new SimpleEmail();
		email.setSmtpPort(587);
		
		DefaultAuthenticator defaultAuthenticator = 
				new DefaultAuthenticator( MailDomain.USER_NAME.getValue(), MailDomain.PASSWORD.getValue() );
		
		email.setAuthenticator( defaultAuthenticator );
		email.setDebug(false);
		email.setHostName( MailDomain.MAIL_SERVER_NAME.getValue() );
		email.setFrom( MailDomain.USER_NAME.getValue() );
		email.setSubject("Hi");
		email.setMsg("This is a test mail ... :-)");
		
		//email.addTo( MailDomain.USER_NAME.getValue(), null );
		
		String[] toAddressList = { MailDomain.USER_NAME.getValue(), MailSenderSMTPGmail_Client.TO_ADDRESS };
		email.addTo( toAddressList );
		
		email.setTLS(true);
		email.setStartTLSEnabled( true );
		email.send();
		System.out.println("Mail sent!");
	}
}
