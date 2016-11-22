<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<spring:url value="/products" var="urlProducts" />
<spring:url value="/sales" var="urlSales" />
<spring:url value="/statistics" var="urlStats" />

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/header.jsp" />

<nav class="navbar navbar-default ">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="${urlProducts}">Products</a>
            <a class="navbar-brand" href="${urlSales}">Sales</a>
            <a class="navbar-brand" href="${urlStats}">Statistics</a>
        </div>

    </div>
</nav>

<body>

<div class="container">

    <h1>Discount History</h1>

    <table class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Product Name</th>
            <th>Discount</th>
            <th>From</th>
            <th>To</th>
        </tr>
        </thead>

        <c:forEach var="d" items="${discounts}">
            <tr>
                <td>${d.id}</td>
                <td>${d.product.name}</td>
                <td>${d.amount}%</td>
                <td>${d.startTimeAsString}</td>
                <td>${d.endTimeAsString}</td>
            </tr>
        </c:forEach>
    </table>

</div>
<jsp:include page="../fragments/footer.jsp" />
</body>
</html>