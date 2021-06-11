package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

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
		Connection connection = Connect.getConnection(_dbURL, _userName, _password);

		Statement stmt = connection.createStatement();
		String sql = "Select * from Account where username='" + username + "' and password='" + password + "'";
		System.out.print(sql);
		ResultSet rs = stmt.executeQuery(sql);
		System.out.print(rs);
		if (!rs.next()) {
			connection.close();
			return "-1";
		}
		String result = (String) rs.getString(1);
		System.out.print(result);
		connection.close();
		return  result;
	}

	public boolean Register(String username, String password, String name) throws SQLException {

		Connection connection = Connect.getConnection(_dbURL, _userName, _password);
		String sql = "INSERT INTO Account (username,password,name) VALUES(?,?,?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, username);
		stmt.setString(2, password);
		stmt.setString(3, name);
		
		stmt.executeUpdate();
		connection.close();

		return true;
	}
}
