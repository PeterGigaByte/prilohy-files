$(document).ready(function () {
    let loading = $("#loading");
    let overlay = $("#overlay");
    $("#uploadDBS").click(function () {
        new jBox('Confirm', {
            confirmButton: 'Potvrdiť',
            cancelButton: 'Zrušiť',
            content: "Naozaj chcete nahrať databázu?",
            confirm: function () {
                uploadDbsAjax();
            }
        }).open();
    });
    $(".downloadButton").click(function () {
        let id =this.attributes.item(0).value;
        new jBox('Confirm', {
            confirmButton: 'Potvrdiť',
            cancelButton: 'Zrušiť',
            content: "Naozaj chcete stiahnuť túto databázu? Zmažete tým aktuálnu!",
            confirm: function () {
                downloadDatabaseAjax(id);
            }
        }).open();
    });
    function downloadDatabaseAjax(id) {
        loading.addClass("visible");
        overlay.addClass("visible");
        $.ajax({
            type : "POST",
            contentType : "application/json",
            accept: 'text/plain',
            url : window.location + "/download/dbs/"+id+"/deleteOld",
            success: function(result){
                console.log(result);
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'green',
                    content: 'Databáza bola úspešne stiahnutá.',
                    delayOnHover: true,
                    showCountdown: true
                });
            },
            complete: function (){
                loading.removeClass("visible");
                overlay.removeClass("visible");
            },
            error : function(e) {
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'red',
                    content: 'Databázu sa nepodarilo stiahnuť !!',
                    delayOnHover: true,
                    showCountdown: true
                });
                console.log(e);
            }
        });
    }
    function uploadDbsAjax() {
        loading.addClass("visible");
        overlay.addClass("visible");
        $.ajax({
            type : "POST",
            contentType : "application/json",
            accept: 'text/plain',
            url : window.location + "/upload/dbs",
            success: function(result){
                console.log(result);
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'green',
                    content: 'Databáza bola úspešne nahraná.',
                    delayOnHover: true,
                    showCountdown: true
                });
            },
            complete: function (){
                loading.removeClass("visible");
                overlay.removeClass("visible");
            },
            error : function(e) {
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'red',
                    content: 'Databázu sa nepodarilo nahrať !!',
                    delayOnHover: true,
                    showCountdown: true
                });
                console.log(e);
            }
        });
    }
    $('#table').DataTable({
            "columnDefs": [
                { type: 'de_date', targets: 0 },

                {
                    "targets": [4],
                    "orderable": false
                } ]
        }

    );
});