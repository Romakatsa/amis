$(function() {

    $('#bank-card-select select').val("");




    $("#apply-filter-button").on('click',function() {
        $("tr").removeClass("hidden");
        var phone = $("[name='phone-filter']").val();
        var card = $("[name='card-filter']").val();
        var min_amount = ($("input[name='min-filter']").val());
        var max_amount = ($("input[name='max-filter']").val());
        var from_date = $("input[name='from-date']").val();
        var till_date = $("input[name='till-date']").val();

        var emptyPhone = phone == '';
        var emptyCard = card == '';
        var emptyMin = min_amount == '';
        var emptyMax = max_amount == '';
        var emptyFromDate = from_date == '';
        var emptyTillDate = till_date == '';

        var from_date_parts = from_date.split(".");
        var till_date_parts = till_date.split(".");

        if (!(emptyPhone&&emptyCard&&emptyMin&&emptyMax&&emptyFromDate&&emptyTillDate)) {
            $("tr").each(function(i) {

                if (i != 0) {
                    var tr_phone = $(this).find("td.payment_phone").text();
                    if ((tr_phone !== phone) && (!emptyPhone)) {
                        $(this).addClass("hidden");
                    }

                    var tr_card = $(this).find("td.payment_CardNo").text().substring(15);
                    if ((tr_card !== card) && (!emptyCard)) {
                        $(this).addClass("hidden");
                    }

                    var tr_amount = parseFloat($(this).find("td.payment_amount").text())
                    if ((tr_amount < parseFloat(min_amount)) && (!emptyMin)) {
                        $(this).addClass("hidden");
                    }
                    if ((tr_amount > parseFloat(max_amount)) && (!emptyMax)) {
                        $(this).addClass("hidden");
                    }

                    if (!emptyFromDate || !emptyTillDate) {
                        var tr_date = $(this).find("td.payment_date").text();
                        var datetime_parts = tr_date.split(",");
                        var table_date = datetime_parts[0];
                        var date_parts = table_date.split(".");
                    }

                    if (!emptyFromDate) {


                        if (new Date(date_parts[2], date_parts[1], date_parts[0]) < new Date(from_date_parts[2], from_date_parts[1], from_date_parts[0])) {
                            $(this).addClass("hidden");
                        }
                    }

                    if (!emptyTillDate) {

                        if (new Date(date_parts[2], date_parts[1], date_parts[0]) > new Date(till_date_parts[2], till_date_parts[1], till_date_parts[0])) {
                            $(this).addClass("hidden");
                        }
                    }


                }

            });

        }
        return true;

    });

    $("#reset-filters").on('click',function() {
        $("tr").each(function(tr) {
            $(this).removeClass("hidden");
        });
        $("[name='phone-filter']").val("");
        $("[name='card-filter']").val("");
        $("input[name='min-filter']").val("");
        $("input[name='max-filter']").val("");
        $("input[name='from-date']").val("");
        $("input[name='till-date']").val("");
    });


    $(".menu_item").on('click',function() {
        var that = $(this);
        var action = $(this).attr("action");
        //disable menu
        $("#content").load("/services", {action: action}, function() {
            $(".menu_item").removeClass('menu_selected');
            that.addClass('menu_selected');
            return false;
        });
        return false;
        //enable menu
    });


    $('html').on("click",function(e) {
        $('#phones_drop_list').removeClass('open');
    });
    $(document).on("click","#phone_input",function(e) {
        e.stopPropagation();
        if( $('#phones_drop_list').hasClass('open') ){
            $('#phones_drop_list').removeClass('open');
        }else{
            $('#phones_drop_list').addClass('open');
        }
    });
    $(document).on("click",".phone_span_container",function() {
        var phone = $(this).children(':first').attr('value');
        $('#phone_input').val(phone);
        //This is now the value of your hidden input field
    });

    $("#phone-reset").on("click",function() {
        $('#phone_input').val("");
    });

    $("#card-reset").on("click",function() {
        $('#bank-card-select select').val("");
        $("#card_requisites").removeClass("disable");
        document.getElementById("card_input").disabled = false;
        document.getElementById("month_select").disabled = false;
        document.getElementById("year_select").disabled = false;
        document.getElementById("cvv_input").disabled = false;
    });

    $("select[name='card_select']").on("change", function() {
        $("#card_requisites").addClass("disable");

        document.getElementById("card_input").disabled = true;
        document.getElementById("month_select").disabled = true;
        document.getElementById("year_select").disabled = true;
        document.getElementById("cvv_input").disabled = true;

    });

    /*
        $("#phone_input").on("blur", function() {

        });
        */


});
