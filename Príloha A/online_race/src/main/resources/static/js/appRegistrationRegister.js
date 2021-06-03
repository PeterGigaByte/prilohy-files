$(document).ready(function () {
    $("#buttonR").click(function () {
        registerAthlete();
    });
    $("#buttonD").click(function () {
        deleteAthlete();
    });
    function registerAthlete(){
        let input={
            athlete:$('#athletes').val(),
            discipline:$('#disciplines').val()
        };
        $.ajax({
            type : "POST",
            contentType : "application/json",
            accept: 'text/plain',
            url : window.location + "/register",
            data : JSON.stringify(input),
            dataType: 'text',
            success: function(result){
                if(result == "success"){
                    new jBox('Notice', {
                        theme: 'NoticeFancy',
                        attributes: {
                            x: 'left',
                            y: 'bottom'
                        },
                        color: "green",
                        content: "Atlét bol úspešne zaregistrovaný!",
                        animation: {
                            open: 'slide:bottom',
                            close: 'slide:left'
                        }
                    });
                }
                if(result == "failure"){
                    new jBox('Notice', {
                        animation: 'flip',
                        color: 'red',
                        content: 'Chyba!! Zlé vstupné údaje!!',
                        delayOnHover: true,
                        showCountdown: true
                    });
                }
                if(result=="error-alreadyExist"){
                    new jBox('Notice', {
                        animation: 'flip',
                        color: 'red',
                        content: 'Chyba!! Atlét už je v danej disciplíne zaregistrovaný!',
                        delayOnHover: true,
                        showCountdown: true
                    });
                }
            },
            error : function(e) {
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'red',
                    content: 'Atlét nebol zaregistrovaný!',
                    delayOnHover: true,
                    showCountdown: true
                });
                console.log(e)
            }
        })
    }
    function deleteAthlete(){
        let input={
            athlete:$('#athletes').val(),
            discipline:$('#disciplines').val()
        };
        $.ajax({
            type : "DELETE",
            contentType : "application/json",
            accept: 'text/plain',
            url : window.location + "/delete",
            data : JSON.stringify(input),
            dataType: 'text',
            success: function(result){
                if(result == "success"){
                    new jBox('Notice', {
                        theme: 'NoticeFancy',
                        attributes: {
                            x: 'left',
                            y: 'bottom'
                        },
                        color: "green",
                        content: "Atlét bol úspešne odhlásený!",
                        animation: {
                            open: 'slide:bottom',
                            close: 'slide:left'
                        }
                    });
                }
                if(result == "failure"){
                    new jBox('Notice', {
                        animation: 'flip',
                        color: 'red',
                        content: 'Chyba!! Zlé vstupné údaje!!',
                        delayOnHover: true,
                        showCountdown: true
                    });
                }
                if(result=="error-NotExist"){
                    new jBox('Notice', {
                        animation: 'flip',
                        color: 'red',
                        content: 'Chyba!! Atlét v danej disciplíne neexistuje!',
                        delayOnHover: true,
                        showCountdown: true
                    });
                }
            },
            error : function(e) {
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'red',
                    content: 'Atlét nebol odhlásený!',
                    delayOnHover: true,
                    showCountdown: true
                });
                console.log(e)
            }
        })
    }
});