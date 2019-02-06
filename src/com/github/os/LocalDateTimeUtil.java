package com.github.os;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * @author yashwanth.m
 *
 */
public class LocalDateTimeUtil {
	static String pattern = "yyyy-MM-dd HH:mm:ss";
	static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	static DateTimeFormatter dateTimeFormatter = java.time.format.DateTimeFormatter.ofPattern( pattern );
	
	public static void main(String[] args) throws InterruptedException {
		// Epoch time to Date « https://stackoverflow.com/a/34086460/5081877
		long startTime=System.currentTimeMillis();
			
		Thread.sleep( 1000 * 5 );
		
		long endTime=System.currentTimeMillis();
		
		String str = getDateFromEpochTime(startTime);
		CharSequence charSequence = str.subSequence(0, str.length());
		LocalDate startLocalDate = java.time.LocalDate.parse( charSequence, dateTimeFormatter );
		LocalTime startLocalTime = java.time.LocalTime.parse( charSequence, dateTimeFormatter );
		
		String str2 = getDateFromEpochTime(endTime);
		CharSequence charSequence2 = str2.subSequence(0, str2.length());
		LocalDate endLocalDate = java.time.LocalDate.parse( charSequence2, dateTimeFormatter );
		LocalTime endLocalTime = java.time.LocalTime.parse( charSequence2, dateTimeFormatter );
		
		LocalDateTime localDateTime = LocalDateTime.parse( charSequence, dateTimeFormatter);
		
		long timeTaken = endTime-startTime;
		String executionTime = millisToShortDHMS(timeTaken);
		
		System.out.println("Time Taken for execution : "+ executionTime);
		
		/* com.github.jdbc.DBOperations
		 * Start: 1543219484585 Pattern Date: 2018-11-26 13:34:44
		 * HSQL Date : 2018-11-26
		 */
		System.out.println("Start: "+startTime+" Patternd Date: "+ str);
		System.out.println("Date Time : "+ localDateTime); // 2018-11-26T15:29:08
		
		System.out.println("Start DATE: "+startLocalDate+" TIME: "+ startLocalTime);
		System.out.println("end   DATE: "+endLocalDate  +" TIME: "+ endLocalTime  );
		
		String timeStamp = simpleDateFormat.format( Calendar.getInstance().getTime() );
		System.out.println("TimeStamp : "+ timeStamp); // 2018-11-26 15:29:08
		
		
		/*java.sql.Date valueOf = java.sql.Date.valueOf( localDate );
		System.out.println("HSQL Date : "+ valueOf);
		
		// Statement statement = con.createStatement(); - Update date query is not working.
		 * 
		PreparedStatement pstmt = con.prepareStatement( EXECUTION_TIME );
			pstmt.setDate(1, java.sql.Date.valueOf( startLocalDate )); // DATE
			pstmt.setDate(2, java.sql.Date.valueOf( endLocalDate ));
			pstmt.setTime(3, java.sql.Time.valueOf( startLocalTime )); // TIME
			pstmt.setTime(4, java.sql.Time.valueOf( endLocalTime ));
			pstmt.setString(5, executionTime ); // TEXT
		*/
	}
	
	public static String millisToShortDHMS(long duration) {
		String res = "";	// java.util.concurrent.TimeUnit;
		long days		= TimeUnit.MILLISECONDS.toDays(duration);
		long hours		= TimeUnit.MILLISECONDS.toHours(duration) -
						  TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
		long minutes	= TimeUnit.MILLISECONDS.toMinutes(duration) -
						  TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
		long seconds	= TimeUnit.MILLISECONDS.toSeconds(duration) -
						  TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));
		long millis		= TimeUnit.MILLISECONDS.toMillis(duration) - 
						  TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(duration));

		if (days == 0)	res = String.format("%02d:%02d:%02d.%04d", hours, minutes, seconds, millis);
		else			res = String.format("%dd %02d:%02d:%02d.%04d", days, hours, minutes, seconds, millis);
		return res;
	}
	public static String getDateFromEpochTime(long epoch) {
		String date = simpleDateFormat.format(new java.util.Date( epoch ));
		
		System.out.format("Epoch:[%d] Date:[%s]\n", epoch, date );
		return date;
	}
}
/*
Here are a few Java SimpleDateFormat date pattern examples:
http://tutorials.jenkov.com/java-internationalization/simpledateformat.html

Pattern					Example
dd-MM-yy				31-01-12
dd-MM-yyyy				31-01-2012
MM-dd-yyyy				01-31-2012
yyyy-MM-dd				2012-01-31
yyyy-MM-dd HH:mm:ss			2012-01-31 23:59:59
yyyy-MM-dd HH:mm:ss.SSS		2012-01-31 23:59:59.999
yyyy-MM-dd HH:mm:ss.SSSZ	2012-01-31 23:59:59.999+0100
EEEEE MMMMM yyyy HH:mm:ss.SSSZ	Saturday November 2012 10:45:42.720+0100
*/

