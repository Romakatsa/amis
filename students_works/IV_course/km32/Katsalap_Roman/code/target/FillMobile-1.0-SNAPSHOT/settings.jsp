<form method="post" action="/change" id="settings_form">
    <div id="change_password_div">
        <div class="settings_field"><label>Current password</label><input id="old_pass" type="password" name="old_pass"><input id="old_pass_text" type="text" class="shrink"><span class="show_pass"></span></div>
        <div class="settings_field"><label>Enter new password</label><input id="new_pass" type="password" name="new_pass"><input id="new_pass_text" type="text"  class="shrink"><span class="show_pass"></span></div>
        <div class="settings_field"><label>Repeat new password</label><input id="new_repass" type="password" name="new_repass"><input id="new_repass_text" type="text"  class="shrink"><span class="show_pass"></span></div>
        <button id="change_password">Change password</button>
        <div id="change_password_msg"></div>
    </div>

    <div id="link_email">
        <div class="settings_field"><span id="current_email_label">Current email:</span><span id="current_email">${requestScope.email}</span></div>
        <div class="settings_field"><label>Link new email:</label><input type="email" id="new_email" name="new_email"></div>
        <button id="change_email">Relink email</button>
        <div id="change_email_msg"></div>
    </div>
</form>