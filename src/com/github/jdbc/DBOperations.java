package com.github.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * The class DBConnection is to get the connection form DB.
 * To process the data b/w java and DB Files.
 * <P>In order to execute DAO layer separately with out touching any other
 * layers. We are using this class.</P>
 * 
 * <P>The main aim of this class is to is to map between the 
 * 'User' table in the database and the 'User' model/POJO in the Java code.</p>
 * 
 * @author yashwanth.m
 *
 */
public class DBOperations extends DBCP2_ConnectionPool {
	
	public static void main(String[] args) {
		DBOperations dao = new DBOperations();
		String verifyEmail = dao.verifyEmail("yashwanth.merugu@gmail.com");
		System.out.println("Email : "+verifyEmail);
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