<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="cr" uri="/WEB-INF/custom.tld" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add car</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="../../css/bootstrap.css" rel="stylesheet">
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
    <link href="../../css/car-rental-style.css" rel="stylesheet">
    <link href="../../font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
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
    <fmt:message bundle="${locale}" key="local.mFieldWithoutTags" var="mNoTags"/>
</head>
<body>

<%@include file="../navigation.jspf" %>

<div class="container">
    <c:if test="${sessionScope.user.type.equals('admin')}">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">
                    ${mAddCar}</h1>
            <ol class="breadcrumb">
                <li>
                    <form action="Controller" method="get" class="btn btn-link">
                        <input type="hidden" name="command" value="to-home-page">
                        <input type="submit" value="${home}" class="btn btn-link">
                    </form>
                </li>
                <li>
                    <form action="Controller" method="get" class="btn btn-link">
                        <input type="hidden" name="command" value="to-priv-office-admin">
                        <input type="submit" value="${privateOffice}" class="btn btn-link">
                    </form>
                </li>
                <li class="active">
                        ${mAddCar}
                </li>
            </ol>
        </div>

        <div class="well col-lg-12">
            <h4 class="text-primary">${messageReq}</h4>

            <hr/>

            <p class="text-danger">
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

            <form action="Controller" method="post">
                <input type="hidden" name="command" value="take-models">
                <label for="mark-id">${mMark}*: </label>
                <select name="carMark" onchange="this.form.submit()" class="btn btn-default" id="mark-id">
                    <option value=""></option>
                    <c:forEach var="mark" items="${allCarMarks}">
                        <option value="${mark}" ${mark.equals(sessionScope.carMark) ? 'selected' :''}>${mark}</option>
                    </c:forEach>
                    <input type="hidden" name="carMark" value="${carMark}"/>
                </select>
            </form>

            <br/>

            <form action="Controller" method="post" enctype="multipart/form-data">
                <c:if test="${carMark != null}">
                    <label for="mark-id">${mModel}*: </label>
                    <select name="carModel" class="btn btn-default">
                        <c:forEach var="model" items="${models}">
                            <option value="${model}">${model}</option>
                        </c:forEach>
                    </select>
                </c:if>

                <hr/>

                <div class="input-group">
                    <div class="col-xs-4">
                        <label for="year">&emsp; <abbr title="${messageCarYear}">${mYear}*: </abbr></label>
                        <input type="text" name="carYear" required placeholder="XXXX" maxlength="4"
                               pattern="[1-3][0-9]{3}"
                               title="${messageCarYear}" class="form-control" id="year"/>
                    </div>
                    <div class="col-xs-4">
                        <label for="number">&emsp; <abbr title="${messageGovNumber}">${mGovNumber}*: </abbr></label>
                        <input type="text" name="govNumber" required placeholder="xxxxXX-x" maxlength="8"
                               pattern="[0-9]{4}[A-CEHIKMOPTX]{2}-[0-7]" title="${messageGovNumber}"
                               class="form-control"
                               id="number"/>
                    </div>
                    <div class="col-xs-4">
                        <label for="vinCode">&emsp; <abbr title="${messageVinCode}">${mVinCode}*: </abbr></label>
                        <input type="text" name="vin" required placeholder="XXXXXXXXXXXXXXXXX" maxlength="17"
                               pattern="[0-9A-Z]{17}"
                               title="${messageVinCode}" class="form-control" id="vinCode"/>
                    </div>
                </div>

                <hr/>

                <div class="input-group">
                    <label for="transmission">&emsp; ${mTransmission}*: </label>
                    <select name="transmission" class="btn btn-default" id="transmission">
                        <option value="automatic">${mAutomatic}</option>
                        <option value="mechanic">${mMechanic}</option>
                    </select>

                    <label for="fuel">&emsp; ${mFuel}*: </label>
                    <select name="carFuel" class="btn btn-default" id="fuel">
                        <option value="petrol">${mPetrol}</option>
                        <option value="diesel">${mDiesel}</option>
                        <option value="electricity">${mElectricity}</option>
                    </select>

                    <label for="type">&emsp; ${mType}*: </label>
                    <select name="carType" class="btn btn-default" id="type">
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
                </div>

                <hr/>

                <label for="info"><abbr title="${mNoTags}">${mInformation}: </abbr></label> <br/>
                <textarea name="car-info" cols="45" rows="3" class="input-lg" id="info" maxlength="140"
                          pattern="[^<>]*" title="${mNoTags}"></textarea>

                <hr/>

                <label for="photo">${mImage}: </label>
                <input type="file" name="image" id="photo" class="btn btn-default"/>
                <hr/>
                <input type="hidden" name="command" value="add-car">
                <input type="hidden" name="processRequest" value="redirect">
                <input type="submit" value="${mAddCar}" class="btn btn-primary"/>
            </form>
        </div>
        </c:if>
    </div>
    <c:if test="${!sessionScope.user.type.equals('admin')}">
        <h1 class="page-header"></h1>

        <form action="Controller" method="get">
            <input type="hidden" name="command" value="to-home-page">
            <input type="submit" value="${home}" class="btn btn-info">
        </form>
    </c:if>

    <hr/>
    <%@include file="../footer.jspf" %>
</div>


<script src="../../js/jquery.js"></script>
<script src="../../js/bootstrap.min.js"></script>

</body>
</html>
