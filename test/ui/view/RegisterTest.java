package ui.view;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import db.PersonDb;
import db.PersonDbSql;
import db.ProductDb;
import db.ProductDbSql;
import domain.Person;
import domain.Product;

public class RegisterTest {
	private WebDriver driver;
	private String Url;
	private PersonDb db;
	private ProductDb dab;
	
	@Before
	public void setUp() {
		Url = "http://localhost:8080/R0667956_KeanuTastenhoye/";
		System.setProperty("webdriver.chrome.driver", "C:/Program Files/chromedriver.exe");
		driver = new ChromeDriver();
		//db = new PersonDbSql();
		//dab = new ProductDbSql();
	}
	
	@After
	public void clean() {
		driver.quit();
	}
	
	private String generateRandomUseridInOrderToRunTestMoreThanOnce(String component) {
		int random = (int)(Math.random() * 1000 + 1);
		return random+component;
	}
		
	private void fillOutField(String name,String value) {
		WebElement field=driver.findElement(By.id(name));
		field.clear();
		field.sendKeys(value);
	}
	
	private void submitForm(String userid, String firstName,String lastName, String email, String password) {
		fillOutField("userid", userid);
		fillOutField("firstName", firstName);
		fillOutField("lastName",lastName);
		fillOutField("email", email);
		fillOutField("password", password);
		
		WebElement button=driver.findElement(By.id("signUp"));
		button.click();		
	}
	

	@Test
	public void testRegisterCorrect() {
		String useridRandom = generateRandomUseridInOrderToRunTestMoreThanOnce("jakke");
		submitForm(useridRandom, "Jan", "Janssens", "jan.janssens@hotmail.com" , "1234");
		
		String title=driver.getTitle();
		assertEquals("Home",title);
		
		driver.get("Url" + "Controller?action=toonPerson");
		
		ArrayList<WebElement> listItems=(ArrayList<WebElement>) driver.findElements(By.cssSelector("table tr"));
		boolean found=false;
		for (WebElement listItem:listItems) {
				if (listItem.getText().contains("jan.janssens@hotmail.com") &&  listItem.getText().contains(" Jan Janssens")) {
				found=true;
			}
		}
		assertEquals(true, found);
		
	}
	
	@Test
	public void testRegisterUseridEmpty(){
		submitForm("", "Jan", "Janssens", "jan.janssens@hotmail.com", "1234");
		
		String title=driver.getTitle();
		assertEquals("Sign Up",title);
		
		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("No userid given", errorMsg.getText());

		WebElement fieldUserid=driver.findElement(By.id("userid"));
		assertEquals("",fieldUserid.getAttribute("value"));
		
		WebElement fieldFirstName=driver.findElement(By.id("firstName"));
		assertEquals("Jan",fieldFirstName.getAttribute("value"));
		
		WebElement fieldLastName=driver.findElement(By.id("lastName"));
		assertEquals("Janssens",fieldLastName.getAttribute("value"));
		
		WebElement fieldEmail=driver.findElement(By.id("email"));
		assertEquals("jan.janssens@hotmail.com",fieldEmail.getAttribute("value"));
		

	}
	
	@Test
	public void testRegisterFirstNameEmpty(){
		
		driver.get(Url + "Controller?action=signUp");
		
		submitForm("jakke", "", "Janssens", "jan.janssens@hotmail.com", "1234");
		
		String title=driver.getTitle();
		assertEquals("Sign Up",title);
		
		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("No firstname given", errorMsg.getText());

		WebElement fieldUserid=driver.findElement(By.id("userid"));
		assertEquals("jakke",fieldUserid.getAttribute("value"));

		WebElement fieldFirstName=driver.findElement(By.id("firstName"));
		assertEquals("",fieldFirstName.getAttribute("value"));
		
		WebElement fieldLastName=driver.findElement(By.id("lastName"));
		assertEquals("Janssens",fieldLastName.getAttribute("value"));
		
		WebElement fieldEmail=driver.findElement(By.id("email"));
		assertEquals("jan.janssens@hotmail.com",fieldEmail.getAttribute("value"));
		

	}

	@Test
	public void testRegisterLastNameEmpty(){
		submitForm("jakke", "Jan", "", "jan.janssens@hotmail.com", "1234");
		
		String title=driver.getTitle();
		assertEquals("Sign Up",title);
		
		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("No last name given", errorMsg.getText());

		WebElement fieldUserid=driver.findElement(By.id("userid"));
		assertEquals("jakke",fieldUserid.getAttribute("value"));

		WebElement fieldFirstName=driver.findElement(By.id("firstName"));
		assertEquals("Jan",fieldFirstName.getAttribute("value"));
		
		WebElement fieldLastName=driver.findElement(By.id("lastName"));
		assertEquals("",fieldLastName.getAttribute("value"));
		
		WebElement fieldEmail=driver.findElement(By.id("email"));
		assertEquals("jan.janssens@hotmail.com",fieldEmail.getAttribute("value"));
		

	}

	@Test
	public void testRegisterEmailEmpty(){
		submitForm("jakke", "Jan", "Janssens", "", "1234");
		
		String title=driver.getTitle();
		assertEquals("Sign Up",title);

		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("No email given", errorMsg.getText());

		WebElement fieldUserid=driver.findElement(By.id("userid"));
		assertEquals("jakke",fieldUserid.getAttribute("value"));
		
		WebElement fieldFirstName=driver.findElement(By.id("firstName"));
		assertEquals("Jan",fieldFirstName.getAttribute("value"));
		
		WebElement fieldLastName=driver.findElement(By.id("lastName"));
		assertEquals("Janssens",fieldLastName.getAttribute("value"));
		
		WebElement fieldEmail=driver.findElement(By.id("email"));
		assertEquals("",fieldEmail.getAttribute("value"));
		

	}


	@Test
	public void testRegisterPasswordEmpty(){
		submitForm("jakke", "Jan", "Janssens", "jan.janssens@hotmail.com", "");
		
		String title=driver.getTitle();
		assertEquals("Sign Up",title);
		
		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("No password given", errorMsg.getText());

		WebElement fieldUserid=driver.findElement(By.id("userid"));
		assertEquals("jakke",fieldUserid.getAttribute("value"));

		WebElement fieldFirstName=driver.findElement(By.id("firstName"));
		assertEquals("Jan",fieldFirstName.getAttribute("value"));
		
		WebElement fieldLastName=driver.findElement(By.id("lastName"));
		assertEquals("Janssens",fieldLastName.getAttribute("value"));
		
		WebElement fieldEmail=driver.findElement(By.id("email"));
		assertEquals("jan.janssens@hotmail.com",fieldEmail.getAttribute("value"));
		

	}
	
	@Test
	public void testRegisterUserAlreadyExists(){
		String useridRandom = generateRandomUseridInOrderToRunTestMoreThanOnce("pierke");
		submitForm(useridRandom, "Pieter", "Pieters", "pieter.pieters@hotmail.com", "1234");
		
		driver.get("Url" + "Controller?action=signUp");

		submitForm(useridRandom, "Pieter", "Pieters", "pieter.pieters@hotmail.com", "1234");
		
		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("User already exists", errorMsg.getText());

		WebElement fieldUserid=driver.findElement(By.id("userid"));
		assertEquals(useridRandom,fieldUserid.getAttribute("value"));
		
		WebElement fieldFirstName=driver.findElement(By.id("firstName"));
		assertEquals("Pieter",fieldFirstName.getAttribute("value"));
		
		WebElement fieldLastName=driver.findElement(By.id("lastName"));
		assertEquals("Pieters",fieldLastName.getAttribute("value"));
		
		WebElement fieldEmail=driver.findElement(By.id("email"));
		assertEquals("pieter.pieters@hotmail.com",fieldEmail.getAttribute("value"));
		
	}
	
//	@Test
//	public void testSQL() {
//		Person p = new Person();
//		p.setUserid("aab");
//		p.setFirstName("Jan");
//		p.setLastName("metdepet");
//		p.setEmail("jan.metdepet@email.com");
//		p.setPassword("qqq");
//		System.out.println(db.getAll());
//		db.add(p);
//		System.out.println(db.getAll());
//		p.setLastName("zonderpet");
//		db.update(p);
//		System.out.println(db.getAll());
//		db.delete(p.getUserid());
//		System.out.println(db.getAll());
//	}
//	
//	@Test
//	public void testSQL2() {
//		Product pr = new Product();
//		pr.setName("Fifa");
//		pr.setDescription("Ea Sports Fifa");
//		pr.setPrice("69");
//		System.out.println(dab.getAll());
//		dab.add(pr);
//		System.out.println(dab.getAll());
//		pr.setName("UFC");
//		dab.update(pr);
//		System.out.println(dab.getAll());
//		dab.delete(pr.getProductId());
//		System.out.println(dab.getAll());
//	}
}
