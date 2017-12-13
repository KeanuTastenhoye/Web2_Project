package ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import domain.Product;

public class ProductUI {

	public static void main(String[] args) throws ClassNotFoundException {
		
		Properties properties = new Properties();
		String Url = "jdbc:postgresql://gegevensbanken.khleuven.be:51617/1TX32?" + "currentSchema=r0667956";
		properties.setProperty("user", "r0667956");
		setPassword(properties);
		properties.setProperty("ssl", "true");
		properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		
		Class.forName("org.postgresql.Driver");
		Connection connection;
		try {
		connection = DriverManager.getConnection(Url, properties);
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery( "SELECT * FROM product" );
		while (result.next()) {
			int productId = Integer.parseInt(result.getString("productId"));
			String name = result.getString("name");
			String description = result.getString("description");
			double price;
				price = Double.parseDouble(result.getString("price"));
			double reviewNumber;
				reviewNumber = Double.parseDouble(result.getString("reviewNumber"));
			Product product = new Product(productId, name, description, price, reviewNumber);
			System.out.println(product);
		}
		statement.close();
		connection.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}
	
	private static void setPassword(Properties properties) {
		
		properties.setProperty("password", "Ghia2016");
		
	}

}
