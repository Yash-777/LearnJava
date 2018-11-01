package com.github.coreconcepts.programs;

/**
 * The Fibonacci Sequence is the series of numbers: 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, ...
 * The next number is found by adding up the two numbers before it.
 * <p>
 * Factorial 5! = 5*4*3*2*1
 * @author yashwanth.m
 *
 */
public class Fibonacci_Factorial {
	public static void main(String[] args) {
		System.out.println("Fibonacii Series:");
		sequence();
		
		int fact = factorial( 5 );
		System.out.println("\nFactorial Number: "+fact);
		
		System.out.println("WithOutLoops_Print_1_100 : ");
		recurciveMethod(1, 10);
	}
	private static int factorial(int n) {
		if( n == 1 ) return 1;
		return n * factorial( n-1 );
	}
	public static void sequence() {
		int x = 0, y = 1, z = 1;
		System.out.print( x + " : "+ y +" : "+ z);
		for (int i = 0; i < 10; i++) {
			z = x + y;
			System.out.print(" : "+z);
			
			x = y;
			y = z;
		}
	}
	public static void recurciveMethod(int min, int max) {
		System.out.println( min );
		if( min < max ) {
			recurciveMethod( ++min, max );
		}
	}
}
