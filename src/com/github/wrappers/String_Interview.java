package com.github.wrappers;

import com.github.core.utilcollections.ArrayUtils;
import com.github.coreconcepts.programs.ReverseObject;

public class String_Interview {
	public static void main(String[] args) {
		//hashCodes();
		// With out converting to Numbers add/sum.
		getNumericValue('5');
		getNumericValue('j');
		
		// Form Java8 possible to reduce the code
		addingNumberStrings("65689", "6596");
		addingNumberStrings("95689", "9596");
		
		addingNumberStrings("689", "86596");
	}

	public static void hashCodes() {
		String s = "Aa", y = "BB";
		StringHelper.printHash(s);
		StringHelper.printHash(y);
		/*
		String[Aa], Hash[2112], SystemHash[366712642]
		String[BB], Hash[2112], SystemHash[1829164700]
		*/
		String str = "Yashwanth";
		ReverseObject.stringReverse(str);
		ReverseObject.stringReverseDistinct(str);
	}
	
	private static void addingNumberStrings(String s1, String s2) {
		//String s1 = "65689", s2 = "6596";
		
		String stringResult = "";
		// Convert it to Char array.
		char[] c1 = s1.toCharArray();
		char[] c2 = s2.toCharArray();
		if (c1.length >= c2.length) { // S1 + S2
			stringResult = ArrayUtils.arraysAddition_Char(c1, c2);
		} else { // S2 + S1
			stringResult = ArrayUtils.arraysAddition_Char(c2, c1);
		}
		System.out.format("[%s + %s]=[%s]\n --- \n", s1, s2, stringResult);
	}
	
	/**
	 * <pre>
	 * @code{
	 * ASCII Table
		Dec  = Decimal Value
		Char = Character
		
		'5' has the int value 53
		if we write '5'-'0' it evaluates to 53-48, or the int 5
		if we write char c = 'B'+32; then c stores 'b'
	 * }
	 * </pre>
	 * 
	 * https://www.cs.cmu.edu/~pattis/15-1XX/common/handouts/ascii.html
	 */
	public static int getNumericValue(char charValue) {
		int asciiValue = charValue; // CharacterData - codePoint
		int numericValue = Character.getNumericValue(asciiValue);
		System.out.format("Char[%s], Ascii[%d], Numaric Char[%s]\n", charValue, asciiValue, numericValue);
		if (numericValue > 9) {
			// int numericValue = Integer.parseInt( Character.toString(charValue) );
			System.out.println("Provided Character is not a Number, Which may lead to NumberFormatException");
			numericValue = 0; // Threw Exception: NumberFormatException
		}
		return numericValue;
	}
}
