package com.github.os.networks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLFormatter {
	public static void main(String[] args) {
		String fullPathURL = "https://github.com/Yash-777/LearnJava";
		String domainURL = "https://github.com";
		
		isUrlFormatValid(domainURL);
		parseUrlDomain(domainURL);
		
		isUrlFormatValid(fullPathURL);
		parseUrlDomain(fullPathURL);
	}
	public static boolean isUrlFormatValid(String url) {
		Pattern pattern = Pattern.compile("^(https?:\\/\\/)?(www\\.)?([\\w]+\\.)+[\u200C\u200B\\w]{2,63}\\/?$");
		Matcher matcher = pattern.matcher(url);
		if (matcher.matches()) {
			System.out.println(matcher.group(1));
			return true;
		} else {
			System.err.println("Url format is not valid");
			return false;
		}
	}

	public static String parseUrlDomain(String url) {
		Pattern p = Pattern.compile("^(?:https?:\\/\\/)?(?:www\\.)?((?:[\\w]+\\.)+\\w+)");
		Matcher matcher = p.matcher(url);
		if (matcher.matches()) {
			System.out.println(matcher.group(1));
			return matcher.group(1);
		}
		System.err.println("Url domain is not parsed ");
		return null;
	}
}
