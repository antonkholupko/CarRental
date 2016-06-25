<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="cr" uri="/WEB-INF/custom.tld" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Car</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
    <link href="../../css/car-rental-style.css" rel="stylesheet">
    <link href="../../font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="localization.local" var="locale"/>
    <fmt:message bundle="${locale}" key="local.carRental" var="carRental"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="local.signIn" var="signIn"/>
    <fmt:message bundle="${locale}" key="local.registration" var="registration"/>
    <fmt:message bundle="${locale}" key="local.login" var="login"/>
    <fmt:message bundle="${locale}" key="local.password" var="password"/>
    <fmt:message bundle="${locale}" key="local.messageInvLogin" var="invLogin"/>
    <fmt:message bundle="${locale}" key="local.home" var="home"/>
    <fmt:message bundle="${locale}" key="local.cars" var="cars"/>
    <fmt:message bundle="${locale}" key="local.info" var="info"/>
    <fmt:message bundle="${locale}" key="local.logOut" var="logOut"/>
    <fmt:message bundle="${locale}" key="local.privateOffice" var="privateOffice"/>
    <fmt:message bundle="${locale}" key="local.orders" var="orders"/>
    <fmt:message bundle="${locale}" key="local.price" var="price"/>
    <fmt:message bundle="${locale}" key="local.mark" var="mark"/>
    <fmt:message bundle="${locale}" key="local.model" var="model"/>
    <fmt:message bundle="${locale}" key="local.year" var="year"/>
    <fmt:message bundle="${locale}" key="local.type" var="type"/>
    <fmt:message bundle="${locale}" key="local.fuel" var="fuel"/>
    <fmt:message bundle="${locale}" key="local.transmission" var="transmission"/>
    <fmt:message bundle="${locale}" key="local.carInformation" var="carInfo"/>
    <fmt:message bundle="${locale}" key="local.forMakeOrderMessage" var="forMakeOrderMessage"/>
    <fmt:message bundle="${locale}" key="local.makeOrder" var="makeOrder"/>
    <fmt:message bundle="${locale}" key="local.cabriolet" var="cabriolet"/>
    <fmt:message bundle="${locale}" key="local.cargo" var="cargo"/>
    <fmt:message bundle="${locale}" key="local.coupe" var="coupe"/>
    <fmt:message bundle="${locale}" key="local.jeep" var="jeep"/>
    <fmt:message bundle="${locale}" key="local.smallClass" var="small"/>
    <fmt:message bundle="${locale}" key="local.middleClass" var="middle"/>
    <fmt:message bundle="${locale}" key="local.minibus" var="minibus"/>
    <fmt:message bundle="${locale}" key="local.premium" var="premium"/>
    <fmt:message bundle="${locale}" key="local.vintage" var="vintage"/>
    <fmt:message bundle="${locale}" key="local.petrol" var="petrol"/>
    <fmt:message bundle="${locale}" key="local.diesel" var="diesel"/>
    <fmt:message bundle="${locale}" key="local.electricity" var="electricity"/>
    <fmt:message bundle="${locale}" key="local.automaticTransmission" var="automatic"/>
    <fmt:message bundle="${locale}" key="local.mechanicTransmission" var="mechanic"/>
    <fmt:message bundle="${locale}" key="local.deleteCar" var="deleteCar"/>
    <fmt:message bundle="${locale}" key="local.mThisCarIsUsedInOrders" var="mCarIsUsed"/>
    <fmt:message bundle="${locale}" key="local.home" var="home"/>
    <fmt:message bundle="${locale}" key="local.mDollarsPerDay" var="mDollarsPerDay"/>
    <fmt:message bundle="${locale}" key="local.mAllCars" var="mAutomobiles"/>
</head>
<body>

<%@include file="../navigation.jspf" %>

<div class="container">

    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">
                <c:out value="${sessionScope.selectedCar.mark}"/>
                <c:out value="${sessionScope.selectedCar.model}"/>
            </h1>
            <ol class="breadcrumb">
                <li>
                    <form action="Controller" method="get" class="btn btn-link">
                        <input type="hidden" name="command" value="to-home-page">
                        <input type="submit" value="${home}" class="btn btn-link">
                    </form>
                </li>
                <li>
                    <form action="Controller" method="get" class="btn btn-link">
                        <input type="hidden" name="command" value="view-all-cars"/>
                        <input type="submit" value="${mAutomobiles}" class="btn btn-link"/>
                    </form>
                </li>
                <li class="active">
                    ${sessionScope.selectedCar.mark} ${sessionScope.selectedCar.model}
                </li>
            </ol>
        </div>
    </div>

    <div>
        <c:if test="${requestScope.cannotDelete}">
            <p class="text-danger my-info">
                    ${mCarIsUsed}
            </p>
        </c:if>
        <div class="col-lg-12 well">
            <div class="col-lg-8">
                <img class="img-responsive car-middle-img"
                     src="data:image/jpg;base64,${sessionScope.selectedCar.image}"/>
            </div>

            <div class="col-lg-4">

                <p class="my-info">${mark}: ${sessionScope.selectedCar.mark}</p>

                <p class="my-info">${model}: ${sessionScope.selectedCar.model}</p>

                <p class="my-info">
                    ${year}: ${sessionScope.selectedCar.year}
                </p>

                <p class="my-info">
                    ${type}:
                    <c:if test="${sessionScope.selectedCar.type.equals('Cabriolet')}">
                        <c:out value="${cabriolet}"/>
                    </c:if>
                    <c:if test="${sessionScope.selectedCar.type.equals('Cargo')}">
                        <c:out value="${cargo}"/>
                    </c:if>
                    <c:if test="${sessionScope.selectedCar.type.equals('Coupe')}">
                        <c:out value="${coupe}"/>
                    </c:if>
                    <c:if test="${sessionScope.selectedCar.type.equals('Jeep')}">
                        <c:out value="${jeep}"/>
                    </c:if>
                    <c:if test="${sessionScope.selectedCar.type.equals('Small')}">
                        <c:out value="${small}"/>
                    </c:if>
                    <c:if test="${sessionScope.selectedCar.type.equals('Middle')}">
                        <c:out value="${middle}"/>
                    </c:if>
                    <c:if test="${sessionScope.selectedCar.type.equals('Minibus')}">
                        <c:out value="${minibus}"/>
                    </c:if>
                    <c:if test="${sessionScope.selectedCar.type.equals('Premium')}">
                        <c:out value="${premium}"/>
                    </c:if>
                    <c:if test="${sessionScope.selectedCar.type.equals('Vintage')}">
                        <c:out value="${vintage}"/>
                    </c:if>
                </p>

                <p class="my-info">
                    ${fuel}:
                    <c:if test="${sessionScope.selectedCar.fuel.equals('petrol')}">
                        <c:out value="${petrol}"/>
                    </c:if>
                    <c:if test="${sessionScope.selectedCar.fuel.equals('diesel')}">
                        <c:out value="${diesel}"/>
                    </c:if>
                    <c:if test="${sessionScope.selectedCar.fuel.equals('electricity')}">
                        <c:out value="${electricity}"/>
                    </c:if>
                </p>

                <p class="my-info">
                    ${transmission}:
                    <c:if test="${sessionScope.selectedCar.transmission.equals('automatic')}">
                        <c:out value="${automatic}"/>
                    </c:if>
                    <c:if test="${sessionScope.selectedCar.transmission.equals('mechanic')}">
                        <c:out value="${mechanic}"/>
                    </c:if>
                </p>

                <c:if test="${sessionScope.selectedCar.info != null && !sessionScope.selectedCar.info.equals('')}">
                    <p class="my-info">
                            ${carInfo}: ${sessionScope.selectedCar.info}
                    </p>
                </c:if>
                <hr/>

                <p class="price">${price}: ${sessionScope.selectedCar.price} ${mDollarsPerDay}</p>

                <hr/>
                <c:if test="${sessionScope.user.type.equals('user')}">
                    <div>
                        <form action="Controller" method="get">
                            <input type="hidden" name="command" value="to-make-order">
                            <input type="submit" value="${makeOrder}" class="btn btn-block btn-primary my-info"/>
                        </form>
                    </div>

                </c:if>
                <c:if test="${sessionScope.user.type.equals('admin')}">
                    <div>
                        <form action="Controller" method="post">
                            <input type="hidden" name="command" value="delete-car">
                            <input type="hidden" name="processRequest" value="redirect">
                            <input type="submit" value="${deleteCar}" class="btn btn-block btn-danger my-info">
                        </form>
                    </div>
                </c:if>
                <c:if test="${sessionScope.user == null}">
                    <p class="my-info text-primary">${forMakeOrderMessage}</p>
                </c:if>
            </div>
        </div>
    </div>

    <hr/>
    <%@include file="../footer.jspf" %>
</div>

<script src="../../js/jquery.js"></script>
<script src="../../js/bootstrap.min.js"></script>

</body>
</html>
