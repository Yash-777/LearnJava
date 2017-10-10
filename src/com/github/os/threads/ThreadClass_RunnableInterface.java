package com.github.os.threads;

import java.util.Random;

/**
 * http://www.oracle.com/technetwork/articles/java/fork-join-422606.html
 * 
 * @author yashwanth.m
 *
 */
public class ThreadFeatures {
	public static void main(String[] args) {
		ImplementsRunnable a = new ImplementsRunnable();
		
		Thread r1 = new Thread(a);
		Thread r2 = new Thread(a);
		
		r1.start();
		r2.start();
	}
}

/**
 * you must have to create separate instance for every thread access.
 * Hence different memory is allocated for every class instances and each has separate counter,
 * the value remains same, which means no increment will happen because none of the object reference is same.
 * 
 * @author yashwanth.m
 *
 */
class ExtendsThread extends Thread {
	int et1, et2;
	
	@Override
	public void run() {
		super.run();
	}
}

/**
 * You usually extend a class to add or modify functionality.
 * So, if you don't want to overwrite any Thread behavior, then use Runnable.
 * 
 * @author yashwanth.m
 *
 */
class ImplementsRunnable implements Runnable {
	int ir1 = 0;
	
	Random random = new Random();
	int max = 100, min = 1;
	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			//Integer token_id = random.nextInt( Integer.MAX_VALUE );
			int randomNum = random.nextInt((max - min) + 1) + min;
			ir1 = randomNum;
			sleepThread(10);
			System.out.println(i+" "+Thread.currentThread().getName() +" « Val : "+ir1);
		}
	}
	void sleepThread(int sec) {
		try {
			Thread.sleep( 1000 * sec );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}