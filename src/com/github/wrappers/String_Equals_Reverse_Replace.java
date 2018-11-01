package com.github.wrappers;

import java.util.regex.Pattern;

public class String_Equals_Reverse_Replace {
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	public static void main(String[] args) {
		String_Equals_Reverse_Replace o = new String_Equals_Reverse_Replace();
		System.out.println("Object Hash : "+o.hashCode()); // 366712642
		
		String s1 = new String("Yash");
		String s2 = "Yash";
		hashCode_MemoryAddress(s1); // Data Hash[2748285]	Object Address:[1829164700]
		hashCode_MemoryAddress(s2); // Data Hash[2748285]	Object Address:[2018699554]
		
		Wrappers_Range_EqualityCheck.dataEquality(s1, s2); // Data Matches
		Wrappers_Range_EqualityCheck.referenceEquality(s1, s2); // Reference Not Matches
		
		String str = "//div[contains(@class, 'ui-datepicker-group-first')]//a[contains(@class, 'ui-state-active')]";
		String quotedText = Pattern.quote( "'" );
		replaceAllTokens(str, quotedText, "\\\\'");
	}
	public static void replaceAllTokens(String str, String quotedText, String token) {
		String strNew = str.replaceAll(quotedText, "\\\\'");
		System.out.println("replaceAllTokens « New String : "+ strNew );
	}
	
	public static void hashCode_MemoryAddress(Object o) {
		System.out.println("Object hash code is generated from the content of a String."
				+ "Where as JVM address locations may vary");
		System.out.format("Data Hash[%d]\tObject Address:[%d]\n",
				o.hashCode(),  System.identityHashCode( o ));
	}
}