package com.github.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;


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
	public static void driverManagerConnection() {
		try {
			DB_URL = "jdbc:mysql://preproddb.clictest.com:3306/clictest";
					/*+ "?"
					+ "useServerPrepStmts=false&cachePrepStmts=true";*/
			DB_User = "appsadmin";
			DB_Password = "inf0tree#99";
			System.out.println("DB_URL : "+ DB_URL);
			// The newInstance() call is a work around for some broken Java implementations
			Class.forName( DriverClass ).newInstance();
			Connection con = DriverManager.getConnection(DB_URL, DB_User, DB_Password);
			System.out.println("Got Connection From DB.");
			for (int i = 0; i < 10; i++) {
				long startTime = System.currentTimeMillis();
				
				getStepsForTestDataFromDB(con, 479, "REC_02-02-2018_1517552917_46341_0"); // Pre
				//getStepsForTestDataFromDB(con, 456, "REC_02-02-2018_1517550140_123883_0"); // Tata
				
				long endTime = System.currentTimeMillis();
				long duration = (endTime - startTime);  
				System.out.format("Milli = %s, ( S_Start : %s, S_End : %s ) \n", duration, startTime, endTime );
			
			}
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
	public static void getStepsForTestDataFromDB(Connection conn, int testcaseid_tdc, String recordnameid) {
		try {
			
			PreparedStatement pstmt = conn.prepareStatement(GET_TESTDATA);
			pstmt.setInt(1, testcaseid_tdc);
			pstmt.setString(2, recordnameid);
			ResultSet rs = pstmt.executeQuery();
			
			System.out.println(" Got data success fully Successfully.");
			pstmt.close();
		} catch (Exception e) {
		}
	}
	static String GET_TESTDATA = "SELECT ob.objectname_objrep,ob.type_objrep,ob.id_objrep,ob.locator_objrep,testcasedata.waittime,ts.inputdata,ts.id_tdr,ts.action_type "
			+ "FROM  testdataresults ts "
			+ "JOIN objectrepository ob ON objname = object_id "
			+ "left join ("
			+ "select tc.objectid,tc.waittime from testcasesteps as tc "
			+ "join  testdatatestcases as tdt on tc.testcase_id=tdt.testcase_id "
			+ "where tdt.incrementid_tdtc = ? ) As testcasedata on ob.object_id= testcasedata.objectid "
			+ "WHERE recordid_trd = ? "
			+ "AND ob.status =0  ORDER BY  ts.id_tdr ASC";
	
	public static void main(String[] args) {
		/*DBOperations dao = new DBOperations();
		String verifyEmail = dao.verifyEmail("yashwanth.merugu@gmail.com");
		System.out.println("Email : "+verifyEmail);*/
		
		driverManagerConnection();
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