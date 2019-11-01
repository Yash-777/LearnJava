package com.github.os.threads;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

public class MaintainEqualThreadsPatallel {
	static int parallelCount = 3;
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorPool = Executors.newFixedThreadPool(parallelCount);
		
		boolean threadReturnVlaue = false;
		List<Future<Object>> futureReturns = new LinkedList<Future<Object>>();
		for (int i = 0; i < 7; i++) {
			int uniqueRandomValues = uniqueRandomValues(1, 10);
			if (threadReturnVlaue) { // Return some value
				Future<Object> submit = executorPool.submit( new WorkerCallable(uniqueRandomValues) );
				futureReturns.add(submit);
			} else { // No return value
				executorPool.execute( new WorkerThread(uniqueRandomValues) );
			}
		}
		
		if (threadReturnVlaue) { // WorkerCallable: Blocking main thread until task completes.
			for (Future<Object> future : futureReturns) {
				int threadReturnVal = (int) future.get();
				System.out.println("Future Response : "+threadReturnVal);
			}
		}
		
		// Terminate Pool threads in-order to terminate main thread
		executorPool.shutdown();
	}
	
	public static int uniqueRandomValues(int min, int max) {
		int nextInt = ThreadLocalRandom.current().nextInt(min, max);
		System.out.println("Random Vlaue : "+nextInt);
		return nextInt;
	}
	public void sleepThread(long mills) {
		try {
			Thread.sleep(mills);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	protected void loopFunction(int repeatCount) {
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName +":START");
		for (int i = 1; i <= repeatCount; i++) {
			System.out.println(threadName +":"+ i);
			//sleepThread(1000);
			// Either Static or Instance blocks, Threads will maintain separate copy to execute in Threads Stack
			stackStactic();
			stackInstance();
		}
		System.out.println(threadName +":END");
	}
	int ins = 0;
	void stackInstance() {
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName +":stackInstance():"+System.currentTimeMillis()+"-"+ ins++);
		sleepThread(1000 * 3);
		System.out.println(threadName +":stackInstance():"+System.currentTimeMillis());
	}
	static int stat = 0;
	static void stackStactic() {
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName +":stackStactic():"+System.currentTimeMillis()+"-"+ stat++);
		sleepThreadStatic(1000 * 3);
		System.out.println(threadName +":stackStactic():"+System.currentTimeMillis());
	}
	
	public static void sleepThreadStatic(long mills) {
		try {
			Thread.sleep(mills);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
class WorkerThread extends MaintainEqualThreadsPatallel implements Runnable {
	int randomValue = 0;
	public WorkerThread(int randomValue) {
		this.randomValue = randomValue;
	}
	
	@Override
	public void run() {
		// As separate stack run() function doesn't accepts parameters, pass to constructor.
		loopFunction(randomValue);
	}
}
class WorkerCallable extends MaintainEqualThreadsPatallel implements Callable<Object> {
	int randomValue = 0;
	public WorkerCallable(int randomValue) {
		this.randomValue = randomValue;
	}
	
	public Object call() {
		// As separate stack run() function doesn't accepts parameters, pass to constructor.
		loopFunction(randomValue);
		return randomValue;
	}
}
