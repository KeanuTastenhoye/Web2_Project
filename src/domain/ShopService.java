package domain;

import java.util.List;
import java.util.Properties;

import db.DbException;
import db.PersonDb;
import db.PersonDbSql;
import db.ProductDb;
import db.ProductDbSql;

public class ShopService {
	private PersonDb personDb;
	private ProductDb productDb;

	public ShopService(){
		String url = "jdbc:postgresql://gegevensbanken.khleuven.be:51617/1TX32?currentSchema=r0667956";
		Properties properties = new Properties();
		properties.setProperty("user", "r0667956");
		properties.setProperty("password", "Ghia2016");
		properties.setProperty("ssl", "true");
		properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		properties.setProperty("driver", "org.postgresql.Driver");
		personDb = new PersonDbSql(properties, url);
		productDb = new ProductDbSql(properties, url);
	}
	
	public Person getPerson(String personId) {
		return getPersonDb().get(personId);
	}
	
	public Product getProduct(String id) {
		try{
			return productDb.get(Integer.parseInt(id));
		}catch(NumberFormatException e){
			throw new DbException("Id must be a integer.");
		}
	}

	public List<Person> getPersons() {
		return getPersonDb().getAll();
	}
	
	public List<Product> getProducts() {
		return getProductDb().getAll();
	}

	public void addPerson(Person person) {
		getPersonDb().add(person);
	}
	
	public void addProduct(Product product) {
		getProductDb().add(product);
	}

	public void updatePersons(Person person) {
		getPersonDb().update(person);
	}
	
	public void updateProduct(Product product) {
		getProductDb().update(product);
	}

	public void deletePerson(String personId) {
		getPersonDb().delete(personId);
	}
	
	public void deleteProduct(int productId) {
		productDb.delete(productId);
	}

	private PersonDb getPersonDb() {
		return personDb;
	}
	
	private ProductDb getProductDb() {
		return productDb;
	}
}
