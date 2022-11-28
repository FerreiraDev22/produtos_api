package br.com.cotiinformatica.factories;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String HOST = "jdbc:mysql://localhost:3306/bd_apiprodutos?serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASS = "johnny";

	public static Connection createConnection() throws Exception {
		Class.forName(DRIVER);
		return DriverManager.getConnection(HOST, USER, PASS);
	}
}



