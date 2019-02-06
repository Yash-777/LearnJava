package com.github.os;

import java.util.Map;
import java.util.Objects;

/**
 * <pre><code>
 * void getChars(char dst[], int dstBegin) {
 *      System.arraycopy(value, 0, dst, dstBegin, value.length);
 * }
 * public String(StringBuffer buffer) {
 *   synchronized(buffer) {
 *     this.value = Arrays.copyOf(buffer.getValue(), buffer.length());
 *   }
 * }
 * </code></pre>
 * @author yashwanth.m
 *
 */
public class SystemUtil {
	public static void main(String[] args) {
		String obj1 = "Yash";
		String obj2 = "10";
		
		print(obj1, obj2);
		//java.util.IllegalFormatConversionException: o != java.lang.String [S1= "Y" - %o format]
		print(obj1, 10);
		System.out.println("Objects.equals() : "+ Objects.equals(obj1, obj2));
	}
	public static void printHorizontalLine() {
		System.out.println("===== ----- # ----- =====");
	}
	public static void printWithHashCode(Object obj1) {
		print(obj1);
		System.out.format(" « Hash:[%d]\n", System.identityHashCode(obj1));
	}
	public static void printWithHashCode(Object obj1, Object obj2) {
		print(obj1, obj2);
		System.out.format("  « Hash1:[%d], Hash2:[%d]\n",
				System.identityHashCode(obj1), System.identityHashCode(obj2));
	}
	public static void printMapEntery(Map.Entry<?, ?> entry) {
		System.out.format("Entery Hash:[%10d] « ", System.identityHashCode(entry));
		Object key = entry.getKey();
		Object value = entry.getValue();
		System.out.format("Key:Vlaue = [%s:%s]\n", key.toString(), value.toString());
	}
	public static void print(Object obj1) {
		if (obj1 instanceof CharSequence) { // String, StringBuffer, StringBuilder.
			System.out.format("S:[%s]\n", obj1);
		} else if (obj1 instanceof Number) { // Byte, Integer, Long
			System.out.format("N:[%d]\n", obj1);
		} else if (obj1 instanceof Boolean) {
			System.out.format("B:[%b]\n", obj1);
		} else {
			System.out.format("C:[%c]\n", obj1);
		}
	}
	public static void print(Object obj1, Object obj2) {
		// https://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html
		if( equalTypeObjects(obj1, obj2) ) {
			if (obj1 instanceof CharSequence) { // String, StringBuffer, StringBuilder.
				System.out.format("S1:[%s], S2:[%s]\n", obj1, obj2);
			} else if (obj1 instanceof Number) { // Byte, Integer, Long
				System.out.format("N1:[%d], N2:[%d]\n", obj1, obj2);
			} else if (obj1 instanceof Boolean) {
				System.out.format("B1:[%b], B2:[%b]\n", obj1, obj2);
			} else {
				System.out.format("C1:[%c], C2:[%c]\n", obj1, obj2);
			}
		} else {
			System.out.format("O1:[%s], O2:[%s]\n", obj1.toString(), obj2.toString());
		}
	}
	public static boolean equalTypeObjects(Object obj1, Object obj2) {
		if (obj1.getClass().equals(obj2.getClass())) { // Same Class type
			return true;
		} else {
			return false;
		}
	}
}