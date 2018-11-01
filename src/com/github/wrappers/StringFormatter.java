package com.github.wrappers;

/**
 * https://stackoverflow.com/a/49408297/5081877
 * 
 * @author yashwanth.m
 *
 */
public class StringFormatter {
	
	private static String lineSeparator;
	
	public static void main (String[] args) throws java.lang.Exception {
		
		lineSeparator = java.security.AccessController.doPrivileged(
				new sun.security.action.GetPropertyAction("line.separator"));
		
		String [] countries = {"Egypt", "France", "Japan", "Switzerland"};
		printTable(countries);
	}

	public static void printTable( String[] countries ){
		int maxLength = getLongestString( countries );
		
		
		for(int i = 0; i < countries.length; i++) {
			String key = String.format("%-" + maxLength + "s =[%s], %s", countries[i], countries[i], lineSeparator);
			System.out.println( key );
			//System.out.format("%-" + maxLength + "s %s", countries[i], lineSeparator);
		}

	}
	public static int getLongestString(String[] array) {
		int maxLength = 0;
		String longestString = null;
		for (String s : array) {
			if (s.length() > maxLength) {
				maxLength = s.length();
				longestString = s;
			}
		}
		System.out.format("longest string: '%s'\n", longestString);
		return maxLength;
	}
}
