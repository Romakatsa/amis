<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="admin_general_info">
    <span>Unconfirmed - ${unconfirmed}</span>
    <span>Confirmed - ${confirmed}</span>
    <span>Active - ${active}</span>
    <span>Reseted - ${reseted}</span>
    <span>Banned - ${banned}</span>

</div>
<div id="users_bar" class="menu_bar">
    <h2>Users</h2>
</div>
<div id="users_list" class="admin_list hidden">
    <form name="users_filters" action="/admin" method="post">
        <input type="hidden" value="users" name="action">
        <div><label>Reg date from</label><input name="reg_date_from" type="text" placeholder="dd.mm.yyyy"></div>
        <div><label>Reg date till</label><input name="reg_date_till" type="text" placeholder="dd.mm.yyyy"></div>
        <div><label>Account status</label>
            <input class="status_radio_input" name="status_radio_input" type="radio" value="Unconfirmed"><span>Unconfirmed</span>
            <input class="status_radio_input" name="status_radio_input" type="radio" value="Confirmed"><span>Confirmed</span>
            <input class="status_radio_input" name="status_radio_input" type="radio" value="Active"><span>Active</span>
            <input class="status_radio_input" name="status_radio_input" type="radio" value="Reset"><span>Reset</span>
            <input class="status_radio_input" name="status_radio_input" type="radio" value="Banned"><span>Banned</span>
        </div>
        <div><label>Login</label><input type="text" name="filter_login"></div>
        <div><label>Email (%like%)</label><input type="text" name="filter_email"></div>
        <div>
            <label>Admin</label>
            <input type="radio" name="is_admin" value="admin"><span>Yes</span>
            <input type="radio" name="is_admin" value="user"><span>No</span>
        </div>
        <div>
            <input type="submit" id="admin-apply-users-filters-submit" value="Apply filters">
            <input type = "reset" class="reset-filter" id="admin-reset-users-filters" value="Reset filters">
            <!--<button class="apply-filter" id="admin-apply-users-filters">Apply filters</button>-->
        </div>
    </form>
    <div id="filtered_users">
        <button>Delete all</button>
    </div>
</div>


<div id="payments_bar" class="menu_bar">
    <h2>Payments</h2>
</div>
<div id="payments_list" class="admin_list hidden">

    <form name="payments_filters">
        <div id="admin_id_filter">
            <label>Payment ID</label>
            <input type="text" name="admin_id_input">
        </div>
        <div  id="admin_phone_filter">
            <label>Phone number (%like%)</label>
            <input type="text" name="admin_phone_input">
        </div>
        <div id="admin_amount_filter">
            <div>
            <label>Min amount</label>
            <input type="text"  name="admin_min_amount"/>
            </div>
            <div>
            <label>Max amount</label>
            <input type="text"  name="admin_max_amount"/>
            </div>
        </div>
        <div id="admin_date_filter">
            <label>Date from</label>
            <input type="text" name="admin_from_date" placeholder="dd.mm.yyyy">
            <label>Date till</label>
            <input type="text"  name="admin_till_date" placeholder="dd.mm.yyyy">
        </div>
        <div>
            <button class="reset-filter" id="admin-reset-payment-filters">Reset filters</button>
            <button class="apply-filter" id="admin-apply-payment-filters">Apply filters</button>
        </div>
    </form>
    <div id="filtered_payments">

    </div>

</div>

