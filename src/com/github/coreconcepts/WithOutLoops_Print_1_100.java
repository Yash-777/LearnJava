package com.github.coreconcepts;

public class WithOutLoops_Print_1_100 {
	public static void main(String[] args) {
		fibonacii( 1 );
	}
	public static void fibonacii( int val ) {
		System.out.println( val );
		if( val < 100 ) {
			fibonacii( ++val );
		}
	}
}
