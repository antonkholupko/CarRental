<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="cr" uri="/WEB-INF/custom.tld" %>

<!DOCTYPE html>
<html>
<head>
    <title>All cars</title>
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
    <fmt:message bundle="${locale}" key="local.messageChooseTripDate" var="chooseTripDate"/>
    <fmt:message bundle="${locale}" key="local.mInvalidDate" var="mInvalidDate"/>
    <fmt:message bundle="${locale}" key="local.supposedFromDate" var="mSupFromDate"/>
    <fmt:message bundle="${locale}" key="local.supposedToDate" var="mSupToDate"/>
    <fmt:message bundle="${locale}" key="local.viewFreeCars" var="freeCars"/>
    <fmt:message bundle="${locale}" key="local.cabriolet" var="cabriolet"/>
    <fmt:message bundle="${locale}" key="local.cargo" var="cargo"/>
    <fmt:message bundle="${locale}" key="local.coupe" var="coupe"/>
    <fmt:message bundle="${locale}" key="local.jeep" var="jeep"/>
    <fmt:message bundle="${locale}" key="local.smallClass" var="small"/>
    <fmt:message bundle="${locale}" key="local.middleClass" var="middle"/>
    <fmt:message bundle="${locale}" key="local.minibus" var="minibus"/>
    <fmt:message bundle="${locale}" key="local.premium" var="premium"/>
    <fmt:message bundle="${locale}" key="local.vintage" var="vintage"/>
    <fmt:message bundle="${locale}" key="local.all" var="all"/>
    <fmt:message bundle="${locale}" key="local.carAddedMessage" var="carAdded"/>
    <fmt:message bundle="${locale}" key="local.carDeletedMessage" var="carDeleted"/>
    <fmt:message bundle="${locale}" key="local.Automobiles" var="autos"/>
    <fmt:message bundle="${locale}" key="local.searchForFreeCars" var="searchForFree"/>
    <fmt:message bundle="${locale}" key="local.selectType" var="selectType"/>
    <fmt:message bundle="${locale}" key="local.noCars" var="mNoCars"/>
    <fmt:message bundle="${locale}" key="local.mPage" var="mPage"/>
    <fmt:message bundle="${locale}" key="local.mForMakingOrderTakeCar" var="mForMakingOrder"/>
</head>
<body>

<%@include file="../navigation.jspf" %>

<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">${autos}</h1>
            <ol class="breadcrumb">
                <li>
                    <form action="Controller" method="get" class="btn btn-link">
                        <input type="hidden" name="command" value="to-home-page">
                        <input type="submit" value="${home}" class="btn btn-link">
                    </form>
                </li>
                <li class="active">${autos}</li>
            </ol>
        </div>
    </div>

    <div class="row">
        <div class="well">
            <c:if test="${!user.type.equals('admin')}">
                <p class="text-primary">${mForMakingOrder}</p>
            </c:if>

            <form action="Controller" method="get" role="form">

                <div class="btn-group" data-toggle="buttons">
                    <c:forEach var="type" items="${allTypes}">
                        <label class="btn btn-default">
                            <input type="radio" name="carType" value="${type.type}" autocomplete="off">
                            <c:if test="${type.type.equals('Cabriolet')}">
                                ${cabriolet}
                            </c:if>
                            <c:if test="${type.type.equals('Cargo')}">
                                ${cargo}
                            </c:if>
                            <c:if test="${type.type.equals('Coupe')}">
                                ${coupe}
                            </c:if>
                            <c:if test="${type.type.equals('Jeep')}">
                                ${jeep}
                            </c:if>
                            <c:if test="${type.type.equals('Small')}">
                                ${small}
                            </c:if>
                            <c:if test="${type.type.equals('Middle')}">
                                ${middle}
                            </c:if>
                            <c:if test="${type.type.equals('Minibus')}">
                                ${minibus}
                            </c:if>
                            <c:if test="${type.type.equals('Premium')}">
                                ${premium}
                            </c:if>
                            <c:if test="${type.type.equals('Vintage')}">
                                ${vintage}
                            </c:if>
                        </label>
                    </c:forEach>
                </div>
                <hr/>
                <c:if test="${requestScope.invalidType == true}">
                    <p class="text-danger">
                        <c:out value="${selectType}"/>
                    </p>
                </c:if>
                <c:if test="${!user.type.equals('admin')}">
                    <p class="text-primary">${chooseTripDate}</p>
                </c:if>
                <c:if test="${requestScope.invalidDate == true}">
                    <p class="text-danger">${mInvalidDate}</p>
                </c:if>
                <p>${mSupFromDate}</p>
                <input type="date" value="${sessionScope.supposedDateFrom}" name="supposedDateFrom" required/>
                <input type="time" value="${sessionScope.supposedTimeFrom}" name="supposedTimeFrom" required/>

                <p>${mSupToDate}</p>
                <input type="date" value="${sessionScope.supposedDateTo}" name="supposedDateTo" required/>
                <input type="time" value="${sessionScope.supposedTimeTo}" name="supposedTimeTo" required/>
                <hr/>
                <input type="hidden" name="command" value="view-type-unused"/>
                <input type="submit" value="${searchForFree}" class="btn btn-primary">

            </form>
        </div>
    </div>

    <div class="btn-group">
        <c:forEach var="type" items="${allTypes}">
            <form action="Controller" method="get" class="btn pag">
                <input type="hidden" name="command" value="view-type"/>
                <input type="hidden" name="carType" value="${type.type}"/>
                <c:if test="${type.type.equals('Cabriolet')}">
                    <input type="submit" value="${cabriolet}" class="btn btn-default"/>
                </c:if>
                <c:if test="${type.type.equals('Cargo')}">
                    <input type="submit" value="${cargo}" class="btn btn-default"/>
                </c:if>
                <c:if test="${type.type.equals('Coupe')}">
                    <input type="submit" value="${coupe}" class="btn btn-default"/>
                </c:if>
                <c:if test="${type.type.equals('Jeep')}">
                    <input type="submit" value="${jeep}" class="btn btn-default"/>
                </c:if>
                <c:if test="${type.type.equals('Small')}">
                    <input type="submit" value="${small}" class="btn btn-default"/>
                </c:if>
                <c:if test="${type.type.equals('Middle')}">
                    <input type="submit" value="${middle}" class="btn btn-default"/>
                </c:if>
                <c:if test="${type.type.equals('Minibus')}">
                    <input type="submit" value="${minibus}" class="btn btn-default"/>
                </c:if>
                <c:if test="${type.type.equals('Premium')}">
                    <input type="submit" value="${premium}" class="btn btn-default"/>
                </c:if>
                <c:if test="${type.type.equals('Vintage')}">
                    <input type="submit" value="${vintage}" class="btn btn-default"/>
                </c:if>
            </form>
        </c:forEach>
        <form action="Controller" method="get" class="btn pag">
            <input type="hidden" name="command" value="view-all-cars"/>
            <input type="submit" value="${all}" class="btn btn-default"/>
        </form>
    </div>

    <h2>
        <c:if test="${requestScope.carType.equals('Cabriolet')}">
            ${cabriolet}
        </c:if>
        <c:if test="${requestScope.carType.equals('Cargo')}">
            ${cargo}
        </c:if>
        <c:if test="${requestScope.carType.equals('Coupe')}">
            ${coupe}
        </c:if>
        <c:if test="${requestScope.carType.equals('Jeep')}">
            ${jeep}
        </c:if>
        <c:if test="${requestScope.carType.equals('Small')}">
            ${small}
        </c:if>
        <c:if test="${requestScope.carType.equals('Middle')}">
            ${middle}
        </c:if>
        <c:if test="${requestScope.carType.equals('Minibus')}">
            ${minibus}
        </c:if>
        <c:if test="${requestScope.carType.equals('Premium')}">
            ${premium}
        </c:if>
        <c:if test="${requestScope.carType.equals('Vintage')}">
            ${vintage}
        </c:if>
    </h2>

    <div class="row well">
        <c:if test="${requestScope.noCars == true}">
            <p>${mNoCars}</p>
        </c:if>
        <c:if test="${requestScope.noCars !=true}">
            <c:forEach var="car" items="${allCars}">
                <div class="col-md-4 img-portfolio">
                    <form action="Controller" method="get">
                        <input type="hidden" name="command" value="view-car"/>
                        <input type="hidden" name="selectedCarId" value="${car.id}">
                        <button type="submit" class="btn btn-default">
                            <img class="img-responsive img-hover car-small-img"
                                 src="data:image/jpg;base64,${car.image}"/>

                            <p class="my-info">${car.mark} ${car.model}</p>
                        </button>
                    </form>
                </div>
            </c:forEach>
        </c:if>
    </div>

    <div class="btn-group">
        <c:if test="${requestScope.command == null && requestScope.noCars != false}">
            <c:forEach var="i" begin="1" end="${amountPages}">
                <form action="Controller" method="get" class="btn pag">
                    <input type="hidden" name="command" value="view-all-cars">
                    <input type="hidden" name="pageNumber" value="${i}"/>
                    <c:if test="${requestScope.pageNumber==i}">
                        <input type="submit" value="${i}" class="btn btn-default active"/>
                    </c:if>
                    <c:if test="${requestScope.pageNumber!=i}">
                        <input type="submit" value="${i}" class="btn btn-default"/>
                    </c:if>
                </form>
            </c:forEach>
            <p><c:out value="${mPage}: ${requestScope.pageNumber}"/></p>
        </c:if>

        <c:if test="${requestScope.command != null && requestScope.noCars != false && requestScope.carType != null}">
            <ul class="pagination">
                <c:forEach var="i" begin="1" end="${amountPages}">
                    <li>
                        <form action="Controller" method="get" class="btn pag">
                            <input type="hidden" name="carType" value="${requestScope.carType}">
                            <input type="hidden" name="command" value="${requestScope.command}">
                            <input type="hidden" name="pageNumber" value="${i}"/>
                            <c:if test="${requestScope.pageNumber==i}">
                                <input type="submit" value="${i}" class="btn btn-default active"/>
                            </c:if>
                            <c:if test="${requestScope.pageNumber!=i}">
                                <input type="submit" value="${i}" class="btn btn-default"/>
                            </c:if>
                        </form>
                    </li>
                </c:forEach>
                <p><c:out value="${mPage}: ${requestScope.pageNumber}"/></p>
            </ul>
        </c:if>
    </div>

    <hr/>
    <%@include file="../footer.jspf" %>
</div>

<script src="../../js/jquery.js"></script>
<script src="../../js/bootstrap.min.js"></script>

</body>
</html>
