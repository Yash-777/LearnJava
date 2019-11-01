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
		
		String housrsMinutes = "14:69"; // 14:59
		String[] split = housrsMinutes.split(":");
		String minutes = split[1];
		System.out.println("Split Text: "+minutes);
		
		validate_HHMM(housrsMinutes);
		validate_MM(minutes);
	}
	
	public static void validate_HH_15MinInterval(String housrsMinutes) {
		String timeRegxHHMM =  "(?:[01]\\d|2[0123]):((00)|(15)|(30)|(45))";
		if ((housrsMinutes != null && !housrsMinutes.equals("")) && (!housrsMinutes.matches(timeRegxHHMM))) {
			System.out.println("HH:MM(15 interval) Invalid");
		} else {
			System.out.println("HH(00-23) : MM(15 interval)OR should be 24:00");
		}
	}
	public static void validate_HHMM(String housrsMinutes) {
		String timeRegxHHMM =  "(?:(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]))";
		if ((housrsMinutes != null && !housrsMinutes.equals("")) && (!housrsMinutes.matches(timeRegxHHMM))) {
			System.out.println("HH:MM Invalid");
		} else {
			System.out.println("HH:MM Valid");
		}
	}
	public static void validate_MM(String minutes) {
		String timeRegxMM =  "(?:([0-5][0-9]))";
		if ((minutes != null && !minutes.equals("")) && (!minutes.matches(timeRegxMM))) {
			System.out.println("MM Invalid");
		} else {
			System.out.println("MM Valid");
		}
	}
}
