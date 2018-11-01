package com.github.coreconcepts;

import java.util.Locale;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class JodaTimeTest {
	public static void main(String[] args) {
		// https://stackoverflow.com/q/52479892/5081877
		DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("hh:mm:ss");
		
		Locale usLocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
		//java.lang.IllegalArgumentException: Invalid format: "6:32:30 PM " is too short
		DateTimeFormatter dateTimeFormatter2 = DateTimeFormat.longTime().withLocale( usLocale );
		
		localDateFormatedTime(dateTimeFormatter);
		localDateFormatedTime(dateTimeFormatter2);
	}
	
	public static void localDateFormatedTime( DateTimeFormatter dateTimeFormatter ) {
		String localTimeString = LocalTime.now().toString(dateTimeFormatter);
		LocalTime localTime = LocalTime.parse(localTimeString, dateTimeFormatter);
		System.out.println("LocalTime : "+ localTime );
	}
}
