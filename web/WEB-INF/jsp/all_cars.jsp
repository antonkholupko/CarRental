<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>All cars</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="../../css/style.css">
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
</head>
<body>
<header>
    <div>
        <div class="div1"><h1>${carRental}</h1></div>
        <div class="div2">
            <div>
                <form action="Controller" method="get">
                    <input type="hidden" name="command" value="change-locale">
                    <input type="hidden" name="language" value="en">
                    <input type="submit" value="${en_button}" class="button">
                </form>
            </div>
            <div>
                <form action="Controller" method="get">
                    <input type="hidden" name="command" value="change-locale">
                    <input type="hidden" name="language" value="ru">
                    <input type="submit" value="${ru_button}" class="button">
                </form>
            </div>
        </div>
        <c:if test="${sessionScope.user != null}">
            <div class="div5">
                <form action="Controller" method="post">
                    <input type="hidden" name="command" value="log-out-user">

                    <div><input type="submit" value="${logOut}" class="reg"></div>
                </form>
            </div>
        </c:if>
        <c:if test="${sessionScope.user == null}">
            <div class="div5">
                <form action="Controller" method="post">
                    <div>${login} <input type="text" name="login" value="" maxlength="25"/></div>
                    <div>${password} <input type="password" name="password" value="" maxlength="35"/></div>
                    <div>
                        <input type="hidden" name="command" value="login-user"/>
                        <input type="hidden" name="page-name" value="all-cars"/>
                        <input type="submit" value="${signIn}" class="button"/>
                    </div>
                </form>
                <p>
                    <c:if test="${requestScope.loginFailed == true}">
                        ${invLogin}
                        <c:set var="loginFailed" scope="session" value="false"/>
                    </c:if>
                </p>

                <form action="Controller" method="get">
                    <input type="hidden" name="command" value="to-registration">

                    <div><input type="submit" value="${registration}" class="reg"/></div>
                </form>
            </div>
        </c:if>
        <p>
            <c:if test="${sessionScope.user != null}">
                <c:out value="${sessionScope.user.lastName}"/> <c:out value="${sessionScope.user.firstName}"/>
            </c:if>
        </p>
    </div>
    <div class="div3">
        <div class="divMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="command" value="to-home-page">
                <input type="submit" value="${home}" class="buttonMenu"/>
            </form>
        </div>
        <div class="divMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="command" value="view-all-cars">
                <input type="submit" value="${cars}" class="buttonMenu"/>
            </form>
        </div>
        <c:if test="${sessionScope.user.type.equals('get')}">
            <div class="divMenu">
                <form action="Controller" method="get">
                    <input type="hidden" name="command" value="to-priv-office-user">
                    <input type="submit" value="${privateOffice}" class="buttonMenu"/>
                </form>
            </div>
            <div class="divMenu">
                <form action="Controller" method="get">
                    <input type="hidden" name="command" value="view-orders-user">
                    <input type="submit" value="${orders}" class="buttonMenu"/>
                </form>
            </div>
        </c:if>
        <c:if test="${sessionScope.user.type.equals('get')}">
            <div class="divMenu">
                <form action="Controller" method="post">
                    <input type="hidden" name="command" value="to-priv-office-admin">
                    <input type="submit" value="${privateOffice}" class="buttonMenu"/>
                </form>
            </div>
        </c:if>
        <div class="divMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="command" value="">
                <input type="submit" value="${info}" class="buttonMenu"/>
            </form>
        </div>

    </div>

</header>

<section>
    <h2>Cars</h2>

    <c:if test="${requestScope.carSuccessfulAdded == true}">
        <c:out value="Car successful added."/>
    </c:if>

    <c:if test="${requestScope.carSuccessfulDeleted == true}">
        <c:out value="Car successful deleted."/>
    </c:if>


    <div class="divDateTime">
        <form action="Controller" method="get">

            <c:forEach var="type" items="${allTypes}">
                <div class="divMenu">
                    <input type="radio" name="carType" value="${type.type}"> ${type.type} <br/>
                </div>
            </c:forEach>

            <c:if test="${requestScope.invalidType == true}">
                <p>
                    <c:out value="select type"/>
                </p>
            </c:if>

            <p>${chooseTripDate}</p>
            <c:if test="${requestScope.invalidDate == true}">
                <p>${mInvalidDate}</p>
            </c:if>
            <p>${mSupFromDate}</p>
            <input type="date" value="${sessionScope.supposedDateFrom}" name="supposedDateFrom" required/>
            <input type="time" value="${sessionScope.supposedTimeFrom}" name="supposedTimeFrom" required/>

            <p>${mSupToDate}</p>
            <input type="date" value="${sessionScope.supposedDateTo}" name="supposedDateTo" required/>
            <input type="time" value="${sessionScope.supposedTimeTo}" name="supposedTimeTo" required/>

            <br/>

            <input type="hidden" name="command" value="view-type-unused"/>
            <input type="submit" value="go">


        </form>
    </div>

    <c:forEach var="type" items="${allTypes}">
        <div class="divMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="command" value="view-type"/>
                <input type="hidden" name="carType" value="${type.type}"/>
                <c:if test="${type.type.equals('Cabriolet')}">
                    <input type="submit" value="${cabriolet}"/>
                </c:if>
                <c:if test="${type.type.equals('Cargo')}">
                    <input type="submit" value="${cargo}"/>
                </c:if>
                <c:if test="${type.type.equals('Coupe')}">
                    <input type="submit" value="${coupe}"/>
                </c:if>
                <c:if test="${type.type.equals('Jeep')}">
                    <input type="submit" value="${jeep}"/>
                </c:if>
                <c:if test="${type.type.equals('Small')}">
                    <input type="submit" value="${small}"/>
                </c:if>
                <c:if test="${type.type.equals('Middle')}">
                    <input type="submit" value="${middle}"/>
                </c:if>
                <c:if test="${type.type.equals('Minibus')}">
                    <input type="submit" value="${minibus}"/>
                </c:if>
                <c:if test="${type.type.equals('Premium')}">
                    <input type="submit" value="${premium}"/>
                </c:if>
                <c:if test="${type.type.equals('Vintage')}">
                    <input type="submit" value="${vintage}"/>
                </c:if>
            </form>
        </div>
    </c:forEach>
    <div class="divMenu">
        <form action="Controller" method="get">
            <input type="hidden" name="command" value="view-all-cars"/>
            <input type="hidden" name="carType" value="All"/>
            <input type="submit" value="${all}"/>
        </form>
    </div>

    <article>
        <div class="div1">
            <c:forEach var="car" items="${allCars}">
                <div class="div4">
                    <form action="Controller" method="get">
                        <img class="imgSmall" src="data:image/jpg;base64,${car.image}"/>
                        <input type="hidden" name="command" value="view-car"/>
                        <input type="hidden" name="selectedCarId" value="${car.id}">
                        <input type="submit" value="${car.mark} ${car.model}" class="ref2"/>
                    </form>
                </div>
            </c:forEach>
        </div>
    </article>


    <c:if test="${requestScope.command == null}">

        <c:forEach var="i" begin="1" end="${amountPages}">
            <div class="divMenu">
                <form action="Controller" method="get">
                    <input type="hidden" name="command" value="view-all-cars">
                    <input type="hidden" name="pageNumber" value="${i}"/>
                    <input type="submit" value="${i}"/>
                </form>
            </div>
        </c:forEach>

    </c:if>

    <c:if test="${requestScope.command != null}">
        <c:forEach var="i" begin="1" end="${amountPages}">
            <div class="divMenu">
                <form action="Controller" method="get">
                    <input type="hidden" name="carType" value="${requestScope.carType}">
                    <input type="hidden" name="command" value="${requestScope.command}">
                    <input type="hidden" name="pageNumber" value="${i}"/>
                    <input type="submit" value="${i}"/>
                </form>
            </div>
        </c:forEach>
    </c:if>


</section>

<footer>
    <p>&copy; 2016 Car rental. All rights reserved.</p>
</footer>


</body>
</html>
