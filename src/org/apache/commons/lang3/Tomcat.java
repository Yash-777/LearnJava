package org.apache.commons.lang3;

public class Tomcat {
	public static void main(String[] args) {
		// "%CATALINA_HOME%\lib\catalina.jar" org.apache.catalina.util.ServerInfo
		System.out.println("OS Name:        " + System.getProperty("os.name"));
		System.out.println("OS Version:     " + System.getProperty("os.version"));
		System.out.println("Architecture:   " + System.getProperty("os.arch"));
		System.out.println("JVM Version:    " + System.getProperty("java.runtime.version"));
		System.out.println("JVM Vendor:     " + System.getProperty("java.vm.vendor"));
	}
}
