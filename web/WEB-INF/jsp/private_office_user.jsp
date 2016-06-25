<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="cr" uri="/WEB-INF/custom.tld" %>

<!DOCTYPE html>
<html>
<head>
    <title>Private office</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
    <link href="../../css/car-rental-style.css" rel="stylesheet">
    <link href="../../font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="localization.local" var="locale"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="local.carRental" var="carRental"/>
    <fmt:message bundle="${locale}" key="local.privateOfficeUser" var="privateOffice"/>
    <fmt:message bundle="${locale}" key="local.welcomeToOffice" var="welcomeToOffice"/>
    <fmt:message bundle="${locale}" key="local.logOut" var="logOut"/>
    <fmt:message bundle="${locale}" key="local.home" var="home"/>
    <fmt:message bundle="${locale}" key="local.cars" var="cars"/>
    <fmt:message bundle="${locale}" key="local.info" var="info"/>
    <fmt:message bundle="${locale}" key="local.orders" var="myOrders"/>
    <fmt:message bundle="${locale}" key="local.privateOffice" var="privateOffice"/>
    <fmt:message bundle="${locale}" key="local.orders" var="myOrders"/>
    <fmt:message bundle="${locale}" key="local.mOrderNumber" var="mOrderNumber"/>
    <fmt:message bundle="${locale}" key="local.mark" var="mMark"/>
    <fmt:message bundle="${locale}" key="local.model" var="mModel"/>
    <fmt:message bundle="${locale}" key="local.mSupposedDateFrom" var="dateFrom"/>
    <fmt:message bundle="${locale}" key="local.mSupposedDateTo" var="dateTo"/>
    <fmt:message bundle="${locale}" key="local.mStatus" var="mStatus"/>
    <fmt:message bundle="${locale}" key="local.price" var="mPrice"/>
    <fmt:message bundle="${locale}" key="local.statusNew" var="sNew"/>
    <fmt:message bundle="${locale}" key="local.statusCanceled" var="sCanceld"/>
    <fmt:message bundle="${locale}" key="local.statusRejected" var="sRejected"/>
    <fmt:message bundle="${locale}" key="local.statusAccepted" var="Accepted"/>
    <fmt:message bundle="${locale}" key="local.statusPaid" var="Paid"/>
    <fmt:message bundle="${locale}" key="local.statusDelivered" var="Delivered"/>
    <fmt:message bundle="${locale}" key="local.statusReturned" var="Returned"/>
    <fmt:message bundle="${locale}" key="local.statusExpectsComp" var="ExpectsComp"/>
    <fmt:message bundle="${locale}" key="local.statusClosed" var="Closed"/>
    <fmt:message bundle="${locale}" key="local.mDetails" var="mDetails"/>
    <fmt:message bundle="${locale}" key="local.noOrders" var="mNoOrders"/>
    <fmt:message bundle="${locale}" key="local.mViewAllOrders" var="mViewAllMyOrders"/>
    <fmt:message bundle="${locale}" key="local.viewOrders" var="mViewAllOrders"/>
    <fmt:message bundle="${locale}" key="local.mMakeOrder" var="mMakeOrder"/>
</head>

<body>

<%@include file="../jspf/navigation.jspf" %>

<div class="container">
    <c:if test="${sessionScope.user.type.equals('user')}">
        <div class="col-lg-12">
            <h1 class="page-header">
                    ${privateOffice}
            </h1>
            <ol class="breadcrumb">
                <li>
                    <form action="Controller" method="get" class="btn btn-link">
                        <input type="hidden" name="command" value="to-home-page">
                        <input type="submit" value="${home}" class="btn btn-link">
                    </form>
                </li>
                <li class="active">
                        ${privateOffice}
                </li>
            </ol>
        </div>

        <hr/>

        <div class="btn-group">
            <form action="Controller" method="get" class="btn">
                <input type="hidden" name="command" value="view-orders-user">
                <input type="submit" value="${mViewAllOrders}" class="btn btn-info">
            </form>
            <form action="Controller" method="get" class="btn">
                <input type="hidden" name="command" value="view-all-cars">
                <input type="submit" value="${mMakeOrder}" class="btn btn-info">
            </form>
        </div>

        <hr/>
        <div class="col-lg-12 well">
            <p class="my-info"><c:out value="${sessionScope.user.lastName}"/>
                <c:out value="${sessionScope.user.firstName}"/>
                <c:out value="${sessionScope.user.middleName}"/>,
                    ${welcomeToOffice}.</p>
        </div>
        <hr/>

        <h2>${myOrders}</h2>

        <c:if test="${requestScope.noOrders == true }">
            <p class="text-danger my-info">${mNoOrders}</p>
        </c:if>
        <div class="well">
            <c:forEach var="order" items="${sessionScope.orders}">

                <div class="div-order">

                    <table border="1" width="100%">
                        <thead>
                        <tr>
                            <th>${mOrderNumber}</th>
                            <th>${mMark}</th>
                            <th>${mModel}</th>
                            <th>${dateFrom}</th>
                            <th>${dateTo}</th>
                            <th>${mStatus}</th>
                            <th>${mPrice}, $</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td><c:out value="${order.id}"/></td>
                            <td><c:out value="${order.car.mark}"/></td>
                            <td><c:out value="${order.car.model}"/></td>
                            <td><c:out value="${order.supposedDateFrom}"/></td>
                            <td><c:out value="${order.supposedDateTo}"/></td>
                            <td>
                                <c:if test="${order.status.equals('new')}">
                                    ${sNew}
                                </c:if>
                                <c:if test="${order.status.equals('canceled')}">
                                    ${sCanceld}
                                </c:if>
                                <c:if test="${order.status.equals('rejected')}">
                                    ${sRejected}
                                </c:if>
                                <c:if test="${order.status.equals('accepted')}">
                                    ${Accepted}
                                </c:if>
                                <c:if test="${order.status.equals('payed')}">
                                    ${Paid}
                                </c:if>
                                <c:if test="${order.status.equals('delivered')}">
                                    ${Delivered}
                                </c:if>
                                <c:if test="${order.status.equals('returned')}">
                                    ${Returned}
                                </c:if>
                                <c:if test="${order.status.equals('expectsComp')}">
                                    ${ExpectsComp}
                                </c:if>
                                <c:if test="${order.status.equals('closed')}">
                                    ${Closed}
                                </c:if>
                            </td>
                            <td><c:out value="${order.orderPrice}"/></td>
                        </tr>
                        </tbody>
                    </table>
                    <br/>

                    <div class="btn-block">
                        <form action="Controller" method="get">
                            <input type="hidden" name="selectedOrderId" value="${order.id}">
                            <input type="hidden" name="command" value="view-order-user">
                            <input type="submit" value="${mDetails}" class="btn btn-info"/>
                        </form>
                    </div>
                </div>
                <br/>
            </c:forEach>

            <form action="Controller" method="get">
                <input type="hidden" name="command" value="view-orders-user">
                <input type="submit" value="${mViewAllMyOrders}" class="btn btn-primary"/>
            </form>

        </div>
    </c:if>
    <c:if test="${!sessionScope.user.type.equals('user')}">
        <h1 class="page-header"></h1>

        <form action="Controller" method="get">
            <input type="hidden" name="command" value="to-home-page">
            <input type="submit" value="${home}" class="btn btn-info">
        </form>

    </c:if>

    <hr/>
    <%@include file="../jspf/footer.jspf" %>
</div>

<script src="../../js/jquery.js"></script>
<script src="../../js/bootstrap.min.js"></script>

</body>
</html>
