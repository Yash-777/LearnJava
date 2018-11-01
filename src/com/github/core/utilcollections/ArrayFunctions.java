package com.github.core.utilcollections;

public class ArrayFunctions {
	public static char[] reverseString( String reverse ) {
		if( reverse != null && reverse != "" && reverse.length() > 0 ) {
			
			int nav = reverse.length() / 2;
			char[] arr = reverse.toCharArray();
			
			for( int i = 0, j = arr.length-1; i < nav; i++, j-- ) {
				char temp = arr[j];
				arr[j] = arr[i];
				arr[i] = temp;
			}
			return arr;
		}
		return null;
	}
	
	public static String objectToString(Object[] array) {
		StringBuffer buffer = new StringBuffer("[");
		for (int i = 0; i < array.length; i++) {
			buffer.append(array[i]);
			if( i+1 < array.length ) {
				buffer.append(", ");
			}
		}
		buffer.append("]");
		return buffer.toString();
	}
	public static String charToString(char[] array) {
		StringBuffer buffer = new StringBuffer("[");
		for (int i = 0; i < array.length; i++) {
			buffer.append(array[i]);
			if( i+1 < array.length ) {
				buffer.append(", ");
			}
		}
		buffer.append("]");
		return buffer.toString();
	}
	public static String numberToString(Number[] array) {
		StringBuffer buffer = new StringBuffer("[");
		for (int i = 0; i < array.length; i++) {
			buffer.append(array[i]);
			if( i+1 < array.length ) {
				buffer.append(", ");
			}
		}
		buffer.append("]");
		return buffer.toString();
	}
}
