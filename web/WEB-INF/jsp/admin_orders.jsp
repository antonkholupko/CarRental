<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="cr" uri="/WEB-INF/custom.tld" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin orders</title>
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
    <fmt:message bundle="${locale}" key="local.welcomePrivateOfficeAdmin" var="welcomeAdmin"/>
    <fmt:message bundle="${locale}" key="local.adminPrivateOffice" var="adminPrivateOffice"/>
    <fmt:message bundle="${locale}" key="local.logOut" var="logOut"/>
    <fmt:message bundle="${locale}" key="local.home" var="home"/>
    <fmt:message bundle="${locale}" key="local.cars" var="cars"/>
    <fmt:message bundle="${locale}" key="local.info" var="info"/>
    <fmt:message bundle="${locale}" key="local.privateOffice" var="privateOffice"/>
    <fmt:message bundle="${locale}" key="local.orders" var="mOrders"/>
    <fmt:message bundle="${locale}" key="local.toPrivOffice" var="toPrivOffice"/>
    <fmt:message bundle="${locale}" key="local.mLastName" var="mLastName"/>
    <fmt:message bundle="${locale}" key="local.mFirstName" var="mFirstName"/>
    <fmt:message bundle="${locale}" key="local.mMiddleName" var="mMiddleName"/>
    <fmt:message bundle="${locale}" key="local.mark" var="mMark"/>
    <fmt:message bundle="${locale}" key="local.model" var="mModel"/>
    <fmt:message bundle="${locale}" key="local.mGovNumber" var="mGovNumber"/>
    <fmt:message bundle="${locale}" key="local.mStatus" var="mStatus"/>
    <fmt:message bundle="${locale}" key="local.price" var="price"/>
    <fmt:message bundle="${locale}" key="local.mDamagePrice" var="mDmgPrice"/>
    <fmt:message bundle="${locale}" key="local.mOrderNumber" var="mOrderNumber"/>
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
    <fmt:message bundle="${locale}" key="local.mPage" var="mPage"/>
    <fmt:message bundle="${locale}" key="local.viewOrders" var="viewOrders"/>
    <fmt:message bundle="${locale}" key="local.viewUsers" var="viewUsers"/>
    <fmt:message bundle="${locale}" key="local.addCar" var="addCar"/>
</head>
<body>

<%@include file="../navigation.jspf" %>

<div class="container">
    <c:if test="${sessionScope.user.type.equals('admin')}">
        <div class="col-lg-12">
            <h1 class="page-header">
                    ${mOrders}</h1>
            <ol class="breadcrumb">
                <li>
                    <form action="Controller" method="get" class="btn btn-link">
                        <input type="hidden" name="command" value="to-home-page">
                        <input type="submit" value="${home}" class="btn btn-link">
                    </form>
                </li>
                <li>
                    <form action="Controller" method="get" class="btn btn-link">
                        <input type="hidden" name="command" value="to-priv-office-admin">
                        <input type="submit" value="${privateOffice}" class="btn btn-link">
                    </form>
                </li>
                <li class="active">${mOrders}</li>
            </ol>
        </div>


        <c:if test="${requestScope.noOrders == true }">
            <p>${mNoOrders}</p>
        </c:if>

        <div class="well col-lg-12">
            <c:forEach var="order" items="${orders}">

                <div class="div-order">

                    <table border="1" width="100%">
                        <thead>
                        <tr>
                            <th>${mOrderNumber}</th>
                            <th>${mLastName}</th>
                            <th>${mFirstName}</th>
                            <th>${mMiddleName}</th>
                            <th>${mMark}</th>
                            <th>${mModel}</th>
                            <th>${mGovNumber}</th>
                            <th>${mStatus}</th>
                            <th>${price}, $</th>
                            <th>${mDmgPrice}, $</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td><c:out value="${order.id}"/></td>
                            <td><c:out value="${order.user.lastName}"/></td>
                            <td><c:out value="${order.user.firstName}"/></td>
                            <td><c:out value="${order.user.middleName}"/></td>
                            <td><c:out value="${order.car.mark}"/></td>
                            <td><c:out value="${order.car.model}"/></td>
                            <td><c:out value="${order.car.govNumber}"/></td>
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
                                <c:if test="${order.status.equals('payed')}">
                                    ${Paid}
                                </c:if>
                                <c:if test="${order.status.equals('accepted')}">
                                    ${Accepted}
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
                            <td><c:out value="${order.damagePrice}"/></td>
                        </tr>
                        </tbody>
                    </table>
                    <br/>

                    <div>
                        <form action="Controller" method="get">
                            <input type="hidden" name="selectedOrderId" value="${order.id}">
                            <input type="hidden" name="command" value="view-order-admin">
                            <input type="submit" value="${mDetails}" class="btn btn-info"/>
                        </form>
                    </div>
                </div>
                <hr/>
            </c:forEach>
        </div>


        <div class="btn-group">
            <c:forEach var="i" begin="1" end="${amountPages}">
                <form action="Controller" method="get" class="btn pag">
                    <input type="hidden" name="command" value="view-orders-admin">
                    <input type="hidden" name="pageNumber" value="${i}"/>
                    <c:if test="${requestScope.pageNumber==i}">
                        <input type="submit" value="${i}" class="btn btn-default active"/>
                    </c:if>
                    <c:if test="${requestScope.pageNumber!=i}">
                        <input type="submit" value="${i}" class="btn btn-default"/>
                    </c:if>
                </form>
            </c:forEach>
        </div>
        <p><c:out value="${mPage}: ${requestScope.pageNumber}"/></p>
    </c:if>

    <c:if test="${!sessionScope.user.type.equals('admin')}">
        <h1 class="page-header"></h1>

        <form action="Controller" method="get">
            <input type="hidden" name="command" value="to-home-page">
            <input type="submit" value="${home}" class="btn btn-primary">
        </form>
    </c:if>

    <hr/>
    <%@include file="../footer.jspf" %>
</div>
</body>
</html>
