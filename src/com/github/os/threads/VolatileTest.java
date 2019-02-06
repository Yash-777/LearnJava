package com.github.os.threads;

import com.github.os.RuntimeMemories;

public class VolatileTest implements Runnable {
	
	private static final Object counterLock = new Object();
	private static int counter = 0;
	private static volatile int counter1 = 0;
	
	private volatile int counter2 = 0;
	private int counter3 = 0;
	
	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			concurrentMethodWrong();
		}
		
	}
	
	void addInstanceVolatile() {
		synchronized (counterLock) {
			counter2 = counter2 + 1;
			System.out.println( Thread.currentThread().getName() +"\t\t « InstanceVolatile :: "+ counter2);
		}
	}
	void sleepThread( long sec ) {
		try {
			Thread.sleep( sec * 1000 );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * http://theburningmonk.com/2010/03/threading-understanding-the-volatile-modifier-in-csharp/
	 * https://stackoverflow.com/a/7943472/5081877
	 * 
	 * @throws InterruptedException
	 */
	public void concurrentMethodWrong() {
		counter = counter + 1;
		System.out.println( Thread.currentThread().getName() +" « Static :: "+ counter);
		sleepThread( 1/4 );
		
		counter1 = counter1 + 1;
		System.out.println( Thread.currentThread().getName() +"\t « StaticVolatile :: "+ counter1);
		sleepThread( 1/4 );
		
		addInstanceVolatile();
		sleepThread( 1/4 );
		
		counter3 = counter3 + 1;
		sleepThread( 1/4 );
		System.out.println( Thread.currentThread().getName() +"\t\t\t\t\t « Instance :: "+ counter3);
	}
	public static void main(String[] args) throws InterruptedException {
		RuntimeMemories.displayInfo();
		
		VolatileTest volatileTest = new VolatileTest();
		Thread t1 = new Thread( volatileTest );
		t1.start();
		
		Thread t2 = new Thread( volatileTest );
		t2.start();
		
		Thread t3 = new Thread( volatileTest );
		t3.start();
		
		Thread t4 = new Thread( volatileTest );
		t4.start();
		
		Thread.sleep( 10 );;
		
		Thread optimizeation = new Thread() {
			@Override public void run() {
				System.out.println("Thread Start.");
				
				Integer appendingVal = volatileTest.counter2 + volatileTest.counter2 + volatileTest.counter2;
				
				System.out.println("End of Thread." + appendingVal);
			}
		};
		optimizeation.start();
	}
}
/*
availableProcessors :: 4
MAX JVM will attempt to use : 184
JVM totalMemory also equals to initial heap size of JVM : 12
Returns the amount of free memory in the JVM : 12
 ===== ----- ===== 
Thread-0 « Static :: 1
Thread-1 « Static :: 2
Thread-2 « Static :: 3
Thread-3 « Static :: 4
Thread-0	 « StaticVolatile :: 1
Thread-1	 « StaticVolatile :: 2
Thread-2	 « StaticVolatile :: 3
Thread-0		 « InstanceVolatile :: 1
Thread-3	 « StaticVolatile :: 4
Thread-1		 « InstanceVolatile :: 2
Thread-2		 « InstanceVolatile :: 3
Thread-0					 « Instance :: 1
Thread-3		 « InstanceVolatile :: 4
Thread-1					 « Instance :: 3
Thread-0 « Static :: 5
Thread-2					 « Instance :: 3
Thread-1 « Static :: 6
Thread-3					 « Instance :: 4
Thread-2 « Static :: 7
Thread-1	 « StaticVolatile :: 6
Thread-0	 « StaticVolatile :: 5
Thread-3 « Static :: 8
Thread-2	 « StaticVolatile :: 7
Thread-1		 « InstanceVolatile :: 5
Thread-0		 « InstanceVolatile :: 6
Thread-3	 « StaticVolatile :: 8
Thread-2		 « InstanceVolatile :: 7
Thread-1					 « Instance :: 6
Thread-1 « Static :: 9
Thread-0					 « Instance :: 6
Thread-0 « Static :: 10
Thread-3		 « InstanceVolatile :: 8
Thread-1	 « StaticVolatile :: 9
Thread-0	 « StaticVolatile :: 10
Thread-3					 « Instance :: 8
Thread-1		 « InstanceVolatile :: 9
Thread-2					 « Instance :: 8
Thread-0		 « InstanceVolatile :: 10
Thread-3 « Static :: 11
Thread-2 « Static :: 12
Thread-3	 « StaticVolatile :: 11
Thread-2	 « StaticVolatile :: 12
Thread-0					 « Instance :: 10
Thread-1					 « Instance :: 10
Thread-0 « Static :: 13
Thread-3		 « InstanceVolatile :: 11
Thread-0	 « StaticVolatile :: 13
Thread-1 « Static :: 14
Thread-3					 « Instance :: 11
Thread-3 « Static :: 15
Thread-2		 « InstanceVolatile :: 12
Thread-1	 « StaticVolatile :: 14
Thread-0		 « InstanceVolatile :: 13
Thread-3	 « StaticVolatile :: 15
Thread-1		 « InstanceVolatile :: 14
Thread-3		 « InstanceVolatile :: 15
Thread-2					 « Instance :: 14
Thread-3					 « Instance :: 15
Thread-1					 « Instance :: 15
Thread-1 « Static :: 18
Thread-0					 « Instance :: 14
Thread-0 « Static :: 19
Thread-3 « Static :: 17
Thread-2 « Static :: 16
Thread-1	 « StaticVolatile :: 16
Thread-0	 « StaticVolatile :: 17
Thread-3	 « StaticVolatile :: 18
Thread-2	 « StaticVolatile :: 19
Thread-1		 « InstanceVolatile :: 16
Thread-0		 « InstanceVolatile :: 17
Thread-3		 « InstanceVolatile :: 18
Thread-2		 « InstanceVolatile :: 19
Thread-1					 « Instance :: 19
Thread-0					 « Instance :: 19
Thread-3					 « Instance :: 19
Thread-2					 « Instance :: 19
Thread-2 « Static :: 20
Thread-2	 « StaticVolatile :: 20
Thread-2		 « InstanceVolatile :: 20
Thread-2					 « Instance :: 20
Thread Start.
End of Thread.60

*/
