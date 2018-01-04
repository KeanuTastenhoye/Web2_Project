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
	
	public PersonDbSql(Properties properties, String url) {
		Connection connection;
		try {
			Class.forName(properties.getProperty("driver"));
			connection = DriverManager.getConnection(url, properties);
		}
		catch (SQLException | ClassNotFoundException e) {
			throw new DbException("Failed to initiate db", e);
		}
		this.connection = connection;
	}
	
	@Override
	public Person get(String personId) {
		String sql =  "SELECT * FROM person WHERE userid = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setString(1, personId);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				Person pe = new Person(result.getString("userid"), result.getString("email"), result.getString("password"), result.getBytes("seed"), result.getString("firsName"), result.getString("lastName"));
				return pe;
			}
			else {
				throw new DbException("Failed to get person.");
			}
		}
		catch (Exception e) {
			throw new DbException("Failed to get person.", e);
		}
	}
	
	@Override
	public List<Person> getAll() {
		try (Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery("SELECT * FROM person");
			List<Person> list = new ArrayList<>();
			while (result.next()) {
				try {
					Person pe = new Person(result.getString("userid"), result.getString("email"), result.getString("password"), result.getBytes("seed"), result.getString("firstName"), result.getString("lastName"));
					list.add(pe);
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
		String sql = "INSERT INTO person (userid, email, password, seed, firstName, lastName) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setString(1, person.getUserid());
			statement.setString(2, person.getEmail());
			statement.setString(3, person.getHashedPassword());
			statement.setBytes(4, person.getSeed());
			statement.setString(5, person.getFirstName());
			statement.setString(6, person.getLastName());
			statement.executeUpdate();
		}
		catch (Exception e) {
			throw new DbException("Failed to add person.", e);
		}
	}
	
	@Override
	public void update(Person person) {
		String sql = "UPDATE person SET email = ?, password = ?, seed = ?, firstName = ?, lastName = ? WHERE userid = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setString(1, person.getEmail());
			statement.setString(2, person.getHashedPassword());
			statement.setBytes(3, person.getSeed());
			statement.setString(4, person.getFirstName());
			statement.setString(5, person.getLastName());
			statement.setString(6, person.getUserid());
			statement.executeUpdate();
		}
		catch (Exception e) {
			throw new DbException("Failed to update person.", e);
		}
	}
	
	@Override
	public void delete(String personId) {
		String sql = "DELETE FROM person WHERE userid = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setString(1, personId);
			statement.executeUpdate();
		}
		catch (Exception e) {
			throw new DbException("Failed to remove person.", e);
		}
	}

}
