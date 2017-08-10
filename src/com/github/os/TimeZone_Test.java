package com.github.os;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * https://stackoverflow.com/questions/7707555/getting-date-in-http-format-in-java
 * @author yashwanth.m
 *
 */
public class TimeZone_Test {
	static String datePattern = "EEE, dd MMM yyyy HH:mm:ss 'GMT'",
			defaultZoneDatePattern = "EEE, dd MMM yyyy HH:mm:ss z";
		
	public static void main(String[] args) {
		jodeDateTime();
		
		ZonedDateTime now = ZonedDateTime.now(ZoneId.of("GMT"));
		String format = java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME.format(now);
		System.out.println("Date Time Formater : "+ format);
	}
	
	static void jodeDateTime() {
		DateTimeFormatter RFC1123_DATE_TIME_FORMATTER = DateTimeFormat.forPattern(datePattern)
				.withZoneUTC()
				.withLocale(Locale.US);
		
		String dateTime = RFC1123_DATE_TIME_FORMATTER.print(new DateTime());
		System.out.println("Joda Time : "+ dateTime); // Wed, 21 Jun 2017 09:02:20 GMT
	}
	
	static void apacheDateUtils() {
		Date date = new Date(System.currentTimeMillis());
		String formatDate = org.apache.http.client.utils.DateUtils.formatDate(date);
		System.out.println("Apache Client Date : "+ formatDate);// Wed, 21 Jun 2017 08:46:45 GMT
	}
	
	// https://stackoverflow.com/a/36014624/5081877
	static void javaTimeFormatter() {
		java.time.Instant instant = java.time.Instant.now();
		String formatted = java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME
						.withZone(java.time.ZoneOffset.UTC)
						.format(instant);
		System.out.println("Java Time : "+formatted);// Wed, 21 Jun 2017 08:36:27 GMT
	}
	
	// http://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
	static void calenderSimpleDate() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(defaultZoneDatePattern);
		System.out.println("Date: " + dateFormat.format(calendar.getTime()));
		// Wed, 21 Jun 2017 14:10:27 IST
	}
}
