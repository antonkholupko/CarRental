<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>
<head>
    <title>Add car</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/style.css">
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="localization.local" var="locale"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="local.carRental" var="carRental"/>
    <fmt:message bundle="${locale}" key="local.privateOfficeUser" var="privateOffice"/>
    <fmt:message bundle="${locale}" key="local.welcomeToOffice" var="welcomeToOffice"/>
    <fmt:message bundle="${locale}" key="local.logOut" var="logOut"/>
    <fmt:message bundle="${locale}" key="local.toPrivOffice" var="mToPrivOffice"/>
    <fmt:message bundle="${locale}" key="local.home" var="home"/>
    <fmt:message bundle="${locale}" key="local.cars" var="cars"/>
    <fmt:message bundle="${locale}" key="local.info" var="info"/>
    <fmt:message bundle="${locale}" key="local.privateOffice" var="privateOffice"/>
    <fmt:message bundle="${locale}" key="local.addCar" var="mAddCar"/>
    <fmt:message bundle="${locale}" key="local.chooseMark" var="mChooseMark"/>
    <fmt:message bundle="${locale}" key="local.chooseModel" var="mChooseModel"/>
    <fmt:message bundle="${locale}" key="local.invalidYear" var="mInvalidYear"/>
    <fmt:message bundle="${locale}" key="local.invalidVinCode" var="mInvalidVinCode"/>
    <fmt:message bundle="${locale}" key="local.invalidNumber" var="mInvalidNumber"/>
    <fmt:message bundle="${locale}" key="local.notUniqueVinNumber" var="mNotUniqueVinNumber"/>
    <fmt:message bundle="${locale}" key="local.mark" var="mMark"/>
    <fmt:message bundle="${locale}" key="local.model" var="mModel"/>
    <fmt:message bundle="${locale}" key="local.year" var="mYear"/>
    <fmt:message bundle="${locale}" key="local.transmission" var="mTransmission"/>
    <fmt:message bundle="${locale}" key="local.fuel" var="mFuel"/>
    <fmt:message bundle="${locale}" key="local.type" var="mType"/>
    <fmt:message bundle="${locale}" key="local.mGovNumber" var="mGovNumber"/>
    <fmt:message bundle="${locale}" key="local.vinCode" var="mVinCode"/>
    <fmt:message bundle="${locale}" key="local.carInformation" var="mInformation"/>
    <fmt:message bundle="${locale}" key="local.mImage" var="mImage"/>
    <fmt:message bundle="${locale}" key="local.automaticTransmission" var="mAutomatic"/>
    <fmt:message bundle="${locale}" key="local.mechanicTransmission" var="mMechanic"/>
    <fmt:message bundle="${locale}" key="local.petrol" var="mPetrol"/>
    <fmt:message bundle="${locale}" key="local.diesel" var="mDiesel"/>
    <fmt:message bundle="${locale}" key="local.electricity" var="mElectricity"/>
    <fmt:message bundle="${locale}" key="local.cabriolet" var="cabriolet"/>
    <fmt:message bundle="${locale}" key="local.cargo" var="cargo"/>
    <fmt:message bundle="${locale}" key="local.coupe" var="coupe"/>
    <fmt:message bundle="${locale}" key="local.jeep" var="jeep"/>
    <fmt:message bundle="${locale}" key="local.smallClass" var="small"/>
    <fmt:message bundle="${locale}" key="local.middleClass" var="middle"/>
    <fmt:message bundle="${locale}" key="local.minibus" var="minibus"/>
    <fmt:message bundle="${locale}" key="local.premium" var="premium"/>
    <fmt:message bundle="${locale}" key="local.vintage" var="vintage"/>
    <fmt:message bundle="${locale}" key="local.messageReg" var="messageReq"/>
    <fmt:message bundle="${locale}" key="local.messageCarYear" var="messageCarYear"/>
    <fmt:message bundle="${locale}" key="local.messageGovNumber" var="messageGovNumber"/>
    <fmt:message bundle="${locale}" key="local.messageVinCode" var="messageVinCode"/>
    <fmt:message bundle="${locale}" key="local.mAddCar" var="mAddCar"/>
    <fmt:message bundle="${locale}" key="local.viewOrders" var="viewOrders"/>
    <fmt:message bundle="${locale}" key="local.viewUsers" var="viewUsers"/>
    <fmt:message bundle="${locale}" key="local.addCar" var="addCar"/>
    <fmt:message bundle="${locale}" key="local.mInvalidCarInfo" var="mInvalidCarInfo"/>
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
        <h2>${mAddCar}</h2>

        <hr/>

        <div class="divSubMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="command" value="to-priv-office-admin">
                <input type="submit" value="${mToPrivOffice}" class="buttonSubMenu">
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

        <p>${messageReq}</p>

        <p class="invalidMessage">
            <c:if test="${requestScope.invalidMark == true}">
                ${mChooseMark}
            </c:if>
            <c:if test="${requestScope.invalidModel == true}">
                ${mChooseModel}
            </c:if>
            <c:if test="${requestScope.invalidYear == true}">
                ${mInvalidNumber}
            </c:if>
            <c:if test="${requestScope.invalidGovNumber == true}">
                ${mInvalidNumber}
            </c:if>
            <c:if test="${requestScope.invalidVinCode == true}">
                ${mInvalidVinCode}
            </c:if>
            <c:if test="${requestScope.invalidNumberVin == true}">
                ${mNotUniqueVinNumber}
            </c:if>
            <c:if test="${requestScope.invalidCarInfo == true}">
                ${mInvalidCarInfo}
            </c:if>
        </p>

        <p>${mMark}*: </p>

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
                <p>${mModel}*: </p>
                <select name="carModel">
                    <c:forEach var="model" items="${models}">
                        <option value="${model}">${model}</option>
                    </c:forEach>
                </select>
            </c:if>


            <p>
                <abbr title="${messageCarYear}">${mYear}*: </abbr>
            </p>
            <input type="text" name="carYear" required placeholder="XXXX" maxlength="4" pattern="[1-3][0-9]{3}"
                   title="${messageCarYear}"/>

            <p>${mTransmission}*: </p>
            <select name="transmission">
                <option value="automatic">${mAutomatic}</option>
                <option value="mechanic">${mMechanic}</option>
            </select>

            <p>${mFuel}: </p>
            <select name="carFuel">
                <option value="petrol">${mPetrol}</option>
                <option value="diesel">${mDiesel}</option>
                <option value="electricity">${mElectricity}</option>
            </select>

            <p>${mType}*: </p>
            <select name="carType">
                <c:forEach var="type" items="${allCarTypes}">
                    <option value="${type.type}">
                        <c:if test="${type.type.equals('Cabriolet')}">
                            ${cabriolet}
                        </c:if>
                        <c:if test="${type.type.equals('Cargo')}">
                            ${cargo}
                        </c:if>
                        <c:if test="${type.type.equals('Coupe')}">
                            ${coupe}
                        </c:if>
                        <c:if test="${type.type.equals('Jeep')}">
                            ${jeep}
                        </c:if>
                        <c:if test="${type.type.equals('Small')}">
                            ${small}
                        </c:if>
                        <c:if test="${type.type.equals('Middle')}">
                            ${middle}
                        </c:if>
                        <c:if test="${type.type.equals('Minibus')}">
                            ${minibus}
                        </c:if>
                        <c:if test="${type.type.equals('Premium')}">
                            ${premium}
                        </c:if>
                        <c:if test="${type.type.equals('Vintage')}">
                            ${vintage}
                        </c:if>
                    </option>
                </c:forEach>
            </select>

            <p>
                <abbr title="${messageGovNumber}">${mGovNumber}*: </abbr>
            </p>
            <input type="text" name="govNumber" required placeholder="xxxxXX-x" maxlength="8"
                   pattern="[0-9]{4}[A-CEHIKMOPTX]{2}-[0-7]" title="${messageGovNumber}"/>

            <p>
                <abbr title="${messageVinCode}">${mVinCode}*: </abbr>
            </p>
            <input type="text" name="vin" required placeholder="XXXXXXXXXXXXXXXXX" maxlength="17" pattern="[0-9A-Z]{17}"
                   title="${messageVinCode}"/>

            <p>${mInformation}: </p>
            <textarea name="car-info" cols="40" rows="3"></textarea>

            <p>${mImage}: </p>
            <input type="file" name="image"/>
            <br/>
            <hr/>
            <br/>
            <input type="hidden" name="command" value="add-car">
            <input type="hidden" name="processRequest" value="redirect">
            <input type="submit" value="${mAddCar}" class="buttonPurchase"/>
        </form>
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
