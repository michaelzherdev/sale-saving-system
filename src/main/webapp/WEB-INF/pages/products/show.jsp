<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/header.jsp" />

<spring:url value="/products" var="urlProducts" />

<nav class="navbar navbar-default ">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="${urlProducts}">Products</a>
		</div>
	</div>
</nav>

<div class="container">

	<c:if test="${not empty msg}">
		<div class="alert alert-${css} alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
					<span aria-hidden="true">OK</span>
			</button>
			<strong>${msg}</strong>
		</div>
	</c:if>

	<h1>Product Detail</h1>
	<br />

	<div class="row">
		<label class="col-sm-2">ID</label>
		<div class="col-sm-10">${product.id}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Name</label>
		<div class="col-sm-10">${product.name}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Price, $</label>
		<div class="col-sm-10"><p><fmt:formatNumber type="number" minFractionDigits="2" value="${product.price}"></fmt:formatNumber></p></div>
	</div>

	<div class="row">
		<label class="col-sm-2">Sales</label>
		<div class="col-sm-10">
			<c:forEach var="item" items="${product.items}">
				<p>#: ${item.sale.id}, date: ${item.sale.localDateTimeAsString}
					, quantity: ${item.quantity}
				</p>
			</c:forEach>
		</div>
	</div>


</div>

<jsp:include page="../fragments/footer.jsp" />
</body>
</html>