<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<spring:url value="/products/add" var="urlAddProduct" />
<spring:url value="/sales" var="urlSales" />
<spring:url value="/discounts" var="urlDiscounts" />
<spring:url value="/statistics" var="urlStats" />

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/header.jsp" />

<nav class="navbar navbar-default ">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="${urlSales}">Sales</a>
			<a class="navbar-brand" href="${urlDiscounts}">Discounts</a>
			<a class="navbar-brand" href="${urlStats}">Statistics</a>
		</div>
		<div id="navbar">
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="${urlAddProduct}">Add Product</a></li>
			</ul>
		</div>
	</div>
</nav>

<body>

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

		<h1>All Products</h1>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Price, $</th>
					<th>Discount</th>
					<th>Action</th>
				</tr>
			</thead>

			<c:forEach var="p" items="${products}">
				<tr>
					<td>${p.id}</td>
					<td>${p.name}</td>
					<td><fmt:formatNumber type="number" minFractionDigits="2" value="${p.price}"></fmt:formatNumber></td>
					<td>${(discount.product.id == p.id) ? discount.amount : 0}%</td>
					<td><spring:url value="/products/${p.id}" var="productUrl" />
						<spring:url value="/products/${p.id}/delete" var="deleteUrl" />
						<spring:url value="/products/${p.id}/update" var="updateUrl" />

						<button class="btn btn-info"
							onclick="location.href='${productUrl}'">Info</button>
						<button class="btn btn-warning"
							onclick="location.href='${updateUrl}'">Update</button>
						<button class="btn btn-danger"
							onclick="this.disabled=true;post('${deleteUrl}')">Delete</button>
					</td>
				</tr>
			</c:forEach>
		</table>

	</div>
	<jsp:include page="../fragments/footer.jsp" />
</body>
</html>