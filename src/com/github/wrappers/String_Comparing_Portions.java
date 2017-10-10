package com.github.wrappers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * https://docs.oracle.com/javase/tutorial/java/data/comparestrings.html
 * https://stackoverflow.com/q/86780/5081877
 * 
 * @author yashwanth.m
 *
 */
public class String_Comparing_Portions {
	public static void main(String[] args) {
		String s1 = "My Sample Data", s2 = "My sample data", test1 = "dat", test2 = "data";
		StringBuffer buffer = new StringBuffer(s2);
		// Compares this string to the specified object. 
		System.out.println("Equals : "+ s1.equals( buffer.toString() ));
		System.out.println(" - Ingnore Case : "+ s1.equalsIgnoreCase(s2));
		
		// public boolean contains(CharSequence s) { return indexOf(s.toString()) > -1; }
		System.out.println("Contains : "+ s1.contains( test1 ));
		System.out.println(" - Ignore Case : "+ containsIgnoreCase(s1, test1) );
		boolean containsIgnoreCase = org.apache.commons.lang3.StringUtils.containsIgnoreCase(s1, test1);
		System.out.println(" - Apache Lang : "+ containsIgnoreCase);// [AbBaCca, bac] - true
		System.out.println(" - Exact Word : "+ containsExactWord( s1, test2, false ));
		System.out.println(" - - Ignore Case : "+ containsExactWord( s1, test2, true ));
		
		// Compares this string to the specified CharSequence.
		System.out.println("Content : "+ s1.contentEquals( buffer ));
		
		// Compares two strings lexicographically.
		// The comparison is based on the Unicode value of each character in the strings.
		System.out.println("Compare : "+ s1.compareTo( s2 ));
		System.out.println(" - Ignore Case : "+ s1.compareToIgnoreCase(s2));
		
	}
	
	/**
	 * '\b' Matches a word boundary where a word character is [a-zA-Z0-9_].
	 * Refer ULR: https://stackoverflow.com/a/25418057/5081877
	 */
	public static boolean containsExactWord( String source, String searchStr, boolean isIgnoreCase ) {
		String pattern = "\\b"+searchStr+"\\b";
		Pattern p;
		if (isIgnoreCase) {
			p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		} else {
			p = Pattern.compile(pattern);
		}
		
		Matcher m = p.matcher(source);
		return m.find();
	}
	public static boolean containsIgnoreCase(String source, String searchChar) {
		return StringUtils.containsIgnoreCase(source, searchChar);
	}
}
