package com.github.core.utilcollections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashMapKeyVlaueSets {
	
	public static void main(String[] args) {
		String excelSheet = "1,A,A1~1,B,B1~1,C,C1~~2,A,A2~2,B,B2~~3,A,A3~3,B,B3";
		Map<String, Object> sheet = getExcelData(excelSheet, "~~", "~", ",");
		System.out.println("Sheet: "+sheet);
		
		Set<String> keySet = sheet.keySet();
		System.out.println("X-Axis Keys: "+ keySet);
		
		
	}
	private static Map<String, Object> getExcelData(String excelSheet, String recordSaperator, String rowSplit, String columnSplit) {
		Map<String, Object> sheet = new HashMap<String, Object>();
		
		String[] records = excelSheet.split(recordSaperator);
		for (int i = 0; i < records.length; i++) {
			Map<String, String> matrix = new HashMap<String, String>();
			String[] rows = records[i].split(rowSplit);
			String key = null;
			for (int j = 0; j < rows.length; j++) {
				String[] cloumns = rows[j].split(columnSplit);
				String XAxis = cloumns[0];
				String YAxis = cloumns[1];
				String value = cloumns[2];
				if (j == 0) {
					key = XAxis;
				}
				matrix.put(YAxis, value);
			}
			
			if (key != null) {
				System.out.println("Matrix: "+matrix);
				sheet.put(key, matrix);
			}
		}
		return sheet;
	}
}
