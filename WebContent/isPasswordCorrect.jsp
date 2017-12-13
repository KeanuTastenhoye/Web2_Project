<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta charset="UTF-8">
<title>isPasswoordCorrect</title>
<link rel="stylesheet" type="text/css" href="css/style.css" href="css/red.css" href="css/yellow.css">
</head>
<body>
	<div id="container">
	<%@include file="header.jspf"%>;
		<h2>Check Password</h2>
		<main>
		
	<c:if test="${errors != null}">
				<div class="alert-danger">
						<ul>
							<c:forEach var="error" items="${errors}">
								<li>${error}</li>
							</c:forEach>
						</ul>
				</div>
			</c:if>
		<c:if test="${ correct }">
			<p>Password is correct!</p>
		</c:if>
		<c:if test="${ !correct }">
			<p>Password is wrong!</p>
		</c:if>
		</form>
		</main>
		<%@include file="footer.jspf"%>
	</div>
</body>
</html>