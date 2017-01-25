$(function() {


    $("#reg input[type='submit']").on('click',function(e){
       e.preventDefault();

        $("#reg-msg").removeClass("success");
        $("#reg-msg").removeClass("failed");
        $("#reg-msg").html("");
        var pass = $("#inputPass").val();
        var repass = $("#inputRePass").val();
        if(pass != repass){
            $("#inputPass").parent().addClass("invalid");
            $("#inputRePass").parent().addClass("invalid");
            return;
        }
        else {
            var prevent = false;
            var login = $("#inputLogin").val();
            var inputEmail = $("#inputEmail").val();

            if (!isValidLogin(login)) {
                $("#inputLogin").parent()[0].className = "form-group";
                $("#inputLogin").parent().addClass("invalid");
                prevent = true;
            }
            if (!isValidEmail(inputEmail)) {
                $("#inputEmail").parent()[0].className = "form-group";
                $("#inputEmail").parent().addClass("invalid");
                prevent = true;
            }
            if (pass.length <= 3) {
                $("#inputPass").parent()[0].className = "form-group";
                $("#inputPass").parent().addClass("invalid");
                prevent = true;
            }

            if (!prevent) {

                $.post("/reg", {email: inputEmail, login: login, password: pass}, function (result) {
                   var answer = JSON.parse(result);

                   $("#reg-msg").html(answer.message);

                   if (answer.success) {
                       $("#reg-msg").addClass("success");
                       return true;
                   } else {
                       $("#reg-msg").addClass("failed");
                       return false;
                   }



                   /* if (result. == 'fail') {
                        $("#reg").html("<p class='fail'>Failed to register!</p>");
                    }
                    else {
                        $("#reg").html("<p class='success'>Success! Confirmation link: <a href='"+result+"'></a>"+ result +"</p>");
                    }
                    */
                });

            }
            else {
                $("#reg-msg").addClass("failed");
                $("#reg-msg").html("<span class='reg_err_msg'>Please, correct your inputs.</span>");
                return false;
            }

        }
    });


   $("#inputLogin").change(function() {
       var login = $(this).val();
        $("#inputLogin").parent()[0].className = 'form-group';
       if (isValidLogin(login)) {

           $.ajax({
               url:"/reg",
               method: "get",
               data: {
                   check:"login",
                   login:login
               },
               beforeSend: function() {
                   $("#inputLogin").parent().addClass('wait');
               }

           })
               .done(function(result) {
                   $("#inputLogin").parent()[0].className = "form-group";
                   $("#inputLogin").parent().addClass(result);
               });




       }
       else {
           $("#inputLogin").parent().addClass("invalid");
           //$("#login_msg").addClass("invalid");
       }


   });


    $("#inputEmail").change(function() {
        var email = $(this).val();
        $("#inputEmail").parent()[0].className = 'form-group';
        if (isValidEmail(email)) {

            $.ajax({
                url:"/reg",
                method: "get",
                data: {
                        check:"email",
                        email:email
                },
                beforeSend: function() {
                    $("#inputEmail").parent().addClass('wait');
                }

            })
                .done(function(result) {
                    $("#inputEmail").parent()[0].className = "form-group";
                    $("#inputEmail").parent().addClass(result);

                });

        }
        else {
            $("#inputEmail").parent().addClass("invalid");
            //$("#login_msg").addClass("invalid");
        }


    });

    $("#inputPass").change(function() {
        $("#inputPass").parent()[0].className = 'form-group';
        $("#inputRePass").parent()[0].className = 'form-group';
        if ($(this).val().length > 3) {
                $("#inputPass").parent().addClass('ok');
        }
        else {
            $("#inputPass").parent().addClass("invalid");
            //$("#inputRePass").parent().addClass("invalid");
            //$("#pass_msg").addClass("invalid");
        }

    });

    /*
     $("#forgot_pass").on("click",function() {

     $.post("/restore", function(answer) {
     $("#content").html(answer);
     });

     });
     */


    $("#forgot_pass").on("click",function() {


            $("#content").load("/restore");
    });

    $("#content").on("click","#restore input[type='submit']",function() {
        var login = $("#restore_login").val();
        $.post("/restore", {login: login}, function (result) {

                $("#restore-result").html(result);

        });
        return false;
    });

    $("content").on("click","#resend_restore",function() {
        var login = $(this).attr("login");
        $.post("/restore",{login:login}, function(result) {
            $("#restore-result").html(result);
        });
    });

    $("content").on("click","#resend_confirmation",function() {
        var login = $(this).attr("login");
        $.post("/confirm",{login:login, action:"resend"}, function(result) {

            if (result == 'fail') {
                $("#reg").html("<p class='fail'>Failed to resend confirmation link!</p>");
            }
            else {
                $("#reg").html("<p class='success'>Success! Confirmation link: <a href='"+result+"'></a>"+ result +"</p>");
            }

        });
    });



    $("#logout").on("click",function() {
       $.get("/login", {action:"logout"}, function() {
           window.location.href = "http://localhost:8080/";
       });
    });

});



function isValidLogin(login) {
    var regex = /^[A-Za-z0-9_\.\-]{4,30}$/;
    if (!regex.test(login)) {
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



