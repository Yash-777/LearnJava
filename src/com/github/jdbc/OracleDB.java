package com.github.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;

/**
 * https://docs.oracle.com/cd/E11882_01/appdev.112/e12137/getconn.htm#TDPJD127
 * http://www.codejava.net/java-se/jdbc/connect-to-oracle-database-via-jdbc
 * 
 * @author yashwanth.m
 *
 */
public class OracleDB {
	public static void main(String[] args) {
	
		try {
			boolean useServiceName = true;
			// Load the JDBC driver
			String driverName = "oracle.jdbc.driver.OracleDriver";
			Class.forName(driverName).newInstance();
			
			// Create a connection to the database
			String 
			serverName = "localhost", // 10.101.5.25
			portNumber = "1521",
			sid = "xe", // orcl
			serviceName = "",
			driverURL = "",
			UserName = "test1_data", // scott
			Pasword = "test1_data"; // tiger
			
			if (useServiceName) {
				driverURL = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + "/" + serviceName;
			} else {
				driverURL = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
			}
			Connection con = DriverManager.getConnection(driverURL, UserName, Pasword);
			
			// Create Oracle DatabaseMetaData object
			DatabaseMetaData meta = con.getMetaData();
			System.out.println("JDBC driver version is " + meta.getDriverVersion());
			System.out.println("Connection : "+con.hashCode());
		} catch(ClassNotFoundException ex) {
			System.out.println("Error: unable to load driver class!");
		} catch(IllegalAccessException ex) {
			System.out.println("Error: access problem while loading!");
		} catch(InstantiationException ex) {
			System.out.println("Error: unable to instantiate driver!");
		} catch (SQLRecoverableException e) {
			// IO Error: The Network Adapter could not establish the connection 1522 « 1521
		} catch (SQLException e) {
			String message = e.getMessage();
			// Listener refused the connection with the following error:
			if( message.contains("ORA-12505")) {
				// ORA-12505, TNS:listener does not currently know of SID given in connect descriptor
				System.out.println("Change portNumber:SID « portNumber/serviceName");
				System.out.println("IPAddress:1521:sid « IPAddress:1521/servicename");
				
				// The Connection descriptor used by the client was: localhost:1521:xe
			} else if( message.contains("ORA-12514")) {
				// ORA-12514, TNS:listener does not currently know of service requested in connect descriptor
				System.out.println("Incorrect service name.");
			}
			e.printStackTrace();
		}
	}
}
