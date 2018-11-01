package com.github.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;

@FunctionalInterface 
interface TaskComparator {
	public boolean compareTasks (int al, int a2); 
}
public class TaskComparatcrImpL {
	public static void main (String[] args) {
		TaskComparator myTasComparator = (int al, int a2) -> {return al > a2;};
		
		boolean result = myTasComparator.compareTasks(5, 2);
		System.out.println ( result );
		
		boolean result2 = myTasComparator.compareTasks(5, 10);
		System.out.println ( result2 );
		
		// Iterating over collection elements.
		List<String> stringList = new ArrayList<String>();
		stringList.add("Yash");
		stringList.add("M");
		
		for (String string : stringList) {
			System.out.println("Contents of List : "+string);
		}
		
		Stream<String> stream = stringList.stream();
		stream.forEach( (string) -> { System.out.println("Contents : "+string); } );
		
		Spliterator<String> splitIttr = stringList.stream().spliterator();
		System.out.println("Spliterator Char : "+ splitIttr.characteristics());
		
		// Anonymous InnerClass
		Runnable r = new Runnable() {
			public void run() {
				System.out.println("Anonymous InnerClass");
			}
		};
		Thread t = new Thread(r);
		t.start();
		
		// Runnable using Lambda expression.
		Runnable r2 = () -> System.out.println("Runnable using Lambda expression.");
		Thread t2 = new Thread( r2 );
		t2.start();
		
	}
}
