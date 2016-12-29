<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Fill ME!</title>

</head>
<body>
<form action="/restore" method="post">
    <label>Username</label><input type="text" id="login" name="login">
    <input type="submit" value="Restore">
    <a href="/">Cansel restoring</a>
</form>
</body>
</html>


