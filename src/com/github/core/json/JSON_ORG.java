package com.github.core.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// import jdk.nashorn.internal.parser.JSONParser;

public class JSON_ORG {
	
	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		InputStream inputStream = null;
		Object obj;
		try {
			obj = parser.parse(new InputStreamReader(inputStream));
			JSONObject jsonObject = (JSONObject) obj;
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
}
