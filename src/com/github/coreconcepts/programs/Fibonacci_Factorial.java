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
		
		// Integer can hold values of Minimum = -2,147,483,648 & Maximum = 2,147,483,647
		// Integer [34=0], [32,33=-2147483648], [31=738197504].
		// Long [34=4926277576697053184], [50=-3258495067890909184], [65=-9223372036854775808]
		int num = 65;
		factorial(num);
		
		long fact = factorialStack(num);
		System.out.println("Factorial of a Number using Stack : "+fact);
		
		/*
		System.out.println("WithOutLoops_Print_1_100 : ");
		recurciveMethod(1, 10);
		
		System.out.println("Fibonacii Series:");
		sequence();*/
	}
	public static long factorial(int num) {
		long fact = 1;
		for (int i = 1; i <= num; i++) {
			fact *= i;
		}
		System.out.println("Factorial of a Number using Loop  : "+ fact);
		return fact;
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
	
	/**
	 * If the number is 10 Lacks then it leads to Stack Overflow Exception.
	 * @param num
	 * @return factorial of a number.(3! = 3 * 2 * 1)
	 */
	public static long factorialStack(int num) {
		if( num == 1 ) return 1;
		return num * factorialStack( num-1 );
	}
	public static void recurciveMethod(int min, int max) {
		System.out.println( min );
		if( min < max ) {
			recurciveMethod( ++min, max );
		}
	}
}
