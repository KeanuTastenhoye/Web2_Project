package domain;

import java.util.List;
import java.util.Properties;

import db.*;

public class ShopService {
	private PersonDb personDb;
	private ProductDb productDb;

//	public ShopService() {
//		personDb = new PersonDbSql();
//		productDb = new ProductDbSql();
//	}
	
	public ShopService(Properties properties) {
		personDb = new PersonDbSql(properties);
		productDb = new ProductDbSql(properties);
	}
	
	public Person getPerson(String personId) {
		return getPersonDb().get(personId);
	}
	
	public Product getProduct(int productId) {
		return getProductDb().get(productId);
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
	
	public void updateProducts(Product product) {
		getProductDb().update(product);
	}

	public void deletePerson(String id) {
		getPersonDb().delete(id);
	}
	
	public void deleteProduct(int id) {
		getProductDb().delete(id);
	}

	private PersonDb getPersonDb() {
		return personDb;
	}
	
	private ProductDb getProductDb() {
		return productDb;
	}
	
	public boolean checkPassword(String personId, String password) {
		Person p = personDb.get(personId);
		return p.checkPasswordCorrect(password);
	}
}
