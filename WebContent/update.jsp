<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta charset="UTF-8">
<title>Update Product</title>
<link rel="stylesheet" type="text/css" href="css/style.css" href="css/red.css" href="css/yellow.css">
</head>
<body>
	<div id="container">
	<%@include file="header.jspf"%>;
		<h2>Update Product</h2>
		<main>
		<c:set var="fouten2" value="${fouten2}"/>
		<c:if test="${fouten2 != null}">
			<div class="alert-danger">
				<ul>
					<c:forEach var="error" items="${fouten2}">
						<li> ${error} </li>
					</c:forEach>
				</ul>
			</div>
		</c:if>

    		<form method="post" action="Controller?action=updateProduct&productId=${param.productId}" novalidate="novalidate">
    		
        		<p class="form-group ${nameClass}">
        			<label class="control-label" for="name">Name</label>
        			<input type="text" id="name" name="name" required value="${param.name}">
        		</p>
        		
        		<p class="form-group ${descriptionClass}">
        			<label class="control-label" for="description">Description</label>
        			<input type="text" id="description" name="description" required value="${param.description}">
        		</p>
        		
        		<p class="form-group ${priceClass}">
        			<label class="control-label" for="price">Price</label>
        			<input type="number" id="price" name="price" required value="${param.price}">
        		</p>
        		
        		<p class="form-group ${reviewClass}">
        			<label class="control-label" for="review">Review</label>
        			<input type="number" id="review" name="review" min="0" max="100" value="${products.review}">
        		</p>
        		
        		<p>
        			<input type="submit" id="saveProduct" value="Save">
        		</p>
    		</form>
		</main>
		<%@include file="footer.jspf"%>
	</div>
</body>
</html>
