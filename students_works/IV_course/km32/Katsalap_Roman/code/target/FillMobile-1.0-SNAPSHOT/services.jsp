<%--
  Created by IntelliJ IDEA.
  User: Guest
  Date: 23.12.2016
  Time: 20:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User page</title>
    <script src="resources/js/jquery.js"></script>
    <script src="resources/js/services.js"></script>
    <script src="resources/js/forms.js"></script>
</head>
<body>
    <div id="top">
        <span id="logged_as">Logged as ${login}</span><button id="logout">Log out</button>
    </div>
    <div id="menubar">
        <div class="menu_item" id="payments_menu" action="payments">Payments</div>
        <div class="menu_item" id="cards_menu" action="cards">Cards</div>
        <div class="menu_item" id="phones_menu" action="phones">Phones</div>
        <div class="menu_item" id="settings_menu" action="settings">Settings</div>
    </div>
    <div id="content">
        <c:choose>
            <c:when test="${menu_item == 'payments'}">
                <c:set var="payments" value="${payments}" scope="request" />
                <jsp:include page="payments.jsp"/>

            </c:when>
            <c:when test="${menu_item == 'cards'}">
                <jsp:include page="cards.jsp"></jsp:include>

            </c:when>
            <c:when test="${menu_item == 'settings'}">
                <jsp:include page="settings.jsp"></jsp:include>

            </c:when>


        </c:choose>

    </div>
</body>
</html>
