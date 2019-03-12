package com.github.wrappers;

import com.github.coreconcepts.programs.ReverseObject;

public class String_Interview {
	public static void main(String[] args) {
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
	
}
