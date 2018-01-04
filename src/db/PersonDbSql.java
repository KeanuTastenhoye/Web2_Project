package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import domain.Person;

public class PersonDbSql implements PersonDb {
	
	private Connection connection;
	private Properties properties = new Properties();
	private String url;
	
	public PersonDbSql(Properties properties) {
		
		try {
			Class.forName("org.postgresql.Driver");
			this.properties = properties;
			this.url = properties.getProperty("url");
		}
		
		catch (Exception e) {
			throw new DbException(e.getMessage(), e);
		}
	}
	
	private static void setPassword(Properties properties) {
		properties.setProperty("password", "Ghia2016");
	}

	@Override
	public Person get(String personId) {
		Person person;
		String sql = "SELECT * FROM person WHERE userid = ?";
		try (Connection connection = DriverManager.getConnection(url, properties); PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setString(1, personId);
			ResultSet result = statement.executeQuery();
			result.next();
			String email = result.getString("email");
			String password = result.getString("password");
			String firstName = result.getString("firstName");
			String lastName = result.getString("lastName");
			person = new Person(personId, email, password, firstName, lastName);
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
		return person;
	}

	@Override
	public List<Person> getAll() {
		try (Connection connection = DriverManager.getConnection(url, properties); Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery("SELECT * FROM person");
			List<Person> list = new ArrayList<>();
			while(result.next()) {
				try {
					Person pers = new Person(result.getString("userid"), result.getString("email"), result.getString("password"), result.getString("firstName"), result.getString("lastName"));
					list.add(pers);
				}
				catch (IllegalArgumentException e) {
					
				}
			}
			return list;
		}
		catch (Exception e) {
			throw new DbException("Failed to get persons.", e);
		}
	}

	@Override
	public void add(Person person) {
		
		if (person == null){
			throw new DbException("Nothing to add.");
		}
		String sql = "INSERT INTO person (userid, email, password, firstName, lastName)" + " VALUES (?, ?, ?, ?, ?)";
		try (Connection connection = DriverManager.getConnection(url, properties);
			 PreparedStatement statement = connection.prepareStatement(sql);
			) {
			statement.setString(1, person.getUserid());
			statement.setString(2, person.getEmail());
			statement.setString(3, person.getPassword());
			statement.setString(4, person.getFirstName());
			statement.setString(5, person.getLastName());
			
			statement.execute();
		}
		catch (SQLException e) {
			throw new DbException(e);
		}
	}
		
	@Override
	public void update(Person person) {
		
		if (person == null){
			throw new DbException("Nothing to update.");
		}
		String sql = "UPDATE person SET email = '" + person.getEmail() + "', password = '" + person.getPassword() + "', firstName = '" + person.getFirstName() + "', lastName = '" + person.getLastName() + "' WHERE userid = '" + person.getUserid() + "'";
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
		} 
		catch (SQLException e) {
			throw new DbException(e);
		}
		
	}

	@Override
	public void delete(String personId) {
	
		if (personId == null){
			throw new DbException("Nothing to delete.");
		}
		String sql = "DELETE from person WHERE userid = '" + personId + "'";
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
		} 
		catch (SQLException e) {
			throw new DbException(e);
		}
		
	}

}
