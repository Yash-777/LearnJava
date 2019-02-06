package com.github.jdbc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ClientDataBase_Resource {
	public static void main(String[] args) {
		// DB_Type~HOST~PORT~DBName~USERNAME~Password
		String db_details = "MySQL~127.0.0.1~3306~YashDB~Yash~Yash@123", db_query = "select * from EMP";
		String[] returnMessage = getAsserValue(db_details, db_query);
		System.out.format("Databse Value:Message = [%s,%s]\n", returnMessage[0], returnMessage[1]);
		
		String restURL = ""; //URL~Get
		String[] returnResource = accessResource_JAVA_IO(restURL);
		System.out.format("Resource Value:Message = [%s,%s]\n", returnResource[0], returnResource[1]);
	}
	
	public static String[] getAsserValue( String db_details, String db_query) {
		String returnVlaue = "777", returnMessage = "Invalid DB details.";
		String[] db_info = db_details.split("~");
		if( db_info.length == 6 ) {
			String db_Type = db_info[0];
			String host = db_info[1], port = db_info[2], dbName = db_info[3], userName = db_info[4], password = db_info[5];
			
			System.out.format("DB:[%s], Host:Port[%s:%s] DBName:[%s], User:Pass[%s,%s]\n",
					db_Type, host, port, dbName, userName, password);
			
			String driverClass = "", driverURL = "";
			if( db_Type.equalsIgnoreCase("MYSQL") ) { // DB_Type = [MySQL, Oracle]
				driverClass = "com.mysql.jdbc.Driver";
				driverURL = "jdbc:mysql://"+host+":"+port+"/"+dbName;
			}
			
			if( !driverClass.equals("") ) {
				
				try {
					Class.forName(driverClass).newInstance();
					Connection con = DriverManager.getConnection(driverURL, userName, password);
					Statement c_statement = con.createStatement();
					ResultSet resultSet = c_statement.executeQuery( db_query );
					if (resultSet.next()) {
						String dbValue = resultSet.getString(0);
						returnVlaue = dbValue;
						returnMessage = "Succesfully received data from DataBase.";
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnMessage = e.getMessage();
				}
			}
		} else {
			System.out.println("Insufficent values to communicate with DB.");
		}
		return new String[]{ returnVlaue, returnMessage};

	}
	
	public static String[] accessResource_JAVA_IO(String resourceURL) {
		String returnVlaue = "777", returnMessage = "Invalid Resource details.";
		String[] url_Info = resourceURL.split("~");
		String httpMethod = null, targetURL = null;
		if( url_Info.length == 2 ) {
			targetURL = url_Info[0];
			httpMethod = url_Info[1];
		} else {
			System.out.println("Insufficent values to reach the resource.");
		}
		if( targetURL!= null && targetURL.length() > 10 && httpMethod != null ) {
			if (httpMethod.equalsIgnoreCase("GET")) {
				
				try {
					URL url = new URL( targetURL ); 
					BufferedReader responseStream = new BufferedReader(new InputStreamReader( url.openStream() ));
					
					StringBuilder response = new StringBuilder();
					String line;
					while((line = responseStream.readLine()) != null) {
						response.append(line).append('\r');
					}
					responseStream.close();
					returnVlaue = response.toString();
					returnMessage = "Succesfully received data from provided URL.";
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return new String[]{ returnVlaue, returnMessage};
	}
}
