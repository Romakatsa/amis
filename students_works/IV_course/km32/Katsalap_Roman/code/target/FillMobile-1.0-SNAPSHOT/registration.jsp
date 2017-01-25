<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>

<div></div>
<div id="reg">
    <h2>Registration Form</h2>
    <br>
    <div id="reg-msg">

    </div>
    <form action="/reg" method="post" name="reg_form">

        <div class="form-group ${requestScope.isValidLogin}">
            <label for="inputLogin">Enter login</label>
            <input type="text" class="form-control" name="login" id="inputLogin" maxlength="30" required>
            <span id="login_msg" class="form-msg">from 4 to 30 English characters including '.', '-', '_'</span>

        </div>

        <div class="form-group ${requestScope.isValidEmail}">
            <label for="inputEmail">Enter email address:</label>
            <input type="email" class="form-control" name = "email" id="inputEmail"  maxlenght="30" required>
            <span id="email_msg" class="form-msg">Enter valid email adress of maximum 50 characters</span>

        </div>

        <div class="form-group ${requestScope.isValidPass}" >
            <label for="inputPass">Create password:</label>
            <input type="password" class="form-control" id="inputPass" maxlength="25" name="password" required>
            <span id="pass_msg"  class="form-msg">Any non-space characters of minimum length 5</span>
            <span id="pass_help">Advice: choose strong password</span>
        </div>

        <div class="form-group">
            <label for="inputRePass">Repeat password:</label>
            <input type="password" class="form-control" pattern="^[A-Za-z0-9]+$" id="inputRePass" name="re_password"  maxlength="25"  required>
            <span id="repass_msg" class="form-msg">Passwords do not match</span>
        </div>
        <br />
        <div class="text-right">
            <input type="submit" class="btn btn-primary" value = "Send"></input>
        </div>
    </form>
</div>
</body>
</html>