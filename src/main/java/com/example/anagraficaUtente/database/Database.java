package com.example.anagraficaUtente.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testit", "root", "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch(ClassNotFoundException e) {
		e.printStackTrace();
	}
	
		return conn;
	}
}
	
