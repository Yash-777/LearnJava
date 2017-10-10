package com.github.core.json;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;

public class GoogleCode_JavaScript {
	// org.json.simple.parser.JSONParser;
	
	public static void main(String[] args) {
		String jsonString = getJSONString( "JsonFile.json" );
		try {
			org.json.JSONObject obj = new  org.json.JSONObject( jsonString );
			System.out.println("Data : "+ obj.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public static String getJSONString(String fileName) {
		InputStream is;
		StringBuilder sb = new StringBuilder();
		try {
			is = new FileInputStream( fileName );
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
			while ((line = br.readLine()) != null) {
				sb.append(line+"\n");
			}
			br.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static void writeJson( String file ) {
	
		JSONObject json = new JSONObject();
	
		json.put("Key1", "Value");
		json.put("Key2", 777); // Converts to "777"
		json.put("Key3", null);
		json.put("Key4", false);
	
			JSONArray jsonArray = new JSONArray();
			jsonArray.put("Array-Value1");
			jsonArray.put(10); 
			jsonArray.put("Array-Value2");
	
		json.put("Array : ", jsonArray); // "Array":["Array-Value1", 10,"Array-Value2"]
	
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("Obj-Key1", 20);
			jsonObj.put("Obj-Key2", "Value2");
			jsonObj.put(4, "Value2"); // Converts to "4"
	
		json.put("InnerObject", jsonObj);
	
			JSONObject jsonObjArray = new JSONObject();
				JSONArray objArray = new JSONArray();
				objArray.put("Obj-Array1");
				//objArray.put(0, "Obj-Array3");
			jsonObjArray.put("ObjectArray", objArray);
	
		json.put("InnerObjectArray", jsonObjArray);
	
			Map<String, Integer> sortedTree = new TreeMap<String, Integer>();
			sortedTree.put("Sorted1", 10);
			sortedTree.put("Sorted2", 103);
			sortedTree.put("Sorted3", 14);
	
		json.put("TreeMap", sortedTree);
	
		try {
			System.out.println("Writting JSON into file ...");
			System.out.println(json);
			FileWriter jsonFileWriter = new FileWriter(file);
			jsonFileWriter.write(json.toJSONString());
			jsonFileWriter.flush();
			jsonFileWriter.close();
			System.out.println("Done");
		
		} catch (IOException e) { 
			e.printStackTrace();
		}
	
	}
}
