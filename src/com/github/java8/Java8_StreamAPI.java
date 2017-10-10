package com.github.java8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Java8_StreamAPI {
	public static void main(String[] args) {
		String filePath = "./lib", fileName = "RequiredJarFiles.txt";
		List<String> readFileIntoListOfWords = readFileIntoListOfWords(fileName, filePath);
		System.out.println( readFileIntoListOfWords.toString() );
		
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