package org.apache.commons.lang3;

import com.github.wrappers.StringHelper;

public class StringUtils_UnitTest {
	public static void main(String[] args) {
		/*testReplace(null, "", "", "", null);
		testReplace("", null, "", "", "null");
		testReplace("", "Y", null, "", "");
		testReplace("", "Y", "", null, "");
		testReplace("", "Yash", "", "", "Yash");
		testReplace("Yash[777].[M]", "77", "[", "]", "Yash[77].[M]");
		testReplace("Yash[[777].[M]", "77", "[", "]", "Yash[77].[M]");
		testReplace("Yash[777]].[M]", "77", "[", "]", "Yash[77]].[M]");
		
		testReplace("Yash[[777].[M]", "77", "[[", "]", "Yash[[77].[M]");
		testReplace("Yash[777]].[M]", "77", "[", "]]", "Yash[77]].[M]");
		// Fails When not checking index.
		testReplace("Yash[[777].[M]", "77", "[[[", "]", "Yash[[777].[M]");
		testReplace("Yash[[777].[M]", "77", "[", "]]", "Yash[[777].[M]");
		
		testReplace("Yash[777]", null, "", "", "Yash[777]");*/
		
		System.out.println("StringHelper.equalStrings(str, anotherString)");
		StringHelper.printEqualsCheck("", null);
		StringHelper.printEqualsCheck(null, null);
		StringHelper.printEqualsCheck(null, "");
		StringHelper.printEqualsCheck("", "");
		StringHelper.printEqualsCheck("Yash", "Yash");
		
		/*testSubStr("Yash[[777].[M]", "[[[", "]", null);
		testSubStr("Yash[[777].[M]", "[[", "]", "777");
		testSubStr("Yash[777]]].[M]", "[", "]]]", "777");*/
		
		/*String str = "Yash[[[777]]].[M]";
		final int start = str.indexOf("[");
		String preceding = str.substring(0, start);
		System.out.println( preceding );
		int close = str.indexOf("]");
		System.out.println( str.substring(close));
		*/
		String s = "77"+null;
		StringHelper.isNullType(s);
	}
	
	public static void test(String str, String replace, String open, String close, String expected) {
		StringHelper.printEqualsCheck(StringUtils_Local.substringBetween(str, open, close), expected);
	}
	public static void testSubStr(String str,String open, String close, String expected) {
		StringHelper.printEqualsCheck(StringUtils_Local.substringBetween(str, open, close), expected);
	}
	public static void testReplace(String str, String replace, String open, String close, String expected) {
		StringHelper.printEqualsCheck(StringUtils_Local.replaceSubstringInBetween(str,replace, open, close), expected);
	}
	
}