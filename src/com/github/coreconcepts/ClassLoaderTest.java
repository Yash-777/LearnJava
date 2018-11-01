package com.github.coreconcepts;

/**
 * http://a141.idata.over-blog.com/320x221/4/91/54/42/a1-copy-4.jpg
 * @author yashwanth.m
 *
 */
public class ClassLoaderTest {
	public static void main(String[] args) {
		System.out.println("JVM ClassLoader's");
		/* Bootstrap Class Loader – It loads JDK internal classes, typically loads rt.jar"
		 * and other core classes for example java.lang.* package classes.
		 */
		System.out.println("Core Java libraries HashMap « "
				+ java.util.HashMap.class.getClassLoader());
		
		/* Extensions Class Loader – It loads classes from the JDK extensions directory, 
		 * usually $JAVA_HOME/lib/ext directory.
		 * C:\Program Files (x86)\Java\jdk1.7.0_80\jre\lib\ext\dnsns.jar
		 */
		System.out.println("Extension libraries DNSNameService « "
				+ sun.net.spi.nameservice.dns.DNSNameService.class.getClassLoader());
		
		/* System Class Loader – It loads classes from the current classpath that can be 
		 * set while invoking a program using -cp or -classpath command line options.
		 */
		System.out.println("Build|Class Path libraries this class « "
				+ ClassLoaderTest.class.getClassLoader());
	}
}
/*
JVM ClassLoader's
Core Java libraries HashMap « null
Extension libraries DNSNameService « sun.misc.Launcher$ExtClassLoader@311d617d
Build|Class Path libraries this class « sun.misc.Launcher$AppClassLoader@18b4aac2
*/