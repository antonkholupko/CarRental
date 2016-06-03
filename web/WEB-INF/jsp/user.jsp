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
    <fmt:message bundle="${locale}" key="local.userNumber" var="nUser" />
    <fmt:message bundle="${locale}" key="local.mLogin" var="mLogin" />
    <fmt:message bundle="${locale}" key="local.mType" var="mType" />
    <fmt:message bundle="${locale}" key="local.mLastName" var="mLastName" />
    <fmt:message bundle="${locale}" key="local.mFirstName" var="mFirstName" />
    <fmt:message bundle="${locale}" key="local.mMiddleName" var="mMiddleName" />
    <fmt:message bundle="${locale}" key="local.mPhone" var="mPhone" />
    <fmt:message bundle="${locale}" key="local.mPassport" var="mPassport" />
    <fmt:message bundle="${locale}" key="local.mAddress" var="mAddress" />
    <fmt:message bundle="${locale}" key="local.mUserType" var="mUserType" />
    <fmt:message bundle="${locale}" key="local.mAdminType" var="mAdminType" />
    <fmt:message bundle="${locale}" key="local.toAllUsers" var="toAllUsers" />
    <fmt:message bundle="${locale}" key="local.mUser" var="mUser" />
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
    <h2>${mUser}</h2>

    <hr/>

    <div class="divMenu">
        <form action="Controller" method="get">
            <input type="hidden" name="command" value="view-all-users">
            <input type="submit" value="${toAllUsers}">
        </form>
    </div>

    <hr/>

    <p>
        ${nUser}: <c:out value="${requestScope.selectedUser.id}"/>
    </p>
    <p>
        ${mLogin}: <c:out value="${requestScope.selectedUser.login}"/>
    </p>
    <p>
        ${mType}:
        <c:if test="${requestScope.selectedUser.type.equals('user')}">
            ${mUserType}
        </c:if>
        <c:if test="${requestScope.selectedUser.type.equals('admin')}">
            ${mAdminType}
        </c:if>
    </p>
    <p>
        ${mLastName}: <c:out value="${requestScope.selectedUser.lastName}"/>
    </p>
    <p>
        ${mFirstName}: <c:out value="${requestScope.selectedUser.firstName}"/>
    </p>
    <p>
        ${mMiddleName}: <c:out value="${requestScope.selectedUser.middleName}"/>
    </p>
    <p>
        e-mail: <c:out value="${requestScope.selectedUser.EMail}"/>
    </p>
    <p>
        ${mPhone}: <c:out value="${requestScope.selectedUser.phone}"/>
    </p>
    <p>
        ${mPassport}: <c:out value="${requestScope.selectedUser.passport}"/>
    </p>
    <p>
        ${mAddress}: <c:out value="${requestScope.selectedUser.address}"/>
    </p>
</section>
<footer>
    <p>&copy; 2016 Car rental. All rights reserved.</p>
</footer>
</body>
</html>
