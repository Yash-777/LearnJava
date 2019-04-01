package com.github.os.threads;


/**
 * Object level lock(instance) vs Class level lock(static)
 * 
 * @author yashwanth.m
 *
 */
public class Locks_vs_Synchronize {
	public static void main(String[] args) {
		LoggingWidget w1 = new LoggingWidget();
		Widget w2 = new LoggingWidget();
		
		Thread t1 = new Thread(w1);
		Thread t2 = new Thread(w2);
		t1.start();
		t2.start();
		System.out.println("Completed exe.");
	}
}

// https://github.com/benelog/java-concurrency/blob/master/src/main/java/net/jcip/examples/NonreentrantDeadlock.java
abstract class Widget implements Runnable {
	public synchronized void doSomething() {
		System.out.println(toString() + ": Widget calling doSomething");
	}
}

class LoggingWidget extends Widget {
	public void doSomething() {
		System.out.println(toString() + ": LoggingWidget calling doSomething");
		super.doSomething();
	}

	@Override
	public void run() {
		doSomething();
	}
}