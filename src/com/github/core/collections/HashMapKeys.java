package com.github.core.collections;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class HashMapKeys {
	public static void main(String[] args) {
		HashMap<String, Employee> map = new HashMap<String, Employee>();
		map.put("Key1", new Employee("Yash", "777"));
		map.put("key2", new Employee("Sam", "7"));
		
		Set<Entry<String, Employee>> entrySet = map.entrySet();
		for (Entry<String, Employee> entry : entrySet) {
			System.out.format("[ %s : %s ] \n", entry.getKey(), ((Employee)entry.getValue()).getName() );
		}
		/*
		In order to use any object as Key in HashMap, it must implements equals and hashcode method in Java.
		By default any Class in java with extends the Object Class directly or indirectly.
		 */
		HashMap<Employee, Employee> mapObj = new HashMap<Employee, Employee>();
		mapObj.put( new Employee("Yash", "777") , new Employee("Sam", "7"));
		
		Set<Entry<Employee, Employee>> entrySetObj = mapObj.entrySet();
		for (Entry<Employee, Employee> entry : entrySetObj) {
			System.out.format("[ %s : %s ] \n", entry.getKey(), ((Employee)entry.getValue()).getName() );
		}
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
