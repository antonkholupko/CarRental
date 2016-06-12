<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>User Success</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/style.css">
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="localization.local" var="locale"/>
    <fmt:message bundle="${locale}" key="local.mSuccessfulPaymentForOrder" var="mSuccessfulPayment"/>
    <fmt:message bundle="${locale}" key="local.mOrderAdded" var="orderAded"/>
    <fmt:message bundle="${locale}" key="local.mOrderCanceled" var="orderCanceled"/>
    <fmt:message bundle="${locale}" key="local.mSuccessfulPaymentForDmg" var="mSuccessfulPaymentForDmg"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="local.carRental" var="carRental"/>
    <fmt:message bundle="${locale}" key="local.logOut" var="logOut"/>
    <fmt:message bundle="${locale}" key="local.home" var="home"/>
    <fmt:message bundle="${locale}" key="local.cars" var="cars"/>
    <fmt:message bundle="${locale}" key="local.info" var="info"/>
    <fmt:message bundle="${locale}" key="local.orders" var="myOrders"/>
    <fmt:message bundle="${locale}" key="local.privateOffice" var="privateOffice"/>
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

    <hr/>

    <c:if test="${sessionScope.user.type.equals('user')}">
        <h2>${privateOffice}</h2>

        <hr/>
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

    <hr/>

    <h2>
        <c:if test="${sessionScope.orderSuccessfulMade == true}">
            <p class="rightMessage">${orderAded}</p>
            <c:set scope="session" var="orderSuccessfulMade" value="false"></c:set>
        </c:if>

        <c:if test="${sessionScope.successfulCanceled == true}">
            <p class="rightMessage">${orderCanceled}</p>
            <c:set scope="session" var="successfulCanceled" value="false"></c:set>
        </c:if>

        <c:if test="${sessionScope.successfulPayment == true}">
            <p class="rightMessage">${mSuccessfulPayment}</p>
            <c:set scope="session" var="successfulPayment" value="false"></c:set>
        </c:if>

        <c:if test="${sessionScope.successfulPaymentForDmg == true}">
            <p class="rightMessage">${mSuccessfulPaymentForDmg}</p>
            <c:set scope="session" var="successfulPayment" value="false"></c:set>
        </c:if>
    </h2>

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
