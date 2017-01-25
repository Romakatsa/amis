$(function() {

    $("#content").on('click',"#new_payment input[type='submit']",function(e){
        e.preventDefault();

        $("#amount").removeClass("invalid");
        $("#phones_drop_list").removeClass("invalid");
        $("#bank-card-select").removeClass("invalid");
        $("#cards").removeClass("invalid");
        $("#expire").removeClass("invalid");
        $("#payment_response").html("");

        var phone = $("#phone_input").val();
        var amount = $("#amount_input").val();
        var card = $("select[name='card_select']").val();
        var from_select = true;
        var expire_month = "";
        var expire_year = "";
        var cvv = "";
        if (!card) {
            card = $("#card_input").val();
            from_select = false;
            expire_month = $("#month_select").val();
            expire_year = $("#year_select").val();
            cvv = $("#cvv_input").val();
        }

            var prevent = false;
            if (!isValidPhone(phone)) {
                $("#phones_drop_list").addClass("invalid");
                prevent = true;
            }
            if (!isValidAmount(amount)) {
                $("#amount").addClass("invalid");
                prevent = true;
            }
            if (!from_select)  {
                if (!isValidCard(card)) {

                    $("#cards").addClass("invalid");

                    prevent = true;
                }
            }

            if (!from_select) {
                if (!isValidDate(expire_month,expire_year)) {
                    $("#expire").addClass("invalid");
                    prevent = true;
                }
            }

            if (!prevent) {

                $.post("/pay", {phone_input: phone, amount_input: amount, card_select: card, month_select: expire_month, year_select: expire_year, cvv_input:cvv}, function (result) {
                    var answer = JSON.parse(result);

                    $("#payment_response").html(answer.message);

                    if (answer.success) {
                        $("#payment_response").addClass("success");
                        return;
                    } else {
                        $("#payment_response").addClass("failed");
                        return;
                    }
                });

            }
            else {
                $("#payment_response").addClass("failed");
                $("#payment_response").html("<span class='pay_err_msg'>Please, correct your inputs.</span>");
                return false;
            }


    });


    $("#content").on('click',"#change_password",function() {

        var old_pass = $("#old_pass").val();
        var new_pass = $("#new_pass").val();
        var new_repass = $("#new_repass").val();

        if(new_pass.length < 5) {
            $("#change_password_msg").html("<span class='failed'>Password too short! It must be at least 5 characters</span>");
            return false;
        }

        if (new_pass != new_repass) {
            $("#change_password_msg").html("<span class='failed'>Passwords doesn't matches.</span>");
            return false;
        }

        $.post("/change", {action: "password", old_pass: old_pass, new_pass: new_pass} , function(result) {
            if (result == 'success') {
                $("#change_password_msg").html("<span class='success'>Password changed!</span>");
            }
            else {
                $("#change_password_msg").html("<span class='failed'>Password isn't changed!</span>");
            }
        });
        return false;
    });


    $("#content").on('click',"#change_email",function(e) {


        var new_email = $("#new_email").val();

        if (!isValidEmail(new_email)) {
            $("#change_email_msg").html("<span class='success'>Please, input valid email.</span>");
            return false;
        }

        $.post("/change", {action: "email", new_email: new_email} , function(result) {
            if (result == 'success') {
                $("#change_email_msg").html("<span class='success'>Email changed to "+ new_email +"</span>");
            }
            else {
                $("#change_email_msg").html("<span class='failed'>This email is linked to another account</span>");
            }
        });
        return false;

    });

    $("#content").on("mousedown" ,".show_pass", function() {
        $(this).prev().removeClass("shrink");
        $(this).prev().prev().addClass("shrink");
        $(this).prev().val($(this).prev().prev().val());
    });

    $("#content").on("mouseup" ,".show_pass", function() {
        $(this).prev().addClass("shrink");
        $(this).prev().prev().removeClass("shrink");

    });


});

function isValidPhone(phone) {

    var regex = /^[0-9]{10}$/;
    if (!regex.test(phone)) {
        return false;
    }
    return true;
}

function isValidAmount(amount) {

    var regex = /\d+(?:\.\d{1,2})?/;
    if (!regex.test(amount)) {
        return false;
    }
    return true;
}

function isValidCard(card) {

    var regex = /^[0-9]{16}$/;
    if (!regex.test(card)) {
        return false;
    }
    return true;
}

function isValidDate(month,year) {

    if (isNaN(month) || isNaN(year)) {
        return false;
    }

    if (month > 12 || month < 1) {
        return false;
    }

    var d = new Date();
    var year_now = d.getFullYear() - 2000;
    if (year < year_now) {
        return false;
    }

    if (year == d.getYear() && month< d.getMonth()) {
        return false;
    }

    return true;
}

function isValidEmail(email) {
    var regex = /^([A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,6})$/;
    if (!regex.test(email)) {
        return false;
    }
    return true;
}
