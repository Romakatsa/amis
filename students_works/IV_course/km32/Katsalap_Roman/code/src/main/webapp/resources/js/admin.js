$(function() {


$("#content").on("click",".menu_bar",function() {

    $(this).next().toggleClass("hidden");


});




$("#content").on("click", "#users_list #admin-apply-users-filters",function() {

var reg_date_from = $("input[name='reg_date_from']").val();
var reg_date_till = $("input[name='reg_date_till']").val();
var status = $("input[name='status_radio_input']:checked").val();
var login = $("input[name='filter_login']").val();
var email = $("input[name='filter_email']").val();
var role  = $("input[name='is_admin']:checked").val();

if (typeof status == 'undefined') {
    status = "";
}
if (typeof role == 'undefined') {
    role = "";
}


$.ajax({
        method: "POST",
        url: "http://localhost:8080/admin",
        data: {

            action:"users",
            reg_date_from: reg_date_from,
            reg_date_till: reg_date_till,
            account_status:status,
            login:login,
            email:email,
            role:role

        },
        xhr: createXHR

    });


/*$("#filtered_users").load("http://localhost:8080/admin", {action:"users", reg_date_from: reg_date_from, reg_date_till: reg_date_till, account_status:status, login:login, email:email, role:role}, function() {

});*/
    return false;
});




    $("#content").on("click",".user",function() {

        $("#user_window").load("/userWindow",{action: "fetch", login : $(this).children(".usr_login").text(), status: $(this).children(".usr_status").text()},function() {
            $("#user_window").removeClass("hidden");
        });

    });


    $("#content").on("click","#user_window_header a",function() {
        $("#user_window").addClass("hidden");


    });



    $("#content").on("click","#ban",function() {

        $.post("/userWindow", {action:"ban", login: $("input[name='user_window_login']").val()}) , function(result) {
         if (result == 'success') {
             $("#ban_result").text("Banned");
         }
         else {
             $("#ban_result").text("Error");
         }
        }


    });

    $("#content").on("click","#unban",function() {

        $.post("/userWindow", {action:"unban", login: $("input[name='user_window_login']").val()}) , function(result) {
            if (result == 'success') {
                $("#ban_result").text("Unbanned");
            }
            else {
                    $("#ban_result").text("Error");
            }
        }


    });




});

function createXHR() {
    return new XMLHttpRequest();
}
