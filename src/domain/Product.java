package domain;

public class Product {
	private int productId;
	private String name;
	private String description;
	private double price;
	private double review;
	
	public Product() {
		
	}
	
	public Product(int productId, String name, String description, double price, double review) {
		setProductId(productId);
		setName(name);
		setDescription(description);
		setPrice(price);
		setReview(review);
	}
	public Product(String name, String description, double price, double review) {
		setName(name);
		setDescription(description);
		setPrice(price);
		setReview(review);
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		if (name.isEmpty()) {
			throw new DomainException("No name given");
		}
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		if (description.isEmpty()) {
			throw new DomainException("No description given");
		}
		
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		if (price<0) {
			throw new DomainException("Give a valid price");
		}
		this.price = price;
	}
	public void setPrice(String price) {
		if (price.isEmpty()) {
			throw new DomainException("No price given");
		}
		setPrice(Double.valueOf(price));
	}
	
	public double getReview() {
		return review;
	}
	public void setReview(double review) {
		if (review<0) {
			throw new DomainException("Give a valid review");
		}
		this.review = review;
	}
	public void setReviewNumber(String review) {
		if (review.isEmpty()) {
			throw new DomainException("No number given");
		}
		setReview(Double.valueOf(review));
	}
	
	@Override
	public String toString(){
		return getName() + ": " + getDescription() + " - " + getPrice() + " - " + getReview();
	}
	
}
