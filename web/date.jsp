<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Date</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/style.css">
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="localization.local" var="locale"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="local.carRental" var="carRental"/>
    <fmt:message bundle="${locale}" key="local.mStatusUpdated" var="mStatusUpdated"/>
    <fmt:message bundle="${locale}" key="local.mInvalidDate" var="mInvalidDate"/>
    <fmt:message bundle="${locale}" key="local.setDate" var="mSetDate"/>
    <fmt:message bundle="${locale}" key="local.logOut" var="logOut"/>
    <fmt:message bundle="${locale}" key="local.home" var="home"/>
    <fmt:message bundle="${locale}" key="local.cars" var="cars"/>
    <fmt:message bundle="${locale}" key="local.info" var="info"/>
    <fmt:message bundle="${locale}" key="local.privateOffice" var="privateOffice"/>
    <fmt:message bundle="${locale}" key="local.viewOrders" var="viewOrders"/>
    <fmt:message bundle="${locale}" key="local.viewUsers" var="viewUsers"/>
    <fmt:message bundle="${locale}" key="local.addCar" var="addCar"/>
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
                <c:if test="${sessionScope.user.type.equals('admin')}">
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
                    <c:if test="${sessionScope.user.type.equals('admin')}">
                        <c:out value="${sessionScope.user.lastName}"/> <c:out value="${sessionScope.user.firstName}"/>
                    </c:if>
                </p>
            </div>

        </div>
        <c:if test="${sessionScope.user.type.equals('admin')}">
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
        </c:if>
    </div>

</header>
<section>

    <c:if test="${sessionScope.user.type.equals('admin')}">
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
    </c:if>

    <h2>
        <c:if test="${sessionScope.successfulUpdatedStatus == true}">
            <p class="rightMessage">${mStatusUpdated}</p>
            <c:set scope="session" var="successfulUpdatedStatus" value="false"></c:set>
        </c:if>
    </h2>
    <c:if test="${requestScope.invalidDateFrom == true}">
        <p class="invalidMessage">${mInvalidDate}</p>
    </c:if>

    <c:if test="${requestScope.invalidDateTo == true}">
        <p class="invalidMessage">${mInvalidDate}</p>
    </c:if>

    <c:if test="${sessionScope.fromDate == true}">
        <p>From date:</p>

        <form action="Controller" method="post">
            <input type="date" name="real-date-from" required>
            <input type="time" name="real-time-from" required>
            <input type="hidden" name="selectedOrderId" value="${requestScope.selectedOrder.id}">
            <input type="hidden" name="command" value="update-real-date-from">
            <input type="submit" name="${mSetDate}">
        </form>
    </c:if>

    <c:if test="${sessionScope.toDate == true}">
        <p>To date:</p>

        <form action="Controller" method="post">
            <input type="date" name="real-date-to" required>
            <input type="time" name="real-time-to" required>
            <input type="hidden" name="selectedOrderId" value="${requestScope.selectedOrder.id}">
            <input type="hidden" name="command" value="update-real-date-to">
            <input type="submit" name="${mSetDate}">
        </form>
    </c:if>

    <c:if test="${!sessionScope.user.type.equals('admin')}">
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
