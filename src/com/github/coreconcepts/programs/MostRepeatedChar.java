package com.github.coreconcepts.programs;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

/**
 * To find repeated Character's in a String (or)
 * repeated numbers in an array.
 * 
 * <p>https://codereview.stackexchange.com/a/193433/168515
 * @author yashwanth.m
 *
 */
public class MostRepeatedChar {
	static void findRepeatedNumbers() {
		// https://codereview.stackexchange.com/a/18939/168515
		int[] numbers = { 1, 5, 23, 2, 1, 6, 3, 1, 8, 12, 3 };
		
		Multiset<Integer> set = HashMultiset.create(); // com.google.common.collect.
		for (int c : numbers) {
			set.add( c );
		}
		for( Multiset.Entry<Integer> entry : set.entrySet() ) {
			System.out.println("Element :-> "+entry.getElement()+" Repeat Cnt :-> "+entry.getCount());
		}
		
		Arrays.sort(numbers);

		for(int i = 1; i < numbers.length; i++) {
			if(numbers[i] == numbers[i - 1]) {
				System.out.println("Duplicate: " + numbers[i]);
			}
		}
	}
	static void findRepeatedChars() {
		String str = "how are you doing today";
		
		Map<Character, Integer> repeatedCharsCount = charCountFromString_Java8(str);
				// charCountFromString_Java7(str);
		maxValueFromMap( repeatedCharsCount );
		
		Multiset<Character> set = HashMultiset.create(); // com.google.common.collect.
		for (char c : str.toCharArray()) {
			set.add( c );
		}
		for( Multiset.Entry<Character> entry : set.entrySet() ) {
			System.out.println("Element :-> "+entry.getElement()+" Repeat Cnt :-> "+entry.getCount());
		}
		
		/*
		// merge(K key, V value, BiFunction<prevValue,newValue,resultValur>)
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(1, 1);
		map.merge(1, 5, (prevValue, newValue) -> prevValue + newValue );
		// key 1 is present, so new value 5 will be added to previous value 1, [K:V]=[1:6]
		
		map.put(2, 5);
		map.merge(2, 10, (prevValue, newValue) -> prevValue < newValue ? prevValue : newValue);
		
		map.merge(1, 10, (prevValue, newValue) -> null);
		// Since the BiFunction results in null, the element will be removed from map
		 */
		System.out.println("Using LinkedHashMap to get first most repeated char.");
		Map<Character, Integer> map = new LinkedHashMap<Character, Integer>();
		for (char c : str.toCharArray()) {
			map.merge(c, 1, (prevValue, newValue) -> prevValue + newValue );
		}
		maxValueFromMap( map );
		
		int maxValueInMap = map.values().stream().max( Comparator.comparing( e -> e) ).get();
		System.out.println( maxValueInMap );
	}
	public static void main(String[] args) {
		findRepeatedChars();
		findRepeatedNumbers();
	}
	
	public static Map<Character, Integer> charCountFromString_Java7(String str) {
		Map<Character, Integer> map = new HashMap<>();
		for (char c : str.toCharArray()) {
			
			if( map.containsKey( c ) ) {
				map.put(c, map.get(c)+1 );
			} else {
				map.put(c, 1);
			}
			
			/*Integer count = map.get(c); // null if this map contains no mapping for the key
			if ( count == null ) {
				count = 0;
			}
			map.put(c, count + 1);*/
		}
		
		return map;
	}
	public static Map<Character, Integer> charCountFromString_Java8(String str) {
		Map<Character, Integer> map = new HashMap<>();
		IntStream chars = str.chars();
		//Stream<Integer> boxedInt = chars.boxed(); // Returns a Stream consisting of the elements boxed to an Integer.
		Stream<Character> boxedChars = chars.mapToObj( x -> (char) x );
		
		Set<Entry<Character, Long>> enteries = 
				boxedChars.collect( Collectors.groupingBy( boxedChar -> boxedChar , Collectors.counting() )).entrySet();
		Map<Character, Integer> mapObj = 
				enteries.stream().collect( Collectors.toMap( Map.Entry::getKey, entry -> entry.getValue().intValue() ) );
		//mapObj.forEach( (k,v)->{ System.out.println(k+":"+v); } ); //  (int)(long)entry.getValue()
		map = mapObj;
		
		return map;
	}
	public static Character maxValueFromMap( Map<Character, Integer> map) {
		Character mostRepeatedChar = ' ';
		int maxValueInMap = Collections.max( map.values() );
		for (Entry<Character, Integer> entry : map.entrySet()) {
			if ( entry.getValue() == maxValueInMap ) {
				System.out.format("Char ['%s'] repeated %d times\n", entry.getKey(), maxValueInMap);
				mostRepeatedChar = entry.getKey();
			}
		}
		return mostRepeatedChar;
	}
}