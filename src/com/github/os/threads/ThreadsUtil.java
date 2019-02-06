package com.github.os.threads;

import java.io.IOException;

public class ThreadsUtil {
	public static boolean getUserAcceptence(String msg) {
		System.out.println("Enter Some thing to Cnfirm... : "+ msg);
		try {
			System.in.read();
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		System.out.println("Thanks for your confromation.");
		return true;
	}
	
	/**
	 * Causes the currently executing thread to sleep for the specified number of minutes.
	 * @param time	the length of time to sleep in minutes
	 */
	public void sleepThread(long min) {
		try {
			java.util.concurrent.TimeUnit.MINUTES.sleep( min );
		} catch (InterruptedException e) {
			System.out.println("Sleep Exception:"+ e.getMessage());
		}
	}
	
	public void sleepThread_Sec(long sec) {
		try {
			java.util.concurrent.TimeUnit.SECONDS.sleep( sec );
		} catch (InterruptedException e) {
			System.out.println("Sleep Exception:"+ e.getMessage());
		}
	}
	/**
	 * Causes the currently executing thread to sleep for the specified number of milliseconds.
	 * @param time	the length of time to sleep in milliseconds
	 */
	public static void sleepThread_Milli(long millis) {
		try {
			Thread.sleep( millis );
		} catch (InterruptedException e) {
			System.out.println("Sleep Exception:"+ e.getMessage());
		}
	}
}
