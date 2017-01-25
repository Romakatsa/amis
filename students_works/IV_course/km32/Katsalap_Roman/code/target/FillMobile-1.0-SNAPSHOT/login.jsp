<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form action="/login" method="post" name="login_form">

    <label>Username</label><input type="text" id="login" name="login_login">
    <label>Password</label><input type="password" id="password" name="login_password">
    <input type="submit" value="Sign in">


        <c:if test="${requestScope.showErrorMsg eq 'true'}">
          <!--  <span id="login_error">Invalid Login/Password</span> -->
        </c:if>

    <a id="forgot_pass">Forgot password?</a>
</form>