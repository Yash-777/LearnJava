package com.github.java8;

import java.net.URL;

/**
 * http://docs.oracle.com/javase/9/migrate/toc.htm#JSMIG-GUID-A868D0B9-026F-4D46-B979-901834343F9E
 * 
 * @author yashwanth.m
 *
 */
public class ClassLoaderExample {
	public static void main(String[] args) {
		URL systemResource = ClassLoader.getSystemResource("java/lang/Class.class");
		
		// When run on JDK 8, this method returns a JAR URL of the form:
		System.out.println("Java 8 : "+ systemResource);
		// Java 8 : jar:file:/C:/Program%20Files/Java/jre1.8.0_144/lib/rt.jar!/java/lang/Class.class
	}
}
