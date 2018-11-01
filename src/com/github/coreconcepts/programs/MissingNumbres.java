package com.github.coreconcepts.programs;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class MissingNumbres {
	public static void main(String[] args) {
		int[] arr = { 0, 1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 18, 20, 21, 23 };
		Arrays.sort(arr); // To maintain sorting order.
		
		int min = arr[0], max = arr[arr.length-1];
		System.out.format("Min[%d], Max[%d] \n", min , max);
		
		// If array of elements contain any duplicates then you can remove by adding elements to SET interface.
		// As we don't know the size use Set implementation class.
		Set<Integer> sequence = new TreeSet<Integer>();
		for (int i = min; i <= max; i++) {
			sequence.add(i);
		}
		System.out.println("Sequence Set : "+ sequence);
		for (int i = 0; i < arr.length; i++) {
			if( sequence.contains( arr[i] )) {
				sequence.remove( arr[i] );
			} else {
				System.out.println("Missing : "+ arr[i] );
			}
		}
		
		System.out.println("Missing Numbers : "+ sequence);
		/*
Min[0], Max[23] 
Sequence Set : [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23]
Missing Numbers : [5, 16, 17, 19, 22]
		 */
	}
}
