<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Users</title>
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
    <fmt:message bundle="${locale}" key="local.mUsers" var="mUsers"/>
    <fmt:message bundle="${locale}" key="local.userNumber" var="nUser"/>
    <fmt:message bundle="${locale}" key="local.mLogin" var="mLogin"/>
    <fmt:message bundle="${locale}" key="local.mType" var="mType"/>
    <fmt:message bundle="${locale}" key="local.mLastName" var="mLastName"/>
    <fmt:message bundle="${locale}" key="local.mFirstName" var="mFirstName"/>
    <fmt:message bundle="${locale}" key="local.mPhone" var="mPhone"/>
    <fmt:message bundle="${locale}" key="local.mUserType" var="mUserType"/>
    <fmt:message bundle="${locale}" key="local.mAdminType" var="mAdminType"/>
    <fmt:message bundle="${locale}" key="local.mDetails" var="mDetails"/>
    <fmt:message bundle="${locale}" key="local.toPrivOffice" var="toPrivOffice"/>
    <fmt:message bundle="${locale}" key="local.mPage" var="mPage"/>
    <fmt:message bundle="${locale}" key="local.mNoUsers" var="mNoUsers"/>
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
        <h2>${mUsers}</h2>

        <hr/>

        <div class="divSubMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="command" value="to-priv-office-admin">
                <input type="submit" value="${toPrivOffice}" class="buttonSubMenu">
            </form>
        </div>
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

        <c:if test="${requestScope.noOrders == true }">
            <p>${mNoUsers}</p>
        </c:if>

        <c:forEach var="user" items="${allUsers}">
            <article>
                <div class="divOrders">
                    <table border="1" width="100%">
                        <thead>
                        <tr>
                            <th>${nUser}</th>
                            <th>${mLogin}</th>
                            <th>${mType}</th>
                            <th>${mLastName}</th>
                            <th>${mFirstName}</th>
                            <th>e-mail</th>
                            <th>${mPhone}</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td><c:out value="${user.id}"/></td>
                            <td><c:out value="${user.login}"/></td>
                            <td>
                                <c:if test="${user.type.equals('user')}">
                                    ${mUserType}
                                </c:if>
                                <c:if test="${user.type.equals('admin')}">
                                    ${mAdminType}
                                </c:if>
                            </td>
                            <td><c:out value="${user.lastName}"/></td>
                            <td><c:out value="${user.firstName}"/></td>
                            <td><c:out value="${user.EMail}"/></td>
                            <td><c:out value="${user.phone}"/></td>
                        </tr>
                        </tbody>
                    </table>
                    <br/>

                    <div class="divSubMenu">
                        <form action="Controller" method="get">
                            <input type="hidden" name="selectedUserId" value="${user.id}">
                            <input type="hidden" name="command" value="view-user">
                            <input type="submit" value="${mDetails}" class="buttonSubMenu"/>
                        </form>
                    </div>
                </div>
            </article>
            <br/>
        </c:forEach>

        <p>
            <c:out value="${mPage}: ${requestScope.pageNumber}"/>
        </p>

        <br/>
        <c:forEach var="i" begin="1" end="${amountPages}">
            <div class="divSubMenu">
                <form action="Controller" method="get">
                    <input type="hidden" name="command" value="view-all-users">
                    <input type="hidden" name="pageNumber" value="${i}"/>
                    <input type="submit" value="${i}" class="button2"/>
                </form>
            </div>
        </c:forEach>
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

