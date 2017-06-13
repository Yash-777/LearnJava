package com.github.os.threads;

import java.util.concurrent.CountDownLatch;

/**
 * 
 * https://stackoverflow.com/a/3379003/5081877
 * 
 * @author yashwanth.m
 *
 */
public class ConcurentWaits {
	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(1);
		MyThread t1 = new MyThread(latch);
		Thread thread = new Thread(t1, "Test Thread");
		thread.start();
		
		System.out.println("main Tread Execution started.");
		
		for (int i = 0; i < 20; i++) {
			System.out.println("Main \t : "+i);
			if( i == 2 ) {
				latch.countDown(); //This will inform all the threads to start
			}
		}
		
		try {
			if( thread.isAlive() ) {
				System.out.println(" => Thread has started. So, joining it to main thread.");
				thread.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Main Thread Execution completed.");
	}
}

class MyThread implements Runnable {
	CountDownLatch latch;
	public MyThread(CountDownLatch latch) {
		this.latch = latch;
	}
	@Override
	public void run() {
		try {
			String name = Thread.currentThread().getName();
			System.out.println("@@@@@ Thread : "+name+" started");
			
			latch.await(); //The thread keeps waiting till it is informed
			
			System.out.println("@@@@@ Thread : "+name+" notified");
			for (int i = 0; i < 10; i++) {
				System.out.println("\t : "+i);
			}
		} catch (InterruptedException e) {
		e.printStackTrace();
		}
	}
}
