<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>ERROR 500</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="../../css/style.css">
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="localization.local" var="locale"/>
    <fmt:message bundle="${locale}" key="local.mERROR500" var="mError"/>
    <fmt:message bundle="${locale}" key="local.mErrorMessage500" var="mErrorMessage"/>
    <fmt:message bundle="${locale}" key="local.carRental" var="carRental"/>
</head>
<body>
<header>
    <div class="divHeader">
        <div class="div3">
            <div class="div1"><h1>${carRental}</h1></div>
        </div>
    </div>
</header>
<section>
    <h1>${mError}</h1>
    <hr/>
    <h2>${mErrorMessage}</h2>
</section>
<footer>
    <p>&copy; 2016 Car rental. All rights reserved.</p>
</footer>
</body>
</html>
