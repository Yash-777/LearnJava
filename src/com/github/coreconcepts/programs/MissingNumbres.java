 package com.github.coreconcepts.programs;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.github.core.utilcollections.ArrayFunctions;

public class MissingNumbres {
	public static void main(String[] args) {
		int[] arrayRandom = { 24, 1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 18, 20, 21, 23, 0 };
		//missingJava6( arrayWithInts );
		
		// https://stackoverflow.com/a/48858301/5081877
		System.out.println("IntStream methods average(), findAny(), findFirst(), max(), min(), reduce()");
		IntStream stream = Arrays.stream(arrayRandom);
		int max = stream.max().getAsInt();
		// java.lang.IllegalStateException: stream has already been operated upon or closed
		int min = Arrays.stream(arrayRandom).min().getAsInt();
		System.out.format("Array with Min:Max [%d:%d] values \n", min, max);
		
		int sum = Arrays.stream(arrayRandom).sum();
		OptionalInt reduce = Arrays.stream(arrayRandom).reduce( (x, y)->x + y );
		double average = Arrays.stream(arrayRandom).average().getAsDouble();
		System.out.println("Sum of array elements : "+ sum+", Reduce : "+reduce);
		System.out.println("average() : "+ average); 
		
		// https://stackoverflow.com/a/40554920/5081877
		int[] arraySequence = IntStream.range(min, max).toArray();
		System.out.println("Sequence Array : "+ ArrayFunctions.intToString( arraySequence) );
		
		/*Stream<String> numbers = Stream.of("1", "2", "3", "4", "5");
		int[] ints = 
			numbers.mapToInt(Integer::parseInt).toArray();
			numbers.toArray(Integer[]::new);
		ArrayList list = numbers.collect(Collectors.toCollection(ArrayList::new));
		
		System.out.println("Printing array elements : "); 
		Arrays.stream(arraySequence).forEach(e->System.out.print(e + " "));
		*/
		
	}
	public static void missingJava6( int[] arr ) {
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
