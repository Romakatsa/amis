$(function() {


    $("#reg input[type='submit']").on('click',function(e){
       e.preventDefault();
        var pass = $("#inputPass").val();
        var repass = $("#inputRePass").val();
        if(pass != repass){
            $("#inputPass").parent().addClass("invalid");
            $("#inputRePass").parent().addClass("invalid");
            return;
        }
        else {
            var login = $("#inputLogin").val();
            var inputEmail = $("#inputEmail").val();

            if (!isValidLogin(login)) {
                $("#inputLogin").parent().addClass("invalid");
            }
            if (isValidEmail(inputEmail)) {
                $("#inputEmail").parent().addClass("invalid");
            }
            if (pass.length > 3) {
                $("#inputPass").parent().addClass("invalid");
            }



            $.post("/reg",{email:inputEmail,login:login,password:pass},function(result) {
                if (result == 'success') {
                    $("#reg").html("<p class='success'>Success!</p>");
                }
                else {
                    $("#reg").html("<p class='fail'>Failed to register!</p>");
                }
            });
        }
    });


   $("#inputLogin").change(function() {
       var login = $(this).val();
        $("#inputLogin").parent()[0].className = 'form-group';
       if (isValidLogin(login)) {
           $.get("/reg",{check: "login", login:login},function( result ) {
               $("#inputLogin").parent()[0].className = "form-group";
               $("#inputLogin").parent().addClass(result);
           })
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
            $.get("/reg",{check: "email" , email:email},function( result ) {
                $("#inputEmail").parent().addClass(result);
                alert("email" + result);
            })
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



