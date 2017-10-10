package com.github.wrappers;

public class Wrappers_Range_EqualityCheck {
	public static void main(String[] args) {
		
		Long l1 = 2173l, l2 = 2173l;
		if( l1.equals( l2 ) ) { // Data Equality
			System.out.println("Both are Equal.");
		} else {
			System.out.println("NOT Equal");
		}
		if( l1 == l2 ) { // Reference Equality
			System.out.println("Both are Equal.");
		} else {
			System.out.println("NOT Equal");
		}
	}
}
