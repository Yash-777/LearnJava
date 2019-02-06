package com.github.mailservice;

/**
 * To send a mail need the following details for Outgoing Mail (SMTP) Server:
 * <a href="https://support.google.com/mail/?p=BadCredentials">smtp.gmail.com</a>
 * <br />
 * Gmail POP3, IMAP and SMTP settings 
 * <a href="https://www.hesk.com/knowledgebase/index.php?article=72">hesk.com</a>
 * <table border="1"> <tbody>
 * <tr><td>Setting</td><td>IMAP (incoming)</td><td>POP3 (fetching)</td><td>SMTP (outgoing)</td></tr>
 * <tr><td>GMAIL</td><td>imap.gmail.com</td><td>pop.gmail.com</td><td>smtp.gmail.com</td></tr>
 * <tr><td>MicroSoft</td><td>outlook.office365.com</td><td>outlook.office365.com</td><td>smtp.office365.com</td></tr>
 * <tr><td>Port Number</td><td>993</td><td>995</td><td>Port for SSL: 465<br />Port for TLS/STARTTLS: 587</td></tr>
 * </tbody> </table>
 * <br />
 * <p>If you get an error from Google you might also need to enable 
 * <a href="https://myaccount.google.com/lesssecureapps">"less secure apps"</a> in your Google account.
 * </p>
 * @author yashwanth.m
 *
 */
public enum MailDomain {
	/*
	MAIL_SERVER_NAME("smtp.gmail.com"),
	USER_NAME("XXXXXXXXX@gmail.com"),
	PASSWORD("XXXXXXXX");*/
	
	MAIL_SERVER_NAME("smtp.office365.com"),
	USER_NAME("automation@clicqa.com"),
	PASSWORD("Infotree@123"),
	
	SMTP_SSL_PORT("465"),
	SMTP_STARTTLS_PORT("587"), POP3_PORT("995"), IMAP_PORT("993"),
	SMTP_HOST("smtp.gmail.com"), POP3_HOST("pop.gmail.com"), IMAP_HOST("imap.gmail.com"),
	
	MAIL_PORT( SMTP_STARTTLS_PORT.getValue() ),
	SOCKETFACTORY_CLASS("javax.net.ssl.SSLSocketFactory"),
	ENCRYPTION_METHOD("STARTTLS"), // TLS|STARTTLS, SSL
	MAIL_TRNSPORT_PROTOCOL("smtp"), // SMTP, IMAP, POP3
	;
	
	private String value;
	MailDomain(final String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
