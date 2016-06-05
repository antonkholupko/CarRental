<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="cr" uri="WEB-INF/custom.tld" %>

<html>
<head>
    <title>Car rental</title>
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
    <fmt:message bundle="${locale}" key="local.messageWelcome" var="messageWelcome"/>
    <fmt:message bundle="${locale}" key="local.seeAllBtn" var="seeAllBtn"/>
    <fmt:message bundle="${locale}" key="local.messageInvLogin" var="messageInvLogin"/>
    <fmt:message bundle="${locale}" key="local.messageInvLogin" var="invLogin"/>
    <fmt:message bundle="${locale}" key="local.home" var="home"/>
    <fmt:message bundle="${locale}" key="local.cars" var="cars"/>
    <fmt:message bundle="${locale}" key="local.info" var="info"/>
    <fmt:message bundle="${locale}" key="local.logOut" var="logOut"/>
    <fmt:message bundle="${locale}" key="local.privateOffice" var="privateOffice"/>
    <fmt:message bundle="${locale}" key="local.orders" var="orders"/>
    <fmt:message bundle="${locale}" key="local.cabriolet" var="cabriolet"/>
    <fmt:message bundle="${locale}" key="local.cargo" var="cargo"/>
    <fmt:message bundle="${locale}" key="local.coupe" var="coupe"/>
    <fmt:message bundle="${locale}" key="local.jeep" var="jeep"/>
    <fmt:message bundle="${locale}" key="local.smallClass" var="small"/>
    <fmt:message bundle="${locale}" key="local.middleClass" var="middle"/>
    <fmt:message bundle="${locale}" key="local.minibus" var="minibus"/>
    <fmt:message bundle="${locale}" key="local.premium" var="premium"/>
    <fmt:message bundle="${locale}" key="local.vintage" var="vintage"/>
</head>

<body>

<header class="headerMain">
    <div class="divHeader">

        <div class="div3">
            <div class="div1"><h1>${carRental}</h1></div>

            <div class="div2">
                <div>
                    <form action="Controller" method="post">
                        <input type="hidden" name="command" value="change-locale"/>
                        <input type="hidden" name="language" value="en">
                        <input type="submit" value="${en_button}" class="button">
                    </form>
                </div>
                <div>
                    <form action="Controller" method="post">
                        <input type="hidden" name="command" value="change-locale"/>
                        <input type="hidden" name="language" value="ru">
                        <input type="submit" value="${ru_button}" class="button">
                    </form>
                </div>
            </div>

            <div class="div5">
                <c:if test="${sessionScope.user != null}">
                    <div class="div5">
                        <form action="Controller" method="post">
                            <input type="hidden" name="command" value="log-out-user">

                            <div>
                                <input type="submit" value="${logOut}" class="reg">
                            </div>
                        </form>
                    </div>
                </c:if>
                <c:if test="${sessionScope.user == null}">
                    <form action="Controller" method="post">
                        <div>
                                ${login} <input type="text" name="login" value="" maxlength="25"/>
                        </div>
                        <div>
                                ${password} <input type="password" name="password" value="" maxlength="35"/>
                        </div>
                        <div>
                            <input type="hidden" name="command" value="login-user"/>
                            <input type="hidden" name="page-name" value="index"/>
                            <input type="submit" value="${signIn}" class="button"/>
                        </div>
                    </form>
                </c:if>
                <p>
                    <c:if test="${requestScope.loginFailed == true}">
                        ${invLogin}
                        <c:set var="loginFailed" scope="session" value="false"/>
                    </c:if>
                </p>
                <c:if test="${sessionScope.user == null}">
                    <form action="Controller" method="post">
                        <input type="hidden" name="command" value="to-registration"/>

                        <div>
                            <input type="submit" value="${registration}" class="reg"/>
                        </div>
                    </form>
                </c:if>
                <p>
                    <c:if test="${sessionScope.user != null}">
                        <c:out value="${sessionScope.user.lastName}"/> <c:out value="${sessionScope.user.firstName}"/>
                    </c:if>
                </p>
            </div>

        </div>

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
                <div class="divMenu">
                    <form action="Controller" method="get">
                        <input type="hidden" name="command" value="view-orders-user">
                        <input type="submit" value="${orders}" class="buttonMenu"/>
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

    </div>

</header>

<section>

    <article class="divMsgWelcome">
        <h3 class="message"> ${messageWelcome} </h3>
    </article>

    <article>
        <div class="div1">
            <div class="div4">
                <form action="Controller" method="get">
                    <img src="img/fFocus.jpg"/>
                    <input type="hidden" name="command" value="view-type">
                    <input type="hidden" name="carType" value="Small"/>
                    <input type="submit" value="${small}" class="ref2"/>
                </form>
            </div>
            <div class="div4">
                <form action="Controller" method="get">
                    <img src="img/n370.jpg"/>
                    <input type="hidden" name="command" value="view-type">
                    <input type="hidden" name="carType" value="Coupe"/>
                    <input type="submit" value="${coupe}" class="ref2"/>
                </form>
            </div>
            <div class="div4">
                <form action="Controller" method="get">
                    <img src="img/mbS.jpg"/>
                    <input type="hidden" name="command" value="view-type">
                    <input type="hidden" name="carType" value="Premium"/>
                    <input type="submit" value="${premium}" class="ref2"/>
                </form>
            </div>
            <div class="div4">
                <form action="Controller" method="get">
                    <img src="img/p308cc.jpg"/>
                    <input type="hidden" name="command" value="view-type">
                    <input type="hidden" name="carType" value="Cabriolet"/>
                    <input type="submit" value="${cabriolet}" class="ref2"/>
                </form>
            </div>
            <div class="div4">
                <form action="Controller" method="get">
                    <img src="img/mbSprinter.jpg"/>
                    <input type="hidden" name="command" value="view-type">
                    <input type="hidden" name="carType" value="Cargo">
                    <input type="submit" value="${cargo}" class="ref2"/>
                </form>
            </div>
            <div class="div4">
                <form action="Controller" method="get">
                    <img src="img/g21.jpg"/>
                    <input type="hidden" name="command" value="view-type">
                    <input type="hidden" name="carType" value="Vintage"/>
                    <input type="submit" value="${vintage}" class="ref2"/>
                </form>
            </div>
            <br/>

            <form action="Controller" method="get">
                <input type="hidden" name="command" value="view-all-cars">
                <input type="submit" value="${seeAllBtn}" class="buttonSubMenu"/>
            </form>
        </div>
        </div>
    </article>

</section>

<footer>
    <p><cr:CopyRight/></p>
</footer>

</body>
</html>