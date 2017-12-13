<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Delete Confirmation Person</title>
<link rel="stylesheet" type="text/css" href="css/style.css" href="css/red.css" href="css/yellow.css">
</head>
<body>
	<div id="container">
	<%@include file="header.jspf"%>;
		<h2>Delete Confirmation Person</h2>
			<main>
				<p> Are you sure you want to delete this person? </p>
				
				<form method = "post" action = "Controller?action=deletePerson&userid=${param.userid}">
					<p>
						<input type="submit" id="Yes" value="Yes">
					</p>
				</form>
				
				<form method = "post" action = "Controller?action=toonPerson">
					<p>
						<input type="submit" id="No" value="No">
					</p>
				</form>
			</main>
			<%@include file="footer.jspf"%>
	</div>
</body>
</html>