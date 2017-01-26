<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Fill ME!</title>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/forms.js"></script>
    <link rel="stylesheet" href="resources/css/start.css" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Oxygen:300,400" rel="stylesheet">
</head>
<body>
<div id="wrapper">
    <div id="page-top">

        <c:if test="${logged eq 'false'}">

            <jsp:include page="login.jsp"/>
        </c:if>
    </div>
    <div id="content">
        <c:if test="${not empty message}">
            <div id="login_error_message">
                <span>${message}</span>
            </div>
        </c:if>
        <c:if test="${logged eq 'false'}">
            <c:set var="isValidLogin" value="${isValidLogin}" scope="request" />
            <c:set var="isValidPass" value="${isValidPass}" scope="request" />
            <c:set var="isValidEmail" value="${isValidEmail}" scope="request" />
            <jsp:include page="registration.jsp"/>
        </c:if>
        <!--include register form-->

    </div>
</div>

<div id="footer">
    <img src="${pageContext.request.contextPath}/resources/images/Card.png" id="1">
    <img src="${pageContext.request.contextPath}/resources/images/arrow.png" id="2">
    <img src="${pageContext.request.contextPath}/resources/images/code.jpg" id="3">
    <img src="${pageContext.request.contextPath}/resources/images/arrow_down.png" id="4">
    <img src="${pageContext.request.contextPath}/resources/images/jewish_run.jpg" id="5">

</div>
</body>
</html>