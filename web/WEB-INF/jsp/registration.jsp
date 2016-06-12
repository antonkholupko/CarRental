<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Registration</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/style.css">
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="localization.local" var="locale"/>
    <fmt:message bundle="${locale}" key="local.carRental" var="carRental"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="local.registration" var="registration"/>
    <fmt:message bundle="${locale}" key="local.messageReg" var="messageReg"/>
    <fmt:message bundle="${locale}" key="local.reqLogin" var="reqLogin"/>
    <fmt:message bundle="${locale}" key="local.reqPassword" var="reqPassword"/>
    <fmt:message bundle="${locale}" key="local.reqConfirmPassword" var="reqConfirmPassword"/>
    <fmt:message bundle="${locale}" key="local.reqLastName" var="reqLastName"/>
    <fmt:message bundle="${locale}" key="local.reqFirstName" var="reqFirstName"/>
    <fmt:message bundle="${locale}" key="local.reqMiddleName" var="reqMiddleName"/>
    <fmt:message bundle="${locale}" key="local.reqEMail" var="reqEMail"/>
    <fmt:message bundle="${locale}" key="local.reqPhone" var="reqPhone"/>
    <fmt:message bundle="${locale}" key="local.reqPassport" var="reqPassport"/>
    <fmt:message bundle="${locale}" key="local.address" var="address"/>
    <fmt:message bundle="${locale}" key="local.register" var="register"/>
    <fmt:message bundle="${locale}" key="local.notUniqueLogin" var="notUniqueLogin"/>
    <fmt:message bundle="${locale}" key="local.notUniqueEmail" var="notUniqueEmail"/>
    <fmt:message bundle="${locale}" key="local.notUniquePassport" var="notUniquePassport"/>
    <fmt:message bundle="${locale}" key="local.messageLogin" var="messageLogin"/>
    <fmt:message bundle="${locale}" key="local.messagePassword" var="messagePassword"/>
    <fmt:message bundle="${locale}" key="local.messageConfirmPassword" var="messageConfirmPassword"/>
    <fmt:message bundle="${locale}" key="local.messageEmail" var="messageEmail"/>
    <fmt:message bundle="${locale}" key="local.messageName" var="messageName"/>
    <fmt:message bundle="${locale}" key="local.messagePhone" var="messagePhone"/>
    <fmt:message bundle="${locale}" key="local.messagePassport" var="messagePassport"/>
    <fmt:message bundle="${locale}" key="local.messageAddress" var="messageAddress"/>
    <fmt:message bundle="${locale}" key="local.invalidLogin" var="invalidLogin"/>
    <fmt:message bundle="${locale}" key="local.invalidPassword" var="invalidPassword"/>
    <fmt:message bundle="${locale}" key="local.invalidEmail" var="invalidEmail"/>
    <fmt:message bundle="${locale}" key="local.invalidLastName" var="invalidLastName"/>
    <fmt:message bundle="${locale}" key="local.invalidFirstName" var="invalidFirstName"/>
    <fmt:message bundle="${locale}" key="local.invalidMiddleName" var="invalidMiddleName"/>
    <fmt:message bundle="${locale}" key="local.invalidPhone" var="invalidPhone"/>
    <fmt:message bundle="${locale}" key="local.invalidPassport" var="invalidPassport"/>
    <fmt:message bundle="${locale}" key="local.invalidAddress" var="invalidAddress"/>
    <fmt:message bundle="${locale}" key="local.messageConfirmationPassword" var="notConfirmedPassword"/>
    <fmt:message bundle="${locale}" key="local.home" var="home"/>
    <fmt:message bundle="${locale}" key="local.cars" var="cars"/>
    <fmt:message bundle="${locale}" key="local.info" var="info"/>
</head>
<body>

<header>
    <div class="div3">
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
    <div class="div3">
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
                <input type="hidden" name="command" value="to-about">
                <input type="submit" value="${info}" class="buttonMenu"/>
            </form>
        </div>
    </div>

</header>


<section>
    <h1>${registration}</h1>

    <p>${messageReg}</p>

    <p class="invalidMessage">
        <c:if test="${requestScope.uniqueLogin == false}">
            ${notUniqueLogin}
        </c:if>
        <c:if test="${requestScope.uniqueEmail == false}">
            ${notUniqueEmail}
        </c:if>
        <c:if test="${requestScope.uniquePassport == false}">
            ${notUniquePassport}
        </c:if>
    </p>

    <p class="invalidMessage">
        <c:if test="${requestScope.validLogin == false}">
            ${invalidLogin}
        </c:if>
        <c:if test="${requestScope.validPassword == false}">
            ${invalidPassword}
        </c:if>
        <c:if test="${requestScope.validEmail == false}">
            ${invalidEmail}
        </c:if>
        <c:if test="${requestScope.validLastName == false}">
            ${invalidLastName}
        </c:if>
        <c:if test="${requestScope.validFirstName == false}">
            ${invalidFirstName}
        </c:if>
        <c:if test="${requestScope.validMiddleName == false}">
            ${invalidMiddleName}
        </c:if>
        <c:if test="${requestScope.validPhone == false}">
            ${invalidPhone}
        </c:if>
        <c:if test="${requestScope.validPassport == false}">
            ${invalidPassport}
        </c:if>
    </p>

    <p class="invalidMessage">
        <c:if test="${requestScope.confirmationPassword == false}">
            ${notConfirmedPassword}
        </c:if>
    </p>

    <div align="center">
        <form action="Controller" method="post">
            <p>
                <abbr title="${messageLogin}">${reqLogin}</abbr>
            </p>
            <input type="text" name="login" value="" maxlength="25" required pattern="[A-Za-z0-9_]+" title="${messageLogin}"/>

            <p>
                <abbr title="${messagePassword}">${reqPassword}</abbr>
            </p>
            <input type="password" name="password" value="" maxlength="35" required pattern="\S+" title="${messagePassword}"/>

            <p>
                <abbr title="${messageConfirmPassword}">${reqConfirmPassword}</abbr>
            </p>
            <input type="password" name="confirm-password" value="" maxlength="35" required pattern="\S+" title="${messageConfirmPassword}"/>

            <p>
                <abbr title="${messageEmail}">${reqEMail}</abbr>
            </p>
            <input type="text" name="e-mail" value="" maxlength="60" required pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" title="${messageEmail}"/>

            <p>
                <abbr title="${messageName}"> ${reqLastName}</abbr>
            </p>
            <input type="text" name="last-name" value="" maxlength="25" required pattern="[A-Za-zА-Яа-я-]+" title="${messageName}"/>

            <p>
                <abbr title="${messageName}"> ${reqFirstName}</abbr>
            </p>
            <input type="text" name="first-name" value="" maxlength="25" required pattern="[A-Za-zА-Яа-я-]+" title="${messageName}"/>

            <p>
                <abbr title="${messageName}">${reqMiddleName}</abbr>
            </p>
            <input type="text" name="middle-name" value="" maxlength="25" required pattern="[A-Za-zА-Яа-я-]+" title="${messageName}"/>

            <p>
                <abbr title="${messagePhone}">${reqPhone}</abbr>
            </p>
            <input type="text" name="phone" value="" maxlength="30" required pattern="[0-9-+\s()]+" title="${messagePhone}"/>

            <p>
                <abbr title="${messagePassport}">${reqPassport}</abbr>
            </p>
            <input type="text" name="passport" value="" maxlength="15" required pattern="[A-Z0-9]+" title="${messagePassport}"/>

            <p>
                <abbr title="${messageAddress}">${address}</abbr>
            </p>
            <input type="text" name="address" value="" maxlength="70"/> <br/>
            <br/>
            <input type="hidden" name="command" value="register">
            <input type="hidden" name="processRequest" value="redirect">
            <input type="submit" value="${register}" class="buttonSubMenu"/>
        </form>
    </div>
</section>
<footer>
    <p>&copy; 2016 Car rental. All rights reserved.</p>
</footer>

</body>
</html>
