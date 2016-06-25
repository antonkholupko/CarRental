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
    <fmt:message bundle="${locale}" key="local.orders" var="myOrders"/>
    <fmt:message bundle="${locale}" key="local.mUpdate" var="mUpdate"/>
    <fmt:message bundle="${locale}" key="local.mCancelOrder" var="mCancelOrder"/>
    <fmt:message bundle="${locale}" key="local.mPayForOrder" var="mPayForOrder"/>
    <fmt:message bundle="${locale}" key="local.mPayForDmg" var="mPayForDmg"/>
    <fmt:message bundle="${locale}" key="local.mOrderNumber" var="mOrderNumber"/>
    <fmt:message bundle="${locale}" key="local.order" var="mOrder"/>
    <fmt:message bundle="${locale}" key="local.carInformation" var="mInformation"/>
    <fmt:message bundle="${locale}" key="local.price" var="mPrice"/>
    <fmt:message bundle="${locale}" key="local.mDamagePrice" var="mDamagePrice"/>
    <fmt:message bundle="${locale}" key="local.fuel" var="mFuel"/>
    <fmt:message bundle="${locale}" key="local.transmission" var="mTransmission"/>
    <fmt:message bundle="${locale}" key="local.mGovNumber" var="mGovNumber"/>
    <fmt:message bundle="${locale}" key="local.supposedFromDate" var="mSupposedDateFrom"/>
    <fmt:message bundle="${locale}" key="local.supposedToDate" var="mSupposedDateTo"/>
    <fmt:message bundle="${locale}" key="local.shippingPlaceMessage" var="mShippingPlace"/>
    <fmt:message bundle="${locale}" key="local.returnPlaceMessage" var="mReturnPlace"/>
    <fmt:message bundle="${locale}" key="local.mStatus" var="mStatus"/>
    <fmt:message bundle="${locale}" key="local.mark" var="mMark"/>
    <fmt:message bundle="${locale}" key="local.model" var="mModel"/>
    <fmt:message bundle="${locale}" key="local.automaticTransmission" var="mAutomatic"/>
    <fmt:message bundle="${locale}" key="local.mechanicTransmission" var="mMechanic"/>
    <fmt:message bundle="${locale}" key="local.statusNew" var="sNew"/>
    <fmt:message bundle="${locale}" key="local.statusCanceled" var="sCanceld"/>
    <fmt:message bundle="${locale}" key="local.statusRejected" var="sRejected"/>
    <fmt:message bundle="${locale}" key="local.statusAccepted" var="Accepted"/>
    <fmt:message bundle="${locale}" key="local.statusPaid" var="Paid"/>
    <fmt:message bundle="${locale}" key="local.statusDelivered" var="Delivered"/>
    <fmt:message bundle="${locale}" key="local.statusReturned" var="Returned"/>
    <fmt:message bundle="${locale}" key="local.statusExpectsComp" var="ExpectsComp"/>
    <fmt:message bundle="${locale}" key="local.statusClosed" var="Closed"/>
    <fmt:message bundle="${locale}" key="local.petrol" var="mPetrol"/>
    <fmt:message bundle="${locale}" key="local.diesel" var="mDiesel"/>
    <fmt:message bundle="${locale}" key="local.electricity" var="mElectricity"/>
    <fmt:message bundle="${locale}" key="local.mToAllOrders" var="mToAllOrders"/>
    <fmt:message bundle="${locale}" key="local.mOrder" var="mOrder"/>
    <fmt:message bundle="${locale}" key="local.viewOrders" var="mViewAllOrders"/>
    <fmt:message bundle="${locale}" key="local.mMakeOrder" var="mMakeOrder"/>
    <fmt:message bundle="${locale}" key="local.mDollarsPerDay" var="mDollarsPerDay"/>
    <fmt:message bundle="${locale}" key="local.mAllOrders" var="mAllOrders"/>
</head>
<body>

<%@include file="../jspf/navigation.jspf" %>

<div class="container">

    <c:if test="${sessionScope.user.type.equals('user')}">
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
                        <input type="hidden" name="command" value="to-priv-office-user">
                        <input type="submit" value="${privateOffice}" class="btn btn-link">
                    </form>
                </li>
                <li>
                    <form action="Controller" method="get" class="btn btn-link">
                        <input type="hidden" name="command" value="view-orders-user">
                        <input type="submit" value="${mAllOrders}" class="btn btn-link">
                    </form>
                </li>
                <li class="active">${mOrder}</li>
            </ol>
        </div>

        <div class="row">

            <h2>${mOrderNumber}: <c:out value="${sessionScope.selectedOrder.id}"/></h2>

            <div class="col-lg-12 well my-info">

                <div class="col-md-4">
                    <p>
                            ${mStatus}:
                        <c:if test="${sessionScope.selectedOrder.status.equals('new')}">
                            ${sNew}
                        </c:if>
                        <c:if test="${sessionScope.selectedOrder.status.equals('canceled')}">
                            ${sCanceld}
                        </c:if>
                        <c:if test="${sessionScope.selectedOrder.status.equals('rejected')}">
                            ${sRejected}
                        </c:if>
                        <c:if test="${sessionScope.selectedOrder.status.equals('accepted')}">
                            ${Accepted}
                        </c:if>
                        <c:if test="${sessionScope.selectedOrder.status.equals('payed')}">
                            ${Paid}
                        </c:if>
                        <c:if test="${sessionScope.selectedOrder.status.equals('delivered')}">
                            ${Delivered}
                        </c:if>
                        <c:if test="${sessionScope.selectedOrder.status.equals('returned')}">
                            ${Returned}
                        </c:if>
                        <c:if test="${sessionScope.selectedOrder.status.equals('expectsComp')}">
                            ${ExpectsComp}
                        </c:if>
                        <c:if test="${sessionScope.selectedOrder.status.equals('closed')}">
                            ${Closed}
                        </c:if>
                    </p>

                    <p>${mPrice}: <c:out value="${sessionScope.selectedOrder.orderPrice}$"/></p>

                    <p>${mDamagePrice}: <c:out value="${sessionScope.selectedOrder.damagePrice}$"/></p>

                    <div class="btn-group">
                        <c:if test="${sessionScope.selectedOrder.status.equals('new')}">
                            <form action="Controller" method="post" class="btn">
                                <input type="hidden" name="command" value="cancel-order"/>
                                <input type="hidden" name="processRequest" value="redirect">
                                <input type="submit" value="${mCancelOrder}" class="btn btn-warning"/>
                            </form>
                        </c:if>
                        <c:if test="${sessionScope.selectedOrder.status.equals('accepted')}">
                            <form action="Controller" method="get" class="btn">
                                <input type="hidden" name="payment-type" value="order"/>
                                <input type="hidden" name="command" value="to-payment-page"/>
                                <input type="submit" value="${mPayForOrder}" class="btn btn-primary"/>
                            </form>
                        </c:if>

                        <c:if test="${sessionScope.selectedOrder.damagePrice != 0 && sessionScope.selectedOrder.status.equals('expectsComp')}">
                            <form action="Controller" method="get" class="btn">
                                <input type="hidden" name="payment-type" value="damage"/>
                                <input type="hidden" name="command" value="to-payment-page"/>
                                <input type="submit" value="${mPayForDmg}" class="btn btn-primary"/>
                            </form>
                        </c:if>

                        <form action="Controller" method="get" class="btn">
                            <input type="hidden" name="selectedOrderId" value="${sessionScope.selectedOrder.id}">
                            <input type="hidden" name="command" value="view-order-user">
                            <input type="submit" value="${mUpdate}" class="btn btn-default"/>
                        </form>

                    </div>
                </div>

                <div class="col-md-8">
                    <p>${mSupposedDateFrom} <c:out value="${sessionScope.selectedOrder.supposedDateFrom}"/></p>

                    <p>${mSupposedDateTo} <c:out value="${sessionScope.selectedOrder.supposedDateTo}"/></p>

                    <p>${mShippingPlace} <c:out value="${sessionScope.selectedOrder.shippingPlace}"/></p>

                    <p>${mReturnPlace} <c:out value="${sessionScope.selectedOrder.returnPlace}"/></p>
                </div>

                <hr class="col-lg-12"/>

                <div class="col-lg-4">
                    <c:if test="${sessionScope.selectedOrder.info != null}">
                        <p>${mInformation}: <c:out value="${sessionScope.selectedOrder.info}"/></p>
                    </c:if>
                </div>

                <hr class="col-lg-12"/>

                <div class="col-md-8">
                    <img class="img-responsive car-middle-img"
                         src="data:image/jpg;base64,${sessionScope.selectedOrder.car.image}"/>
                </div>

                <div class="col-md-4 my-info">

                    <p>${mMark}: <c:out value="${sessionScope.selectedOrder.car.mark}"/></p>

                    <p>${mModel}: <c:out value="${sessionScope.selectedOrder.car.model}"/></p>

                    <p>${mFuel}:
                        <c:if test="${sessionScope.selectedOrder.car.fuel.equals('petrol')}">
                            ${mPetrol}
                        </c:if>
                        <c:if test="${sessionScope.selectedOrder.car.fuel.equals('diesel')}">
                            ${mDiesel}
                        </c:if>
                        <c:if test="${sessionScope.selectedOrder.car.fuel.equals('electricity')}">
                            ${mElectricity}
                        </c:if>
                    </p>

                    <p>${mTransmission}:
                        <c:if test="${sessionScope.selectedOrder.car.transmission.equals('automatic')}">
                            ${mAutomatic}
                        </c:if>
                        <c:if test="${sessionScope.selectedOrder.car.transmission.equals('mechanic')}">
                            ${mMechanic}
                        </c:if>
                    </p>

                    <p>${mGovNumber}: <c:out value="${sessionScope.selectedOrder.car.govNumber}"/></p>

                </div>

            </div>
        </div>
    </c:if>

    <c:if test="${!sessionScope.user.type.equals('user')}">
        <h1 class="page-header"></h1>

        <form action="Controller" method="get">
            <input type="hidden" name="command" value="to-home-page">
            <input type="submit" value="${home}" class="btn btn-info">
        </form>
    </c:if>

    <hr/>
    <%@include file="../jspf/footer.jspf" %>
</div>

<script src="../../js/jquery.js"></script>
<script src="../../js/bootstrap.min.js"></script>

</body>
</html>
