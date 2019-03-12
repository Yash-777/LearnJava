package com.github.wrappers;

import java.util.Objects;

/**
 * org.hibernate.internal.util.StringHelper
 * 
 * @author yashwanth.m
 *
 */
public class StringHelper {
	private StringHelper() { /* static methods only - hide constructor */
	}
	
	public static boolean isNotEmpty(String string) {
		return string != null && string.length() > 0;
	}

	public static boolean isEmpty(String string) {
		return string == null || string.length() == 0;
	}
	
	public static String replaceOnce(String template, String placeholder, String replacement) {
		if ( template == null ) {
			return null;  // returnign null!
		}
		int loc = template.indexOf( placeholder );
		if ( loc < 0 ) {
			return template;
		}
		else {
			return template.substring( 0, loc ) + replacement + template.substring( loc + placeholder.length() );
		}
	}
	public static void printEqualsCheck(String str, String anotherString) {
		System.out.format("[%b]:Status \t OutCome:Expected ['%s':'%s']\n", StringHelper.equalStrings(str, anotherString), str, anotherString);
	}
	
	
	public static void isNullType(String s) {
		if( s == null) {
			System.out.format("Null type [%s]\n", s);
		} else {
			System.out.format("Null value [%s]\n", s);
		}
	}
	public static boolean equalStrings(String str, String anotherString) {
		/*boolean status = false;
		if (str == null && anotherString == null) {
			status = true;
		} else if (str != null && str.equals(anotherString)) {
			status = true;
		}
		return status;*/
		System.out.println("Objects.equals() : "+ Objects.equals(str, anotherString));
		return  Objects.equals(str, anotherString);
		//return (a == b) || (a != null && a.equals(b));
	}
	
	public static void printHash(String str) {
		System.out.format("String[%s], Hash[%d], SystemHash[%d]\n",
				str, str.hashCode(), System.identityHashCode(str));
	}
	
	public static void objectInfo(Object o) {
		System.out.format("Address:[%d], Hash:[%d]\n",System.identityHashCode(o), o.hashCode());
	}
}
