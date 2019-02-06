package com.github.wrappers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class NestedSubString_InBetween {
	public static void main(String[] args) {
		String str = "test string (67) and (77)", open = "(", close = ")";
		
		// Generic Solution
		String subStr = str.substring(str.indexOf( open ) + 1, str.indexOf( close ));
		System.out.format("String[%s] Parsed IntValue[%d]\n", subStr, Integer.parseInt( subStr ));
		
		// Regular-Expressions
		String patternMatch = patternMatch(generateRegex(open, close), str);
		System.out.println("Regular expression Value : "+ patternMatch);
		
		// Apache Software Foundation
		String substringBetween = StringUtils.substringBetween(subStr, open, close);
		System.out.println("Commons Lang3 : "+ substringBetween);
		
	}
	public static String generateRegex(String open, String close) {
		return "(" + RegexUtils.escapeQuotes(open) + ")(.*?)(" + RegexUtils.escapeQuotes(close) + ").*";
	}
	
	public static String patternMatch(String regex, CharSequence string) {
		// In DOTALL mode, the expression . matches any character,including a line terminator.
		// By default this expression does not match line terminators. 
		final Pattern pattern  = Pattern.compile(regex, Pattern.DOTALL);
		final Matcher matcher = pattern .matcher(string);
		
		String returnGroupValue = null;
		if (matcher.find()) { // while() { Pattern.MULTILINE }
			System.out.println("Full match: " + matcher.group(0));
			System.out.format("Character Index [Start:End]«[%d:%d]\n",matcher.start(),matcher.end());
			for (int i = 1; i <= matcher.groupCount(); i++) {
				System.out.println("Group " + i + ": " + matcher.group(i));
				if( i == 2 ) returnGroupValue = matcher.group( 2 );
			}
		}
		return returnGroupValue;
	}
}
