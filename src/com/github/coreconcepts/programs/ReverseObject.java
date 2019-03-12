package com.github.coreconcepts.programs;

import java.util.LinkedList;
import java.util.List;

public class ReverseObject {
	public static void main(String[] args) {
		int num = 1234;
		reverseNumber(num);
		reverseNumberString(num);
		
		String str = "Yashwanth";
		stringReverse(str);
		stringReverseDistinct(str);
		
		StringBuffer buff = new StringBuffer(str);
		System.out.println("String Buffer : "+ buff.reverse());
		
		// Use List interface to reverse string.
		char[] ch = str.toCharArray();
		List<Character> reverse = new LinkedList<>();
		for (int i = ch.length - 1; i >= 0; i--) {
			reverse.add(ch[i]);
		}
		System.out.println("Reverse of a String with LIST : "+ reverse);
	}
	
	public static String stringReverseDistinct(String str) {
		char[] ch = str.toCharArray();
		String reverse = "";
		for (int i = ch.length - 1; i >= 0; i--) {
			if ( !(reverse.indexOf(ch[i]) > -1) ) // Remove duplicates
				reverse += ch[i];
		}
		System.out.println("Reverse of a String with DISTINCT : "+ reverse);
		return reverse;
	}

	public static String stringReverse(String str) {
		char[] ch = str.toCharArray();
		String reverse = "";
		for (int i = ch.length - 1; i >= 0; i--) {
			//if ( !(reverse.indexOf(ch[i]) > -1) ) // Remove duplicates
				reverse += ch[i];
		}
		System.out.println("Reverse of a String : "+ reverse);
		return reverse;
	}

	public static int reverseNumberString(int num) {
		String str = new Integer(num).toString();
		char[] ch = str.toCharArray();
		String reverse = "";
		for (int i = ch.length - 1; i >= 0; i--) {
			//if ( !(reverse.indexOf(ch[i]) > -1) ) // Remove duplicates
				reverse += ch[i];
		}
		System.out.println("Reverse of a String Number : "+ reverse);
		return Integer.parseInt(reverse);
	}

	public static int reverseNumber(int num) {
		int temp = num;
		int rev = 0;
		int sum = 0; // sumOfDigits
		while (temp > 0 ) {
			rev = (rev * 10) + temp % 10;
			sum += temp % 10;
			temp = temp / 10;
		}
		System.out.println("Reverse of a Number : "+ rev);
		System.out.println("Sum of Digits of a Number : "+ sum);
		
		if(rev == num) {
			System.out.println("Polyndrome Number : [121 = 121]");
		}
		return rev;
	}
}
