<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <input type="hidden" name="user_window_login" value="${login}">
    <div id="user_window_header"><span>User: ${login}</span><a href="#">Close</a></div>
    <c:if test="${status != 'BANNED'}">
        <button id="ban">Ban</button>
    </c:if>
    <c:if test="${status == 'BANNED'}">
        <button id="unban">Unban</button>
    </c:if>
    <!--
    <c:if test="${role == 'user'}">
        <button id="grant_admin">Grant admin</button>
    </c:if>
    <c:if test="${status == 'admin'}">
        <button id="unban">Revoke admin</button>
    </c:if>
    -->
    <div id="ban_result"></div>

    <table id="user_statuses">
        <tr>
            <th>#</th><th>Status name</th><th>Date of status</th><th>Status message</th>
        </tr>
        <c:set var="rows" value="1" scope="page" />
        <c:forEach items="${requestScope.statuses}" var="user">
            <tr class="user_status">
                <td class="row_num">${rows}</td>
                <td class="usr_login">${user.getStatus().toString()}</td>
                <td class="usr_email">${user.getStatusDateTimeFormatted()}</td>
                <td class="usr_role">${user.getStatus_msg()}</td>
            </tr>
            <c:set var="rows" value="${rows + 1}" scope="page"/>
        </c:forEach>
    </table>







</div>
