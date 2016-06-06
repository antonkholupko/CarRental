<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Private office</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/style.css">
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="localization.local" var="locale"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="local.carRental" var="carRental"/>
    <fmt:message bundle="${locale}" key="local.welcomePrivateOfficeAdmin" var="welcomeAdmin"/>
    <fmt:message bundle="${locale}" key="local.adminPrivateOffice" var="adminPrivateOffice"/>
    <fmt:message bundle="${locale}" key="local.logOut" var="logOut"/>
    <fmt:message bundle="${locale}" key="local.home" var="home"/>
    <fmt:message bundle="${locale}" key="local.cars" var="cars"/>
    <fmt:message bundle="${locale}" key="local.info" var="info"/>
    <fmt:message bundle="${locale}" key="local.privateOffice" var="privateOffice"/>
    <fmt:message bundle="${locale}" key="local.viewOrders" var="viewOrders"/>
    <fmt:message bundle="${locale}" key="local.viewUsers" var="viewUsers"/>
    <fmt:message bundle="${locale}" key="local.addCar" var="addCar"/>
    <fmt:message bundle="${locale}" key="local.statusNew" var="sNew"/>
    <fmt:message bundle="${locale}" key="local.statusCanceled" var="sCanceld"/>
    <fmt:message bundle="${locale}" key="local.statusRejected" var="sRejected"/>
    <fmt:message bundle="${locale}" key="local.statusAccepted" var="Accepted"/>
    <fmt:message bundle="${locale}" key="local.statusPaid" var="Paid"/>
    <fmt:message bundle="${locale}" key="local.statusDelivered" var="Delivered"/>
    <fmt:message bundle="${locale}" key="local.statusReturned" var="Returned"/>
    <fmt:message bundle="${locale}" key="local.statusExpectsComp" var="ExpectsComp"/>
    <fmt:message bundle="${locale}" key="local.statusClosed" var="Closed"/>
    <fmt:message bundle="${locale}" key="local.mDetails" var="mDetails"/>
    <fmt:message bundle="${locale}" key="local.noOrders" var="mNoOrders"/>
    <fmt:message bundle="${locale}" key="local.orders" var="mOrders"/>
    <fmt:message bundle="${locale}" key="local.mLastName" var="mLastName"/>
    <fmt:message bundle="${locale}" key="local.mFirstName" var="mFirstName"/>
    <fmt:message bundle="${locale}" key="local.mMiddleName" var="mMiddleName"/>
    <fmt:message bundle="${locale}" key="local.mark" var="mMark"/>
    <fmt:message bundle="${locale}" key="local.model" var="mModel"/>
    <fmt:message bundle="${locale}" key="local.mGovNumber" var="mGovNumber"/>
    <fmt:message bundle="${locale}" key="local.mStatus" var="mStatus"/>
    <fmt:message bundle="${locale}" key="local.price" var="price"/>
    <fmt:message bundle="${locale}" key="local.mDamagePrice" var="mDmgPrice"/>
    <fmt:message bundle="${locale}" key="local.mOrderNumber" var="mOrderNumber"/>
    <fmt:message bundle="${locale}" key="local.orders" var="myOrders"/>
    <fmt:message bundle="${locale}" key="local.viewOrders" var="viewAllOrders"/>
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
    </div>
    <div class="div5">
        <form action="Controller" method="post">
            <input type="hidden" name="command" value="log-out-user">

            <div><input type="submit" value="${logOut}" class="buttonLogOut"></div>
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
                <input type="hidden" name="command" value="to-priv-office-admin">
                <input type="submit" value="${privateOffice}" class="buttonMenu"/>
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

    <h2>${adminPrivateOffice}</h2>

    <hr/>

    <div class="divSubMenu">
        <form action="Controller" method="get">
            <input type="hidden" name="command" value="view-orders-admin">
            <input type="submit" value="${viewOrders}" class="buttonSubMenu">
        </form>
    </div>
    <div class="divSubMenu">
        <form action="Controller" method="get">
            <input type="hidden" name="command" value="view-all-users">
            <input type="submit" value="${viewUsers}" class="buttonSubMenu">
        </form>
    </div>
    <div class="divSubMenu">
        <form action="Controller" method="get">
            <input type="hidden" name="command" value="to-add-car">
            <input type="submit" value="${addCar}" class="buttonSubMenu">
        </form>
    </div>

    <hr/>

    <p><c:out value="${sessionScope.user.lastName}"/>
        <c:out value="${sessionScope.user.firstName}"/>
        <c:out value="${sessionScope.user.middleName}"/>,
        ${welcomeAdmin}.</p>

    <br/>
    <hr/>
    <h2>${myOrders}</h2>
    <br/>

    <c:if test="${requestScope.noOrders == true }">
        <p>${mNoOrders}</p>
    </c:if>

    <article class="articleForTables">
        <c:forEach var="order" items="${orders}">
            <article>
                <div class="divOrders">

                    <table border="1" width="100%">
                        <thead>
                        <tr>
                            <th>${mOrderNumber}</th>
                            <th>${mLastName}</th>
                            <th>${mFirstName}</th>
                            <th>${mMiddleName}</th>
                            <th>${mMark}</th>
                            <th>${mModel}</th>
                            <th>${mGovNumber}</th>
                            <th>${mStatus}</th>
                            <th>${price}</th>
                            <th>${mDmgPrice}</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td><c:out value="${order.id}"/></td>
                            <td><c:out value="${order.user.lastName}"/></td>
                            <td><c:out value="${order.user.firstName}"/></td>
                            <td><c:out value="${order.user.middleName}"/></td>
                            <td><c:out value="${order.car.mark}"/></td>
                            <td><c:out value="${order.car.model}"/></td>
                            <td><c:out value="${order.car.govNumber}"/></td>
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
                                <c:if test="${order.status.equals('payed')}">
                                    ${Paid}
                                </c:if>
                                <c:if test="${order.status.equals('accepted')}">
                                    ${Accepted}
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
                            <td><c:out value="${order.damagePrice}"/></td>
                        </tr>
                        </tbody>
                    </table>
                    <br/>

                    <div class="divSubMenu">
                        <form action="Controller" method="get">
                            <input type="hidden" name="selectedOrderId" value="${order.id}">
                            <input type="hidden" name="command" value="view-order-admin">
                            <input type="submit" value="${mDetails}" class="buttonSubMenu"/>
                        </form>
                    </div>
                </div>
            </article>
            <br/>
        </c:forEach>
        <form action="Controller" method="get">
            <input type="hidden" name="command" value="view-orders-admin">
            <input type="submit" value="${viewAllOrders}>>" class="buttonSubMenu"/>
        </form>
    </article>
</section>
<footer>
    <p>&copy; 2016 Car rental. All rights reserved.</p>
</footer>
</body>
</html>