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

import domain.Product;

public class ProductDbSql implements ProductDb {
	
	private Connection connection;
	private Properties properties = new Properties();
	private String url;
	
	public ProductDbSql(Properties properties) {
		
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
	public Product get(int productId) {
		Product product;
		String sql = "SELECT * FROM person WHERE productId = '" + "?";
		try (Connection connection = DriverManager.getConnection(url, properties); PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setInt(1, productId);
			ResultSet result = statement.executeQuery();
			result.next();
			String name = result.getString("name");
			String description = result.getString("description");
			Double price = result.getDouble("price");
			Double review = result.getDouble("review");
			product = new Product(productId, name, description, price, review);
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
		return product;
	}

	@Override
	public List<Product> getAll() {
		try (Connection connection = DriverManager.getConnection(url, properties); Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery("SELECT * FROM product");
			List<Product> list = new ArrayList<>();
			while(result.next()) {
				try {
					Product prod = new Product(result.getInt("productId"), result.getString("name"), result.getString("description"), result.getDouble("price"), result.getDouble("review"));
					list.add(prod);
				}
				catch (IllegalArgumentException e) {
					
				}
			}
			return list;
		}
		catch (Exception e) {
			throw new DbException("Failed to get products.", e);
		}
	}

	@Override
	public void add(Product product) {
		
		if (product == null) {
			throw new DbException("Nothing to add.");
		}
		String sql = "INSERT INTO product (productId, name, description, price, review)" + " VALUES (?, ?, ?, ?, ?)";
		try (Connection connection = DriverManager.getConnection(url, properties); 
			 PreparedStatement statement = connection.prepareStatement(sql);
			){
			statement.setInt(1, product.getProductId());
			statement.setString(2, product.getName());
			statement.setString(3, product.getDescription());
			statement.setDouble(4, product.getPrice());
			statement.setDouble(5, product.getReview());
			
			statement.execute();
		}
		catch (SQLException e) {
			throw new DbException(e);
		}
	}

	@Override
	public void update(Product product) {
		
		if (product == null){
			throw new DbException("Nothing to update.");
		}
		String sql = "UPDATE product SET name = '" + product.getName() + "', description = '" + product.getDescription() + "', price = '" + product.getPrice() + "',review = '" + product.getReview() + "' WHERE productId = '" + product.getProductId() + "'";
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
		} 
		catch (SQLException e) {
			throw new DbException(e);
		}
		
	}

	@Override
	public void delete(int productId) {
		
		String sql = "DELETE from product WHERE productId = '" + productId + "'";
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
		} 
		catch (SQLException e) {
			throw new DbException(e);
		}
		
	}

}
