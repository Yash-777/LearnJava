package com.github.core.json;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Google_GSON {
	public static JsonParser parser = new JsonParser();
	
	public static void readgson(String file) {
		try {
			System.out.println( "Reading JSON file from Java program" );
	
			FileReader fileReader = new FileReader( file );
			JsonObject object = (JsonObject) parser.parse( fileReader );
	
			Set <java.util.Map.Entry<String, com.google.gson.JsonElement>> keys = object.entrySet();
			if ( keys.isEmpty() ) {
				System.out.println( "Empty JSON Object" );
			}else {
				Map<String, Object> map = json_UnKnown_Format( keys );
				System.out.println("Json 2 Map : "+map);
			}
	
		} catch (IOException ex) {
			System.out.println("Input File Does not Exists.");
		}
	}
	
	public static Map<String, Object> json_UnKnown_Format( Set <Entry<String, JsonElement>> keys ){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		for (Entry<String, JsonElement> entry : keys) {
			String keyEntry = entry.getKey();
			System.out.println(keyEntry + " : ");
			JsonElement valuesEntry =  entry.getValue();
			if (valuesEntry.isJsonNull()) {
				System.out.println(valuesEntry);
				jsonMap.put(keyEntry, valuesEntry);
			} else if (valuesEntry.isJsonPrimitive()) {
				System.out.println("P - "+valuesEntry);
				jsonMap.put(keyEntry, valuesEntry);
			} else if (valuesEntry.isJsonArray()) {
				JsonArray array = valuesEntry.getAsJsonArray();
				List<Object> array2List = new ArrayList<Object>();
				for (JsonElement jsonElements : array) {
					System.out.println("A - "+jsonElements);
					array2List.add(jsonElements);
				}
				jsonMap.put(keyEntry, array2List);
			} else if (valuesEntry.isJsonObject()) {
				JsonObject obj = (JsonObject) parser.parse(valuesEntry.toString());
				Set <java.util.Map.Entry<String, JsonElement>> obj_key = obj.entrySet();
				jsonMap.put(keyEntry, json_UnKnown_Format(obj_key));
			}
		}
		return jsonMap;
	}
}
