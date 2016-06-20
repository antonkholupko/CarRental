<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Make order</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/style.css">
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
    <fmt:message bundle="${locale}" key="local.carIsUsed" var="carIsUsed"/>
    <fmt:message bundle="${locale}" key="local.seeUnusedCarsMessage" var="unusedCarsMsg"/>
    <fmt:message bundle="${locale}" key="local.toCarsPage" var="toCars"/>
    <fmt:message bundle="${locale}" key="local.supposedFromDate" var="fromDate"/>
    <fmt:message bundle="${locale}" key="local.supposedToDate" var="toDate"/>
    <fmt:message bundle="${locale}" key="local.shippingPlaceMessage" var="mShippingPlace"/>
    <fmt:message bundle="${locale}" key="local.returnPlaceMessage" var="mReturnPlace"/>
    <fmt:message bundle="${locale}" key="local.mInvalidDate" var="mInvalidDate"/>
    <fmt:message bundle="${locale}" key="local.mInvalidPlaces" var="mInvalidPlaces"/>
    <fmt:message bundle="${locale}" key="local.makeOrder" var="makeOrder"/>
    <fmt:message bundle="${locale}" key="local.mMakingOrder" var="mMakingOrder"/>
    <fmt:message bundle="${locale}" key="local.viewOrders" var="mViewAllOrders"/>
    <fmt:message bundle="${locale}" key="local.mMakeOrder" var="mMakeOrder"/>
</head>
<body>
<header>
    <div class="divHeader">

        <div class="div3">
            <div class="div1"><h1>${carRental}</h1></div>

            <div class="div2">
                <div>
                    <form action="Controller" method="post">
                        <input type="hidden" name="command" value="change-locale"/>
                        <input type="hidden" name="language" value="en">
                        <input type="submit" value="${en_button}" class="buttonLocalReg">
                    </form>
                </div>
                <div>
                    <form action="Controller" method="post">
                        <input type="hidden" name="command" value="change-locale"/>
                        <input type="hidden" name="language" value="ru">
                        <input type="submit" value="${ru_button}" class="buttonLocalReg">
                    </form>
                </div>
            </div>

            <div class="div5">
                <c:if test="${sessionScope.user.type.equals('user')}">
                    <div class="div5">
                        <form action="Controller" method="post">
                            <input type="hidden" name="command" value="log-out-user">

                            <div>
                                <input type="submit" value="${logOut}" class="buttonLogOut">
                            </div>
                        </form>
                    </div>
                </c:if>
                <p>
                    <c:if test="${sessionScope.user.type.equals('user')}">
                        <c:out value="${sessionScope.user.lastName}"/> <c:out value="${sessionScope.user.firstName}"/>
                    </c:if>
                </p>
            </div>

        </div>
        <c:if test="${sessionScope.user.type.equals('user')}">
            <div class="divMenu">
                <div class="divMenu">
                    <form action="Controller" method="post">
                        <input type="hidden" name="command" value="to-home-page">
                        <input type="submit" value=${home} class="buttonMenu"/>
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
                <div class="divMenu">
                    <form action="Controller" method="get">
                        <input type="hidden" name="command" value="to-about">
                        <input type="submit" value="${info}" class="buttonMenu"/>
                    </form>
                </div>
            </div>
        </c:if>
    </div>
</header>
<section>
    <c:if test="${sessionScope.user.type.equals('user')}">
        <h2>${mMakingOrder}</h2>
        <c:if test="${requestScope.addOrderFailed == true}">
            <p class="invalidMessage">${carIsUsed}</p>

            <p class="helpMessage">${unusedCarsMsg}</p>

            <div class="divSubMenu">
                <form action="Controller" method="get">
                    <input type="hidden" name="command" value="view-all-cars"/>
                    <input type="submit" value="${toCars}" class="buttonSubMenu"/>
                </form>
            </div>
        </c:if>
        <hr/>
        <c:if test="${sessionScope.selectedCar.id != null}">
            <img class="imgSmall" src="data:image/jpg;base64,${sessionScope.selectedCar.image}"/>

            <p><c:out value="${sessionScope.selectedCar.mark}"/> <c:out value="${sessionScope.selectedCar.model}"/></p>
        </c:if>
        <hr/>
        <c:if test="${requestScope.addOrderFailed == true}">
            <div class="divSubMenu">
                <form action="Controller" method="get">
                    <input type="hidden" name="command" value="view-orders-user">
                    <input type="submit" value="${mViewAllOrders}" class="buttonSubMenu">
                </form>
            </div>
            <div class="divSubMenu">
                <form action="Controller" method="get">
                    <input type="hidden" name="command" value="view-all-cars">
                    <input type="submit" value="${mMakeOrder}" class="buttonSubMenu">
                </form>
            </div>
        </c:if>

        <form action="Controller" method="post">

            <c:if test="${requestScope.invalidDate == true}">
                <p class="invalidMessage"> ${mInvalidDate} </p>
            </c:if>

            <p>${fromDate}</p>
            <input type="date" name="supposedDateFrom" value="${sessionScope.supposedDateFrom}" required/>
            <input type="time" name="supposedTimeFrom" value="${sessionScope.supposedTimeFrom}" required/>

            <p>${toDate}</p>
            <input type="date" name="supposedDateTo" value="${sessionScope.supposedDateTo}" required/>
            <input type="time" name="supposedTimeTo" value="${sessionScope.supposedTimeTo}" required/>

            <c:if test="${requestScope.invalidPlaces == true}">
                <p class="invalidMessage"> ${mInvalidPlaces} </p>
            </c:if>

            <p>${mShippingPlace}</p>
            <input type="text" name="shippingPlace" value="" maxlength="44"/>

            <p>${mReturnPlace}</p>
            <input type="text" name="returnPlace" value="" maxlength="44"/>
            <input type="hidden" name="command" value="make-order">
            <input type="hidden" name="processRequest" value="redirect">
            <input type="submit" value="${makeOrder}" class="buttonPurchase"/>
        </form>
    </c:if>
    <c:if test="${!sessionScope.user.type.equals('user')}">
        <div class="divSubMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="command" value="to-home-page">
                <input type="submit" value="${home}" class="buttonSubMenu">
            </form>
        </div>
    </c:if>
</section>
<footer>
    <p>&copy; 2016 Car rental. All rights reserved.</p>
</footer>
</body>
</html>
