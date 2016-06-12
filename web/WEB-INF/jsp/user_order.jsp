<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>
<head>
    <title>Orders</title>
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
                <c:if test="${sessionScope.user.type.equals('user')}">
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
                    <c:if test="${sessionScope.user.type.equals('user')}">
                        <c:out value="${sessionScope.user.lastName}"/> <c:out value="${sessionScope.user.firstName}"/>
                    </c:if>
                </p>
            </div>

        </div>
        <c:if test="${sessionScope.user.type.equals('user')}">
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
                <c:if test="${sessionScope.user.type.equals('user')}">
                    <div class="divMenu">
                        <form action="Controller" method="get">
                            <input type="hidden" name="command" value="to-priv-office-user">
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
    <c:if test="${sessionScope.user.type.equals('user')}">
        <h2>${mOrder}</h2>

        <hr/>

        <div class="divSubMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="selectedOrderId" value="${sessionScope.selectedOrder.id}">
                <input type="hidden" name="command" value="view-orders-user">
                <input type="submit" value="${mToAllOrders}" class="buttonSubMenu"/>
            </form>
        </div>

        <div class="divSubMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="selectedOrderId" value="${sessionScope.selectedOrder.id}">
                <input type="hidden" name="command" value="view-order-user">
                <input type="submit" value="${mUpdate}" class="buttonSubMenu"/>
            </form>
        </div>

        <c:if test="${sessionScope.selectedOrder.status.equals('new')}">
            <div class="divSubMenu">
                <form action="Controller" method="post">
                    <input type="hidden" name="command" value="cancel-order"/>
                    <input type="hidden" name="processRequest" value="redirect">
                    <input type="submit" value="${mCancelOrder}" class="buttonSubMenu"/>
                </form>
            </div>
        </c:if>
        <c:if test="${sessionScope.selectedOrder.status.equals('accepted')}">
            <div class="divSubMenu">
                <form action="Controller" method="get">
                    <input type="hidden" name="payment-type" value="order"/>
                    <input type="hidden" name="command" value="to-payment-page"/>
                    <input type="submit" value="${mPayForOrder}" class="buttonSubMenu"/>
                </form>
            </div>
        </c:if>

        <c:if test="${sessionScope.selectedOrder.damagePrice != 0 && sessionScope.selectedOrder.status.equals('expectsComp')}">
            <div class="divSubMenu">
                <form action="Controller" method="get">
                    <input type="hidden" name="payment-type" value="damage"/>
                    <input type="hidden" name="command" value="to-payment-page"/>
                    <input type="submit" value="${mPayForDmg}" class="buttonSubMenu"/>
                </form>
            </div>
        </c:if>

        <div class="divSubMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="command" value="view-orders-user">
                <input type="submit" value="${mViewAllOrders}" class="buttonSubMenu">
            </form>
        </div>
        <div class="divSubMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="command" value="view-all-cars">
                <input type="submit" value="${mMakeOrder}" class="buttonSubMenu">
            </form>
        </div>

        <hr/>

        <h2>${mOrderNumber}: <c:out value="${sessionScope.selectedOrder.id}"/></h2>

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

        <p>${mInformation}: <c:out value="${sessionScope.selectedOrder.info}"/></p>

        <p>${mPrice}: <c:out value="${sessionScope.selectedOrder.orderPrice}"/></p>

        <p>${mDamagePrice}: <c:out value="${sessionScope.selectedOrder.damagePrice}"/></p>
        <img class="imgSmall" src="data:image/jpg;base64,${sessionScope.selectedOrder.car.image}"/>

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

        <p>${mSupposedDateFrom} <c:out value="${sessionScope.selectedOrder.supposedDateFrom}"/></p>

        <p>${mSupposedDateTo} <c:out value="${sessionScope.selectedOrder.supposedDateTo}"/></p>

        <p>${mShippingPlace} <c:out value="${sessionScope.selectedOrder.shippingPlace}"/></p>

        <p>${mReturnPlace} <c:out value="${sessionScope.selectedOrder.returnPlace}"/></p>
    </c:if>
    <c:if test="${!sessionScope.user.type.equals('user')}">
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
