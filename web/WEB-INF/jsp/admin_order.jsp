<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="cr" uri="/WEB-INF/custom.tld" %>

<!DOCTYPE html>
<html>
<head>
    <title>Orders</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
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
    <fmt:message bundle="${locale}" key="local.home" var="home"/>
    <fmt:message bundle="${locale}" key="local.cars" var="cars"/>
    <fmt:message bundle="${locale}" key="local.info" var="info"/>
    <fmt:message bundle="${locale}" key="local.privateOffice" var="privateOffice"/>
    <fmt:message bundle="${locale}" key="local.order" var="mOrder"/>
    <fmt:message bundle="${locale}" key="local.mOrderNumber" var="mOrderNumber"/>
    <fmt:message bundle="${locale}" key="local.mStatus" var="mStatus"/>
    <fmt:message bundle="${locale}" key="local.reason" var="reason"/>
    <fmt:message bundle="${locale}" key="local.invalidReason" var="invalidReason"/>
    <fmt:message bundle="${locale}" key="local.selectRealFrom" var="selectFrom"/>
    <fmt:message bundle="${locale}" key="local.selectRealTo" var="selectTo"/>
    <fmt:message bundle="${locale}" key="local.statusNew" var="sNew"/>
    <fmt:message bundle="${locale}" key="local.statusCanceled" var="sCanceld"/>
    <fmt:message bundle="${locale}" key="local.statusRejected" var="sRejected"/>
    <fmt:message bundle="${locale}" key="local.statusAccepted" var="Accepted"/>
    <fmt:message bundle="${locale}" key="local.statusPaid" var="Paid"/>
    <fmt:message bundle="${locale}" key="local.statusDelivered" var="Delivered"/>
    <fmt:message bundle="${locale}" key="local.statusReturned" var="Returned"/>
    <fmt:message bundle="${locale}" key="local.statusExpectsComp" var="ExpectsComp"/>
    <fmt:message bundle="${locale}" key="local.statusClosed" var="Closed"/>
    <fmt:message bundle="${locale}" key="local.changeStatus" var="changeStatus"/>
    <fmt:message bundle="${locale}" key="local.supposedFromDate" var="mSupposedFromDate"/>
    <fmt:message bundle="${locale}" key="local.supposedToDate" var="mSupposedToDate"/>
    <fmt:message bundle="${locale}" key="local.shippingPlaceMessage" var="mShippingPlace"/>
    <fmt:message bundle="${locale}" key="local.returnPlaceMessage" var="mReturnPlace"/>
    <fmt:message bundle="${locale}" key="local.realDateFrom" var="mRealDateFrom"/>
    <fmt:message bundle="${locale}" key="local.realDateTo" var="mRealDateTo"/>
    <fmt:message bundle="${locale}" key="local.orderPrice" var="mOrderPrice"/>
    <fmt:message bundle="${locale}" key="local.mInvalidDate" var="mInvalidDate"/>
    <fmt:message bundle="${locale}" key="local.setDate" var="mSetDate"/>
    <fmt:message bundle="${locale}" key="local.mDamagePrice" var="mDmgPrice"/>
    <fmt:message bundle="${locale}" key="local.invalidDmgPrice" var="invalidDmgPrice"/>
    <fmt:message bundle="${locale}" key="local.setDmgPrice" var="setDmgPrice"/>
    <fmt:message bundle="${locale}" key="local.carInformation" var="information"/>
    <fmt:message bundle="${locale}" key="local.mUser" var="mUser"/>
    <fmt:message bundle="${locale}" key="local.mLogin" var="mLogin"/>
    <fmt:message bundle="${locale}" key="local.mLastName" var="mLastName"/>
    <fmt:message bundle="${locale}" key="local.mFirstName" var="mFirstName"/>
    <fmt:message bundle="${locale}" key="local.mMiddleName" var="mMiddleName"/>
    <fmt:message bundle="${locale}" key="local.mPhone" var="mPhone"/>
    <fmt:message bundle="${locale}" key="local.mPassport" var="mPassport"/>
    <fmt:message bundle="${locale}" key="local.mAddress" var="mAddress"/>
    <fmt:message bundle="${locale}" key="local.mark" var="mMark"/>
    <fmt:message bundle="${locale}" key="local.model" var="mModel"/>
    <fmt:message bundle="${locale}" key="local.mGovNumber" var="mGovNumber"/>
    <fmt:message bundle="${locale}" key="local.year" var="mYear"/>
    <fmt:message bundle="${locale}" key="local.type" var="mType"/>
    <fmt:message bundle="${locale}" key="local.fuel" var="mFuel"/>
    <fmt:message bundle="${locale}" key="local.mCar" var="mCar"/>
    <fmt:message bundle="${locale}" key="local.transmission" var="mTransmission"/>
    <fmt:message bundle="${locale}" key="local.automaticTransmission" var="mAutomatic"/>
    <fmt:message bundle="${locale}" key="local.mechanicTransmission" var="mMechamic"/>
    <fmt:message bundle="${locale}" key="local.cabriolet" var="cabriolet"/>
    <fmt:message bundle="${locale}" key="local.cargo" var="cargo"/>
    <fmt:message bundle="${locale}" key="local.coupe" var="coupe"/>
    <fmt:message bundle="${locale}" key="local.jeep" var="jeep"/>
    <fmt:message bundle="${locale}" key="local.smallClass" var="small"/>
    <fmt:message bundle="${locale}" key="local.middleClass" var="middle"/>
    <fmt:message bundle="${locale}" key="local.minibus" var="minibus"/>
    <fmt:message bundle="${locale}" key="local.premium" var="premium"/>
    <fmt:message bundle="${locale}" key="local.vintage" var="vintage"/>
    <fmt:message bundle="${locale}" key="local.petrol" var="petrol"/>
    <fmt:message bundle="${locale}" key="local.diesel" var="diesel"/>
    <fmt:message bundle="${locale}" key="local.electricity" var="electricity"/>
    <fmt:message bundle="${locale}" key="local.mToAllOrders" var="mToAllOrders"/>
    <fmt:message bundle="${locale}" key="local.mUpdate" var="mUpdate"/>
    <fmt:message bundle="${locale}" key="local.viewOrders" var="viewOrders"/>
    <fmt:message bundle="${locale}" key="local.viewUsers" var="viewUsers"/>
    <fmt:message bundle="${locale}" key="local.addCar" var="addCar"/>
    <fmt:message bundle="${locale}" key="local.mAllOrders" var="mAllOrders"/>
    <fmt:message bundle="${locale}" key="local.mSetDateFrom" var="mSetDateFrom"/>
    <fmt:message bundle="${locale}" key="local.mSetDateTo" var="mSetDateTo"/>
    <fmt:message bundle="${locale}" key="local.mFieldWithoutTags" var="mNoTags"/>
    <fmt:message bundle="${locale}" key="local.dmgPriceField" var="mDmgPriceField"/>
</head>
<body>

<%@include file="../navigation.jspf" %>

<div class="container">

    <c:if test="${sessionScope.user.type.equals('admin')}">
        <div class="col-lg-12">
            <h1 class="page-header">${mOrder}</h1>
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
                <li>
                    <form action="Controller" method="get" class="btn btn-link">
                        <input type="hidden" name="command" value="view-orders-admin">
                        <input type="submit" value="${mAllOrders}" class="btn btn-link">
                    </form>
                </li>
                <li class="active">${mOrder}</li>
            </ol>
        </div>


        <h2>${mOrderNumber} <c:out value="${requestScope.selectedOrder.id}"/></h2>

        <div class="well col-lg-12">
            <div class="btn-group">
                <form action="Controller" method="get" class="btn">
                    <input type="hidden" name="command" value="view-order-admin">
                    <input type="hidden" name="selectedOrderId" value="${requestScope.selectedOrderId}">
                    <input type="submit" value="${mUpdate}" class="btn btn-default">
                </form>
            </div>

            <ul class="nav navbar-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">${mUser}<span
                            class="caret"></span></a>
                    <ul class="dropdown-menu order-cr-us-info">
                        <li>
                            <div>

                                <p>${mLogin}: <c:out value="${requestScope.selectedOrder.user.login}"/></p>

                                <p>${mLastName}: <c:out value="${requestScope.selectedOrder.user.lastName}"/></p>

                                <p>${mFirstName}: <c:out value="${requestScope.selectedOrder.user.firstName}"/></p>

                                <p>${mMiddleName}: <c:out
                                        value="${requestScope.selectedOrder.user.middleName}"/></p>

                                <p>e-mail: <c:out value="${requestScope.selectedOrder.user.EMail}"/></p>

                                <p>${mPhone}: <c:out value="${requestScope.selectedOrder.user.phone}"/></p>

                                <p>${mPassport}: <c:out value="${requestScope.selectedOrder.user.passport}"/></p>

                                <p>${mAddress}: <c:out value="${requestScope.selectedOrder.user.address}"/></p>
                            </div>
                        </li>
                    </ul>
                </li>

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">${mCar}<span
                            class="caret"></span></a>
                    <ul class="dropdown-menu order-cr-us-info">
                        <li>
                            <div>
                                <img class="img-responsive car-small-img"
                                     src="data:image/jpg;base64,${requestScope.selectedOrder.car.image}"/>

                                <p>${mMark}: <c:out value="${requestScope.selectedOrder.car.mark}"/></p>

                                <p>${mModel}: <c:out value="${requestScope.selectedOrder.car.model}"/></p>

                                <p>${mGovNumber}: <c:out value="${requestScope.selectedOrder.car.govNumber}"/></p>

                                <p>${mYear}: <c:out value="${requestScope.selectedOrder.car.year}"/></p>

                                <p>
                                        ${mTransmission}:
                                    <c:if test="${requestScope.selectedOrder.car.transmission.equals('automatic')}">
                                        ${mAutomatic}
                                    </c:if>
                                    <c:if test="${requestScope.selectedOrder.car.transmission.equals('mechanic')}">
                                        ${mMechamic}
                                    </c:if>
                                </p>

                                <p>
                                        ${mType}:
                                    <c:if test="${requestScope.selectedOrder.car.type.equals('Cabriolet')}">
                                        ${cabriolet}
                                    </c:if>
                                    <c:if test="${requestScope.selectedOrder.car.type.equals('Cargo')}">
                                        ${cargo}
                                    </c:if>
                                    <c:if test="${requestScope.selectedOrder.car.type.equals('Coupe')}">
                                        ${coupe}
                                    </c:if>
                                    <c:if test="${requestScope.selectedOrder.car.type.equals('Jeep')}">
                                        ${jeep}
                                    </c:if>
                                    <c:if test="${requestScope.selectedOrder.car.type.equals('Small')}">
                                        ${small}
                                    </c:if>
                                    <c:if test="${requestScope.selectedOrder.car.type.equals('Middle')}">
                                        ${middle}
                                    </c:if>
                                    <c:if test="${requestScope.selectedOrder.car.type.equals('Minibus')}">
                                        ${minibus}
                                    </c:if>
                                    <c:if test="${requestScope.selectedOrder.car.type.equals('Premium')}">
                                        ${premium}
                                    </c:if>
                                    <c:if test="${requestScope.selectedOrder.car.type.equals('Vintage')}">
                                        ${vintage}
                                    </c:if>
                                </p>

                                <p>
                                        ${mFuel}:
                                    <c:if test="${requestScope.selectedOrder.car.fuel.equals('petrol')}">
                                        ${petrol}
                                    </c:if>
                                    <c:if test="${requestScope.selectedOrder.car.fuel.equals('diesel')}">
                                        ${diesel}
                                    </c:if>
                                    <c:if test="${requestScope.selectedOrder.car.fuel.equals('electricity')}">
                                        ${electricity}
                                    </c:if>
                                </p>
                            </div>
                        </li>
                    </ul>

                </li>

            </ul>

            <br/>
            <hr/>
            <c:if test="${requestScope.selectedOrder.status.equals('delivered') &&
                                    requestScope.selectedOrder.realDateFrom == null}">
                <h4 class="text-primary"><c:out value="${selectFrom}"/></h4>
            </c:if>
            <c:if test="${requestScope.selectedOrder.status.equals('returned') &&
                                requestScope.selectedOrder.realDateTo == null}">
                <h4 class="text-primary"><c:out value="${selectTo}"/></h4>
            </c:if>
            <c:if test="${requestScope.invalidInfo == true}">
                <h4 class="text-danger my-info">${invalidReason}</h4>
            </c:if>
            <p> ${mStatus}:
                <c:if test="${requestScope.selectedOrder.status.equals('new')}">
                    ${sNew}
                </c:if>
                <c:if test="${requestScope.selectedOrder.status.equals('canceled')}">
                    ${sCanceld}
                </c:if>
                <c:if test="${requestScope.selectedOrder.status.equals('rejected')}">
                    ${sRejected}
                </c:if>
                <c:if test="${requestScope.selectedOrder.status.equals('accepted')}">
                    ${Accepted}
                </c:if>
                <c:if test="${requestScope.selectedOrder.status.equals('payed')}">
                    ${Paid}
                </c:if>
                <c:if test="${requestScope.selectedOrder.status.equals('delivered')}">
                    ${Delivered}
                </c:if>
                <c:if test="${requestScope.selectedOrder.status.equals('returned')}">
                    ${Returned}
                </c:if>
                <c:if test="${requestScope.selectedOrder.status.equals('expectsComp')}">
                    ${ExpectsComp}
                </c:if>
                <c:if test="${requestScope.selectedOrder.status.equals('closed')}">
                    ${Closed}
                </c:if>
            </p>

            <br/>

            <c:if test="${!requestScope.selectedOrder.status.equals('rejected') &&
                        !requestScope.selectedOrder.status.equals('canceled') &&
                        !requestScope.selectedOrder.status.equals('closed') &&
                        !requestScope.selectedOrder.status.equals('accepted') &&
                        !requestScope.selectedOrder.status.equals('expectsComp')}">

                <form action="Controller" method="post" class="col-lg-4">
                    <select name="statusOrder" class="form-control">
                        <c:if test="${requestScope.selectedOrder.status.equals('new')}">
                            <option value="accepted">${Accepted}</option>
                            <option value="rejected">${sRejected}</option>
                        </c:if>
                        <c:if test="${requestScope.selectedOrder.status.equals('payed')}">
                            <option value="delivered">${Delivered}</option>
                        </c:if>
                        <c:if test="${requestScope.selectedOrder.status.equals('delivered') &&
                                    requestScope.selectedOrder.realDateFrom != null}">
                            <option value="returned">${Returned}</option>
                        </c:if>
                        <c:if test="${requestScope.selectedOrder.status.equals('returned') &&
                                    requestScope.selectedOrder.realDateTo != null}">
                            <option value="closed">${Closed}</option>
                        </c:if>
                    </select>
                    <br/>

                    <c:if test="${!(requestScope.selectedOrder.status.equals('delivered') &&
                        requestScope.selectedOrder.realDateFrom == null) &&
                        !(requestScope.selectedOrder.status.equals('returned') &&
                        requestScope.selectedOrder.realDateTo == null)}">
                        <p>${reason}:</p>
                <textarea name="order-info" cols="40" rows="3" required
                          oninvalid="this.setCustomValidity('Fill it')"
                          oninput="setCustomValidity('')" class="form-control" maxlength="140" pattern="[^<>]*"
                          tittle="${mNoTags}"></textarea>
                        <br/>
                        <input type="hidden" name="orderId" value="${requestScope.selectedOrder.id}">
                        <input type="hidden" name="command" value="update-status">
                        <input type="submit" value="${changeStatus}" class="btn btn-primary"/>
                    </c:if>
                </form>
            </c:if>

            <div class="col-lg-8">
                <p> ${mSupposedFromDate} <c:out value="${requestScope.selectedOrder.supposedDateFrom}"/></p>

                <p> ${mSupposedToDate} <c:out value="${requestScope.selectedOrder.supposedDateTo}"/></p>

                <p> ${mShippingPlace} <c:out value="${requestScope.selectedOrder.shippingPlace}"/></p>

                <p> ${mReturnPlace} <c:out value="${requestScope.selectedOrder.returnPlace}"/></p>

                <p> ${mOrderPrice} <c:out value="${requestScope.selectedOrder.orderPrice}"/>$</p>

                <p> ${mRealDateFrom} <c:out value="${requestScope.selectedOrder.realDateFrom}"/></p>

                <p> ${mRealDateTo} <c:out value="${requestScope.selectedOrder.realDateTo}"/></p>


                <c:if test="${requestScope.selectedOrder.status.equals('delivered') && requestScope.selectedOrder.realDateFrom == null}">
                    <c:if test="${requestScope.invalidDateFrom == true}">
                        <p class="text-danger">${mInvalidDate}</p>
                    </c:if>
                    <form action="Controller" method="get">
                        <input type="hidden" name="command" value="to-date">
                        <input type="hidden" name="setDateFrom" value="true">
                        <input type="hidden" name="selectedOrderId" value="${requestScope.selectedOrder.id}">
                        <input type="submit" value="${mSetDateFrom}" class="btn btn-primary">
                        <br/>
                    </form>
                </c:if>
                <c:if test="${requestScope.selectedOrder.realDateFrom != null &&
                                requestScope.selectedOrder.status.equals('returned') && requestScope.selectedOrder.realDateTo == null}">
                    <c:if test="${requestScope.invalidDateTo == true}">
                        <p class="text-danger">${mInvalidDate}</p>
                    </c:if>
                    <p>${mRealDateTo}</p>

                    <form action="Controller" method="get">
                        <input type="hidden" name="command" value="to-date">
                        <input type="hidden" name="setDateTo" value="true">
                        <input type="hidden" name="selectedOrderId" value="${requestScope.selectedOrder.id}">
                        <input type="submit" value="${mSetDateTo}" class="btn btn-primary">
                    </form>
                    <br/>
                </c:if>
                <c:if test="${sessionScope.selectedOrder.damagePrice != null}">
                    <p> ${mDmgPrice}: <c:out value="${sessionScope.selectedOrder.damagePrice}"/></p>
                </c:if>
                <c:if test="${requestScope.selectedOrder.status.equals('returned') && requestScope.selectedOrder.realDateTo != null}">
                    <c:if test="${requestScope.invalidDamagePrice == true}">
                        <p class="text-danger">${invalidDmgPrice}</p>
                    </c:if>
                    <form action="Controller" method="post">
                        <input type="text" name="damage-price" min=""
                               pattern="(^[0-9]+\.([0-9][0-9]|[0-9])$)|(^[0-9]+$)" class="form-control"
                               title="${mDmgPriceField}">
                        <input type="hidden" name="selectedOrderId" value="${requestScope.selectedOrder.id}">
                        <input type="hidden" name="command" value="update-damage-price">
                        <br/>
                        <input type="submit" value="${setDmgPrice}" class="btn btn-primary">
                    </form>
                </c:if>
                <br/>

                <p> ${information}: <c:out value="${requestScope.selectedOrder.info}"/></p>
            </div>
        </div>
    </c:if>

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
