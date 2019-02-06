package com.github.os;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Convert seconds to HH:MM:SS
 * https://stackoverflow.com/a/24385265/5081877
 * @author yashwanth.m
 *
 */
public class TimeUint_Converter {
	public static void main(String[] args) {
		long seconds = TimeUnit.MINUTES.toSeconds(8);
		System.out.println(" MINUTES.toSeconds : "+seconds);
		
		long sec_duration = 545488745L;
		System.out.println("DD:"+TimeUnit.SECONDS.toDays(sec_duration));
		
		System.out.println("HH:"+TimeUnit.SECONDS.toHours(sec_duration));
		System.out.println("MM:"+TimeUnit.SECONDS.toMinutes(sec_duration));
		System.out.println("SS:"+TimeUnit.SECONDS.toSeconds(sec_duration));
		
		System.out.println("Time Zone : "+ convertSecond_to_TimeZone(sec_duration));
		
		ConvertSecondToHHMMSS(sec_duration);
		
		System.out.println("TIme : "+ secToTime( (int)sec_duration ));
	}
	
	static String convertSecond_to_TimeZone(long secondsDuration) {
		TimeZone tz = TimeZone.getTimeZone("UTC");
		SimpleDateFormat df = new SimpleDateFormat("MM dd HH:mm:ss");
		df.setTimeZone(tz);
		
		Date date = new Date(secondsDuration*1000L); //new Date(System.currentTimeMillis());
		String time = df.format(date);
	
		return time;
	}
	
	// Java8 - https://stackoverflow.com/a/24385265/5081877
	public static String ConvertSecondToHHMMSS(long sec_duration) {
		String datePattern = "HH:mm:ss";
		DateTimeFormatter RFC1123 = DateTimeFormatter.ofPattern(datePattern);
		String timeJava8 = LocalTime.MIN.plusSeconds( sec_duration ).format(RFC1123).toString();
		System.out.println("Java 8 : "+ timeJava8);
		return timeJava8;
	}
	
	/**
	 * Test : https://www.tools4noobs.com/online_tools/seconds_to_hh_mm_ss/
	 * Result: 00:00:36. - 36
	 * Result: 01:00:07. - 3607
	 * Result: 6313 days 12:39:05. - 545488745
	 * @param sec
	 * @return
	 */
	static String secToTime(int sec) {
		int seconds = sec % 60;
		int minutes = sec / 60;
		if (minutes >= 60) {
			int hours = minutes / 60;
			minutes %= 60;
			if( hours >= 24) {
				int days = hours / 24;
				return String.format("%d days %02d:%02d:%02d.", days,hours%24, minutes, seconds);
			}
			return String.format("%02d:%02d:%02d", hours, minutes, seconds);
		}
		return String.format("00:%02d:%02d", minutes, seconds);
	}
}
