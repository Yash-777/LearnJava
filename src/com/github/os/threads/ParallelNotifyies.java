package com.github.os.threads;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * <a href="http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/CyclicBarrier.html">CyclicBarriers</a>
 * <p>A synchronization aid that allows a set of threads to all wait for each other to reach a common barrier point.
 * CyclicBarriers are useful in programs involving a fixed sized party of threads that must occasionally wait 
 * for each other. The barrier is called cyclic because it can be re-used after the waiting threads are released.</p>
 * @author yashwanth.m
 *
 */
public class ParallelNotifyies {
	public static void main(String[] args) {
		// We want to start just 2 threads at the same time, but let's control that 
		// timing from the main thread. That's why we have 3 "parties" instead of 2.
		final CyclicBarrier barrier = new CyclicBarrier(3);

		Thread t1 = new Thread() {
			public void run() {
				try {
					String name = Thread.currentThread().getName();
					System.out.println("@@@@@ Thread : "+name+" started");
					
					barrier.await();
					System.out.println("@@@@@ Thread : "+name+" notified");
					for (int i = 0; i < 30; i++) {
						System.out.println(name+"\t : "+i);
					}
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
		};
		
		Thread t2 = new Thread() {
			public void run() {
				try {
					String name = Thread.currentThread().getName();
					System.out.println("@@@@@ Thread : "+name+" started");
					
					barrier.await();
					System.out.println("@@@@@ Thread : "+name+" notified");
					for (int i = 30; i > 0; i--) {
						System.out.println(name+"\t : "+i);
					}
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
		};
		t1.setName("Forword Thread");
		t2.setName("Backword Thread");
		t1.start();
		t2.start();

		for (int i = 0; i < 50; i++) {
			System.out.println("Main \t : "+i);
			if( i == 20 ) {
				try {
					barrier.await();
					System.out.println("all threads started");
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
		}

		// At this point, t1 and t2 are blocking on the gate. 
		// Since we gave "3" as the argument, gate is not opened yet.
		// Now if we block on the gate from the main thread, it will open
		// and all threads will start to do stuff!
		
		System.out.println("Main Thread Execution completed.");
	}
}