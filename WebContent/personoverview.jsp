<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta charset="UTF-8">
<title>Overview</title>
<link rel="stylesheet" type="text/css" href="css/style.css" href="css/red.css" href="css/yellow.css">
</head>
<body>
	<div id="container">
	<%@include file="header.jspf"%>;
		<h2>User Overview</h2>
		<main>
			<table>
				<tr>
					<th>E-mail</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Delete</th>
					<th>Sorteer</th>
					<th>Check Passwoord</th>
				</tr>

				<c:forEach var="persons" items="${alleUsers}">
				
				<tr>
					<td>${persons.email}</td>
					<td>${persons.firstName}</td>
					<td>${persons.lastName}</td>
					<td><a href ="Controller?action=naarDeleteConfirmationPerson&userid=${persons.userid}">Delete</a></td>
					<td><a href ="Controller?action=naarSorteer">Sorteer</a>
					<td><a href ="Controller?action=naarCheckPasswoord&userid=${persons.userid}">Check</a>
				</tr>
				
				</c:forEach>

				<caption>Users Overview</caption>
			</table>
		</main>
		<%@include file="footer.jspf"%>
	</div>
</body>
</html>