package com.github.wrappers;

import com.github.core.utilcollections.ArrayFunctions;
import com.github.java8.Java8_StreamAPI;

/**
String [Immutable - every method in the String class creates new object and return the value]
	- When you Concatenate a value to String it creates new object in Heap Memorie | it check the value present in pool if not present creates it.

String Buffer|Builder [mutable - updates the existing value ]

When to use which one :
If a string is going to remain constant throughout the program, then use String class object because a String object is immutable.
If a string can change (example: lots of logic and operations in the construction of the string) and will only be accessed from a single thread, using a StringBuilder is good enough.
If a string can change, and will be accessed from multiple threads, use a StringBuffer because StringBuffer is synchronous so you have thread-safety.

Strings are constant; their values cannot be changed after they are created. String buffers support mutable strings. Because String objects are immutable they can be shared.

String [Immutable - every method in the String class creates new object and return the value]
	- When you Concatenate a value to String it creates new object in Heap Memorie | it check the value present in pool if not present creates it.

String Buffer|Builder [mutable - upades the existing value ]

When to use which one :
If a string is going to remain constant throughout the program, then use String class object because a String object is immutable.
If a string can change (example: lots of logic and operations in the construction of the string) and will only be accessed from a single thread, using a StringBuilder is good enough.
If a string can change, and will be accessed from multiple threads, use a StringBuffer because StringBuffer is synchronous so you have thread-safety.

Strings are constant; their values cannot be changed after they are created. String buffers support mutable strings. Because String objects are immutable they can be shared.


 * @author yashwanth.m
 *
 */
public class String_Builder_Buffer {
	public static void main(String[] args) {
		//stringFunctions();
		
		String s = new String("Yash");
		StringBuffer buff = new StringBuffer("Yash");
		
		// To clear the buffer data
		/*buff.setLength(0);
		buff.delete(0, buff.length());*/
		
		String sbuff = buff.toString();
		equalityCheck(s, sbuff);
		
		System.out.println("Identity HashCodes : "+ System.identityHashCode(s));
		
		System.out.println("Identity HashCodes : "+ System.identityHashCode(buff));
			buff.append(". M"); // appends data to the object in Heap.
		System.out.println("Identity HashCodes : "+ System.identityHashCode(buff));
		
		
		String reverse = "Yashwanth";
		char[] reverseChars = ArrayFunctions.reverseCharacters(reverse);
		System.out.println("Reverse String : "+ ArrayFunctions.charToString( reverseChars ) );
		
		Java8_StreamAPI.reverseStringArray(reverse);
	}
	
	static void stringFunctions() {
		String str = "literal"; // Pool {1}
		
		str = str + " - A CharSequence is a readable sequence of char values"; // Pool {1,2}
		System.out.println("String : "+str);
		
		str.concat("."); // Pool {1,2,3} {1}
		
		// https://stackoverflow.com/questions/46398042/string-touppercase-created-a-new-object-in-heap-or-string-pool
		String upperCase = str.toUpperCase(); // Pool {1,2,3} {1,2}
		System.out.println("Case : "+upperCase);
		
		String hashCheck = str.toUpperCase();
		equalityCheck(hashCheck, upperCase);
	}
	static void equalityCheck(String s1, String s2) {
		
		if( s1 == s2 ) {
			System.out.println(" ===== Equal Hash Codes ===== ");
		}
		if( s1 != null && s1.equals(s2) ) { // java.lang.NullPointerException
			System.out.println(" ----- Equal Pool Values ----- ");
		}
	}
}
