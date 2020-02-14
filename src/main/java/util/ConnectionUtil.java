package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
	public static Connection getConnection() {
	
	/*		
		String url = System.getenv("JDBC_URL");
		String user = System.getenv("JDBC_USER");
		String password = System.getenv("JDBC_PASSWORD");
		System.out.println(url + user + password);
	 */	
		
		String url = "jdbc:postgresql://database-1.cwr1aa0nfzpu.us-east-1.rds.amazonaws.com:5432/postgres";
		String user = "abc";
		String password = "abc";
		
		try {
//			 System.out.println("Trying to connect to the database...");
			// DriverManager picks apt JDBC driver to connect to DB
			return DriverManager.getConnection(url, user, password);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Unable to connect to Database");
		return null;
	}
}
