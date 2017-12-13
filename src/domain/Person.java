package domain;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person {
	private String userid;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String salt;

	public Person(String userid, String email, String password, String firstName, String lastName, String salt) {
		setUserid(userid);
		setEmail(email);
		setPassword(password);
		setFirstName(firstName);
		setLastName(lastName);
		setSalt(salt);
	}
	
	public Person(String userid, String email, String password, String firstName, String lastName) {
		setUserid(userid);
		setEmail(email);
		setPassword(password);
		setFirstName(firstName);
		setLastName(lastName);
	}
	
	public Person() {
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		if(userid.isEmpty()){
			throw new DomainException("No userid given");
		}
		this.userid = userid;
	}

	public void setEmail(String email) {
		if(email.isEmpty()){
			throw new DomainException("No email given");
		}
		String USERID_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern p = Pattern.compile(USERID_PATTERN);
		Matcher m = p.matcher(email);
		if (!m.matches()) {
			throw new DomainException("Email not valid");
		}
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		if(password.isEmpty()){
			throw new DomainException("No password given");
		}
		this.password = password;
	}
	
	public void setPasswordHashed(String password) {
		if(password.isEmpty()){
			throw new DomainException("No hashed password given");
		}
		this.password = password;
	}
	
	public boolean isCorrectPassword(String password) {
		if(password.isEmpty()){
			throw new DomainException("No password given");
		}
		return getPassword().equals(password);
	}
	
	private String hashPassword(String password, String salt) {
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-512");
			crypt.reset();
			SecureRandom random = new SecureRandom();
			byte[] seed = random.generateSeed(20);
			crypt.update(seed);
			byte[] passwordBytes = password.getBytes("UTF-8");
			crypt.update(passwordBytes);
			byte[] digest = crypt.digest();
			BigInteger digestAsBigInteger = new BigInteger(1, digest);
			return digestAsBigInteger.toString(16);
		}
		catch (Exception e){
			throw new DomainException(e.getMessage());
		}
	}
	
	public void setSalt(String salt) {
		SecureRandom random = new SecureRandom();
		// generate seed
		byte[] seed = random.generateSeed(20);
		BigInteger digestAsBigInteger = new BigInteger(1, seed);
		this.salt = digestAsBigInteger.toString();
	}
	
	private void setSaltPerson(String salt) {
		if (salt == null || salt.trim().isEmpty()) {
			throw new DomainException("empty salt given, class person");
		} else {
			this.salt = salt;
		}
	}
	
	public boolean checkPasswordCorrect(String password) {
		String passwordTocheck = hashPassword(password, this.salt);
		if (passwordTocheck.equals(this.password)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getSalt() {
		return salt;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if(firstName.isEmpty()){
			throw new DomainException("No firstname given");
		}
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if(lastName.isEmpty()){
			throw new DomainException("No last name given");
		}
		this.lastName = lastName;
	}
	
	@Override
	public String toString(){
		return getFirstName() + " " + getLastName() + ": " + getUserid() + ", " + getEmail();
	}	
}
