package com.github.files;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * <p> The Java Logging API facilitates software servicing and maintenance at customer sites by producing 
 * log reports suitable for analysis by end users, system administrators, field service engineers,
 * and software development teams. The Logging APIs capture information such as security failures,
 * configuration errors, performance bottlenecks, and/or bugs in the application or platform.
 * The core package includes support for delivering plain text or XML formatted log records to memory,
 * output streams,consoles, files, and sockets. In addition, the logging APIs are capable of interacting
 * with logging services that already exist on the host operating system.</p>
 * <p>log4j is a popular Java-based logging utility. Log4j is an open source project based on the work
 * of many authors. It allows the developer to control which log statements are output to a variety of
 * locations by using Appenders [console, files, DB and email].
 * It is fully configurable at runtime using external configuration files.</p>
 * <ul>
 * <li><a href="https://stackoverflow.com/a/47789493/5081877">Log4j</a></li>
 * <li><a href="https://stackoverflow.com/a/30021470/5081877">Log4j2<a> </li>
 * </ul>
 * @author yashwanth.m
 *
 */
public class LogFiles {
	// Define a static logger variable so that it references the Logger instance named "LogFiles".
	static final Logger log = Logger.getLogger( LogFiles.class );
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		System.out.println("CONFIGURATION_FILE « "+LogManager.DEFAULT_CONFIGURATION_FILE);
		System.out.println("DEFAULT_XML_CONFIGURATION_FILE = 'log4j.xml' « Default access modifier");
		
		String fileName = //"";
				//"log4j_External.xml";
				"log4j_External.properties";
		String configurationFile = System.getProperty("user.dir")+"/src/" + fileName;
		// To specify a custom configuration with an external file, https://logging.apache.org/log4j/1.2/manual.html#defaultInit
		// The custom configurator you specify must implement the Configurator interface.
		// when default configuration files "log4j.properties", "log4j.xml" are not available
		// « For "log4j.properties" you can fed to the PropertyConfigurator.configure(java.net.URL) method.
		// « For "log4j.xml" DOMConfigurator will be used.
		// Configuration files can be written in XML or in Java properties (key=value) format.
		if( fileName.contains(".xml") ) {
			DOMConfigurator.configure( configurationFile );
			log.info("Extension *.xml");
		} else if ( fileName.contains(".properties") ) {
			PropertyConfigurator.configure( configurationFile );
			log.info("Extension *.properties");
		} else {
			DailyRollingFileAppender dailyRollingAppender = new DailyRollingFileAppender();
			dailyRollingAppender.setFile("E:/Logs/logRollingDayFile.log");
			dailyRollingAppender.setDatePattern("'_'yyyy-MM-dd");
			
			PatternLayout layout = new PatternLayout();
			layout.setConversionPattern( "%-5p - %d{yyyy-MM-dd HH:mm:ss.SSS} %C{1}:%12.20M:%L - %m %n" );
			dailyRollingAppender.setLayout(layout);
			
			dailyRollingAppender.activateOptions();
			
			Logger rootLogger = Logger.getRootLogger();
			rootLogger.setLevel(Level.DEBUG);
			rootLogger.addAppender(dailyRollingAppender);
			
			log.info("Configuring from Java Class.");
		}
		
		log.info("Console.Message.");
		method2();
		methodException(0);
	}
	
	static void method2() {
		log.info("method2 - Console.Message.");
	}
	static void methodException(int b) {
		try {
			int a = 10/b;
			System.out.println("Result : "+ a);
			log.info("Result : "+ a);
		} catch (Exception ex) { // ArithmeticException: / by zero
			log.error(String.format("\n\tException occurred: %s", stackTraceToString(ex)));
		}
	}
	public static String stackTraceToString(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		return sw.toString();
	}
}
/* » «
ERROR StatusLogger No log4j2 configuration file found. Using default configuration: logging only errors to the console.
Set system property 'log4j2.debug' to show Log4j2 internal initialization logging.

MongoDB - Log Data per each Log Event.
===== ----- =====
{
    "_id" : ObjectId("5a2f91d04f0e3a3848a35010"),
    "timestamp" : ISODate("2017-12-12T08:22:40.905Z"),
    "level" : "INFO",
    "thread" : "main",
    "message" : "Console.Message.",
    "loggerName" : {
        "fullyQualifiedClassName" : "com.github.files.LogFiles",
        "package" : [ 
            "com", 
            "github", 
            "files", 
            "LogFiles"
        ],
        "className" : "LogFiles"
    },
    "fileName" : "LogFiles.java",
    "method" : "main",
    "lineNumber" : "17",
    "class" : {
        "fullyQualifiedClassName" : "com.github.files.LogFiles",
        "package" : [ 
            "com", 
            "github", 
            "files", 
            "LogFiles"
        ],
        "className" : "LogFiles"
    },
    "host" : {
        "process" : "14408@Development-45",
        "name" : "Development-45",
        "ip" : "192.168.56.1"
    }
}
===== ----- =====
*/