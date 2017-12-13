package ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JOptionPane;

import domain.Person;

public class PersonUI {

	public static void main(String[] args) throws ClassNotFoundException {
		
		Properties properties = new Properties();
		
		String Url = "jdbc:postgresql://gegevensbanken.khleuven.be:51617/1TX32?" + "currentSchema=r0667956";
		properties.setProperty("user", "r0667956");
		setPassword(properties);
		properties.setProperty("ssl", "true");
		properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		
		String id = JOptionPane.showInputDialog("UserID:");
		String em = JOptionPane.showInputDialog("Email:");
		String pw = JOptionPane.showInputDialog("Password:");
		String fn = JOptionPane.showInputDialog("FirstName:");
		String ln = JOptionPane.showInputDialog("LastName:");
		
		Person person2 = new Person(id, em, pw, fn, ln);
		
		Class.forName("org.postgresql.Driver");
		Connection connection;
		
		try {
		connection = DriverManager.getConnection(Url, properties);
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery( "SELECT * FROM person" );
		while (result.next()) {
			String userid = result.getString("userid");
			String email = result.getString("email");
			String password = result.getString("password");
			String firstName = result.getString("firstName");
			String lastName = result.getString("lastName");
			
			Person person = new Person(userid, email, password, firstName, lastName);
			
			System.out.println(person);
		}
		System.out.println(person2);
		statement.close();
		connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	private static void setPassword(Properties properties) {
		
		properties.setProperty("password", "Ghia2016");
		
	}

}
