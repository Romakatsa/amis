<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Fill ME!</title>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/forms.js"></script>
    <link rel="stylesheet" href="resources/css/restore.css" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Oxygen:300,400" rel="stylesheet">
</head>
<body>
<div id="wrapper">
    <div id="page-top">
            <jsp:include page="login.jsp"/>
    </div>
    <div id="content" class="${status}">
        <a href="/">Main page</a>
        <c:if test="${status eq 'fail'}">
            <h3>Operation can not be performed</h3>
            <p id="restore_message">${message}</p>
        </c:if>
        <c:if test="${status eq 'success'}">
            <h3>Operation successful</h3>
            <p id="restore_message">${message}</p>
            <p>${new_pass}</p>
        </c:if>
    </div>
</div>
</body>
</html>
