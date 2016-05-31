<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>
<head>
    <title>Add car</title>
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

    <h2>Add car</h2>
    <hr/>

    <p>
        <c:if test="${requestScope.invalidMark == true}">
            Choose mark.
        </c:if>
        <c:if test="${requestScope.invalidModel == true}">
            Choose model.
        </c:if>
        <c:if test="${requestScope.invalidYear == true}">
            Invalid year.
        </c:if>
        <c:if test="${requestScope.invalidGovNumber == true}">
            Invalid number.
        </c:if>
        <c:if test="${requestScope.invalidVinCode == true}">
            Invalid vin code.
        </c:if>
        <c:if test="${requestScope.invalidNumberVin == true}">
            Not unique number or vin code.
        </c:if>
    </p>

    <p>Mark: </p>

    <form action="Controller" method="post">
        <input type="hidden" name="command" value="take-models">
        <select name="carMark" onchange="this.form.submit()">
            <option value=""></option>
            <c:forEach var="mark" items="${allCarMarks}">
                <option value="${mark}" ${mark.equals(sessionScope.carMark) ? 'selected' :''}>${mark}</option>
            </c:forEach>
            <input type="hidden" name="carMark" value="${carMark}"/>
        </select>
    </form>

    <form action="Controller" method="post" enctype="multipart/form-data">
        <c:if test="${carMark != null}">
            <p>Model: </p>
            <select name="carModel">
                <c:forEach var="model" items="${models}">
                    <option value="${model}">${model}</option>
                </c:forEach>
            </select>
        </c:if>


        <p>Year: </p>
        <input type="text" name="carYear" required maxlength="4" pattern="[1-3][0-9]{3}"/>

        <p>Transmission: </p>
        <select name="transmission">
            <option value="АКПП">Automatic</option>
            <option value="МКПП">Mechanic</option>
        </select>

        <p>Fuel: </p>
        <select name="carFuel">
            <option value="бензин">Petrol</option>
            <option value="дизель">Diesel</option>
            <option value="электричество">Electricity</option>
        </select>

        <p>Type: </p>
        <select name="carType">
            <c:forEach var="type" items="${allCarTypes}">
                <option value="${type.type}">${type.type}</option>
            </c:forEach>
        </select>

        <p>Government: </p>
        <input type="text" name="govNumber" required maxlength="8" pattern="[0-9]{4}[A-CEHIKMOPTX]{2}-[0-7]"/>

        <p>Vin: </p>
        <input type="text" name="vin" required maxlength="17" pattern="[0-9A-Z]{17}"/>

        <p>Info: </p>
        <textarea name="car-info" cols="40" rows="3"></textarea>

        <p>Image: </p>
        <input type="file" name="image"/>
        <br/>
        <input type="hidden" name="command" value="add-car">
        <input type="submit" value="Add car"/>
    </form>
</section>
<footer>
    <p>&copy; 2016 Car rental. All rights reserved.</p>
</footer>
</body>
</html>
