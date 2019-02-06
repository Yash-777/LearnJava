package com.github.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * The class DBConnection is to get the connection form DB.
 * To process the data b/w java and DB Files.
 * <P>In order to execute DAO layer separately with out touching any other
 * layers. We are using this class.</P>
 * 
 * <P>The main aim of this class is to is to map between the 
 * 'User' table in the database and the 'User' model/POJO in the Java code.</p>
 * 
 * https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-usagenotes-connect-drivermanager.html
 * http://jtds.sourceforge.net/faq.html#urlFormat
 * @author yashwanth.m
 *
 */
public class DBOperations extends DBCP2_ConnectionPool {
	
	/**
	 * When you are using JDBC outside of an application server, the DriverManager class manages the 
	 * establishment of connections.
	 * Specify to the DriverManager which JDBC drivers to try to make Connections with. The easiest 
	 * way to do this is to use Class.forName() on the class that implements the java.sql.Driver interface.
	 * 
	 * Batch Updates Performance improve : https://stackoverflow.com/a/10617768/5081877
	 * jdbc:mysql://host:3306/db?useServerPrepStmts=false&rewriteBatchedStatements=true
	 * 
	 */
	static boolean MySQL_ConnectorType4_Ver5 = true;
	public void driverManagerConnection() {
		try {
			// The newInstance() call is a work around for some broken Java implementations
			if( MySQL_ConnectorType4_Ver5 ) {
				Class.forName( DriverClass ).newInstance(); // // MySQL Connector/J » 6.0.6
			} else {
				// Loading class `com.mysql.jdbc.Driver'. This is deprecated.
				// The new driver class is `com.mysql.cj.jdbc.Driver'.
				DB_URL += "?autoReconnect=true&useSSL=false";
				Class.forName("com.mysql.cj.jdbc.Driver").newInstance(); // MySQL Connector/J » 6.0.6
			}
			System.out.println("DB_URL : "+ DB_URL);
			
			
			Connection con = DriverManager.getConnection(DB_URL, DB_User, DB_Password);
			System.out.println("Got Connection From DB.");
			
			long startTime = System.currentTimeMillis();
			
			String verifyEmail = verifyEmail("yashwanth.merugu@gmail.com");
			System.out.println("Email : "+verifyEmail);
			
			long endTime = System.currentTimeMillis();
			long duration = (endTime - startTime);  
			System.out.format("Milli = %s, ( S_Start : %s, S_End : %s ) \n", duration, startTime, endTime );
			con.close();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		DBOperations dao = new DBOperations();
		/*
		String verifyEmail = dao.verifyEmail("yashwanth.merugu@gmail.com");
		System.out.println("Email : "+verifyEmail);*/
		
		dao.driverManagerConnection();
		
	}
	
	private static final String VERIFY_EMAIL = "SELECT * FROM `gmailusers` WHERE `email` = ?";
	public String verifyEmail(String email) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String isMailRegistered = "UnRegistered";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement( VERIFY_EMAIL );
			pstmt.setString(1, email );
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ) {
				isMailRegistered = "Registered";
			}
		} catch ( Exception e ) {
			System.out.println("Exception : verifyEmail " + e.toString());
			System.out.println(VERIFY_EMAIL +"\n"+ email);
		} finally {
			closeConnections(rs, pstmt, null, conn);
		}
		return isMailRegistered;
	}
}