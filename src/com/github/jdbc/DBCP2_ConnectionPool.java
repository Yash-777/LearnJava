package com.github.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 * In <a href="https://stackoverflow.com/a/37785577/5081877">Connection pool</a>
 * mechanism, when the class is loaded it get's the 
 * physical JDBC connection objects and provides a wrapped physical connection 
 * object to user. PoolableConnection is a wrapper around the actual connection.
<UL>
<LI>getConnection() pick one of the free wrapped-connection form the connection object-pool and returns it.</LI>
<LI>close() instead of closing it returns the wrapped-connection back to pool.</LI>
</ul>
 * 
 * @author yashwanth.m
 *
 */
public class DBCP2_ConnectionPool {
	
	static Properties props = new Properties();
	static final BasicDataSource ds_dbcp2 = new BasicDataSource();
	public static String DriverClass = "com.mysql.jdbc.Driver";
	public static String DB_URL, DB_User, DB_Password;
	
	static {
		ClassLoader loader = DBCP2_ConnectionPool.class.getClassLoader();
		
		try {
			props.load( loader.getResourceAsStream("connectionpool.properties") );
			
			if (props.getProperty("DBURL") != null	&& !"".equals(props.getProperty("DBURL")) &&
					props.getProperty("UserName") != null	&& !"".equals(props.getProperty("UserName")) &&
					props.getProperty("Password") != null ){
				
				DB_URL = "jdbc:mysql://"+props.getProperty("DBURL");
				DB_User = props.getProperty("UserName");
				DB_Password = props.getProperty("Password");
				
				System.out.println("Credentials:" + DB_URL);
				
				ds_dbcp2.setDriverClassName( DriverClass );
				ds_dbcp2.setUrl( DB_URL );
				ds_dbcp2.setUsername( DB_User );
				ds_dbcp2.setPassword( DB_Password );
				ds_dbcp2.setInitialSize( 5 ); // ds_dbcp.setMaxActive( 5 );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method pick's one of the free wrapped-connection form the connection 
	 * object-pool and returns it.
	 * 
	 * @return the SQL connection to caller.
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return ds_dbcp2.getConnection();
	}
	
	public void closeConnections(ResultSet rs, PreparedStatement pstmt, Statement stmt, Connection conn) {
		try { if( null != rs    )    rs.close(); } catch (SQLException e) { e.printStackTrace(); }
		try { if( null != stmt  )  stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
		try { if( null != pstmt ) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
		try { if( null != conn  )  conn.close(); } catch (SQLException e) { e.printStackTrace(); }
	}
}