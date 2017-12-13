<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta charset="UTF-8">
<title>ProductOverview</title>
<link rel="stylesheet" type="text/css" href="css/style.css" href="css/red.css" href="css/yellow.css">
</head>
<body>
	<div id="container">
	<%@include file="header.jspf"%>;
		<h2>Product Overview</h2>
		<main>
			<table>
				<tr>
					<th>Name</th>
					<th>Description</th>
					<th>Price</th>
					<th>Review</th>
					<th>Delete</th>
				</tr>

				<c:forEach var="products" items="${alleProducten}">
				
				<tr>
					<td><a href="Controller?action=update&productId=${products.productId}&name=${products.name}&description=${products.description}&price=${products.price}&review=${products.review}">${products.name}</a></td>
					<td>${products.description}</td>
					<td>${products.price}</td>
					<td>${products.review}</td>
					<td><a href ="Controller?action=naarDeleteConfirmationProduct&productId=${products.productId}">Delete</a></td>
				</tr>
				
				</c:forEach>

				<caption>Products Overview</caption>
			</table>
		</main>
		<%@include file="footer.jspf"%>
	</div>
</body>
</html>