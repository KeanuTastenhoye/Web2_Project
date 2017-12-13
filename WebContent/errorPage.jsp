<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta charset="UTF-8">
<title>Something wrong</title>
<link rel="stylesheet" type="text/css" href="css/style.css" href="css/red.css" href="css/yellow.css">
</head>
<body>
	<div id="container">
		<%@include file = "header.jspf" %>
			<h2>Oops!</h2>
		<main>
			<p>Je hebt een '${pageContext.exception}' veroorzaakt op de server!</p>
			<p>
				<a href="${pageContext.request.contextPath }/Controller">Home</a>
			</p>
		</main>
		<%@include file = "footer.jspf" %>
	</div>	
</body>
</html>