package com.github.os.threads;

import java.lang.Thread.State;
import java.util.concurrent.CountDownLatch;

/**
 * 
 * https://stackoverflow.com/a/3379003/5081877
 * 
 * https://stackoverflow.com/questions/861346/in-java-how-do-you-determine-if-a-thread-is-running
 * 
 * @author yashwanth.m
 *
 */
public class Concurrent_WorkerThreadsWait {
	public static void main(String[] args) {
		ThreadGroup group = new ThreadGroup("ThreadGroup1");
		
		CountDownLatch latch = new CountDownLatch(1);
		
		MyThread runnableTarget1 = new MyThread(latch, 5);
		Thread thread = new Thread(group, runnableTarget1, "Thread_1");
		thread.start();
		
		MyThread runnableTarget2 = new MyThread(latch, 50);
		Thread thread2 = new Thread(group, runnableTarget2, "Thread_2");
		thread2.start();
		// https://docs.oracle.com/javase/8/docs/technotes/guides/vm/thread-priorities.html
		thread2.setPriority( Thread.MIN_PRIORITY );
		
		MyThread runnableTarget3 = new MyThread(latch, 50);
		Thread thread3 = new Thread(group, runnableTarget3, "Thread_3");
		thread3.start();
		thread3.setPriority( Thread.MAX_PRIORITY );
		
		System.out.println("main Tread Execution started.");
		
		for (int i = 0; i < 10; i++) {
			System.out.println("Main \t : "+i);
			if( i == 2 ) {
				latch.countDown(); //This will inform all the waiting threads to start
				sleepThread( 1 );
			}
		}
		
		try {
		
			State state = thread.getState();
			boolean alive = thread.isAlive();
			System.out.format("State : %s, IsAlive: %b \n", state, alive);
			
			if( alive ) {
				System.out.println(" => Thread has started. So, joining it to main thread.");
				thread.join();
				System.out.println("Main Thread waits till the Joined thread's to treminate ('not-alive').");
				sleepThread( 1 );
				
				group.stop();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Main Thread Execution completed.");
	}
	
	static void sleepThread(int sec) {
		try {
			Thread.sleep( 1000 * sec );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class MyThread implements Runnable {
	CountDownLatch latch;
	int repeatCount;
	
	public MyThread(CountDownLatch latch, int repeatCount) {
		this.latch = latch;
		this.repeatCount = repeatCount;
	}
	@Override
	public void run() {
		try {
			String name = Thread.currentThread().getName();
			System.out.println("@@@@@ Thread : "+name+" started");
			
			latch.await(); //The thread keeps waiting till it is informed.
			
			System.out.println("@@@@@ Thread : "+name+" notified");
			for (int i = 0; i < repeatCount; i++) {
				Concurrent_WorkerThreadsWait.sleepThread( 1 );
				System.out.println("\t "+ name +": "+i);
			}
		} catch (InterruptedException e) {
		e.printStackTrace();
		}
	}
}
