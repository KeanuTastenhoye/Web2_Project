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
	
	public ProductDbSql(Properties properties, String url) {
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
	public Product get(int id) {
		String sql = "SELECT * FROM product WHERE productid = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				Product pr = new Product(result.getInt("productId"), result.getString("name"), result.getString("description"), result.getDouble("price"));
				return pr;
			}
			else {
				throw new DbException("Failed to get product");
			}
		}
		catch (Exception e) {
			throw new DbException("Failed to get product.", e);
		}
	}
	
	@Override
	public List<Product> getAll() {
		try (Statement statement = connection.createStatement();) {
			String sql = "SELECT * FROM product";
			ResultSet result = statement.executeQuery(sql);
			List<Product> list = new ArrayList<>();
			while (result.next()){
				try {
					Product pr = new Product(result.getInt("productId"), result.getString("name"), result.getString("description"), result.getDouble("price"));
					list.add(pr);
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
		String sql = "INSERT INTO product VALUES (?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setInt(1, product.getProductId());
			statement.setString(2, product.getName());
			statement.setString(3, product.getDescription());
			statement.setDouble(4, product.getPrice());
			statement.executeUpdate();
		}
		catch (Exception e) {
			throw new DbException("Failed to add product.", e);
		}
	}
	
	@Override
	public void update(Product product) {
		String sql = "UPDATE product SET name = ?, description = ?, price = ? WHERE productId = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setString(1, product.getName());
			statement.setString(2, product.getDescription());
			statement.setDouble(3, product.getPrice());
			statement.setInt(4, product.getProductId());
			statement.executeUpdate();
		}
		catch (Exception e) {
			throw new DbException("Failed to update product.", e);
		}
	}
	
	@Override
	public void delete(int id) {
		String sql = "DELETE FROM product WHERE productId = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setInt(1, id);
			statement.executeUpdate();
		}
		catch (Exception e) {
			throw new DbException("Failed to delete product.", e);
		}
	}
}
