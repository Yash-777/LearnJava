package com.github.wrappers;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.common.base.CharMatcher;

/**
 * https://docs.rs/regex/0.2.2/regex/
 * https://www.regular-expressions.info/java.html
 * http://www.regular-expressions.info/unicode.html
 * 
 *  https://javascript.info/regexp-groups
 * A group may be excluded by adding ?: in the beginning.
 * 
 * @author yashwanth.m
 *
 */
public class RegualrExpression_PatternMatcher {
	static Pattern pattern;
	public static void main(String[] args) {
		
		String source = "Gass";
		pattern = Pattern.compile(".s");
		Matcher matcher = pattern.matcher(source);
		System.out.println("Matcher : "+ findFullGruopMatcher(matcher));
		
		pathContainsSubPath();
		// https://stackoverflow.com/questions/10372862/java-string-remove-all-non-numeric-characters
		getDigitValue();
		// sequenceMatch();
		
		onlyDigits();
	}
	static boolean pathContainsSubPath() {
		// Make an RE pattern to match as a word only (\b=word boundary)
		//div[2]/p[1]/select[1]/option[2]/div"; « //p[1]/select[1]/option[2]";
		String xpath =  "//div[2]/p[1]/select[1]/option[2]/div";
		Pattern p = Pattern.compile(".*/p.*/select.*/option.*");
		Matcher m = p.matcher( xpath );
		boolean matches = m.matches();
		System.out.println("Match case >> "+matches);
		return matches;
	}
	static void getDigitValue() {
		// https://regexr.com/
		String input = "y.a12.334tyz.78x.77";
				// "Total: Rs. 1,322.00";
		
		// https://www.safaribooksonline.com/library/view/java-cookbook-3rd/9781449338794/ch04.html
		// [^a-zA-Z] - Any one character not from those listed -  .compile("[ [\b0-9\b] || [.+] ]", Pattern.DOTALL|Pattern.MULTILINE)
		Pattern pat = Pattern.compile("[ [\\p{Nd}] || [.+] ]");
		Matcher matcher = pat.matcher( input );
		findFullGruopMatcher(matcher);
		
		System.out.println("DATA : "+ input.replaceAll("[^\\d.]", "") ); // [^\\d]
		
		// Guava - https://github.com/google/guava
		String result = CharMatcher.inRange('0', '9').or(CharMatcher.is('.')).retainFrom(input);
		System.out.println("Guava : "+ result);
		
		// https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html
		ArrayList<Character> employees = (ArrayList<Character>) input.chars()
				.mapToObj( i -> (char)i ).sequential()
				.filter( c -> Character.isDigit(c) || c == '.' )
				.collect(Collectors.toList());
		System.out.println("Java 8 - List of Charectors : "+ employees.toString());
		
		StringBuilder builder = new StringBuilder();
		input.chars()
				.mapToObj( i -> (char)i ).sequential()
				.filter( c -> Character.isDigit(c) || c == '.' )
				.forEach( builder::append );
		System.out.println("Java 8 - String Buffer : "+ builder.toString());
	}
	
	static void onlyDigits() {
		String source = "The following order is already in a pickinglist in draft status: 1006/221";
		String pattern = "(\\d+\\/\\d+)";
		Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(source);
		findFullGruopMatcher(matcher);
		
		String source2 = "Case #: 432     Title: #432";
		String pattern2 = "(#)(\\d+)";
		Pattern p2 = Pattern.compile(pattern2, Pattern.CASE_INSENSITIVE);
		Matcher matcher2 = p2.matcher(source2);
		findFullGruopMatcher(matcher2);
		System.out.println("Pattern Match : "+ buffer.toString().replace("#", "")); // Case#:Title:#
		
	}
	static void browserVersionRange() {
		String ff = "49";
		System.out.println( ff.matches("[0-9]+") );
		System.out.println( ff.length() );
		if (ff.matches("[0-9]+")) {
			int parseInt = Integer.parseInt(ff);
			System.out.println( "Int : "+ parseInt );
			
			if ( parseInt <= 47 ) {
				System.out.println("Marinto Driver");
			} else if ( parseInt > 47 ) {
				System.out.println("Gecho Driver");
			}
		}
	}
	static void replceSlashes() {
		String xapthJSON = 
				"//window[2]//header[@id=\"top\"]/div[1]/nav[1]/ul[1]/li[1]/a[1]";
				//"//window[2]//header[@id=\\\\\'top\\\\\']/div[1]/nav[1]/ul[1]/li[1]/a[1]";
		
		for (int i = 0; i < 5; i++) {
			xapthJSON = xapthJSON.replaceAll("\"", "\'");
			
			// As the windows navigation forward and backward this replace takes place.
			xapthJSON = xapthJSON.replaceAll("\'", "\\\\\'");
			System.out.println("\t « "+xapthJSON);
		}
		
		System.out.println("xapthJSON \n\t"+xapthJSON);
		
		
		/*String missingSemicolonEx = "";
				// (String) jse.executeScript("return sessionStorage.capturedXPaths;");
		if (missingSemicolonEx != null && !missingSemicolonEx.equalsIgnoreCase("undefined")) {
			xapthJSON = missingSemicolonEx.replaceAll("\'", "\\\\\'");
		}*/
	}
	static void sequenceMatch() { // https://stackoverflow.com/a/31242852/5081877
		String[] str = {"MCDL", "XMLIVD", "ABXMLVA", "XMLABCIX"}; 
		Pattern p = Pattern.compile("^(M|D|C|L|X|V|I|i|v|x|l|c|d|m)+$");
		// Returns: true if, and only if, the entire region sequence matches this matcher's pattern
		for (String sequence : str ) {
			boolean match = false, find = false;
			Matcher matcher = p.matcher( sequence );
			if ( !matcher.matches() ) match = true;
			find = findFullGruopMatcher(matcher);
			
			System.out.format("%s \t Match[%s] Find[%s]\n", sequence, match, find);
		}
	}
	
	static StringBuffer buffer = new StringBuffer();
	public static boolean findFullGruopMatcher( Matcher matcher ) {
		// On every call clear the buffer.
		buffer.setLength(0);
		
		boolean find = false;
		while( matcher.find() ) {
			find  = true; // finds the next expression that matches the pattern.
			String group = matcher.group();
			System.out.println("Capturing Group () Value : "+ group);
			buffer.append( group );
		}
		if (find) {
			System.out.println("Full Match Group : "+ buffer.toString() );
		}
		return find;
	}
}
