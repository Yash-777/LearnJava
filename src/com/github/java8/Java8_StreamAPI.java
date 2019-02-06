package com.github.java8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Java8_StreamAPI {
	public static void main(String[] args) {
		String filePath = "./lib", fileName = "RequiredJarFiles.txt";
		List<String> readFileIntoListOfWords = readFileIntoListOfWords(fileName, filePath);
		System.out.println( readFileIntoListOfWords.toString() );
		
	}
	
	public static Integer[] primitive2Wrapper(int[] premitive) {
		// https://stackoverflow.com/a/27043087/5081877
		IntStream intStream = Arrays.stream( premitive );
							// IntStream.of( premitive );
		Integer[] wrapper = intStream.boxed() // Stream of the elements boxed to an Integer. 
							.toArray( Integer[]::new );
							// .collect( Collectors.toList() )
		return wrapper;
	}
	
	public static IntStream reverseRange(int from, int to) {
		return IntStream.range(from, to).map(i -> to - i + from - 1);
	}
	public static boolean isNumericString(String str) {
		IntStream cahrStream = str.chars();
		return cahrStream.allMatch(x -> Character.isDigit(x));
	}
	public static String reverseString(String str) {
		IntStream cahrStream = str.chars();
		final int[] array = cahrStream.map( x -> x ).toArray();
		
		int from = 0, upTo = array.length;
		IntFunction<String> reverseMapper = (i) -> ( Character.toString((char) array[ (upTo - i) + (from - 1) ]) );
		
		String reverseString = IntStream.range(from, upTo) // for (int i = from; i < upTo ; i++) { ... }
				.mapToObj( reverseMapper )                 // array[ lastElement ]
				.collect(Collectors.joining())             // Joining stream of elements together into a String.
				.toString();                               // This object (which is already a string!) is itself returned.
		
		System.out.println("Reverse Stream as String : "+ reverseString);
		return reverseString;
	}
	public static char[] reverseStringArray(String str) {
		IntStream cahrStream = str.chars();
		final int[] array = cahrStream.map( x -> x ).toArray();
		
		int from = 0, upTo = array.length;
		IntFunction<String> reverseMapper = (i) -> ( Character.toString((char) array[ (upTo - i) + (from - 1) ]) );
		
		char[] reverseChars = IntStream.range(from, upTo) // for (int i = from; i < upTo ; i++) { ... }
				.mapToObj( reverseMapper )                // array[ lastElement ]
				.collect(Collectors.joining())            // Joining stream of elements together into a String.
				.toCharArray();                           // then converting into char array.
		
		System.out.println("Reverse Charector Stream as an Array : "+ Arrays.toString( reverseChars ));
		return reverseChars;
		/*boolean isNumeric = isNumericString( str );
		if( isNumeric ) {
			final int[] array = str.chars().map( x -> x ).toArray();
		} else {
			//char[] charArray = str.toCharArray();
			//final int[] array  = charArray.stream().map(x -> x - '0').toArray();
		}*/
	}
	
	public static int[] reverseIntegerArray(int[] array) {
		// https://stackoverflow.com/a/~/5081877 « [40696146, 46969009]
		// int startInclusive = 0, endExclusive = array.length;
		int from = 0, upTo = array.length;
		IntUnaryOperator reverseMapper = (i) -> (array[ (upTo - i) + (from - 1) ]);
		
		int[] reverseArr = IntStream.range(from, upTo)
									.map( reverseMapper )
									.toArray();
		
		System.out.println("Reverse Integer Stream Array : "+ Arrays.toString( reverseArr ));
		return reverseArr;
		// IntStream.rangeClosed(1, array.length).mapToObj(i -> array[array.length - i]).toArray();
		
		/*Stream.of( array ).collect(Collectors.toCollection(LinkedList::new))
						  .descendingIterator().forEachRemaining(System.out::println);*/
	}
	public static int[] reverseIntegerArrayBoxed(int[] array) {
		int from = 0, upTo = array.length;
		
		ToIntFunction<Integer> reverseMapper = (i) -> (array[ (upTo - i) + (from - 1) ]);
		
		int[] reverseArr = IntStream.range(from, upTo)
									.boxed().mapToInt( reverseMapper ).toArray();
		
		System.out.println("Reverse Integer Stream from Boxed Array : "+ Arrays.toString( reverseArr ));
		return reverseArr;
	}
	
	public static void reverse_stream_collect_to_list() {
		String[] arr = {"1", "4", "6", "9", "2"};
		Iterator<String> reversedStream = Stream.of(arr)
				.collect(Collectors.toCollection(LinkedList::new))
				.descendingIterator();

		List<String> listReversedOrder = StreamSupport
				.stream(Spliterators.spliteratorUnknownSize(reversedStream, Spliterator.ORDERED), false)
				.collect( Collectors.<String> toList() );
		//listReversedOrder = Lists.newArrayList(reversedStream);// guava utility
		System.out.println(listReversedOrder);
	}
	
	/**
	 * https://stackoverflow.com/a/23202527/5081877
	 * 
	 * @param fileName
	 * @param filePath
	 * @return
	 */
	public static List<String> readFileIntoListOfWords( String fileName, String filePath ) {
		List<String> list = new ArrayList<>();
		try {
			Path path = Paths.get( filePath ); // path to folder
			Path file = path.resolve( fileName ); // filename 
			@SuppressWarnings("resource")
			Stream<String> lines = Files.lines(file);
			list = lines.skip(0)
					.map((line) -> line.split("(\r\n|\r|\n)", -1))
					.flatMap((array) -> Arrays.asList(array).stream())
					.collect(Collectors.toList());
			// (array) -> Arrays.asList(array).stream() (OR) Arrays::stream
			
			System.out.println("Commented Lines:");
			Files.lines(file)
				.filter(stream -> stream.startsWith("<!--"))
				.forEach(System.out::println);
			
			/*.filter(s -> s.length() > 2) « Length > than 2*/
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}