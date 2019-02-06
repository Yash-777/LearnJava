package com.github.objects;

import java.lang.reflect.Constructor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Singleton Design Pattern:
 * Ensures that a class has only one instance and provide a global point of access to it.
 * 
 * @author yashwanth.m
 *
 */
public class SingleTon_VoilatingRules {
	static void useSingleTon() {
		SingleTonRuntime s1 = SingleTonRuntime.getInstance();
		print("useSingleTon()", s1);
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		
		/* Multiple Thread's trying to access getInstance(). then, in such case
		 * You may get different objects.
		 * To overcome this problem make method as synchronized.
		*/
		ExecutorService service = Executors.newFixedThreadPool(2);
		service.submit(SingleTon_VoilatingRules::useSingleTon);
		service.submit(SingleTon_VoilatingRules::useSingleTon);
		
		service.shutdown();
		
		SingleTonRuntime s1 = SingleTonRuntime.getInstance();
		print("S1", s1);
		SingleTonRuntime s2 = SingleTonRuntime.getInstance();
		print("S2", s2);
		
		// Reflection
		Class<?> clazz = Class.forName("com.github.objects.SingleTonRuntime");
		Constructor<SingleTonRuntime>[] constructors = 
				(Constructor<SingleTonRuntime>[]) clazz.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			constructor.setAccessible(true);
			SingleTonRuntime ref = (SingleTonRuntime) constructor.newInstance(null);
			print("Reflection", ref);
			break;
		}
		System.out.println("=====");
	}
	
	public static void print(String msg, Object obj) {
		System.out.format("Object:[%s], HashCode:[%d]\n", msg, obj.hashCode() );
	}
}

class SingleTonRuntime {
	/** Eagerly creating instance while loading the class. */
	private static SingleTonRuntime currentRuntime = null; //new SingleTonRuntime();
	
	/**
	 * Returns the runtime object associated with the current Java application.
	 * Most of the methods of class <code>Runtime</code> are instance
	 * methods and must be invoked with respect to the current runtime object.
	 *
	 * @return  the <code>Runtime</code> object associated with the current Java application.
	 */
	public static synchronized SingleTonRuntime getInstance() {
		// In case of Multi-Threading you may face the problem.
		if( currentRuntime == null ) {
			// Lazy loading, When ever necessary then only create instance.
			currentRuntime = new SingleTonRuntime();
			System.out.println("Lazy"+ currentRuntime.hashCode());
			
			try { // For testing purpose, waiting the current thread.
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return currentRuntime;
	}
	
	/** Don't let anyone else instantiate this class */
	private SingleTonRuntime() {
		if( currentRuntime != null ) { // To overcome Reflection API problem
			throw new RuntimeException("Cannot create in this approach, Please use getInstance().");
		}
		System.out.println("Creation Instance...");
	}
}