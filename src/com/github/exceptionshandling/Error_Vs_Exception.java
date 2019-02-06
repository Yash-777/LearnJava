package com.github.exceptionshandling;

import com.github.os.RuntimeMemories;

public class Error_Vs_Exception {
	public static void main(String[] args) {
		// java.lang.StackOverflowError
		//StackOverflowErrorExample.recursivePrint(1);
		
		RuntimeMemories.displayInfo();
	}
	
	/**
	* Get the stack trace of a given thread. Works by getting all stack traces
	* and looking at the specified thread.
	* @param t The thread.
	* @return The newline-delimited stack trace as a single string.
	*/
	public static String stackTraceOfThread(Thread t) {
		StackTraceElement[] stackTraceElements = Thread.getAllStackTraces().get(t);
		String stackTrace = "";
		if (stackTraceElements != null) {
			for (StackTraceElement stackTraceElement : stackTraceElements) {
				stackTrace += stackTraceElement + "\n";
			}
		}
		return stackTrace;
	}
}
