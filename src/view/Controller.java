package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
	private final ShopService service;
       
    public Controller() {
        super();
        service = new ShopService();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		verwerkRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		verwerkRequest(request, response);
	}
	
	private void verwerkRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
			if (action == null) {
				action = "";
			}
		
		switch(action) {
			case "personOverview":
				personOverview(request, response);
			break;
			
			case "productOverview":
				productOverview(request, response);
			break;
			
			case "naarSignUp":
				naarSignUp(request, response);
			break;
			
			case "naarAddProduct":
				naarAddProduct(request, response);
			break;
			
			case "registerPerson":
				registerPerson(request, response);
			break;
			
			case "registerProduct":
				registerProduct(request, response);
			break;
			
			case "updateProduct":
				updateProduct(request, response);
			break;
			
			case "editProduct":
				editProduct(request, response);
			break;
			
			case "removeProduct":
				removeProduct(request, response);
			break;
			
			case "removePerson":
				removePerson(request, response);
			break;
			
			case "deleteProduct":
				deleteProduct(request, response);
			break;
			
			case "deletePerson":
				deletePerson(request, response);
			break;
			
			case "checkPassword":
				checkPassword(request, response);
			break;
			
			case "verifyPassword":
				verifyPassword(request, response);
			break;
			
			default:
				naarIndex(request, response);
			break;
		}
	}
	
	private void naarIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	
	private void naarSignUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("signUp.jsp").forward(request, response);
	}
	
	private void naarAddProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("addProduct.jsp").forward(request, response);
	}
	
	private void personOverview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("persons", service.getPersons());
		request.getRequestDispatcher("personoverview.jsp").forward(request, response);
	}
	
	private void productOverview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("products", service.getProducts());
		request.getRequestDispatcher("productoverview.jsp").forward(request, response);
	}
	
	private void registerPerson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		Person pe = new Person();
		List<String> errors = new ArrayList<String>();
		
		this.setUserid(errors, pe, userid);
		this.setFirstName(errors, pe, firstName);
		this.setLastName(errors, pe, lastName);
		this.setEmail(errors, pe, email);
		this.setPassword(errors, pe, password);
		
		try {
			if (errors.isEmpty()) {
				service.addPerson(pe);
			}
		}
		catch (DbException e) {
			errors.add(e.getMessage());
		}
		if (errors.isEmpty()) {
			personOverview(request, response);
		}
		else {
			request.setAttribute("errors", errors);
			request.setAttribute("userid", userid);
			request.setAttribute("firstName", firstName);
			request.setAttribute("lastName", lastName);
			request.setAttribute("email", email);
			naarSignUp(request, response);
		}
	}
	
	private void registerProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String price = request.getParameter("price");
		
		Product pr = new Product();
		List<String> errors = new ArrayList<String>();
		
		this.setName(errors, pr, name);
		this.setDescription(errors, pr, description);
		this.setPrice(errors, pr, price);
		
		try {
			if (errors.isEmpty()) {
				service.addProduct(pr);
			}
		}
		catch (DbException e) {
			errors.add(e.getMessage());
		}
		if (errors.isEmpty()) {
			productOverview(request, response);
		}
		else {
			request.setAttribute("errors", errors);
			request.setAttribute("name", name);
			request.setAttribute("description", description);
			request.setAttribute("price", price);
			naarAddProduct(request, response);
		}
	}
	
	private void setUserid (List<String> errors, Person pe, String userid) {
		try {
			pe.setUserid(userid);
		}
		catch (IllegalArgumentException e) {
			errors.add(e.getMessage());
		}
	}
	
	private void setFirstName (List<String> errors, Person pe, String firstName) {
		try {
			pe.setFirstName(firstName);
		}
		catch (IllegalArgumentException e) {
			errors.add(e.getMessage());
		}
	}
	
	private void setLastName (List<String> errors, Person pe, String lastName) {
		try {
			pe.setLastName(lastName);
		}
		catch (IllegalArgumentException e) {
			errors.add(e.getMessage());
		}
	}
	
	private void setEmail (List<String> errors, Person pe, String email) {
		try {
			pe.setEmail(email);
		}
		catch (IllegalArgumentException e) {
			errors.add(e.getMessage());
		}
	}
	
	private void setPassword (List<String> errors, Person pe, String password) {
		try {
			pe.setPassword(password);
		}
		catch (IllegalArgumentException e) { 
			errors.add(e.getMessage());
		}
	}
	
	private void setName (List<String> errors, Product pr, String name) {
		try {
			pr.setName(name);
		}
		catch (DomainException e) {
			errors.add(e.getMessage());
		}
	}
	
	private void setDescription (List<String> errors, Product pr, String description) {
		try {
			pr.setDescription(description);
		}
		catch (DomainException e) {
			errors.add(e.getMessage());
		}
	}
	
	private void setPrice (List<String> errors, Product pr, String price) {
		try {
			pr.setPrice(price);
		}
		catch (DomainException e) {
			errors.add(e.getMessage());
		}
	}
	
	private void updateProduct (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String productId = request.getParameter("productId");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String price = request.getParameter("price");
		
		try {
			Product pr = service.getProduct(productId);
			List<String> errors = new ArrayList<String>();
			
			this.setName(errors, pr, name);
			this.setDescription(errors, pr, description);
			this.setPrice(errors, pr, price);
			
			try {
				if (errors.isEmpty()) {
					service.updateProduct(pr);
				}
			}
			catch (DbException e) {
				errors.add(e.getMessage());
			}
			
			if (errors.isEmpty()) {
				productOverview(request, response);
			}
			else {
				request.setAttribute("errors", errors);
				request.setAttribute("product", pr);
				editProduct(request, response);
			}
		}
		catch (DbException e) {
			editProduct(request, response);
		}
	}
	
	private void editProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String productId = request.getParameter("productId");
		
		try{
			Product pr = service.getProduct(productId);
			request.setAttribute("product", pr);
			request.getRequestDispatcher("updateProduct.jsp").forward(request, response);
		}
		catch (DbException e){
			productOverview(request, response);
		}
	}
	
	private void removeProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String productId = request.getParameter("productId");
		
		try {
			request.setAttribute("product", service.getProduct(productId));
			request.getRequestDispatcher("productDelete.jsp").forward(request, response);
		}
		catch (DbException e) {
			productOverview(request, response);
		}
	}
	
	private void removePerson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");
		
		try {
			request.setAttribute("person", service.getPerson(userid));
			request.getRequestDispatcher("personDelete.jsp").forward(request, response);
		}
		catch (DbException e) {
			personOverview(request, response);
		}
	}
	
	private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String productId = request.getParameter("productId");
		
		try {
			service.deleteProduct(Integer.parseInt(productId));
		}
		catch (NumberFormatException e) {
			
		}
		catch (DbException e) {
			
		}
		productOverview(request, response);
	}
	
	private void deletePerson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");
		
		try {
			service.deletePerson(userid);
		}
		catch (DbException e) {
			
		}
		personOverview(request, response);
	}
	
	private void checkPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");
		
		try {
			Person pe = service.getPerson(userid);
			request.setAttribute("userid", userid);
			request.getRequestDispatcher("checkPassword.jsp").forward(request, response);
		}
		catch (DbException e) {
			personOverview(request, response);
		}
	}
	
	private void verifyPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		
		try {
			Person pe = service.getPerson(userid);
			request.setAttribute("correct", pe.isCorrectPassword(password));
			request.getRequestDispatcher("correctPassword.jsp").forward(request, response);
		}
		catch (DbException e) {
			personOverview(request, response);
		}
	}
		
}
	