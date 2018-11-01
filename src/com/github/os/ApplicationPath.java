package com.github.os;

import java.io.File;

/**
 * https://docs.oracle.com/javase/tutorial/essential/environment/paths.html
 * 
 * @author yashwanth.m
 *
 */
public class ApplicationPath {
	public static void main(String[] args) {
		
		getApplicationPath();
		
		getClassPath();
	}
	
	public static String getClassPath() {
		String codeSourceURI = ApplicationPath.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		
		File classPathFile = new File( codeSourceURI );
		String classPath = classPathFile.getAbsolutePath();
		
		//String classPath = codeSourceURI.replaceFirst( "/", ""); // File.separator()
		System.out.println("Class path : "+ classPath);
		
		return classPath;
	}
	
	public static String getApplicationPath() {
		// The default value of the class path is ".", meaning that only the current directory is searched.
		String absolutePath = new File("").getAbsolutePath();
		System.out.println("Absolute File path : "+ absolutePath);
		
		return absolutePath;
	}
}
