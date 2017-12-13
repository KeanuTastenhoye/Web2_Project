<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta charset="UTF-8">
<title>Sign Up</title>
<link rel="stylesheet" type="text/css" href="css/style.css" href="css/red.css" href="css/yellow.css">
</head>
<body>
	<div id="container">
	<%@include file="header.jspf"%>;
		<h2>Sign Up</h2>
		<main>
		<c:set var="fouten" value="${fouten}"/>
		<c:if test="${fouten != null}">
			<div class="alert-danger">
				<ul>
					<c:forEach var="errors" items="${fouten}">
						<li> ${errors} </li>
					</c:forEach>
				</ul>
			</div>
		</c:if>

    		<form method="post" action="Controller?action=nieuwPerson" novalidate="novalidate">
        		<p class="form-group ${useridClass}">
        			<label class="control-label" for="userid">User id</label>
        			<input type="text" id="userid" name="userid" required value="${persons.userid}">
        		</p>
        		
        		<p class="form-group ${firstNameClass}">
        			<label class="control-label" for="firstName">First Name</label>
        			<input type="text" id="firstName" name="firstName" required value="${persons.firstName}">
        		</p>
        		
        		<p class="form-group ${lastNameClass}">
        			<label class="control-label" for="lastName">Last Name</label>
        			<input type="text" id="lastName" name="lastName" required value="${persons.lastName}">
        		</p>
        		
        		<p class="form-group ${emailClass}">
        			<label class="control-label" for="email">Email</label>
        			<input type="email" id="email" name="email" required value="${persons.email}">
        		</p>
        		
        		<p>
        			<label for="password">Password</label>
        			<input type="password" id="password" name="password" required>
        		</p>
        		
        		<p>
        			<input type="submit" id="signUp" value="Sign Up">
        		</p>
    		</form>
		</main>
		<%@include file="footer.jspf"%>
	</div>
</body>
</html>
