<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isErrorPage = "true" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Something wrong</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/yellow.css">
</head>
<body>
	<div id="container">
		<main>
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
					<li><a href="Controller?action=naarAddProduct">Add product</a></li>
				</ul>
			</nav>
			<h2>Error</h2>
		</header>
			<article>
				<p> You caused a ${pageContext.exception} on the server! </p>
				<p>
					<a href="${pageContext.request.contextPath}/Controller">Home</a>
				</p>
			</article>
		<footer> &copy; Webontwikkeling 3, UC Leuven-Limburg </footer>
		</main>
	</div>
</body>
</html>