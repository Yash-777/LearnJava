package com.github.core.utilcollections;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Differences between HashMap and Hashtable? « https://stackoverflow.com/a/48094803/5081877
 * Theory « https://stackoverflow.com/a/31243387/5081877
 * @author yashwanth.m
 *
 */
public class Hash_M_T_Stack {
	static void sleepThread( int sec ) {
		try {
			Thread.sleep( 1000 * sec );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		//HashMap<String, Integer> hm = new HashMap<>();
		//Hashtable<String, Integer> hm = new Hashtable<String, Integer>();
		ConcurrentHashMap<String, Integer> hm = new ConcurrentHashMap<>();
		
		//test(hm);
		hm.put("key", 10);
		Integer integer = hm.get("key");
		System.out.println( integer );
		System.out.println( hm.get("key3") );
		
		new Thread() {
			@Override public void run() {
				try {
					for (int i = 10; i < 20; i++) {
						sleepThread(1);
						System.out.println("T1 :- Key"+i);
						hm.put("Key"+i, i);
					}
					System.out.println( System.identityHashCode( hm ) );
				} catch ( Exception e ) {
					e.printStackTrace();
				}
			}
		}.start();
		new Thread() {
			@Override public void run() {
				try {
					sleepThread(5);
					/*for (Enumeration<String> e = hm.keys(); e.hasMoreElements(); ) {
						sleepThread(1);
						System.out.println("T2 : "+ e.nextElement());
					}*/
					/*for (Iterator< Entry<String, Integer> > it = hm.entrySet().iterator(); it.hasNext(); ) {
						sleepThread(1);
						System.out.println("T2 : "+ it.next());
						// java.util.ConcurrentModificationException at java.util.Hashtable$Enumerator.next(Unknown Source)
					}*/
					Set< Entry<String, Integer> > entrySet = hm.entrySet();
					//Iterator< Entry<String, Integer> > it = entrySet.iterator();
					Enumeration<Entry<String, Integer>> entryEnumeration = Collections.enumeration( entrySet );
					while( entryEnumeration.hasMoreElements() ) {
						sleepThread(1);
						Entry<String, Integer> nextElement = entryEnumeration.nextElement();
						System.out.println("T2 : "+ nextElement.getKey() +" : "+ nextElement.getValue() );
					}
				
				} catch ( Exception e ) {
					e.printStackTrace();
				}
			}
		}.start();
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("Key1", "Yash");
		map.put("key2", "Sam");
		
		Map<String, String> unmodifiableMap = Collections.unmodifiableMap( map );
		try {
			unmodifiableMap.put("key4", "unmodifiableMap");
		} catch (java.lang.UnsupportedOperationException e) {
			System.err.println("UnsupportedOperationException : "+ e.getMessage() );
		}
		
		ConcurrentHashMap<String, String> cmap = new ConcurrentHashMap<>();
		cmap.put("Key11", "Yash");
		cmap.put("key21", "Sam");
		displayMap_entrySet(cmap);
	}
	
	static void displayMap_entrySet( Map<String, String> map ) {
		try {
			Set<Entry<String, String>> entrySet = map.entrySet();
			for (Entry<String, String> entry : entrySet) {
				System.out.format("[ %s : %s ] \n", entry.getKey(), entry.getValue() );
				
				map.put("key3", "ConcurrentModificationException");
			}
		} catch (java.util.ConcurrentModificationException e) {
			System.err.println("ConcurrentModificationException : "+ e.getMessage() );
		}
	}
	static void mapWithUserClass() {
		HashMap<String, Employee> map = new HashMap<String, Employee>();
		map.put("Key1", new Employee("Yash", "777"));
		map.put("key2", new Employee("Sam", "7"));
		
		Set<Entry<String, Employee>> entrySet = map.entrySet();
		for (Entry<String, Employee> entry : entrySet) {
			System.out.format("[ %s : %s ] \n", entry.getKey(), ((Employee)entry.getValue()).getName() );
		} // [ Key1 : Yash ]
		
		
		/*
		In order to use any object as Key in HashMap, it must implements equals and hashcode method in Java.
		By default any Class in java with extends the Object Class directly or indirectly.
		 */
		HashMap<Employee, Employee> mapObj = new HashMap<Employee, Employee>();
		mapObj.put( new Employee("Yash", "777") , new Employee("Sam", "7"));
		
		Set<Entry<Employee, Employee>> entrySetObj = mapObj.entrySet();
		for (Entry<Employee, Employee> entry : entrySetObj) {
			System.out.format("[ %s : %s ] \n", entry.getKey(), ((Employee)entry.getValue()).getName() );
		} // [ com.github.core.collections.Employee@7852e922 : Sam ]
	}
}

class Employee {
	String name, id;
	public Employee( String name, String id) {
		this.name = name;
		this.id = id;
	}
	String getName() {
		return this.name;
	}
}