<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<spring:url value="/products" var="urlProducts" />
<spring:url value="/sales" var="urlSales" />
<spring:url value="/discounts" var="urlDiscounts" />

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/header.jsp" />

<nav class="navbar navbar-default ">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="${urlProducts}">Products</a>
            <a class="navbar-brand" href="${urlSales}">Sales</a>
            <a class="navbar-brand" href="${urlDiscounts}">Discounts</a>
        </div>

    </div>
</nav>

<body>

<div class="container">

    <h1>Statistics for today</h1>

    <table class="table table-striped">
        <thead>
        <tr>
            <th>Period</th>
            <th>Check Count</th>
            <th>Check Sum</th>
            <th>Average Check</th>
            <th>Discount Sum</th>
            <th>Check Sum With Discount</th>
            <th>Average Check With Discount</th>
        </tr>
        </thead>

        <c:forEach var="s" items="${stats}">
            <tr>
                <td>${s.period}</td>
                <td>${s.salesCount}</td>
                <td>${s.costCommon}</td>
                <td>${s.costAverage}</td>
                <td>${s.discountSum}</td>
                <td>${s.costCommonWithDiscounts}</td>
                <td>${s.costAverageWithDiscounts}</td>
            </tr>
        </c:forEach>
    </table>

</div>
<jsp:include page="../fragments/footer.jsp" />
</body>
</html>