<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Orders</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="../../css/style.css">
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
    <fmt:message bundle="${locale}" key="local.orders" var="myOrders"/>
    <fmt:message bundle="${locale}" key="local.mOrderPrice" var="mOrderPrice" />
    <fmt:message bundle="${locale}" key="local.mDamagePrice" var="mDmgPrice" />
    <fmt:message bundle="${locale}" key="local.mPayForOrder" var="mPayForOrder" />
    <fmt:message bundle="${locale}" key="local.mPayForDmg" var="mPayForDmg" />
    <fmt:message bundle="${locale}" key="local.mPaymentPage" var="mPaymentPage" />
    <fmt:message bundle="${locale}" key="local.mToOrder" var="mToOrder" />
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
    </div>
    <div class="div5">
        <form action="Controller" method="post">
            <input type="hidden" name="command" value="log-out-user">

            <div><input type="submit" value="${logOut}" class="reg"></div>
        </form>
    </div>
    <p>
        <c:out value="${sessionScope.user.lastName}"/> <c:out value="${sessionScope.user.firstName}"/>
    </p>

    <div class="divMenu">
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
        <div class="divMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="command" value="to-priv-office-user">
                <input type="submit" value="${privateOffice}" class="buttonMenu"/>
            </form>
        </div>
        <div class="divMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="command" value="view-orders-user">
                <input type="submit" value="${myOrders}" class="buttonMenu"/>
            </form>
        </div>
        <div class="divMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="command" value="to-about">
                <input type="submit" value="${info}" class="buttonMenu"/>
            </form>
        </div>
    </div>
</header>
<section>

    <h2>${mPaymentPage}</h2>

    <hr/>

    <div class="divMenu">
        <form action="Controller" method="get">
            <input type="hidden" name="selectedOrderId" value="${sessionScope.selectedOrder.id}">
            <input type="hidden" name="command" value="view-order-user">
            <input type="submit" value="${mToOrder}"/>
        </form>
    </div>

    <hr/>

    <c:if test="${sessionScope.paymentType.equals('order')}">
        <h2>${mOrderPrice}: </h2>

        <h2><c:out value="${sessionScope.selectedOrder.orderPrice}"/>$</h2>

        <form action="Controller" method="post">
            <input type="hidden" name="command" value="pay">
            <input type="submit" value="${mPayForOrder}" class="button2"/>
        </form>
    </c:if>
    <c:if test="${sessionScope.paymentType.equals('damage')}">
        <h2>${mDmgPrice}: </h2>

        <h2><c:out value="${sessionScope.selectedOrder.damagePrice}"/></h2>

        <form action="Controller" method="post">
            <input type="hidden" name="command" value="pay-for-damage">
            <input type="submit" value="${mPayForDmg}" class="button2"/>
        </form>
    </c:if>
</section>
<footer>
    <p>&copy; 2016 Car rental. All rights reserved.</p>
</footer>
</body>
</html>