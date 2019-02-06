package com.github.core.utilcollections;

public class ArrayFunctions {
	public static char[] reverseCharacters( String reverse ) {
		if( reverse != null && reverse != "" && reverse.length() > 0 ) {
			
			char[] arr = reverse.toCharArray();
			// This algorithm iterate over array and swap elements until you reach the midpoint.
			for( int i = 0, j = arr.length-1; i < reverse.length() / 2; i++, j-- ) {
				char temp = arr[j];
				arr[j] = arr[i];
				arr[i] = temp;
			}
			return arr;
		}
		return null;
	}
	public static void reverseStringParts( String reverse ) {
		if( reverse != null && reverse != "" && reverse.length() > 0 ) {
			
			char[] arr = reverse.toCharArray();
			String forwordRevesePart = "", backwordReversePart = "", middleChar = "";
			for( int i = reverse.length()/2 - reverse.length()%2, j = arr.length-1; i >= 0; i--, j-- ) {
				forwordRevesePart += arr[i];
				backwordReversePart += arr[j];
			}
			System.out.println("Forword Reverse Charectors : "+ forwordRevesePart);
			System.out.println("Backword Reverse Charectors : "+ backwordReversePart);
			
			if( reverse.length() % 2 != 0 ) {
				middleChar += arr[reverse.length() / 2];
				System.out.println("Middle Charector : "+ middleChar);
			}
		}
	}
	public static String reverseString( String reverse ) {
		if( reverse != null && reverse != "" && reverse.length() > 0 ) {
			
			char[] arr = reverse.toCharArray();
			String temp = "";
			for( int i = arr.length-1; i >= 0; i-- ) {
				temp += arr[i];
			}
			System.out.println("Reverse String : "+ temp);
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
		
		// return Arrays.toString( array );
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
	public static String intToString(int[] arraySequence) {
		StringBuffer buffer = new StringBuffer("[");
		for (int i = 0; i < arraySequence.length; i++) {
			buffer.append(arraySequence[i]);
			if( i+1 < arraySequence.length ) {
				buffer.append(", ");
			}
		}
		buffer.append("]");
		return buffer.toString();
	}
	public static String toString(int[] a) { // Arrays.toString( array );
		if (a == null)
			return "null";
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0; ; i++) {
			b.append(a[i]);
			if (i == iMax)
				return b.append(']').toString();
			b.append(", ");
		}
	}
}
