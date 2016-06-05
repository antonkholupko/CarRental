<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>
<head>
    <title>Orders</title>
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
    <fmt:message bundle="${locale}" key="local.toPrivOffice" var="mToPrivateOffice" />
    <fmt:message bundle="${locale}" key="local.home" var="home"/>
    <fmt:message bundle="${locale}" key="local.cars" var="cars"/>
    <fmt:message bundle="${locale}" key="local.info" var="info"/>
    <fmt:message bundle="${locale}" key="local.privateOffice" var="privateOffice"/>
    <fmt:message bundle="${locale}" key="local.orders" var="myOrders"/>
    <fmt:message bundle="${locale}" key="local.mOrderNumber" var="mOrderNumber" />
    <fmt:message bundle="${locale}" key="local.mark" var="mMark" />
    <fmt:message bundle="${locale}" key="local.model" var="mModel" />
    <fmt:message bundle="${locale}" key="local.mSupposedDateFrom" var="dateFrom" />
    <fmt:message bundle="${locale}" key="local.mSupposedDateTo" var="dateTo" />
    <fmt:message bundle="${locale}" key="local.mStatus" var="mStatus" />
    <fmt:message bundle="${locale}" key="local.price" var="mPrice" />
    <fmt:message bundle="${locale}" key="local.statusNew" var="sNew" />
    <fmt:message bundle="${locale}" key="local.statusCanceled" var="sCanceld" />
    <fmt:message bundle="${locale}" key="local.statusRejected" var="sRejected" />
    <fmt:message bundle="${locale}" key="local.statusAccepted" var="Accepted" />
    <fmt:message bundle="${locale}" key="local.statusPaid" var="Paid" />
    <fmt:message bundle="${locale}" key="local.statusDelivered" var="Delivered" />
    <fmt:message bundle="${locale}" key="local.statusReturned" var="Returned" />
    <fmt:message bundle="${locale}" key="local.statusExpectsComp" var="ExpectsComp" />
    <fmt:message bundle="${locale}" key="local.statusClosed" var="Closed" />
    <fmt:message bundle="${locale}" key="local.mDetails" var="mDetails" />
    <fmt:message bundle="${locale}" key="local.noOrders" var="mNoOrders" />
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
    <h2>${myOrders}</h2>

    <hr/>

    <div class="divSubMenu">
        <form action="Controller" method="get">
            <input type="hidden" name="command" value="to-priv-office-user">
            <input type="submit" value="${mToPrivateOffice}" class="buttonSubMenu">
        </form>
    </div>

    <hr/>

    <c:if test="${requestScope.noOrders == true }">
        <p>${mNoOrders}</p>
    </c:if>

    <c:forEach var="order" items="${sessionScope.orders}">
        <article>
            <div class="divOrders">

                <table border="1" width="100%">
                    <thead>
                    <tr>
                        <th>${mOrderNumber}</th>
                        <th>${mMark}</th>
                        <th>${mModel}</th>
                        <th>${dateFrom}</th>
                        <th>${dateTo}</th>
                        <th>${mStatus}</th>
                        <th>${mPrice}</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td><c:out value="${order.id}"/></td>
                        <td><c:out value="${order.car.mark}"/></td>
                        <td><c:out value="${order.car.model}"/></td>
                        <td><c:out value="${order.supposedDateFrom}"/></td>
                        <td><c:out value="${order.supposedDateTo}"/></td>
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
                            <c:if test="${order.status.equals('accepted')}">
                                ${Accepted}
                            </c:if>
                            <c:if test="${order.status.equals('payed')}">
                                ${Paid}
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
                    </tr>
                    </tbody>
                </table>
                <br/>

                <div class="divSubMenu">
                    <form action="Controller" method="get">
                        <input type="hidden" name="selectedOrderId" value="${order.id}">
                        <input type="hidden" name="command" value="view-order-user">
                        <input type="submit" value="${mDetails}" class="buttonSubMenu"/>
                    </form>
                </div>
            </div>
        </article>
        <br/>
    </c:forEach>
</section>
<footer>
    <p>&copy; 2016 Car rental. All rights reserved.</p>
</footer>
</body>
</html>
