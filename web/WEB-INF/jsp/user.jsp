<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="cr" uri="/WEB-INF/custom.tld" %>

<!DOCTYPE html>
<html>
<head>
    <title>Orders</title>
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
    <fmt:message bundle="${locale}" key="local.privateOffice" var="privateOffice"/>
    <fmt:message bundle="${locale}" key="local.userNumber" var="nUser"/>
    <fmt:message bundle="${locale}" key="local.mLogin" var="mLogin"/>
    <fmt:message bundle="${locale}" key="local.mType" var="mType"/>
    <fmt:message bundle="${locale}" key="local.mLastName" var="mLastName"/>
    <fmt:message bundle="${locale}" key="local.mFirstName" var="mFirstName"/>
    <fmt:message bundle="${locale}" key="local.mMiddleName" var="mMiddleName"/>
    <fmt:message bundle="${locale}" key="local.mPhone" var="mPhone"/>
    <fmt:message bundle="${locale}" key="local.mPassport" var="mPassport"/>
    <fmt:message bundle="${locale}" key="local.mAddress" var="mAddress"/>
    <fmt:message bundle="${locale}" key="local.mUserType" var="mUserType"/>
    <fmt:message bundle="${locale}" key="local.mAdminType" var="mAdminType"/>
    <fmt:message bundle="${locale}" key="local.toAllUsers" var="toAllUsers"/>
    <fmt:message bundle="${locale}" key="local.mUser" var="mUser"/>
    <fmt:message bundle="${locale}" key="local.viewOrders" var="viewOrders"/>
    <fmt:message bundle="${locale}" key="local.viewUsers" var="viewUsers"/>
    <fmt:message bundle="${locale}" key="local.addCar" var="addCar"/>
</head>
<body>

<%@include file="../jspf/navigation.jspf" %>

<div class="container">
    <c:if test="${sessionScope.user.type.equals('admin')}">
        <div class="col-lg-12">
            <h1 class="page-header">
                    ${mUser}</h1>
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
                <li>
                    <form action="Controller" method="get" class="btn btn-link">
                        <input type="hidden" name="command" value="view-all-users">
                        <input type="submit" value="${viewUsers}" class="btn btn-link">
                    </form>
                </li>
                <li class="active">${mUser}</li>
            </ol>
        </div>

        <hr/>

        <div class="well col-lg-12">
            <p class="my-info">
                    ${nUser}: <c:out value="${requestScope.selectedUser.id}"/>
            </p>

            <p class="my-info">
                    ${mLogin}: <c:out value="${requestScope.selectedUser.login}"/>
            </p>

            <p class="my-info">
                    ${mType}:
                <c:if test="${requestScope.selectedUser.type.equals('user')}">
                    ${mUserType}
                </c:if>
                <c:if test="${requestScope.selectedUser.type.equals('admin')}">
                    ${mAdminType}
                </c:if>
            </p>

            <p class="my-info">
                    ${mLastName}: <c:out value="${requestScope.selectedUser.lastName}"/>
            </p>

            <p class="my-info">
                    ${mFirstName}: <c:out value="${requestScope.selectedUser.firstName}"/>
            </p>

            <p class="my-info">
                    ${mMiddleName}: <c:out value="${requestScope.selectedUser.middleName}"/>
            </p>

            <p class="my-info">
                e-mail: <c:out value="${requestScope.selectedUser.EMail}"/>
            </p>

            <p class="my-info">
                    ${mPhone}: <c:out value="${requestScope.selectedUser.phone}"/>
            </p>

            <p class="my-info">
                    ${mPassport}: <c:out value="${requestScope.selectedUser.passport}"/>
            </p>

            <c:if test="${requestScope.selectedUser.address != null && !requestScope.selectedUser.address.equals('')}">
                <p class="my-info">
                        ${mAddress}: <c:out value="${requestScope.selectedUser.address}"/>
                </p>
            </c:if>

        </div>
    </c:if>

    <c:if test="${!sessionScope.user.type.equals('admin')}">
        <h1 class="page-header"></h1>

        <form action="Controller" method="get">
            <input type="hidden" name="command" value="to-home-page">
            <input type="submit" value="${home}" class="btn btn-info">
        </form>
    </c:if>

    <hr/>
    <%@include file="../jspf/footer.jspf" %>

</div>

</body>
</html>
