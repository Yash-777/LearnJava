package com.github.wrappers;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Locale;

import me.xuender.unidecode.Unidecode;


/**
 * UNICODE - http://www.unicode.org/standard/WhatIsUnicode-more.html
 * http://www.online-toolz.com/tools/text-unicode-entities-convertor.php
 * 
 * http://r12a.github.io/apps/conversion/
 * 
 * @author yashwanth.m
 *
 */
public class Strings_UnicodeEqalityCheck {
	static String strUni = "Tĥïŝ ĩš â fůňķŷ Šťŕĭńġ Æ,Ø,Ð,ß";
	public static void main(String[] args) {
		String s1 = "My Sample Space Data", s2 = "My Sample Space Data";
		System.out.format("S1 Bytes: %s\n", Arrays.toString(s1.getBytes()));
		System.out.format("S2 Bytes: %s\n", Arrays.toString(s2.getBytes()));

		String initials = Unidecode.decode( s2 );
		if( s1.equals(s2)) { //[ , ] %A0 - %2C - %20 « http://www.ascii-code.com/
			System.out.println("Equal Unicode Strings");
		} else if( s1.equals( initials ) ) {
			System.out.println("Equal Non Unicode Strings");
		} else {
			System.out.println("Not Equal");
		}
		
		//variousSpaceCheck(s1, s2);
		//replaceCheck(s1, s2);
		//Normalizing_Text(s1, s2);
		
		// Supported Character sets « http://php.net/manual/en/function.htmlentities.php
		byte[] unicodeBytes = Charset.forName("UTF-8").encode( s1 ).array();
		for (byte codePoint : unicodeBytes) {
			String hexString = Integer.toHexString( (int)codePoint ).toUpperCase(Locale.ENGLISH);;
			String UnicodeName = Character.getName( (int) codePoint );
			String replace = Character.UnicodeBlock.of( codePoint ).toString();
			System.out.println(replace +" :: "+ hexString +" :: "+ UnicodeName);
		}
		System.out.println("String to byte array : "+ Arrays.toString( unicodeBytes ) );
	}
	public static void variousSpaceCheck( String s1, String s2 ) {
		String spacing_entities = "very wide space,narrow space,regular space,invisible separator";
		System.out.println("Space String :"+ spacing_entities);
		byte[] byteArray =
				//spacing_entities.getBytes( Charset.forName("UTF-8") );
				{-30, -128, -125, 44, -30, -128, -126, 44, 32, 44, -62, -96};
		System.out.println("Bytes:"+ Arrays.toString( byteArray ) );
		try {
			System.out.format("Bytes to String[%S] \n ", new String(byteArray, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		byte[] unicodeBytes = Charset.forName("UTF-16").encode( s2 ).array();
		for (byte b : unicodeBytes) {
			
			String hexString = Float.toHexString( (float)b );
			System.out.println( hexString );
		}
		System.out.println("String to byte array : "+ Arrays.toString( unicodeBytes ) );
	}
	public static void replaceCheck( String s1, String s2 ) {
		// http://www.regular-expressions.info/unicode.html
		// \p{Z} or \p{Separator}: any kind of whitespace or invisible separator.
		// s2 = s2.replaceAll("\\p{Zs}", " ");
		// s2 = s2.replaceAll("[^\\p{ASCII}]", " ");
		if( s1.equals(s2)) { //[ , ] %A0 - %2C - %20 « http://www.ascii-code.com/
			System.out.println("Equal Unicode Strings");
		} else if( s1.equals(s2.replace(" ", " ")) ) {
			System.out.println("Equal Non Unicode Strings");
		} else {
			System.out.println("Not Equal");
		}
	}
	public static void Normalizing_Text( String s1, String s2 ) {
		// Using java.text.Normalizer ➩  http://docs.oracle.com/javase/tutorial/i18n/text/normalizerapi.html
		// Compatibility decomposition « http://www.unicode.org/versions/Unicode5.0.0/ch03.pdf#G729
		/* java.text.Normalizer.Form
		 * This enum provides constants of the four Unicode normalization forms that are described in [Unicode Standard Annex #15]() 
		 * — Unicode Normalization Forms and two methods to access them.
		 * 
		 * http://www.unicode.org/reports/tr15/tr15-23.html
		 * 
		 * Compatibility - http://www.unicode.org/reports/tr15/Slide4.JPG
		 * Canonical     - http://www.unicode.org/reports/tr15/Slide3.JPG
		 */
		s2 = Normalizer.normalize(s2, Normalizer.Form.NFKC);
		System.out.println("After Normalization : "+s2);
		if( s1.equals(s2)) {
			System.out.println("Equal Unicode Strings");
		} else {
			System.out.println("Not Equal");
		}
		
		
	}
}
