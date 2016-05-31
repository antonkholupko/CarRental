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
                <input type="submit" value="Home" class="buttonMenu"/>
            </form>
        </div>
        <div class="divMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="command" value="view-all-cars">
                <input type="submit" value="Cars" class="buttonMenu"/>
            </form>
        </div>
        <div class="divMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="command" value="to-priv-office-user">
                <input type="submit" value="Private office" class="buttonMenu"/>
            </form>
        </div>
        <div class="divMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="command" value="view-orders-user">
                <input type="submit" value="My Orders" class="buttonMenu"/>
            </form>
        </div>
        <div class="divMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="command" value="">
                <input type="submit" value="Info" class="buttonMenu"/>
            </form>
        </div>
    </div>
</header>
<section>

    <div class="divMenu">
        <form action="Controller" method="get">
            <input type="hidden" name="selectedOrderId" value="${sessionScope.selectedOrder.id}">
            <input type="hidden" name="command" value="view-order-user">
            <input type="submit" value="Details"/>
        </form>
    </div>

    <c:if test="${sessionScope.selectedOrder.status.equals('новый')}">
        <div class="divMenu">
            <form action="Controller" method="post">
                <input type="hidden" name="command" value="cancel-order"/>
                <input type="submit" value="Cancel order"/>
            </form>
        </div>
    </c:if>
    <c:if test="${sessionScope.selectedOrder.status.equals('принят')}">
        <div class="divMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="payment-type" value="order"/>
                <input type="hidden" name="command" value="to-payment-page"/>
                <input type="submit" value="Pay"/>
            </form>
        </div>
    </c:if>

    <c:if test="${sessionScope.selectedOrder.damagePrice != 0 && sessionScope.selectedOrder.status.equals('ожидаетКомп')}">
        <div class="divMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="payment-type" value="damage"/>
                <input type="hidden" name="command" value="to-payment-page"/>
                <input type="submit" value="Pay for damage"/>
            </form>
        </div>
    </c:if>

    <h2>Order № <c:out value="${sessionScope.selectedOrder.id}"/></h2>
    <c:if test="${requestScope.successfulPayment == true}">
        <p>Successful payment!!!</p>
    </c:if>
    <p>Order status: <c:out value="${sessionScope.selectedOrder.status}"/></p>

    <p>Order info: <c:out value="${sessionScope.selectedOrder.info}"/></p>

    <p>Order price: <c:out value="${sessionScope.selectedOrder.orderPrice}"/></p>

    <p>Damage price: <c:out value="${sessionScope.selectedOrder.damagePrice}"/></p>
    <img class="imgSmall" src="data:image/jpg;base64,${sessionScope.selectedOrder.car.image}"/>

    <p>Car mark: <c:out value="${sessionScope.selectedOrder.car.mark}"/></p>

    <p>Car model: <c:out value="${sessionScope.selectedOrder.car.model}"/></p>

    <p>Fuel: <c:out value="${sessionScope.selectedOrder.car.fuel}"/></p>

    <p>Transmission: <c:out value="${sessionScope.selectedOrder.car.transmission}"/></p>

    <p>Sign: <c:out value="${sessionScope.selectedOrder.car.govNumber}"/></p>

    <p>Supposed date from: <c:out value="${sessionScope.selectedOrder.supposedDateFrom}"/></p>

    <p>Supposed date to: <c:out value="${sessionScope.selectedOrder.supposedDateTo}"/></p>

    <p>Shipping place: <c:out value="${sessionScope.selectedOrder.shippingPlace}"/></p>

    <p>Return place: <c:out value="${sessionScope.selectedOrder.returnPlace}"/></p>

</section>
<footer>
    <p>&copy; 2016 Car rental. All rights reserved.</p>
</footer>
</body>
</html>
