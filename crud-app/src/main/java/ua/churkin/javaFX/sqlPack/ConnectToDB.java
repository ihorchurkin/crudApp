package ua.churkin.javaFX.sqlPack;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import ua.churkin.javaFX.Entities.User;
import ua.churkin.javaFX.crud_app.App;

public class ConnectToDB {
	private static Connection getConnection() throws SQLException, IOException, URISyntaxException {
		Properties props = new Properties();
		URI uri = App.class.getResource("database.properties").toURI();
		
		try (InputStream in = Files.newInputStream(Paths.get(uri))) {
			props.load(in);
		}
		String url = props.getProperty("url");
		String username = props.getProperty("username");
		String password = props.getProperty("password");
		return DriverManager.getConnection(url, username, password);
	}
	
	public static List<User> selectAll() throws SQLException, IOException, URISyntaxException {
		List<User> list = new ArrayList<User>();
		
		try (Connection conn = getConnection()) {
			PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM usersformyapp");
			ResultSet results = preparedStatement.executeQuery();
			
			while(results.next()) {
				list.add(new User(results.getInt("userID"), results.getString("email"), 
						results.getString("firstName"), results.getString("lastName")));
			}
		}
		return list;
	}
	
	public static void addUser(User newUser) throws SQLException, IOException, URISyntaxException {
		try (Connection conn = getConnection()) {
			String sql = "INSERT INTO usersformyapp (email, firstName, lastName) VALUES (?, ?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, newUser.getEmail());
			preparedStatement.setString(2, newUser.getFirstName());
			preparedStatement.setString(3, newUser.getLastName());
			
			preparedStatement.executeUpdate();
		}
	}
	
	public static void deleteUser(int id) throws SQLException, IOException, URISyntaxException {
		try (Connection conn = getConnection()) {
			String sql = "DELETE FROM usersformyapp WHERE userID = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			
			preparedStatement.executeUpdate();
		}
	}
	
	public static void changeUser(User user) throws SQLException, IOException, URISyntaxException {
		try (Connection conn = getConnection()) {
			String sql = "UPDATE usersformyapp SET email = ?, firstName = ?, lastName = ? WHERE userID = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getFirstName());
			preparedStatement.setString(3, user.getLastName());
			preparedStatement.setInt(4, user.getId());
			
			preparedStatement.executeUpdate();
		}
	}
}
