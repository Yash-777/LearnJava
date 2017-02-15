package com.github.jars;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The jar command is a general-purpose archiving and compression tool, based on ZIP 
 * and the ZLIB compression format. However, the jar command was designed mainly to package Java applets
 * or applications into a single archive.
 * <P>JAR archive containing class and resource files for the application, 
 * with the startup class indicated by the Main-Class manifest header.
 * 
 * <P> Jar File Name : LearnJava.jar
 * <UL> MANIFEST.MF
 * <LI>Manifest-Version: 1.0</LI>
 * <LI><a href='http://www.java67.com/2014/04/how-to-make-executable-jar-file-in-Java-Eclipse.html'>
 * Main-Class</a>: com.github.jars.RemoveHardCode</LI>
 * </UL>
 * 
 * <UL> Remove HardCode in you program through any of the following.
 * <LI> <a href ='https://docs.oracle.com/javase/tutorial/essential/environment/cmdLineArgs.html'>
 * Command-Line Arguments</a> A Java application can accept any number of arguments from the command line.
 * This allows the user to specify configuration information when the application is launched.</LI>
 * <LI> <a href ='https://docs.oracle.com/javase/tutorial/essential/environment/env.html'>
 * Environment Variables</a> Many operating systems use environment variables to pass 
 * configuration information to applications. Like properties in the Java platform, 
 * environment variables are key/value pairs, where both the key and the value are strings.</LI>
 * <LI> <a href='https://docs.oracle.com/javase/tutorial/essential/environment/properties.html'>
 * Properties</a> Properties are configuration values managed as key/value pairs. In each pair, 
 * the key and value are both String values. The key identifies, and is used to retrieve, 
 * the value, much as a variable name is used to retrieve the variable's value. </LI>
 * <LI> <a href='https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html'>
 * System Properties</a> The System class maintains a Properties object that defines the 
 * configuration of the current working environment.</LI>
 * <LI>XML Files - Used for server applications.
 * </UL>
 * 
 * <P><a href="https://docs.oracle.com/javase/8/docs/technotes/tools/windows/jar.html">
 * Java Archive (JAR) files</a>
 * @author yashwanth.m
 *
 */
public class RunJARFile {

	/**
	 * If we need a client to start.
	 * <P>NOTE: if you are using new client then we cannot read the client 'STREAM'.
	 * <UL>
	 * <LI>CMD.exe can be found at %windir%\SysWoW64\cmd.exe</LI>
	 * <LI>/C « Run Command and then terminate.</LI>
	 * </UL>
	 */
	static String newClient = "cmd.exe /c start ";
	
	/**
	 * The ProcessBuilder.start() and Runtime.exec methods create a native process and 
	 * return an instance of a subclass of Process that can be used to control the process 
	 * and obtain information about it. 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		runJarUsingParams();
		
		String command = "ping www.github.com";
		commandsOfOS(newClient, command);
	}
	private static void commandsOfOS(String newClient, String command) {
		try {
			Process process = Runtime.getRuntime().exec( newClient+command);
			InputStream inputStream = process.getInputStream();
			
			SubProcessThread inputConsumer = new SubProcessThread( inputStream, true );
			inputConsumer.start();
			
			int status = process.waitFor();
			System.out.println("Exited with status: " + status);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void runJarUsingParams() {
		String jarLocation = "D:\\JarFiles\\LearnJava.jar";
		
		List<String> commandLineArguments = new ArrayList<String>();
		commandLineArguments.add("args1");
		commandLineArguments.add("args2");
		commandLineArguments.add("args3");
		Map<String, String> systemProperties = new HashMap<String, String>();
		systemProperties.put("key1", "value1");
		systemProperties.put("key2", "value2");
		systemProperties.put("key3", "value3");
		systemProperties.put("key4", "value4");
		
		System.out.println("Command-Line Arguments : "+ commandLineArgumentsList(commandLineArguments));
		System.out.println("Environment Variables"+ systemPropertiesMap(systemProperties));
		runtime(jarLocation, commandLineArguments, systemProperties);
		builder(jarLocation, commandLineArguments, systemProperties);
	}
	
	public static void list2StringArray(List<String> commandLineArguments ) {
		Object[] arguments = commandLineArguments.toArray();
		String[] strCopyArguments = Arrays.copyOf(arguments, arguments.length, String[].class);
		System.out.println("Command-Line Arguments : "+ strCopyArguments.toString());
		for(String sc : strCopyArguments)
			System.out.println(sc);
		
		String[] strArrayArguments = new String[commandLineArguments.size()];
		strArrayArguments = commandLineArguments.toArray(strArrayArguments);
		System.out.println("Command-Line Arguments : "+ strArrayArguments.toString());
		for(String sa : strArrayArguments)
			System.out.println(sa);
		
		String[] arr = commandLineArguments.toArray(new String[commandLineArguments.size()]);
		System.out.println("Command-Line Arguments : "+ arr.toString());
		for(String sa : arr)
			System.out.println(sa);
	}
	
	public static String commandLineArgumentsList(List<String> commandLineArguments) {
		return commandLineArguments.toString().replaceAll("\\[|,|\\]", "");
	}
	
	public static String systemPropertiesMap( Map<String, String> systemProperties ) {
		return systemProperties.toString().replaceAll("\\{|[,]", " -D")
				.replaceAll("\\-D ", "-D").replaceAll("}", "");
	}
	
	public static void runtime(String jarLocation, 
			List<String> commandLineArguments, Map<String, String> systemProperties ) {
		
		String runCommand = "java "
				+ systemPropertiesMap( systemProperties )
				+ " -jar " + jarLocation + " "
				+ commandLineArgumentsList( commandLineArguments );
		System.out.println("Run Command : ["+runCommand+"]");
		try {
			Process process = Runtime.getRuntime().exec( runCommand );
			
			Thread.sleep( 1000 * 5 );
			process.destroy();
			
			InputStream inputStream = process.getInputStream();
			SubProcessRunnable spr = new SubProcessRunnable( inputStream );
			Thread thread = new Thread(spr, "StreamReader_ThreadID");
			thread.start();
			
			int status = process.waitFor();
			System.out.println("Exited with status: " + status);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * The ProcessBuilder.start() and Runtime.exec methods create a native process and 
	 * return an instance of a subclass of Process that can be used to control the process 
	 * and obtain information about it. 
	 * 
	 * <P> waitfor() : Causes the current thread to wait until the process object terminates
	 * <P> the subprocess represented by this Process object will return some exit values, 
	 * based on the native code - getStillActive() method.
	 * <UL> exit values of subprocess based on native code.
	 * <LI> 0 « normal termination.
	 * <LI> 1 « abnormal termination.
	 * 
	 * "com.github.hardcode.RemoveHardCode"
	 * 
	 * @param jarLocation
	 * @param commandLineArguments
	 * @param systemProperties
	 */
	public static void builder(String jarLocation, 
			List<String> commandLineArguments, Map<String, String> systemProperties ) {
		String runCommand = "java "
				+ systemPropertiesMap( systemProperties )
				+ " -jar " + jarLocation + " "
				+ commandLineArgumentsList( commandLineArguments );
		System.out.println("Run Command : ["+runCommand+"]");
		ProcessBuilder builder = new ProcessBuilder( Arrays.asList( runCommand.split(" ") ) );
		try {
			Process process = builder.start();
			
			InputStream inputStream = process.getInputStream();
			
			SubProcessThread inputConsumer = new SubProcessThread( inputStream, true );
			inputConsumer.start();
			
			Thread.sleep( 1000 * 5 );
			process.destroy();
			
			int exitValue = process.waitFor();
			if (exitValue != 0) {
				System.out.println("Abnormal process termination");
			}
			System.out.println("Exited with status: " + exitValue);
			
			String processOuput = inputConsumer.output.toString();
			System.out.println("OUT PUT «\n" + processOuput );
			
			//System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
