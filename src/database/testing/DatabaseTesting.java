package database.testing;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.DatabaseConnector;

public class DatabaseTesting {
	DatabaseConnector connector;

	@BeforeTest
	public void getConnection() {
		Reporter.log("In Before test");
		connector = new DatabaseConnector();
	}

	@AfterTest
	public void closeConnection() {
		Reporter.log("In After test");
		try {
			if (DatabaseConnector.con != null) {
				DatabaseConnector.con.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getResults() {
		try {
			String query = "select * from userinfo";
			ResultSet res = connector.getConection().executeQuery(query);
			Reporter.log("Results for the query are: \n");
			Reporter.log("\n");
			while (res.next()) {

				Reporter.log(
						res.getString(1) + "\t" + res.getString(2) + "\t" + res.getString(3) + "\t" + res.getString(4));
				System.out.print(res.getString(1));
				System.out.print("\t" + res.getString(2));
				System.out.print("\t" + res.getString(3));
				System.out.println("\t" + res.getString(4));

			}
			System.out.println("\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}