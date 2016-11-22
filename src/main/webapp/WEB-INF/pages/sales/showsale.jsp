<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/header.jsp" />

<spring:url value="/sales" var="urlSales" />

<nav class="navbar navbar-default">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="${urlSales}">Sales</a>
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

	<h1>Sale Detail</h1>
	<br />

	<div class="row">
		<label class="col-sm-2">ID</label>
		<div class="col-sm-10">${sale.id}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Date</label>
		<div class="col-sm-10">${sale.localDateTimeAsString}
			<%--<fmt:formatDate value="${sale.date}" pattern="dd/MM/yyyy HH:mm" />--%>
		</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Sum, $</label>
		<div class="col-sm-10">${sale.cost}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Sum With Discount, $</label>
		<div class="col-sm-10">${sale.costWithDiscount}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Items</label>
		<div class="col-sm-10">
			<c:forEach var="item" items="${sale.items}">
				<p>${item.product.name}  *${item.quantity} =${item.sum}</p>
			</c:forEach>
		</div>
	</div>



</div>

<jsp:include page="../fragments/footer.jsp" />
</body>
</html>