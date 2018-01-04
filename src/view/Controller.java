package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DbException;
import domain.DomainException;
import domain.Person;
import domain.Product;
import domain.ShopService;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ShopService service;
       
    public Controller() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		verwerkRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		verwerkRequest(request, response);
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		ServletContext context = getServletContext();
		
		Properties properties = new Properties();
				
		properties.setProperty("user", context.getInitParameter("user"));
		properties.setProperty("password", context.getInitParameter("password"));
		properties.setProperty("ssl", context.getInitParameter("ssl"));
		properties.setProperty("sslfactory", context.getInitParameter("sslfactory"));
		properties.setProperty("url", context.getInitParameter("url"));
		
		service = new ShopService(properties);
	}
	
	protected void verwerkRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Cookie q = null;
		if (request.getCookies() != null) {
			for (Cookie c: request.getCookies()) {
				if (c.getName().equals("color")) {
					q = c;
					break;
				}
			}
		}
		
		if (q == null) {
			q = new Cookie("color", "yellow");
		}
		
		String color = q.getValue();
		if (!(color.equals("yellow") || color.equals("red"))) {
			request.setAttribute("color", "yellow");
		}
		
		else {
			request.setAttribute("color", color);
		}
		
		String doel;
		String action = request.getParameter("action");
		if (action == null) {
			action = "";
		}
		
		switch(action) {
			case "signUp":
				doel = "signUp.jsp";
			break;
			
			case "addProduct":
				doel = "addProduct.jsp";
			break;
			
			case "update":
				doel = "update.jsp";
			break;
			
			case "nieuwPerson":
				doel = maakUser(request, response);
			break;
			
			case "nieuwProduct":
				doel = maakProduct(request, response);
			break;
			
			case "toonPerson":
				doel = toonUser(request, response);
			break;
			
			case "toonProduct":
				doel = toonProduct(request, response);
			break;
			
			case "deleteProduct":
				doel = deleteProduct(request, response);
			break;
			
			case "deletePerson":
				doel = deletePerson(request, response);
			break;
			
			case "naarDeleteConfirmationProduct":
				doel = "deleteConfirmationProduct.jsp";
			break;
			
			case "naarDeleteConfirmationPerson":
				doel = "deleteConfirmationPerson.jsp";
			break;
			
			case "updateProduct":
				doel = updateProduct(request, response);
			break;
			
			case "naarSorteer":
				doel = "sorteer.jsp";
			break;
			
			case "checkPasswoord":
				doel = check(request, response);
			break;
			
			case "verify":
				doel = verifyPassword(request, response);
			break;
			
			case "naarCheckPasswoord":
				doel = "checkPasswoord.jsp";
				String userid = request.getParameter("userid");
				request.setAttribute("userid", userid);
			break;
			
			case "changeColor":
				doel = changeColor(request, response, q);
			
			default:
				doel = "index.jsp";
			break;
		}
		RequestDispatcher rd = request.getRequestDispatcher(doel);
		rd.forward(request, response);
	}
	

	private String maakUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Person persons = new Person();
		List<String> fouten = new ArrayList<String>();
		
		verwerkUserid(persons, request, fouten);
		verwerkFirstName(persons, request, fouten);
		verwerkLastName(persons, request, fouten);
		verwerkEmail(persons, request, fouten);
		try {
			persons.setPassword("password");
		}
		catch (IllegalArgumentException e) {
			fouten.add(e.getMessage());
		}
		
		if (fouten.isEmpty()) {
			try {
				service.addPerson(persons);
			}
			catch (DbException e) {
				fouten.add(e.getMessage());
			}
			
			if (fouten.isEmpty()) {
				return toonUser(request, response);
			}
			else {
				request.setAttribute("fouten", fouten);
				request.setAttribute("persons", persons);
				return "signUp.jsp";
			}
		}
		
		else {
			request.setAttribute("fouten", fouten);
			return "signUp.jsp";
		}
	}
	
	private void verwerkUserid(Person persons, HttpServletRequest request, List<String> fouten) {
		
		String userid = request.getParameter("userid");
		
		try {
			persons.setUserid(userid);
			request.setAttribute("useridGelukt", "OK");
			request.setAttribute("ingevuldeUserid", userid);
		}
		
		catch (DomainException e) {
			request.setAttribute("useridGelukt", "NOK");
			fouten.add(e.getMessage());
		}
		
	}
	
	private void verwerkFirstName(Person persons, HttpServletRequest request, List<String> fouten) {
		
		String firstName = request.getParameter("firstName");
		
		try {
			persons.setFirstName(firstName);
			request.setAttribute("firstNameGelukt", "OK");
			request.setAttribute("ingevuldeFirstName", firstName);
		}
		
		catch (DomainException e) {
			request.setAttribute("firstNameGelukt", "NOK");
			fouten.add(e.getMessage());
		}
		
	}
	
	private void verwerkLastName(Person persons, HttpServletRequest request, List<String> fouten) {
		
		String lastName = request.getParameter("lastName");
		
		try {
			persons.setLastName(lastName);
			request.setAttribute("lastNameGelukt", "OK");
			request.setAttribute("ingevuldeLastName", lastName);
		}
		
		catch (DomainException e) {
			request.setAttribute("lastNameGelukt", "NOK");
			fouten.add(e.getMessage());
		}
		
	}
	
	private void verwerkEmail(Person persons, HttpServletRequest request, List<String> fouten) {
		
		String email = request.getParameter("email");
		
		try {
			persons.setEmail(email);
			request.setAttribute("emailGelukt", "OK");
			request.setAttribute("ingevuldeEmail", email);
		}
		
		catch (DomainException e) {
			request.setAttribute("emailGelukt", "NOK");
			fouten.add(e.getMessage());
		}
		
	}
	
	private String maakProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Product products = new Product();
		List<String> fouten2 = new ArrayList<String>();
		
		verwerkName(products, request, fouten2);
		verwerkDescription(products, request, fouten2);
		verwerkPrice(products, request, fouten2);
		verwerkReview(products, request, fouten2);
		
		if (fouten2.isEmpty()) {
			try {
				service.addProduct(products);
			}
			
			catch (DbException e) {
				fouten2.add(e.getMessage());
			}
			
			if (fouten2.isEmpty()) {
				return toonProduct(request, response);
			}
			
			else {
				request.setAttribute("fouten2", fouten2);
				request.setAttribute("products", products);
				return "addProduct.jsp";
			}
		}
		
		else {
			request.setAttribute("fouten2", fouten2);
			return "addProduct.jsp";
		}
	}
	
	private void verwerkName(Product products, HttpServletRequest request, List<String> fouten2) {
		
		String name = request.getParameter("name");
		
		try {
			products.setName(name);
			request.setAttribute("nameGelukt", "OK");
			request.setAttribute("ingevuldeName", name);
		}
		
		catch (DomainException e) {
			request.setAttribute("nameGelukt", "NOK");
			fouten2.add(e.getMessage());
		}
		
	}
	
	private void verwerkDescription(Product products, HttpServletRequest request, List<String> fouten2) {
		
		String description = request.getParameter("description");
		
		try {
			products.setDescription(description);
			request.setAttribute("descriptionGelukt", "OK");
			request.setAttribute("ingevuldeDescription", description);
		}
		
		catch (DomainException e) {
			request.setAttribute("descriptionGelukt", "NOK");
			fouten2.add(e.getMessage());
		}
		
	}
	
	private void verwerkPrice(Product products, HttpServletRequest request, List<String> fouten2) {
		
		try {
			double price = Double.parseDouble(request.getParameter("price"));
			products.setPrice(price);
			request.setAttribute("priceGelukt", "OK");
			request.setAttribute("ingevuldePrice", price);
		}
		
		catch (DomainException e) {
			request.setAttribute("priceGelukt", "NOK");
			fouten2.add(e.getMessage());
		}
		
		catch (NumberFormatException e) {
			request.setAttribute("priceGelukt", "NOK");
			fouten2.add("Vul een geldige waarde in als prijs!");
		}
		
	}
	
	private void verwerkReview(Product products, HttpServletRequest request, List<String> fouten2) {
		
		try {
			double review = Double.parseDouble(request.getParameter("review"));
			products.setReview(review);
			request.setAttribute("reviewGelukt", "OK");
			request.setAttribute("ingevuldeReview", review);
		}
		
		catch (DomainException e) {
			request.setAttribute("reviewGelukt", "NOK");
			fouten2.add(e.getMessage());
		}
		
		catch (NumberFormatException e) {
			request.setAttribute("reviewGelukt", "NOK");
			fouten2.add("Vul een geldige waarde in als nummer!");
		}
		
	}
	
	private String toonUser(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("alleUsers", service.getPersons());
		
		return "personoverview.jsp";
		
	}
	
	private String toonProduct(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("alleProducten", service.getProducts());
		
		return "productoverview.jsp";
		
	}
	
	private String deletePerson(HttpServletRequest request, HttpServletResponse response) {
		
		String userid = request.getParameter("userid");
		
		service.deletePerson(userid);
		
		return toonUser(request, response);
		
	}
	
	private String deleteProduct(HttpServletRequest request, HttpServletResponse response) {
		
		int productId = Integer.parseInt(request.getParameter("productId"));
		
		service.deleteProduct(productId);
		
		return toonProduct(request, response);
		
	}
	
	private String updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int productId = Integer.parseInt(request.getParameter("productId"));
		
		service.deleteProduct(productId);
		
		return maakProduct(request, response);
		
	}
	
	private String check(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userid = request.getParameter("userid");
		
		try {
			Person p = service.getPerson(userid);
			request.setAttribute("userid", userid);
			request.getRequestDispatcher("checkPasswoord.jsp").forward(request, response);
		}
		
		catch(DbException e) {
			toonUser(request, response);
		}
		
		return "checkPasswoord.jsp";
		
	}
	
	private String verifyPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		
		try{
			Person p = service.getPerson(userid);
			request.setAttribute("correct", p.isCorrectPassword(password));
			return "isPasswordCorrect.jsp";
		}
		catch(DbException e){
			return toonUser(request, response);
		}
	}
	
	private String changeColor (HttpServletRequest request, HttpServletResponse response, Cookie c) throws ServletException, IOException {

		String origin = request.getParameter("page");
		if (c.getValue().equals("yellow")) {
			c.setValue("red");
		}
		else {
			c.setValue("yellow");
		}
		response.addCookie(c);
		response.sendRedirect("Controller?action=" + origin);
		
	}
}