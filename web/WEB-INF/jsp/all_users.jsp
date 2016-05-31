<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Users</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="../../css/style.css">
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="localization.local" var="locale"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="local.carRental" var="carRental"/>
    <fmt:message bundle="${locale}" key="local.welcomePrivateOfficeAdmin" var="welcomeAdmin"/>
    <fmt:message bundle="${locale}" key="local.adminPrivateOffice" var="adminPrivateOffice"/>
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
    <h2>Users</h2>
    <article>
        <c:forEach var="user" items="${allUsers}">
            <div class="divOrders">
                <table border="1" width="100%">
                    <thead>
                    <tr>
                        <th>User ID</th>
                        <th>Login</th>
                        <th>Type</th>
                        <th>Last Name</th>
                        <th>First Name</th>
                        <th>e-mail</th>
                        <th>Phone</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td><c:out value="${user.id}"/></td>
                        <td><c:out value="${user.login}"/></td>
                        <td><c:out value="${user.type}"/></td>
                        <td><c:out value="${user.lastName}"/></td>
                        <td><c:out value="${user.firstName}"/></td>
                        <td><c:out value="${user.EMail}"/></td>
                        <td><c:out value="${user.phone}"/></td>
                    </tr>
                    </tbody>
                </table>
                <form action="Controller" method="get">
                    <input type="hidden" name="selectedUserId" value="${user.id}">
                    <input type="hidden" name="command" value="view-user">
                    <input type="submit" value="Details" class="button2"/>
                </form>
            </div>
        </c:forEach>
    </article>
</section>
<footer>
    <p>&copy; 2016 Car rental. All rights reserved.</p>
</footer>
</body>
</html>

