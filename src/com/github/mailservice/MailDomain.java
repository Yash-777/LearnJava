package com.github.mailservice;

public enum MailDomain {
	USER_NAME("XXXXXXXXX@gmail.com"),
	PASSWORD("XXXXXXXX");
	
	private String value;
	MailDomain(final String value) {	this.value = value;	}

	public String getValue() {	return value;	}
	
}
