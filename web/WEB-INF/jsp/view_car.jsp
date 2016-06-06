<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<title>Car</title>
<meta charset="utf-8">
<link rel="stylesheet" href="css/style.css">
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
<fmt:message bundle="${locale}" key="local.deleteCar" var="deleteCar" />

<body>
<header>
    <div>
        <div class="div1"><h1>${carRental}</h1></div>
        <div class="div2">
            <div>
                <form action="Controller" method="get">
                    <input type="hidden" name="command" value="change-locale">
                    <input type="hidden" name="language" value="en">
                    <input type="submit" value="${en_button}" class="buttonLocalReg">
                </form>
            </div>
            <div>
                <form action="Controller" method="get">
                    <input type="hidden" name="command" value="change-locale">
                    <input type="hidden" name="language" value="ru">
                    <input type="submit" value="${ru_button}" class="buttonLocalReg">
                </form>
            </div>
        </div>
        <c:if test="${sessionScope.user != null}">
            <div class="div5">
                <form action="Controller" method="post">
                    <input type="hidden" name="command" value="log-out-user">

                    <div><input type="submit" value="${logOut}" class="buttonLogOut"></div>
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
                        <input type="hidden" name="page-name" value="view-car"/>
                        <input type="submit" value="${signIn}" class="buttonSignIn"/>
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

                    <div><input type="submit" value="${registration}" class="buttonLocalReg"/></div>
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
        <c:if test="${sessionScope.user.type.equals('user')}">
            <div class="divMenu">
                <form action="Controller" method="get">
                    <input type="hidden" name="command" value="to-priv-office-user">
                    <input type="submit" value="${privateOffice}" class="buttonMenu"/>
                </form>
            </div>
        </c:if>
        <c:if test="${sessionScope.user.type.equals('admin')}">
            <div class="divMenu">
                <form action="Controller" method="get">
                    <input type="hidden" name="command" value="to-priv-office-admin">
                    <input type="submit" value="${privateOffice}" class="buttonMenu"/>
                </form>
            </div>
        </c:if>
        <div class="divMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="command" value="to-about">
                <input type="submit" value="${info}" class="buttonMenu"/>
            </form>
        </div>
    </div>

</header>
<section>
    <h2><c:out value="${sessionScope.selectedCar.mark}"/> <c:out value="${sessionScope.selectedCar.model}"/></h2>
    <article class="articleForTables">
        <span>
            <div class="divBigForPhoto">
                <img class="imgBig" src="data:image/jpg;base64,${sessionScope.selectedCar.image}"/>
            </div>

            <div class="divBigForDescription">
                <p>${price}: <c:out value="${sessionScope.selectedCar.price}"/></p>

                <p>${mark}: <c:out value="${sessionScope.selectedCar.mark}"/></p>

                <p>${model}: <c:out value="${sessionScope.selectedCar.model}"/></p>

                <p>${year}: <c:out value="${sessionScope.selectedCar.year}"/></p>

                <p>${type}:
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

                <p>${fuel}:
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

                <p>${transmission}:
                    <c:if test="${sessionScope.selectedCar.transmission.equals('automatic')}">
                        <c:out value="${automatic}"/>
                    </c:if>
                    <c:if test="${sessionScope.selectedCar.transmission.equals('mechanic')}">
                        <c:out value="${mechanic}"/>
                    </c:if>
                </p>

                <p>${carInfo}: <c:out value="${sessionScope.selectedCar.info}"/></p>
                <c:if test="${sessionScope.user.type.equals('user')}">
                <div>
                    <br/>

                    <form action="Controller" method="post">
                        <input type="hidden" name="command" value="to-make-order">
                        <input type="submit" value="${makeOrder}" class="buttonPurchase"/>
                    </form>
                </div>
            </div>
            </c:if>
            <c:if test="${sessionScope.user.type.equals('admin')}">
                <div>
                    <br/>

                    <form action="Controller" method="post">
                        <input type="hidden" name="command" value="delete-car">
                        <input type="submit" value="${deleteCar}" class="buttonDelete">
                    </form>
                </div>
            </c:if>
            <c:if test="${sessionScope.user == null}">
                <p>${forMakeOrderMessage}</p>
            </c:if>
        </span>
    </article>
</section>
<footer>
    <p>&copy; 2016 Car rental. All rights reserved.</p>
</footer>
</body>
</html>
