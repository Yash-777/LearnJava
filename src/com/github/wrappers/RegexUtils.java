package com.github.wrappers;

/**
 * 
 * <a href="https://docs.oracle.com/javase/tutorial/essential/regex/literals.html">
 * Regfex Literals</a>
 * 
 * @author yashwanth.m
 *
 */
public class RegexUtils {
	static String escapeChars = "\\.?![]{}()<>*+-=^$|";
	public static String escapeQuotes(String str) {
		if(str != null && str.length() > 0) {
			return str.replaceAll("[\\W]", "\\\\$0"); // \W designates non-word characters
		}
		return "";
	}
	
	public static void main(String[] args) {
		String matched = "(hello)", regexExpGrup = "(" + escapeQuotes(matched) + ")";
		System.out.println("Regex : "+ regexExpGrup); // (\(hello\))
	}
}
