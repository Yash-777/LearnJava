package com.github.wrappers;

public class Wrappers_Range_EqualityCheck {
	public static void main(String[] args) {
		int bucket1 = 127; // as int ranges to 0-127
		int bucket11 = 127;
		System.out.println("In range same bucket : "+ System.identityHashCode(bucket1) ); // 366712642
		System.out.println("In range same bucket : "+ System.identityHashCode(bucket11) ); // 366712642
		
		int bucket2 = 128; // out of ranges so chooses any super bucket
		int bucket22 = 128;
		System.out.println("out of range diff bucket : "+ System.identityHashCode(bucket2) ); // 1829164700
		System.out.println("out of range diff bucket : "+ System.identityHashCode(bucket22) ); // 2018699554
		
		referenceEquality(bucket1, bucket11); // Reference Matches
		referenceEquality(bucket2, bucket22); // Reference Not Matches
		dataEquality(bucket2, bucket22); // Data Matches
		
		Long l1 = 2173l, l2 = 2173l;
		dataEquality(l1, l2); // Data Matches
		referenceEquality(l1, l2); // Reference Not Matches
	}
	public static void dataEquality(Object x, Object y) {
		if( x != null && x.equals( y ) ) { // Data Equality
			System.out.println("Data Matches");
		} else {
			System.out.println("Data Not Matches");
		}
	}
	public static void referenceEquality(Object x, Object y) {
		if( x == y ) { // Reference Equality
			System.out.println("Reference Matches");
		} else {
			System.out.println("Reference Not Matches");
		}
	}
}
