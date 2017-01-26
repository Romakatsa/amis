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
    <meta http-equiv="Cache-control" content="no-cache">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <title>User page</title>
    <link rel="stylesheet" href="resources/css/services.css" type="text/css">
    <script>var contextPath = "${pageContext.request.contextPath}";</script>
    <script src="resources/js/jquery.js"></script>
    <script src="resources/js/services.js"></script>
    <script src="resources/js/services-forms.js"></script>
    <script src="resources/js/forms.js"></script>

    <c:if test="${role == 'admin'}">
        <script src="resources/js/admin.js"></script>
        <link rel="stylesheet" href="resources/css/admin.css" type="text/css">
    </c:if>
    <link href="https://fonts.googleapis.com/css?family=Oxygen:300,400" rel="stylesheet">
</head>
<body>
<div id="wrapper">
    <div id="top">
        <span id="logged_as">Logged as ${login}</span><button id="logout">Log out</button>
    </div>
    <div id="menubar" class="menubar_${role}">
        <div class="menu_item menu_selected" id="payments_menu" action="payments">Payments</div>
        <div class="menu_item" id="cards_menu" action="cards">Cards</div>
        <div class="menu_item" id="phones_menu" action="phones">Phones</div>
        <div class="menu_item" id="settings_menu" action="settings">Settings</div>
        <c:if test="${role == 'admin'}">
            <div class="menu_item" id="admin_menu" action="admin">Admin</div>
        </c:if>
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
            <c:when test="${menu_item == 'admin'}">
                <c:set var="unconfirmed" value="${unconfirmed}" scope = "request" />
                <c:set var="confirmed" value="${confirmed}" scope = "request" />
                <c:set var="banned" value="${banned}" scope = "request" />
                <c:set var="reseted" value="${reseted}" scope = "request" />
                <c:set var="active" value="${active}" scope = "request" />
                <jsp:include page="admin.jsp"></jsp:include>
            </c:when>

        </c:choose>

    </div>
</div>
</body>
</html>
