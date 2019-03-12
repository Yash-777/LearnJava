package com.github.core.utilcollections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Vector;

public class Collection_Interfaces {
	public static void main(String[] args) {
		interface_LIST();
		
		// Interface SET
		
		
	}
	public static void interface_LIST() {
		// Because of Memory We go for ArrayList instead of Vector.
		ArrayList<Integer> list = new ArrayList<>(); // JDK1.2 Initial Capacity 10, Increase Capacity 50%
		for (int i = 0; i < 10; i++) {
			list.add(i);
		}
			//System.out.println("ArrayList Size : "+ list.); // Initial Capacity DEFAULT_CAPACITY=10
			
		Vector<Integer> v = new Vector<>(); // JDK1.0 Initial Capacity 10, Increase Capacity 100%
		for (int i = 0; i < 10; i++) {
			v.add(i);
		}
			System.out.println("Vector Size : "+ v.capacity());
		v.addElement(20); // Adds and Increased the Size by One.
			System.out.println("Vector Size : "+ v.capacity()); // 20 increased Capacity to 100% = 10+10
		
		display(v);
		
		System.out.println("Modified the Element.");
		v.set(0, 12);
		v.setElementAt(22, 1);
		display(v);
		
		Stack<Integer> s = new Stack<>(); // JDK1.0
		for (int i = 0; i < 10; i++) {
			s.push(i);
		}
		System.out.println("Stack Size : "+ s.capacity()); // Initial Capacity 10
		s.addElement(20);
		System.out.println("Stack Size : "+ s.capacity());
		
		LinkedList<Integer> node = new LinkedList<>();
		node.add(10);
	}
	
	public static void display(Vector<Integer> v) {
		for (Iterator<Integer> iterator = v.iterator(); iterator.hasNext();) {
			Integer integer = (Integer) iterator.next();
			System.out.println("Val : "+integer);
		}
	}

}
