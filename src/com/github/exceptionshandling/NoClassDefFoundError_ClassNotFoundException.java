package com.github.exceptionshandling;

public class NoClassDefFoundError_ClassNotFoundException {
	public static void main(String[] args) {
		
		//classNotFound();
		
		// NoClassDefFoundError extends LinkageError  extends Error extends Throwable
		/*
		 * Eclipse « Project « (Un Check) Build Automatically.
		 * Then delete the DeleteRuntime.class file from the System Explorer.
		 * {WorkSpace}/LearnJava\target\classes\com\github\exceptionshandling
		 * Then run the class with out recompiling to get NoClassDefFoundError.
		 */
		DeleteRuntime noClass = new DeleteRuntime();
		noClass.method1();
	}
	
	/**
	 * ClassNotFoundException is a runtime exception that is thrown when an application tries to load a class at runtime
	 * using the Class.forName() or loadClass() or findSystemClass() methods ,and the class with specified name are
	 * not found in the classpath.
	 */
	public static void classNotFound() {
		// ClassNotFoundException extends ReflectiveOperationException extends Exception extends Throwable
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver_not");
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); // ClassLoader.loadClass(ClassLoader.java:424)
		}
	}
}

class DeleteRuntime {
	public void method1() {
		System.out.println("Subclass Method.");
	}
}