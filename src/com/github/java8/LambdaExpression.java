package com.github.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
<ul> Syntax of lambda expression:
	<li>(params) -> expression</li>
	<li>(params) -> statement</li>
	<li>(params) -> { statements }</li>
</ul>
<b><a href="http://javarevisited.blogspot.in/2014/02/10-example-of-lambda-expressions-in-java8.html">
Examples:</a><b>
	<p><pre>
	() -> System.out.println("Hello Lambda Expressions");
	(int even, int odd) -> even + odd
	forEach((name) -> { System.out.println(name + " "); } </pre></p>
 * @author yashwanth.m
 *
 */
public class LambdaExpression {
	static List<String> listItems = Arrays.asList("Lambdas", "Default Method",
			"Stream API", "Date and Time API", "Lambdas");
	public static void main(String[] args) {
		/*Threads_Implementing_Runnable();
		Iterating_Over_List();
		stringPredictions();*/
		intPredictions();
	}
	/**
	 * range(3,5) = 4
	 * if 4 % listStream == 0 return
	 * 
	 * Sum( all returned values )
	 */
	public static void intPredictions() {
		// https://stackoverflow.com/q/44427520/5081877
		List<Integer> intList = Arrays.asList(5, 7, 4, 6, 1, 2);
		// (As Used Range - ) IllegalStateException: stream has already been operated upon or closed
		// Stream<Integer> stream = intList.stream();
		IntPredicate predicate  = arg -> intList.stream().anyMatch( streamVal -> arg % streamVal == 0 );
		int sum = IntStream.range(3, 15).filter( predicate ).sum();
		System.out.println("Sum : "+sum);
	}
	
	public static void stringPredictions() {
		// java.util.function.Predicate
		Predicate<String> startsWith = names -> names.startsWith("D");
		Predicate<String> endsWith = names -> names.endsWith("I");
		Predicate<String> length7 = names -> names.length() == 7;
		Predicate<String> charAtPosition = names -> (names.charAt(1) == 'a');
		
		System.out.println("Functional interface Predicate.");
		listItems.stream()
			.filter( endsWith )
			.forEach( System.out::println );
		
		System.out.println("logical operator - and(), or() and xor().");
		listItems.stream()
			.filter( ( endsWith.and( startsWith ) ).or( length7 )  )
			.forEach( System.out::println );
		
		System.out.println("Second Charector starts with letter a.");
		listItems.stream()
			.filter( charAtPosition )
			.forEach( System.out::println );
		
		System.out.println("Allow distinct elements by avoiding duplicates.");
		listItems.stream().distinct()
			.forEach( System.out::println );
	}
	
	public static void Iterating_Over_List() {
		//Prior Java 8 :
		for (String feature : listItems) { System.out.println(feature); }
		//In Java 8:
		listItems.forEach(n -> System.out.println(n));
		
		// method reference is denoted by :: (double colon) operator
		listItems.forEach(System.out::println);
	}

	public static void Threads_Implementing_Runnable() {
		//Before Java 8:
		new Thread(new Runnable() {
			@Override public void run() {
				System.out.println("Before Java8, @Overrid run()");
			}
		}).start();
		
		//Java 8 way: 
		new Thread( () -> System.out.println("In Java8, Lambda expression!!") ).start();
	}
}
