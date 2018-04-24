package com.insonix.klv.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
			try {
				Class.forName(DRIVER_CLASS_NAME);
				connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				System.out.println("Connection Successful");
			} catch (Exception e) {
				System.out.println("Failed to connect: " + e.getMessage());
				e.printStackTrace();
			}
	}
	

	public static Connection getConnection() {
		if(null==connection) {
			openConnection();
		}
		return connection;
	}

	public static void closeConnection() {
		if (null != connection) {
			try {
				connection.close();
				System.out.println("Connection closed.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method is use for close the prepared statement object.
	 * @param preparedStatement
	 */
	public static void closePreparedStatement(PreparedStatement preparedStatement) {
		if (null != preparedStatement) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		SQLServerConnection.getConnection();
	}

}
