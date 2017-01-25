<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="restore">
<form action="/restore" method="post" id="restore-form">
    <label>Username</label><input type="text" id="restore_login" name="login">
    <input type="submit" value="Restore">
    <a href="/">Cancel restoring</a>
    <p id="restore-result"></p>
</form>
</div>



