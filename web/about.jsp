<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
  <title>About</title>
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
  <fmt:message bundle="${locale}" key="local.messageInvLogin" var="messageInvLogin"/>
  <fmt:message bundle="${locale}" key="local.messageInvLogin" var="invLogin"/>
  <fmt:message bundle="${locale}" key="local.home" var="home"/>
  <fmt:message bundle="${locale}" key="local.cars" var="cars"/>
  <fmt:message bundle="${locale}" key="local.info" var="info"/>
  <fmt:message bundle="${locale}" key="local.logOut" var="logOut"/>
  <fmt:message bundle="${locale}" key="local.privateOffice" var="privateOffice"/>
  <fmt:message bundle="${locale}" key="local.orders" var="orders"/>
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
    <c:if test="${sessionScope.user != null}">
      <div class="div5">
        <form action="Controller" method="post">
          <input type="hidden" name="command" value="log-out-user">

          <div><input type="submit" value="${logOut}" class="buttonLogOut"></div>
        </form>
      </div>
    </c:if>
    <c:if test="${sessionScope.user == null}">
      <div class="div5">
        <form action="Controller" method="post">
          <div>${login} <input type="text" name="login" value="" maxlength="25"/></div>
          <div>${password} <input type="password" name="password" value="" maxlength="35"/></div>
          <div>
            <input type="hidden" name="command" value="login-user"/>
            <input type="hidden" name="page-name" value="index"/>
            <input type="submit" value="${signIn}" class="buttonSignIn"/>
          </div>
        </form>
        <p>
          <c:if test="${requestScope.loginFailed == true}">
            ${invLogin}
            <c:set var="loginFailed" scope="session" value="false"/>
          </c:if>
        </p>

        <form action="Controller" method="get">
          <input type="hidden" name="command" value="to-registration">

          <div><input type="submit" value="${registration}" class="buttonLocalReg"/></div>
        </form>
      </div>
    </c:if>
    <p>
      <c:if test="${sessionScope.user != null}">
        <c:out value="${sessionScope.user.lastName}"/> <c:out value="${sessionScope.user.firstName}"/>
      </c:if>
    </p>
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
    <c:if test="${sessionScope.user.type.equals('user')}">
      <div class="divMenu">
        <form action="Controller" method="get">
          <input type="hidden" name="command" value="to-priv-office-user">
          <input type="submit" value="${privateOffice}" class="buttonMenu"/>
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

</header>

<section>
  <h2>${info}</h2>

  <p>Some information about company.</p>
</section>
<footer>
  <p>&copy; 2016 Car rental. All rights reserved.</p>
</footer>
