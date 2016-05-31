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
                <input type="hidden" name="command" value="to-priv-office-admin">
                <input type="submit" value="Private office" class="buttonMenu"/>
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
    <h2>№ Order: <c:out value="${sessionScope.selectedOrder.id}"/></h2>
    <article>
        <div class="divOrders">
            <h2>Order</h2>
            <c:if test="${sessionScope.successfulUpdate == true}">
                <h3>Successfully updated!!!</h3>
            </c:if>
            <c:if test="${sessionScope.selectedOrder.status.equals('доставлен') &&
                                    sessionScope.selectedOrder.realDateFrom == null}">
                <c:out value="Select real from date."/>
            </c:if>
            <c:if test="${sessionScope.selectedOrder.status.equals('возвращен') &&
                                sessionScope.selectedOrder.realDateTo == null}">
                <c:out value="Select real to date"/>
            </c:if>
            <c:if test="${sessionScope.invalidInfo == true}">
                <p>Invalid Info</p>
            </c:if>
            <p> Status: <c:out value="${sessionScope.selectedOrder.status}"/></p>
            <c:if test="${!sessionScope.selectedOrder.status.equals('отклонен') &&
                        !sessionScope.selectedOrder.status.equals('отменен') &&
                        !sessionScope.selectedOrder.status.equals('закрыт') &&
                        !sessionScope.selectedOrder.status.equals('принят') &&
                        !sessionScope.selectedOrder.status.equals('ожидаетКомп')}">
            <form action="Controller" method="post">
                <select name="statusOrder">
                    <c:if test="${sessionScope.selectedOrder.status.equals('новый')}">
                        <option value="принят">Принят</option>
                        <option value="отклонен">Отклонен</option>
                    </c:if>
                    <c:if test="${sessionScope.selectedOrder.status.equals('оплачен')}">
                        <option value="доставлен">Доставлен</option>
                    </c:if>
                    <c:if test="${sessionScope.selectedOrder.status.equals('доставлен') &&
                                    sessionScope.selectedOrder.realDateFrom != null}">
                        <option value="возвращен">Возвращен</option>
                    </c:if>
                    <c:if test="${sessionScope.selectedOrder.status.equals('возвращен') &&
                                    sessionScope.selectedOrder.realDateTo != null}">
                        <option value="закрыт">Закрыт</option>
                    </c:if>
                </select>
                        <textarea name="order-info" cols="40" rows="3" required
                                  oninvalid="this.setCustomValidity('Fill it')"
                                  oninput="setCustomValidity('')"></textarea>
                <input type="hidden" name="orderId" value="${sessionScope.selectedOrder.id}">
                <input type="hidden" name="command" value="update-status">
                <input type="submit" value="Change status"/>
                </c:if>
            </form>
            <p> Supposed date from: <c:out value="${sessionScope.selectedOrder.supposedDateFrom}"/></p>

            <p> Supposed date to: <c:out value="${sessionScope.selectedOrder.supposedDateTo}"/></p>

            <p> Shipping place: <c:out value="${sessionScope.selectedOrder.shippingPlace}"/></p>

            <p> Return place: <c:out value="${sessionScope.selectedOrder.returnPlace}"/></p>

            <p> Order price: <c:out value="${sessionScope.selectedOrder.orderPrice}"/></p>

            <p> Real date from: <c:out value="${sessionScope.selectedOrder.realDateFrom}"/></p>

            <p> Real date to: <c:out value="${sessionScope.selectedOrder.realDateTo}"/></p>

            <c:if test="${sessionScope.selectedOrder.status.equals('доставлен')}">
                <c:if test="${requestScope.invalidDateFrom == true}">
                    <p>Invalid date</p>
                </c:if>
                <form action="Controller" method="post">
                    <input type="date" name="real-date-from" required>
                    <input type="time" name="real-time-from" required>
                    <input type="hidden" name="command" value="update-real-date-from">
                    <input type="submit" name="Set real date">
                </form>
            </c:if>
            <c:if test="${sessionScope.selectedOrder.realDateFrom != null &&
                                sessionScope.selectedOrder.status.equals('возвращен')}">
                <c:if test="${requestScope.invalidDateTo == true}">
                    <p>Invalid date</p>
                </c:if>
                <form action="Controller" method="post">
                    <input type="date" name="real-date-to" required>
                    <input type="time" name="real-time-to" required>
                    <input type="hidden" name="command" value="update-real-date-to">
                    <input type="submit" name="Set real date">
                </form>
            </c:if>
            <p> Damage price: <c:out value="${sessionScope.selectedOrder.damagePrice}"/></p>
            <c:if test="${sessionScope.selectedOrder.status.equals('возвращен')}">
                <c:if test="${requestScope.invalidDamagePrice == true}">
                    <p>Invalid damage price</p>
                </c:if>
                <form action="Controller" method="post">
                    <input type="text" name="damage-price" min="" pattern="(^[0-9]+\.([0-9][0-9]|[0-9])$)|(^[0-9]+$)">
                    <input type="hidden" name="command" value="update-damage-price">
                    <input type="submit" value="Set damage price">
                </form>
            </c:if>
            <p> Information: <c:out value="${sessionScope.selectedOrder.info}"/></p>
        </div>
        <div class="divOrders">
            <h2>User</h2>

            <p>Login: <c:out value="${sessionScope.selectedOrder.user.login}"/></p>

            <p>Last name: <c:out value="${sessionScope.selectedOrder.user.lastName}"/></p>

            <p>First name: <c:out value="${sessionScope.selectedOrder.user.firstName}"/></p>

            <p>Middle name: <c:out value="${sessionScope.selectedOrder.user.middleName}"/></p>

            <p>e-mail: <c:out value="${sessionScope.selectedOrder.user.EMail}"/></p>

            <p>Phone: <c:out value="${sessionScope.selectedOrder.user.phone}"/></p>

            <p>Passport: <c:out value="${sessionScope.selectedOrder.user.passport}"/></p>

            <p>Address: <c:out value="${sessionScope.selectedOrder.user.address}"/></p>
        </div>
        <div class="divOrders">
            <h2>Car</h2>
            <img class="imgSmall" src="data:image/jpg;base64,${sessionScope.selectedOrder.car.image}"/>

            <p>Mark: <c:out value="${sessionScope.selectedOrder.car.mark}"/></p>

            <p>Model: <c:out value="${sessionScope.selectedOrder.car.model}"/></p>

            <p>Sign: <c:out value="${sessionScope.selectedOrder.car.govNumber}"/></p>

            <p>Year: <c:out value="${sessionScope.selectedOrder.car.year}"/></p>

            <p>Transmission: <c:out value="${sessionScope.selectedOrder.car.transmission}"/></p>

            <p>Type: <c:out value="${sessionScope.selectedOrder.car.type}"/></p>

            <p>Fuel: <c:out value="${sessionScope.selectedOrder.car.fuel}"/></p>
        </div>
    </article>
</section>
<footer>
    <p>&copy; 2016 Car rental. All rights reserved.</p>
</footer>
</body>
</html>
