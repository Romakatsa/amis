<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="users_bar" class="menu_bar">
    <h2>Users</h2>
</div>
<div id="users_list" class="admin_list hidden">
    <form name="users_filters">
        <div><label>Reg date from</label><input name="reg_date_from" type="text" placeholder="dd.mm.yyyy"></div>
        <div><label>Reg date till</label><input name="reg_date_till" type="text" placeholder="dd.mm.yyyy"></div>
        <div><label>Account status</label>
            <input name="check_unconfirmed" type="checkbox" value="Unconfirmed"><span>Unconfirmed</span>
            <input name="check_confirmed" type="checkbox" value="Confirmed"><span>Confirmed</span>
            <input name="check_reset" type="checkbox" value="Reset"><span>Reset</span>
            <input name="check_banned" type="checkbox" value="Banned"><span>Banned</span>
        </div>
        <div><label>Login</label><input type="text" name="filter_login"></div>
        <div><label>Email</label><input type="text" name="filter_email"></div>
        <div>
            <label>Admin</label>
            <input type="radio" name="is_admin"><span>Yes</span>
            <input type="radio" name="is_not_admin"><span>No</span>
        </div>
        <div>
            <input type="submit" value="Apply filters">
        </div>
    </form>
    <div id="filtered_users">
    </div>
</div>


<div id="payments_bar" class="menu_bar">
    <h2>Payments</h2>
</div>
<div id="payments_list" class="admin_list hidden">

    <form name="payments_filters">
        <div class="filter-div" id="admin_id_filter">
            <label>Payment ID</label>
            <input type="text" name="admin_id_input">
        </div>
        <div class="filter-div"  id="admin_phone_filter">
            <label>Phone number (%like%)</label>
            <input type="text" name="admin_phone_input">
        </div>
        <div class="filter-div" id="admin_amount_filter">
            <label>Min amount</label>
            <input type="text"  name="admin_min_amount"/>
            <label>Max amount</label>
            <input type="text"  name="admin_max_amount"/>
        </div>
        <div class="filter-div" id="admin_date_filter">
            <label>Date from</label>
            <input type="text" name="admin_from_date" placeholder="dd.mm.yyyy">
            <label>Date till</label>
            <input type="text"  name="admin_till_date" placeholder="dd.mm.yyyy">
        </div>
        <div>
            <input type="submit" value="Apply filters">
        </div>
    </form>
    <div id="filtered_payments">

    </div>

</div>

