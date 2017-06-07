package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.Reporter;

public class DatabaseConnector {
	public static Connection con = null;
	private static Statement stmt;
	public static String DB_URL = "jdbc:mysql://localhost:3306/user";
	public static String DB_USER = "root";
	public static String DB_PASSWORD = "";

	public Statement getConection() {
		String dbClass = "com.mysql.jdbc.Driver";
		try {
			Reporter.log("Getting new instance for connection class");
			Class.forName(dbClass).newInstance();
			try {
				Reporter.log("Getting connection with connection class");
				Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				Reporter.log("Getting statement for the query");
				stmt = con.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		Reporter.log("Returning statement to the function");
		return stmt;
	}
}
