<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>
<head>
    <title>Orders</title>
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
    <fmt:message bundle="${locale}" key="local.mToAllOrders" var="mToAllOrders" />
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
                <input type="hidden" name="command" value="to-priv-office-user">
                <input type="submit" value="${privateOffice}" class="buttonMenu"/>
            </form>
        </div>
        <div class="divMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="command" value="view-orders-user">
                <input type="submit" value="${myOrders}" class="buttonMenu"/>
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

    <hr/>

    <div class="divMenu">
        <form action="Controller" method="get">
            <input type="hidden" name="selectedOrderId" value="${sessionScope.selectedOrder.id}">
            <input type="hidden" name="command" value="view-orders-user">
            <input type="submit" value="${mToAllOrders}"/>
        </form>
    </div>

    <div class="divMenu">
        <form action="Controller" method="get">
            <input type="hidden" name="selectedOrderId" value="${sessionScope.selectedOrder.id}">
            <input type="hidden" name="command" value="view-order-user">
            <input type="submit" value="${mUpdate}"/>
        </form>
    </div>

    <c:if test="${sessionScope.selectedOrder.status.equals('новый')}">
        <div class="divMenu">
            <form action="Controller" method="post">
                <input type="hidden" name="command" value="cancel-order"/>
                <input type="submit" value="${mCancelOrder}"/>
            </form>
        </div>
    </c:if>
    <c:if test="${sessionScope.selectedOrder.status.equals('принят')}">
        <div class="divMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="payment-type" value="order"/>
                <input type="hidden" name="command" value="to-payment-page"/>
                <input type="submit" value="${mPayForOrder}"/>
            </form>
        </div>
    </c:if>

    <c:if test="${sessionScope.selectedOrder.damagePrice != 0 && sessionScope.selectedOrder.status.equals('ожидаетКомп')}">
        <div class="divMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="payment-type" value="damage"/>
                <input type="hidden" name="command" value="to-payment-page"/>
                <input type="submit" value="${mPayForDmg}"/>
            </form>
        </div>
    </c:if>

    <hr/>

    <h2>${mOrderNumber}: <c:out value="${sessionScope.selectedOrder.id}"/></h2>

    <p>
        ${mStatus}:
        <c:if test="${sessionScope.selectedOrder.status.equals('новый')}">
            ${sNew}
        </c:if>
        <c:if test="${sessionScope.selectedOrder.status.equals('отменен')}">
            ${sCanceld}
        </c:if>
        <c:if test="${sessionScope.selectedOrder.status.equals('отклонен')}">
            ${sRejected}
        </c:if>
        <c:if test="${sessionScope.selectedOrder.status.equals('принят')}">
            ${Accepted}
        </c:if>
        <c:if test="${sessionScope.selectedOrder.status.equals('оплачен')}">
            ${Paid}
        </c:if>
        <c:if test="${sessionScope.selectedOrder.status.equals('доставлен')}">
            ${Delivered}
        </c:if>
        <c:if test="${sessionScope.selectedOrder.status.equals('возвращен')}">
            ${Returned}
        </c:if>
        <c:if test="${sessionScope.selectedOrder.status.equals('ожидаетКомп')}">
            ${ExpectsComp}
        </c:if>
        <c:if test="${sessionScope.selectedOrder.status.equals('закрыт')}">
            ${Closed}
        </c:if>
    </p>

    <p>${mInformation}: <c:out value="${sessionScope.selectedOrder.info}"/></p>

    <p>${mPrice}: <c:out value="${sessionScope.selectedOrder.orderPrice}"/></p>

    <p>${mDamagePrice}: <c:out value="${sessionScope.selectedOrder.damagePrice}"/></p>
    <img class="imgSmall" src="data:image/jpg;base64,${sessionScope.selectedOrder.car.image}"/>

    <p>${mMark}: <c:out value="${sessionScope.selectedOrder.car.mark}"/></p>

    <p>${mModel}: <c:out value="${sessionScope.selectedOrder.car.model}"/></p>

    <p>${mFuel}:
        <c:if test="${sessionScope.selectedOrder.car.fuel.equals('бензин')}">
            ${mPetrol}
        </c:if>
        <c:if test="${sessionScope.selectedOrder.car.fuel.equals('дизель')}">
            ${mDiesel}
        </c:if>
        <c:if test="${sessionScope.selectedOrder.car.fuel.equals('электричество')}">
            ${mElectricity}
        </c:if>
    </p>

    <p>${mTransmission}:
        <c:if test="${sessionScope.selectedOrder.car.transmission.equals('АКПП')}">
            ${mAutomatic}
        </c:if>
        <c:if test="${sessionScope.selectedOrder.car.transmission.equals('МКПП')}">
            ${mMechanic}
        </c:if>
    </p>

    <p>${mGovNumber}: <c:out value="${sessionScope.selectedOrder.car.govNumber}"/></p>

    <p>${mSupposedDateFrom} <c:out value="${sessionScope.selectedOrder.supposedDateFrom}"/></p>

    <p>${mSupposedDateTo} <c:out value="${sessionScope.selectedOrder.supposedDateTo}"/></p>

    <p>${mShippingPlace} <c:out value="${sessionScope.selectedOrder.shippingPlace}"/></p>

    <p>${mReturnPlace} <c:out value="${sessionScope.selectedOrder.returnPlace}"/></p>

</section>
<footer>
    <p>&copy; 2016 Car rental. All rights reserved.</p>
</footer>
</body>
</html>
