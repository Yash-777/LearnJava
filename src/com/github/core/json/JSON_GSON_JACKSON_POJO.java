package com.github.core.json;

/**
 * JOSN « org.json/json/20090211
 * JavaScript « com.googlecode.json-simple/json-simple/1.1
 * GSON « com.google.code.gson/gson/2.8.0
 * JACKSON « com.fasterxml.jackson.core/jackson-core/2.3.1
 * 
 * writing JavaScript-JSON And reading GSON-JSON.
 * 
 * JOSN to Map|List|Object « https://stackoverflow.com/a/32219975/5081877
 * 
 * https://stackoverflow.com/questions/31202840/creating-a-jsonobject-from-inputstreamreader
 * https://stackoverflow.com/questions/27036857/create-jsonobject-from-pojo
 * 
 * @author yashwanth.m
 *
 */
public class JSON_GSON_JACKSON_POJO {

	public static void main(String args[]) {
		GoogleCode_JavaScript.writeJson("JsonFile.json");
		Google_GSON.readgson("JsonFile.json");
	}
	
}