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

	<h1>Add Order Item</h1>
	<br />

	<spring:url value="/sales" var="saleActionUrl" />

	<form:form class="form-horizontal" method="post"
		modelAttribute="orderItemForm" action="${orderItemActionUrl}">

		<form:hidden path="id" />


		<spring:bind path="product">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Product</label>
				<div class="col-sm-5">
					<form:select path="product" class="form-control">
						<form:options items="${products}" itemLabel="name"
							itemValue="id" />
					</form:select>

					<form:errors path="product" class="control-label" />
				</div>
				<div class="col-sm-5"></div>
			</div>
		</spring:bind>

		<spring:bind path="quantity">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Quantity</label>
				<div class="col-sm-10">
					<form:radiobuttons path="quantity" items="${numberList}"
						element="label class='radio-inline'" />
					<br />
					<form:errors path="quantity" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn-lg btn-primary pull-right">Add
				</button>
			</div>
		</div>
	</form:form>
</div>

<jsp:include page="../fragments/footer.jsp" />

</body>
</html>