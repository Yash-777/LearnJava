package com.github.os.threads;

public class Runnable_Start_Run implements Runnable {
	static int count = 0;
	
	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			System.out.println( Thread.currentThread().getId() +" K : "+ i);
			count++;
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Runnable_Start_Run target = new Runnable_Start_Run();
		Thread t = new Thread(target);
		t.start(); // New Thread Stack
		t.run(); // Main Thread Stack
		// The join() method is used to hold the execution of currently running
		// thread until the specified thread is dead(finished execution).
		t.join(); // The join() method waits for a thread to die.
		System.out.println("Thread Joined to Main Thread and Dead : "+ !t.isAlive());
		t.run(); // Main Thread Stack
		
		target.run();
		
		while (Thread.activeCount() > 1) {
			// Other Thread's are running so, wait until they complete execution.
			System.out.println("Using Thread.activeCount() instead of join()");
		}
		System.out.println("Runnable_Start_Run « "+ count);
		
		
		int loopCount = 0;
		for (int i = 0, j = 0; i < 5; ++i, j = i+1) {
			loopCount += i;
			System.out.format("I[%d], J[%d] \n", i, j );
		}
		System.out.println("Loop Count : "+ loopCount);
	}
}
