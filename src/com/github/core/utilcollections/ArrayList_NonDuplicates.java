package com.github.core.utilcollections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayList_NonDuplicates {

	public static void main(String[] args) {
		
		Integer[] elements = {1, 3, 3, 5, 5, 6, 7, 12, 1, 3};
		Integer[] ele = {25, 77 , 66};
		List<Integer> al = new ArrayList<>();
		
		// Adds all of the specified elements to the specified collection.
		// Elements to be added may be specified individually or as an array
		Collections.addAll( al, elements ); //Since:1.5
		Collections.addAll( al, ele );
		System.out.println("List with duplicates : "+ al );
		
		// This method also provides a convenient way to create a fixed-size list initialized to contain several elements
		List<Integer> list = Arrays.asList( elements );
		// list.add( 77 ); // Calling implementation of abstract AbstractList class which throws UnsupportedOperationException();
		System.out.println("Arrays.asList : "+ list );
		
		// ClassCastException: java.util.Arrays$ArrayList cannot be cast to java.util.ArrayList
		// ArrayList<Integer> listArray = (ArrayList<Integer>) Arrays.asList( elements );
		
		List<Integer> distinctList = list.stream().distinct().collect(Collectors.toList());
		distinctList.add( 77 );
		System.out.println("JAVA 8 « "+ distinctList);
		
		// List allows duplicates, Set contains unique values
		List<Integer> listSet = new ArrayList<>( new LinkedHashSet<>( list ) );
		listSet.add( 77 );
		System.out.println("[List « Set « List] : "+ listSet);
	}
}