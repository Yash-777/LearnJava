package com.github.wrappers;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.StringUtils_Local;

/**
 * A pull request to apache/commons-lang
 * <a href="https://github.com/apache/commons-lang/pull/393">393</a>
 * , 
 * <a href="https://github.com/apache/commons-lang/pull/394">394</a>
 * ,
 * <a href="https://github.com/apache/commons-lang/pull/395">395</a>
 * https://travis-ci.org/apache/commons-lang/builds/475193675
 * 
 * @author yashwanth.m
 *
 */
public class AppacheStringUtils {

	public static void main(String[] args) throws Exception {
		print( StringUtils_Local.replaceSubstringInBetween("wx[b]yz", "YY", "[", "]") );
		print( StringUtils_Local.replaceSubstringInBetween(null, null, "*", "*") );
		print( StringUtils_Local.replaceSubstringInBetween("yabczyabcz", "---", "y", "z") );
	}
	
	public static String dynamicRecordData(String str, String rowSaperator, String rowDataSaperator, List<String[]> dynamicRecords) throws Exception {
		if( str.length() <= ( (rowSaperator.length())*2 + rowDataSaperator.length()) ) {
			return str;
		}
		
		String substringBetween = StringUtils.substringBetween(str, rowSaperator, rowSaperator);
		if( substringBetween == null) return str;
		// System.out.println("Sub String : "+ substringBetween );
		
		int countMatches = StringUtils.countMatches(substringBetween, rowDataSaperator);
		// System.out.println("Count Matches : "+ countMatches);
		
		if( str.length() <= ( (rowSaperator.length())*2 + (rowDataSaperator.length())*countMatches ) ) {
			return str;
		}
		
		StringBuffer dynamicRowData = new StringBuffer();
		
		String[] searchList = null;
		for (String[] dynamicRecord : dynamicRecords) {
			if( dynamicRecord.length == countMatches ) {
				if( searchList == null ) {
					searchList = new String[countMatches];
					for (int i = 0; i < countMatches; i++) {
						searchList[i] = rowDataSaperator;
					}
				}
				
				String replaceEach = StringUtils.replaceEach(substringBetween, searchList, dynamicRecord);
				// System.out.println("After Replace : "+ replaceEach);
				
				dynamicRowData.append(replaceEach);
			} else {
				throw new Exception("Records mismatch.");
			}
		}
		
		// System.out.println("Dynamic Records Data :\n"+ dynamicRowData.toString() );
		
		String finalTableData = StringUtils_Local.replaceSubstringInBetween(str, dynamicRowData.toString(), rowSaperator, rowSaperator);
		// System.out.println("Final :\n"+ finalTableData);
		return finalTableData;
	}

	public static void print(String str) {
		System.out.println( str );
	}
}