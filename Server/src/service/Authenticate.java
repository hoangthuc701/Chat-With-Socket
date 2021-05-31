package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.Connect;

public class Authenticate {
	private static String _dbURL;
	private static String _userName;
	private static String _password;

	public Authenticate(String dbURL, String userName, String password) {
		_dbURL = dbURL;
		_userName = userName;
		_password = password;
	}

	public String Login(String username, String password) throws SQLException {
		Connection connection = Connect.getConnection(_dbURL,_userName, _password);
		
		
		Statement stmt = connection.createStatement();
		String sql = "Select * from Students";
		ResultSet rs = stmt.executeQuery(sql);
		
		if (!rs.next()) {
			connection.close();
			return "-1";
		}
		connection.close();
		return (String) rs.getString(1);
	}

	public boolean Register(String username, String password, String name) {
		return true;
	}
}
