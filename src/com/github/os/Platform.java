package com.github.os;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

import com.github.jars.ZIPExtracter;

/**
 * https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html
 * 
 * @author yashwanth.m
 *
 */
public class Platform {
	
	static String OS, jvmBitVersion;
	static String discard, major, minor, update, build, vmVersion;
	static String userName, userHome;
	protected static String userDir;
	static String driverOS;
	
	static String[] classpathEntries;
	
	static ClassLoader classLoader;
	static ProtectionDomain protectionDomain;
	static {
		classLoader = Platform.class.getClassLoader();
		protectionDomain = ZIPExtracter.class.getProtectionDomain();
	}
	@Override
	public String toString() {
		return "Platform [getOS()=" + getOS() + ", getBitVersion()="
				+ getBitVersion() + ", getJavaVersion()=" + getJavaVersion()
				+ ", getJavaElements()=" + getJavaElements()
				+ ", getUsersHome()=" + getUsersHomeDir()
				+ ", getSeleniumOSDriver()=" + getSeleniumOSDriver() + "]";
	}

	/**
	 * https://java.com/en/download/help/version_manual.xml
	 * http://stackoverflow.com/questions/2591083/getting-version-of-java-in-runtime
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println( new Platform().toString() );
		
	}
	
	public void ClassPath_Lists() {
		// [file.separator = \], [path.separator = ;] 
		String classpath = System.getProperty("java.class.path");
		classpathEntries = classpath.split( File.pathSeparator );
		System.out.println("Class Path Entries : " + classpath);
		
		final File codePath = new File( protectionDomain.getCodeSource().getLocation().getPath() );
		System.out.println("Source CODE Path : "+codePath);
	}
	
	/**
	 * Get OS Name (Like: Windows 7, Windows 8, Windows XP, etc.)
	 *
	 * <P> This set of system properties always includes values 
	 * for the following keys: 
	 * <table summary="Shows property keys and associated values">
	 * <tr><th>Key</th>
	 *     <th>Description of Associated Value</th></tr>
	 * <tr><td><code>java.version</code></td>
	 *     <td>Java Runtime Environment version</td></tr>
	 * <tr><td><code>java.vendor</code></td>
	 *     <td>Java Runtime Environment vendor</td></tr
	 * <tr><td><code>java.vendor.url</code></td>
	 *     <td>Java vendor URL</td></tr>
	 * <tr><td><code>java.home</code></td>
	 *     <td>Java installation directory</td></tr>
	 * <tr><td><code>java.vm.specification.version</code></td>
	 *     <td>Java Virtual Machine specification version</td></tr>
	 * <tr><td><code>java.vm.specification.vendor</code></td>
	 *     <td>Java Virtual Machine specification vendor</td></tr>
	 * <tr><td><code>java.vm.specification.name</code></td>
	 *     <td>Java Virtual Machine specification name</td></tr>
	 * <tr><td><code>java.vm.version</code></td>
	 *     <td>Java Virtual Machine implementation version</td></tr>
	 * <tr><td><code>java.vm.vendor</code></td>
	 *     <td>Java Virtual Machine implementation vendor</td></tr>
	 * <tr><td><code>java.vm.name</code></td>
	 *     <td>Java Virtual Machine implementation name</td></tr>
	 * <tr><td><code>java.specification.version</code></td>
	 *     <td>Java Runtime Environment specification  version</td></tr>
	 * <tr><td><code>java.specification.vendor</code></td>
	 *     <td>Java Runtime Environment specification  vendor</td></tr>
	 * <tr><td><code>java.specification.name</code></td>
	 *     <td>Java Runtime Environment specification  name</td></tr>
	 * <tr><td><code>java.class.version</code></td>
	 *     <td>Java class format version number</td></tr>
	 * <tr><td><code>java.class.path</code></td>
	 *     <td>Java class path</td></tr>
	 * <tr><td><code>java.library.path</code></td>
	 *     <td>List of paths to search when loading libraries</td></tr>
	 * <tr><td><code>java.io.tmpdir</code></td>
	 *     <td>Default temp file path</td></tr>
	 * <tr><td><code>java.compiler</code></td>
	 *     <td>Name of JIT compiler to use</td></tr>
	 * <tr><td><code>java.ext.dirs</code></td>
	 *     <td>Path of extension directory or directories</td></tr>
	 * <tr><td><code>os.name</code></td>
	 *     <td>Operating system name</td></tr>
	 * <tr><td><code>os.arch</code></td>
	 *     <td>Operating system architecture</td></tr>
	 * <tr><td><code>os.version</code></td>
	 *     <td>Operating system version</td></tr>
	 * <tr><td><code>file.separator</code></td>
	 *     <td>File separator ("/" on UNIX)</td></tr>
	 * <tr><td><code>path.separator</code></td>
	 *     <td>Path separator (":" on UNIX)</td></tr>
	 * <tr><td><code>line.separator</code></td>
	 *     <td>Line separator ("\n" on UNIX)</td></tr>
	 * <tr><td><code>user.name</code></td>
	 *     <td>User's account name</td></tr>
	 * <tr><td><code>user.home</code></td>
	 *     <td>User's home directory</td></tr>
	 * <tr><td><code>user.dir</code></td>
	 *     <td>User's current working directory</td></tr>
	 * </table>
	 *</P>
	 * @return the name of platform.
	 */
	public String getOS() {
		OS = System.getProperty("os.name").toUpperCase();
		System.out.println("SystemArchitecture : "+ System.getProperty("os.arch"));
		System.out.println("BIT Version : "+ System.getProperty("os.version"));
		return OS;
	}
	
	/**
	 * JRE architecture bit version - i.e 64 bit or 32 bit JRE
	 * @return the JRE bit version.
	 */
	public String getBitVersion() {
		jvmBitVersion = System.getProperty("sun.arch.data.model");
		return jvmBitVersion;
		
	}
	/**
	 * 
	 * @return the java major version number.
	 */
	public Integer getJavaVersion() {
		major = System.getProperty("java.vm.specification.version").split("\\.")[1];
		return Integer.valueOf( major );
	}
	
	public Map<String, String> getJavaElements() { // 1.7.0_79-b15
		String[] javaVersionElements = System.getProperty("java.runtime.version").split("\\.|_|-b");
		
		Map<String, String> elements = new HashMap<String, String>();
		elements.put(discard, discard = javaVersionElements[0]);
		elements.put(major,   major   = javaVersionElements[1]);
		elements.put(minor,   minor   = javaVersionElements[2]);
		elements.put(update,  update  = javaVersionElements[3]);
		elements.put(build,   build   = javaVersionElements[4]);
		elements.put(vmVersion, vmVersion = System.getProperty("java.vm.version"));
		
		return elements;
	}

	/**
	 * To support all platforms we are replacing with File separator ("/").
	 * @return	returns user home directory path.
	 */
	public String getUsersHomeDir() {
		userHome = System.getProperty("user.home").replace("\\", "/"); // to support all platforms.
		userName = System.getProperty("user.name");
		userDir  = System.getProperty("user.dir");
		return userHome;
	}
	/**
	 * Checks the directory already exists or not. if the directory does not exist, create it.
	 * 
	 * <P> http://stackoverflow.com/questions/3634853/how-to-create-a-directory-in-java
	 * @param directoryName the name of the folder, you want to create.
	 */
	public void createFolder( String directoryName ) {
		int javaVersion = getJavaVersion();
		String path = getUsersHomeDir() + File.separator + directoryName;
		if ( javaVersion <= 6 ) {
			File theDir = new File( path );
			try {
				if ( !theDir.exists() ) {
					System.out.println("creating directory: " + directoryName);
					theDir.mkdir();
				}
			} catch(SecurityException se) {
				try {
					FileUtils.forceMkdir( theDir );
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				Files.createDirectories(Paths.get( path ));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getSeleniumOSDriver() {
		if ( OS.indexOf("WIN") >= 0 ) {
			driverOS = "win";
		} else if ( OS.indexOf("MAC") >= 0 ) {
			driverOS = "mac";
		} else if ( OS.indexOf("NIX") >= 0 || OS.indexOf("NUX") >= 0 || OS.indexOf("AIX") > 0 ) {
			driverOS = "linux";
		}else {
			System.out.println("Your OS is not support to get Driver!!");
		}
		return driverOS;
	}
	
	public void printPropertiesList() {
		for (Entry<Object, Object> e : System.getProperties().entrySet()) {
			System.out.println(String.format("%s = %s", e.getKey(), e.getValue())); 
		}
	}
}