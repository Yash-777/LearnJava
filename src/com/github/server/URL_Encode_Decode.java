package com.github.server;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/**
 * 
 * https://stackoverflow.com/a/47590792/5081877
 * 
 * https://help.marklogic.com/Knowledgebase/Article/View/251/0/using-url-encoding-to-handle-special-characters-in-a-document-uri
 * 
 * https://stackoverflow.com/q/2657515/5081877
 * 
 * @author yashwanth.m
 *
 */
public class URL_Encode_Decode {
	public static void main(String[] args) {
		try {
			
			String paramUrl = "https://www.google.co.in/search?q=encode+url&oq=encode+url";
			paramUrl = paramUrl.replaceAll("&", "!~!");
			String subURL = URLEncoder.encode( paramUrl, "UTF-8");
			String myMainUrl = "http://example.com/index.html?url=" + subURL +"&name=chrome&version=56";
			
			System.out.println("Main URL : "+ myMainUrl );
			
			String decodeMainURL = URLDecoder.decode(myMainUrl, "UTF-8");
			System.out.println("Main URL : "+ decodeMainURL );
			decodeMainURL = decodeMainURL.replaceAll("!~!", "&");
			String[] split = decodeMainURL.split(Pattern.quote( "?" ), 2);
			
			String[] Parameters = split[1].split("&");
			for (String param : Parameters) {
				System.out.println( param );
			}
			
			/*String enc = "http%3A%2F%2Flocalhost%2Fpath%3Fstore%3D17%26key%3DStockCustomSigns";
			String dec = "http://localhost/path?store=17&key=StockCustomSigns";
			String decode = URLDecoder.decode( enc, "UTF-8");
			System.out.println("Decode URL : "+ decode);
			
			System.out.println("URL : "+dec);
			
			String md5Str = "528ec839527e0d6d9016c3d6f2a62d81";
			
			String md5_to_String = stringTomd5( md5Str );
			System.out.println("md5_to_String : "+ md5_to_String);*/
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	static String stringTomd5( String md5Str ) {

		try {
			final MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(md5Str.getBytes(Charset.forName("UTF8")));
			final byte[] resultByte = md.digest();
			
			BigInteger hash = new BigInteger(1, md.digest());
			String result32 = hash.toString(16);
			while(result32.length() < 32) { //40 for SHA-1
				result32 = "0" + result32;
			}
			System.out.println("Res 32 : "+result32);
			
			char[] encodeHex = org.apache.commons.codec.binary.Hex.encodeHex(resultByte);
			final String result = new String(encodeHex);
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}
