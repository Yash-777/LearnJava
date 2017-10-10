package com.github.os.threads;

/**
 * <UL>
 * <LI> A program in execution is often referred as process which requires separate address space and it .
 * A thread is a subset(part) of the process which shares same address space of its process.</LI>
 * <LI> A process consists of multiple threads. A thread is a smallest part of the process that can 
 * execute concurrently with other parts(threads) of the process.</LI>
 * </UL>
 * 
 * <p><a href="https://www.cs.uic.edu/~jbell/CourseNotes/OperatingSystems/4_Threads.html">Reference Link</a>
 * 
 * https://stackoverflow.com/q/200469/5081877
 * 
 * @author yashwanth.m
 * 
 */
public class ThreadsFlow {
	public static void main(String[] args) {
		System.out.println("Main Thread Started. entered into STACK");
		
		Thread t1 = new Thread() {
			@Override public void run() {
				System.out.println("T1 Start.");
				
				for(int i=0; i<=10; i++) {
					sleepThread( 2 );
					System.out.println("T1 : "+i);
				}
				
				System.out.println("End of T1.");
			}
		};
		
		Thread t2 = new Thread( new Runnable() {
			@Override public void run() {
				System.out.println("T2 Start.");
				
				for(int i=100; i >= 95; i--) {
					sleepThread( 1 );
					System.out.println("T2 : "+i);
				}
				
				System.out.println("End of T2.");
			}
		});
		
		//t1.setPriority(10);
		t1.setPriority( Thread.MAX_PRIORITY );
		t1.start();
		
		t2.setPriority(1);
		t2.start();
		
		try {
			// http://www.javaquery.com/2016/08/understanding-threadjoin-in-java.html
			t2.join();
			System.out.println("Main Thread will stop untill Thread-2 Execution Completes.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Main Thread End. - remove from STACK");
	}
	
	static void sleepThread(int sec) {
		try {
			Thread.sleep( 1000 * sec );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}