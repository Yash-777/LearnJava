package com.github.jdbc;

import com.github.files.CSV_FileOperations;
import com.mongodb.DBObject;

/**
 * http://www.csvjson.com/csv2json
 * http://shancarter.github.io/mr-data-converter/
 * 
 * @author yashwanth.m
 *
 */
public class File2BSON extends CSV_FileOperations {
	
	public static void main(String[] args) {
		String csvFilename = "D:/Yashwanth/json2Bson.csv";
		
		String key = "?yash";
		System.out.println( key.replace("?", ""));
		
		csvToJSONString(csvFilename);
		String jsonData = jsonArray.toString();
		System.out.println("File JSON Data : \n"+ jsonData);
		
		// https://stackoverflow.com/a/12261133/5081877
		DBObject bson = ( DBObject ) com.mongodb.util.JSON.parse( jsonData );
		System.out.println("BSON Object : "+ bson);
		//String db_json = com.mongodb.util.JSON.serialize( dbObj );
		
		/*JSONObject json = new JSONObject();
		json.put("K1", "V1");
		json.put("K2", "V2");
		System.out.println("Json : "+ json.toString() ); // {"K1":"V1","K2":"V2"}
		
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("K1", "V1");
		data.put("K2", "V2");
		System.out.println("Map : "+ data.toString() ); // {K1=V1, K2=V2}
		 */
	}
}