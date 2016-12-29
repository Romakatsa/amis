<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form action="/login" method="post">

    <label>Username</label><input type="text" id="login" name="login">
    <label>Password</label><input type="password" id="password" name="password">
    <input type="submit" value="Sign in">

        <c:if test="${requestScope.showErrorMsg eq 'true'}">
            <span id="login_error">Invalid Login/Password</span>
        </c:if>

    <a href="/restore">Forgot password?</a>
</form>