package com.github.files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import sun.awt.RepaintArea;

public class DuplicateWordSearcher {

	public static void main(String[] args) {
		String text = 
				"a r b k c d se f g a d f s s f d s ft gh f ws w f v x s g h d h j j k f sd j e wed a d f";
				/*"Write a program to read words from a file. Count the repeated or duplicated words."
				+ "Sort it by maximum repeated or duplicated word count.";*/
		List<String> list = Arrays.asList(text.split(" "));
		
		Set<String> uniqueWords = new HashSet<String>(list);
		for (String word : uniqueWords) {
			System.out.println(word + ": " + Collections.frequency(list, word));
		}
		
		repeatedWordFromFile("FileData.txt");
	}
	// "C:\\sample.txt"
	static void repeatedWordFromFile(String fileName) {
		//Creating wordCountMap which holds words as keys and their occurrences as values
		HashMap<String, Integer> wordCountMap = new HashMap<String, Integer>();
		BufferedReader reader = null;
		
		try {
			//Creating BufferedReader object
			reader = new BufferedReader(new FileReader(fileName));
			
			//Reading the first line into currentLine
			String currentLine = reader.readLine();
			
			while (currentLine != null) {
				//splitting the currentLine into words
				String[] words = currentLine.toLowerCase().split(" ");
				//Iterating each word
				for (String word : words) {
					//if word is already present in wordCountMap, updating its count
					if(wordCountMap.containsKey(word)) {
						wordCountMap.put(word, wordCountMap.get(word)+1);
					} else { //otherwise inserting the word as key and 1 as its value
						wordCountMap.put(word, 1);
					}
				}
				//Reading next line into currentLine
				currentLine = reader.readLine();
			}
			//Getting all the entries of wordCountMap in the form of Set
			Set<Entry<String, Integer>> entrySet = wordCountMap.entrySet();
			
			//Creating a List by passing the entrySet
			List<Entry<String, Integer>> list = new ArrayList<Entry<String,Integer>>(entrySet);
			
			//Sorting the list in the decreasing order of values 
			Collections.sort(list, new Comparator<Entry<String, Integer>>() {
				@Override
				public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
					return (e2.getValue().compareTo(e1.getValue()));
				}
			});
			//Printing the repeated words in input file along with their occurrences
			System.out.println("Repeated Words In Input File Are :");
			
			for (Entry<String, Integer> entry : list) {
				if (entry.getValue() > 1) {
					System.out.println(entry.getKey() + " : "+ entry.getValue());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close(); //Closing the reader
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}