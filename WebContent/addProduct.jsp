<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta charset="UTF-8">
<title>Add product</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/yellow.css">
</head>
<body>
	<div id="container">
		<header>
			<h1>
				<span>Web shop</span>
			</h1>
			<nav>
				<ul>
					<li><a href="Controller">Home</a></li>
					<li><a href="Controller?action=personOverview">Person Overview</a></li>
					<li><a href="Controller?action=productOverview">Product Overview</a></li>
					<li><a href="Controller?action=naarSignUp">Sign up</a></li>
					<li id="actual"><a href="Controller?action=naarAddProduct">Add product</a></li>
				</ul>
			</nav>
			<h2>Add product</h2>

		</header>
		<main>
			<c:set var="fouten" value="${errors}"/>
			<c:if test="${errors != null}">
				<div class="alert-danger">
					<ul>
						<c:forEach var="error" items="${errors}">
							<li>${error}</li>
						</c:forEach>
					</ul>
				</div>
			</c:if>

    			<form method="post" action="Controller?action=registerProduct" novalidate="novalidate">
        			<p>
        				<label for="productId">Product Id</label>
        				<input type="text" id="productId" name="productId" required value="<c:out value='${productId}'/>">
        			</p>
        			
        			<p>
        				<label for="name">Name</label>
        				<input type="text" id="name" name="name" required value="<c:out value='${name}'/>">
        			</p>
        			
       				<p>
       					<label for="description">Description</label>
       					<input type="text" id="description" name="description" required value="<c:out value='${description}'/>">
       				</p>
       				
        			<p>
        				<label for="price">Price</label>
        				<input type="number" id="price" name="price" required value="<c:out value='${price}'/>">
        			</p>
        			
        			<p>
        				<input type="submit" id="signUp" value="Sign Up">
        			</p>
    			</form>
		</main>
		<footer>
			&copy; Webontwikkeling 3, UC Leuven-Limburg
		</footer>
	</div>
</body>
</html>
