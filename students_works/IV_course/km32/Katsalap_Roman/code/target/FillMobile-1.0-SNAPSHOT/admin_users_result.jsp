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
    <a href="/services?action=admin">Back to filters</a>
    <div id="content">
        <table id="users-filter-result">
            <tr>
                <th>#</th><th>Login</th><th>Email</th><th>Role</th><th>Current status</th><th>Date of status</th><th>Reg date</th>
            </tr>
            <c:set var="count" value="1" scope="page" />
            <c:forEach items="${requestScope.users}" var="user">
                <tr class="user">
                    <td class="row_num">${count}</td>
                    <td class="usr_login">${user.getLogin()}</td>
                    <td class="usr_email">${user.getEmail()}</td>
                    <td class="usr_role">${user.getRole()}</td>
                    <td class="usr_status">${user.getStatus().toString()}</td>
                    <td class="usr_status_date">${user.getStatusDateTimeFormatted()}</td>
                    <td class="usr_reg_date">${user.getRegDateTimeFormatted()}</td>
                </tr>
                <c:set var="count" value="${count + 1}" scope="page"/>
            </c:forEach>


        </table>
        <div class="hidden" id="user_window"></div>
    </div>
</div>
</body>
</html>