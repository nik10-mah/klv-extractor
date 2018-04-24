package com.insonix.klv.dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Pramod Maurya
 * @since 19-Mar-2018
 */
public class SQLServerConnection {

	private static final String DRIVER_CLASS_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	// the client and the SQL server are running on the same machine. We specify
	// this mode by adding the property integratedSecurity=true
	// private static final String URL =
	// "jdbc:sqlserver://localhost:1433;database=codice-alliance-db;integratedSecurity=true;";
	private static final String URL = "jdbc:sqlserver://DESKTOP-9JON4NV\\SQLEXPRESS;databaseName=AMPS9000;integratedSecurity=true;";
	//
	private static final String USERNAME = "Nikhil";
	private static final String PASSWORD = "1qaz!QAZ";

	// private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
	// private static final String URL = "jdbc:mysql://localhost:3306/klv";
	// private static final String USERNAME = "root";
	// private static final String PASSWORD = "root";

	private static Connection connection;

	public static void openConnection() {
		if (null == connection) {
			try {
				Class.forName(DRIVER_CLASS_NAME);
				connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				System.out.println("Connection Successful");
			} catch (Exception e) {
				System.out.println("Failed to connect: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public static Connection getConnection() {
		return connection;
	}

	public static void main(String[] args) {
		SQLServerConnection.openConnection();
	}

}
