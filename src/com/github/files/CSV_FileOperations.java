package com.github.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import org.json.simple.JSONObject;

import com.google.common.base.CharMatcher;

/**
 * https://stackoverflow.com/q/1695699/5081877
 * 
 * @author yashwanth.m
 *
 */
public class CSV_FileOperations {
	static List<HashMap<String, String>> listObjects = new ArrayList<HashMap<String,String>>();
	protected static List<JSONObject> jsonArray = new ArrayList<JSONObject >();
	
	public static void main(String[] args) {
		String csvFilename = "D:/Yashwanth/json2Bson.csv";
		//Reading( csvFilename );
		
		csvToJSONString(csvFilename);
		String jsonData = jsonArray.toString();
		System.out.println("File JSON Data : \n"+ jsonData);
	}
	
	public static void Reading( String csvFilename ) {
		try {
			File file = new File( csvFilename );
			FileInputStream inputStream = new FileInputStream(file);
			
			String fileExtensionName = csvFilename.substring(csvFilename.indexOf(".")); // fileName.split(".")[1];
			System.out.println("File Extension : "+ fileExtensionName);
			
			// FileReader always uses the default encoding for the system.
			// FileReader fileReader = new FileReader( csvFilename );
			
			InputStreamReader inputStreamReader = new InputStreamReader( inputStream );
			
			BufferedReader buffer = new BufferedReader( inputStreamReader );
			Stream<String> readLines = buffer.lines();
			boolean headerStream = true;
			for (String line : (Iterable<String>) () -> readLines.iterator()) {
				String[] columns = line.split(",");
				if (headerStream) {
					System.out.println(" ===== Headers =====");
					
					listRowData( columns );
					headerStream = false;
					System.out.println(" ===== ----- Data ----- =====");
				} else {
					listRowData( columns );
				}
			}
			inputStreamReader.close();
			buffer.close();
			
			/*@SuppressWarnings("resource")
			CSVReader csvReader = new CSVReader( inputStreamReader );
								// new CSVReader( fileReader );
			List<String[]> lines = csvReader.readAll();
			boolean headers = true;
			for (String[] line : lines) {
				
				if (headers) {
					System.out.println(" ===== Headers =====");
					listRowData(line);
					headers = false;
					System.out.println(" ===== ----- Data ----- =====");
				} else {
					listRowData(line);
				}
			}
			
			inputStream.close();*/
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String listRowData( String[] row ) {
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < row.length; i++) {
			
			builder.append( row[i] );
			
			if( i+1 < row.length ) builder.append("<!!>");
		}
		System.out.println("Data: "+ builder.toString() );
		return builder.toString();
	}
	
	@SuppressWarnings("deprecation")
	public static String csvToJSONString( String csvFilename ) {
		try {
			File file = new File( csvFilename );
			FileInputStream inputStream = new FileInputStream(file);
			
			String fileExtensionName = csvFilename.substring(csvFilename.indexOf(".")); // fileName.split(".")[1];
			System.out.println("File Extension : "+ fileExtensionName);
			
			// [{"Key2":"21","﻿Key1":"11","Key3":"31"} ]
			InputStreamReader inputStreamReader = new InputStreamReader( inputStream, "UTF-8" );
			
			BufferedReader buffer = new BufferedReader( inputStreamReader );
			Stream<String> readLines = buffer.lines();
			boolean headerStream = true;
			
			List<String> headers = new ArrayList<String>();
			for (String line : (Iterable<String>) () -> readLines.iterator()) {
				String[] columns = line.split(",");
				if (headerStream) {
					System.out.println(" ===== Headers =====");
					
					for (String keys : columns) {
						// ﻿ - UTF-8 - ? � https://stackoverflow.com/a/11021401/5081877
						String printable = CharMatcher.INVISIBLE.removeFrom( keys );
						String clean = CharMatcher.ASCII.retainFrom(printable);
						String key = clean.replace("\\P{Print}", "");
						headers.add( key );
					}
					headerStream = false;
					System.out.println(" ===== ----- Data ----- =====");
				} else {
					addCSVData(headers, columns );
				}
			}
			inputStreamReader.close();
			buffer.close();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public static void addCSVData( List<String> headers, String[] row ) {
		if( headers.size() == row.length ) {
			HashMap<String,String> mapObj = new HashMap<String,String>();
			JSONObject jsonObj = new JSONObject();
			for (int i = 0; i < row.length; i++) {
				mapObj.put(headers.get(i), row[i]);
				jsonObj.put(headers.get(i), row[i]);
			}
			jsonArray.add(jsonObj);
			listObjects.add(mapObj);
		} else {
			System.out.println("Avoiding the Row Data...");
		}
	}
}
