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
        <h2>${mOrder}</h2>
        <hr/>

        <div class="divSubMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="command" value="view-orders-admin">
                <input type="submit" value="${mToAllOrders}" class="buttonSubMenu">
            </form>
        </div>

        <div class="divSubMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="command" value="view-order-admin">
                <input type="hidden" name="selectedOrderId" value="${requestScope.selectedOrderId}">
                <input type="submit" value="${mUpdate}" class="buttonSubMenu">
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
        <article>
            <div class="divOrders">
                <h2>${mOrderNumber} <c:out value="${requestScope.selectedOrder.id}"/></h2>
                <c:if test="${requestScope.selectedOrder.status.equals('delivered') &&
                                    requestScope.selectedOrder.realDateFrom == null}">
                    <c:out value="${selectFrom}"/>
                </c:if>
                <c:if test="${requestScope.selectedOrder.status.equals('returned') &&
                                requestScope.selectedOrder.realDateTo == null}">
                    <c:out value="${selectTo}"/>
                </c:if>
                <c:if test="${sessionScope.invalidInfo == true}">
                    <p class="invalidMessage">${invalidReason}</p>
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
                <c:if test="${!requestScope.selectedOrder.status.equals('rejected') &&
                        !requestScope.selectedOrder.status.equals('canceled') &&
                        !requestScope.selectedOrder.status.equals('closed') &&
                        !requestScope.selectedOrder.status.equals('accepted') &&
                        !requestScope.selectedOrder.status.equals('expectsComp')}">
                <form action="Controller" method="post">
                    <select name="statusOrder">
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

                    <p>${reason}:</p>
                <textarea name="order-info" cols="40" rows="3" required
                          oninvalid="this.setCustomValidity('Fill it')"
                          oninput="setCustomValidity('')"></textarea>
                    <input type="hidden" name="orderId" value="${requestScope.selectedOrder.id}">
                    <input type="hidden" name="command" value="update-status">
                    <input type="submit" value="${changeStatus}"/>
                    </c:if>
                </form>
                <p> ${mSupposedFromDate} <c:out value="${requestScope.selectedOrder.supposedDateFrom}"/></p>

                <p> ${mSupposedToDate} <c:out value="${requestScope.selectedOrder.supposedDateTo}"/></p>

                <p> ${mShippingPlace} <c:out value="${requestScope.selectedOrder.shippingPlace}"/></p>

                <p> ${mReturnPlace} <c:out value="${requestScope.selectedOrder.returnPlace}"/></p>

                <p> ${mOrderPrice} <c:out value="${requestScope.selectedOrder.orderPrice}"/></p>

                <p> ${mRealDateFrom} <c:out value="${requestScope.selectedOrder.realDateFrom}"/></p>

                <p> ${mRealDateTo} <c:out value="${requestScope.selectedOrder.realDateTo}"/></p>

                <c:if test="${requestScope.selectedOrder.status.equals('delivered') && requestScope.selectedOrder.realDateFrom == null}">
                    <c:if test="${requestScope.invalidDateFrom == true}">
                        <p class="invalidMessage">${mInvalidDate}</p>
                    </c:if>
                    <form action="Controller" method="get">
                        <input type="hidden" name="command" value="to-date">
                        <input type="hidden" name="setDateFrom" value="true">
                        <input type="hidden" name="selectedOrderId" value="${requestScope.selectedOrder.id}">
                        <input type="submit" value="Set date from">
                    </form>
                </c:if>
                <c:if test="${requestScope.selectedOrder.realDateFrom != null &&
                                requestScope.selectedOrder.status.equals('returned') && requestScope.selectedOrder.realDateTo == null}">
                    <c:if test="${requestScope.invalidDateTo == true}">
                        <p class="invalidMessage">${mInvalidDate}</p>
                    </c:if>
                    <p>${mRealDateTo}</p>

                    <form action="Controller" method="get">
                        <input type="hidden" name="command" value="to-date">
                        <input type="hidden" name="setDateTo" value="true">
                        <input type="hidden" name="selectedOrderId" value="${requestScope.selectedOrder.id}">
                        <input type="submit" value="Set date To">
                    </form>
                </c:if>
                <p> ${mDmgPrice}: <c:out value="${sessionScope.selectedOrder.damagePrice}"/></p>
                <c:if test="${requestScope.selectedOrder.status.equals('returned') && requestScope.selectedOrder.realDateTo != null}">
                    <c:if test="${requestScope.invalidDamagePrice == true}">
                        <p class="invalidMessage">${invalidDmgPrice}</p>
                    </c:if>
                    <form action="Controller" method="post">
                        <input type="text" name="damage-price" min=""
                               pattern="(^[0-9]+\.([0-9][0-9]|[0-9])$)|(^[0-9]+$)">
                        <input type="hidden" name="selectedOrderId" value="${requestScope.selectedOrder.id}">
                        <input type="hidden" name="command" value="update-damage-price">
                        <input type="submit" value="${setDmgPrice}">
                    </form>
                </c:if>
                <p> ${information}: <c:out value="${requestScope.selectedOrder.info}"/></p>
            </div>

            <br/>

            <div class="divCarAndUser">
                <h2>${mUser}</h2>

                <p>${mLogin}: <c:out value="${requestScope.selectedOrder.user.login}"/></p>

                <p>${mLastName}: <c:out value="${requestScope.selectedOrder.user.lastName}"/></p>

                <p>${mFirstName}: <c:out value="${requestScope.selectedOrder.user.firstName}"/></p>

                <p>${mMiddleName}: <c:out value="${requestScope.selectedOrder.user.middleName}"/></p>

                <p>e-mail: <c:out value="${requestScope.selectedOrder.user.EMail}"/></p>

                <p>${mPhone}: <c:out value="${requestScope.selectedOrder.user.phone}"/></p>

                <p>${mPassport}: <c:out value="${requestScope.selectedOrder.user.passport}"/></p>

                <p>${mAddress}: <c:out value="${requestScope.selectedOrder.user.address}"/></p>
            </div>
            <br/>

            <div class="divCarAndUser">
                <h2>${mCar}</h2>
                <img class="imgSmall" src="data:image/jpg;base64,${requestScope.selectedOrder.car.image}"/>

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
        </article>
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
