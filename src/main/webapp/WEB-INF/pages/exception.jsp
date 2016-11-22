<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<spring:url value="/products" var="urlProducts" />

<!DOCTYPE html>
<html lang="en">

<jsp:include page="fragments/header.jsp" />

<nav class="navbar navbar-default">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="${urlProducts}">Back To Products</a>
		</div>
	</div>
</nav>

<h2 class="navbar-brand">Invalid operation.<br>
 You cannot delete this product as you have sale(s) containing this product.<br>
Please return on a previous page.</h2>
</body>
</html>