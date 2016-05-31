<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Make order</title>
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
</header>
<section>
    <h2>Make order</h2>
    <c:if test="${requestScope.addOrderFailed == true}">
        <p>This car is used at this time.</p>

        <p>If you want to see witch cars are not busy at the moment of your trip,
            go to private office, and there you can choose cars by date</p>

        <form action="Controller" method="get">
            <input type="hidden" name="command" value="to-priv-office-user"/>
            <input type="submit" value="To private office"/>
        </form>
    </c:if>
    <c:if test="${requestScope.invalidDate == true}">
        <p>Invalid date</p>
    </c:if>
    <c:if test="${sessionScope.selectedCar.id != null}">
        <img class="imgSmall" src="data:image/jpg;base64,${sessionScope.selectedCar.image}"/>

        <p><c:out value="${sessionScope.selectedCar.mark}"/> <c:out value="${sessionScope.selectedCar.model}"/></p>
    </c:if>

    <form action="Controller" method="post">
        <c:if test="${requestScope.invalidPlaces == true}">
            <p>Invalid places</p>
        </c:if>
        <p>Supposed from date:</p>
        <input type="date" name="supposedDateFrom" value="${sessionScope.supposedDateFrom}" required/>
        <input type="time" name="supposedTimeFrom" value="${sessionScope.supposedTimeFrom}" required/>

        <p>Supposed to date:</p>
        <input type="date" name="supposedDateTo" value="${sessionScope.supposedDateTo}" required/>
        <input type="time" name="supposedTimeTo" value="${sessionScope.supposedTimeTo}" required/>

        <p>Shipping place:</p>
        <input type="text" name="shippingPlace" value="" maxlength="44"/>

        <p>Return place:</p>
        <input type="text" name="returnPlace" value="" maxlength="44"/>
        <input type="hidden" name="command" value="make-order">
        <input type="submit" value="Make order"/>
    </form>

</section>
<footer>
    <p>&copy; 2016 Car rental. All rights reserved.</p>
</footer>
</body>
</html>
