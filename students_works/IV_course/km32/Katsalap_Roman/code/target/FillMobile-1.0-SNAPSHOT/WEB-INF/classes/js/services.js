$(function() {

$(".menu_item").on('click',function() {

    var action = $(this).attr("action");
    //disable menu
    $("#content").load("/services", {action: action});
    //enable menu

})


    $("#apply-filters").on('click',function() {
        var phone = $("[name='phone-filter']").val();
        var card = $("[name='card-filter']").val();
        var min_amount = ($("input[name='min-filter']").val());
        var max_amount = ($("input[name='max-filter']").val());

        var emptyPhone = phone == '';
        var emptyCard = card == '';
        var emptyMin = min_amount == '';
        var emptyMax = max_amount == '';

        if (!(emptyPhone&&emptyCard&&emptyMin&&emptyMax)) {
            $("tr").each(function(tr) {


                var tr_phone = tr.children("td.payment_phone")[0].text();
                if ((tr_phone !== phone)&&(!emptyPhone)) {
                    tr.addClass("hidden");
                }

                var tr_card = tr.children("td.payment_CardNo")[0].text();
                if ((tr_card !== card)&&(!emptyCard)) {
                    tr.addClass("hidden");
                }

                var tr_amount = parseFloat(tr.children("td.payment_amount")[0].text())
                if ((tr_amount < parseFloat(min_amount))&&(!emptyMin)) {
                    tr.addClass("hidden");
                }
                if ((tr_amount > parseFloat(max_amount))&&(!emptyMax)) {
                    tr.addClass("hidden");
                }

            })
        }

    });

    $("#reset-filters").on('click',function() {

        $("tr").each(function(tr) {
            tr.removeClass("hidden");
        });


    });


})
