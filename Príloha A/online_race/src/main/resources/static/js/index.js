
//pridať závod Listener

//addRace.addEventListener("click",showAddRace);
//
function ajaxPostGetRaceById(id){
    // DO POST
    id= {
        id:id
    };
    $.ajax({
        type : "POST",
        contentType : "application/json",
        accept: 'text/plain',
        url : window.location + "races/findRace",
        data : JSON.stringify(id),
        dataType: 'json',
        success : function(result) {
            setData(result);
            jBoxLogin.jBox.open();
        },
        error : function(e) {
            new jBox('Notice', {
                animation: 'flip',
                color: 'red',
                content: 'Atletická súťaž nebola nájdená     !!',
                delayOnHover: true,
                showCountdown: true
            });
            console.log("ERROR: ", e);
        }
    });
}
function setData(race){

    $("#id2").val(race.id);
    $("#raceName2").val(race.raceName);
    $("#place2").val(race.place);
    $("#organizer2").val(race.organizer);
    $("#resultsManager2").val(race.resultsManager);
    $("#phone2").val(race.phone);
    $("#startDate2").val(race.startDate);                             //
    $("#endDate2").val(race.endDate);                                //
    if(race.settings.typeRace == 1){
        $("#raceType2").prop( "checked", true );}
    else{
        $("#raceType2").prop( "checked", false );}
    //
    $("#director2").val(race.director);
    $("#arbitrator2").val(race.arbitrator);
    $("#technicalDelegate2").val(race.technicalDelegate);
    $("#note2").val(race.note);
    if(race.settings.outCompetition == 1){
        $("#outCompetition2").prop( "checked", true );}
    else{
        $("#outCompetition2").prop( "checked", false );}
    if(race.settings.reactions == 1){
        $("#reactions2").prop( "checked", true );}
    else{
        $("#reactions2").prop( "checked", false );}
    $("#numberOfTracks2").val(race.settings.track.numberOfTracks);
    $('.login-button').text("Editovať");
}





// Playground Demo: Login

// We are setting up a global variable where we can adjust html and texts

var jBoxLogin = {
    jBox: null,

    // The html of each of the content containers

    html: {
        newRace:
            '<div id="LoginContainer-newRace" class="login-container">' +
            '   <div class="login-body">' +
            '       <input type="text" id="id2" class="login-textfield hidden" >' +
            '       <input type="text" id="raceName2" class="login-textfield" placeholder="Názov závodu" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required>' +
            '       <input type="text" id="place2" class="login-textfield" placeholder="Miesto" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required>' +
            '       <input type="text" id="organizer2" class="login-textfield" placeholder="Organizátor" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required>' +
            '       <input type="text" id="resultsManager2" class="login-textfield" placeholder="Spracovateľ výsledkov" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required>' +
            '       <input type="tel" pattern="[+][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]" id="phone2" class="login-textfield" placeholder="Telefón" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" >' +
            '       <input type="date" id="startDate2" class="login-textfield" placeholder="Dátum začiatku" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required>' +
            '       <input type="date" id="endDate2" class="login-textfield" placeholder="Dátum konca" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required>' +
            '       <div class="form-check">' + '<span style="display: inline; float: left; margin-top: 5px;">Halová sezóna\t&nbsp;\t&nbsp;</span>' +
            '       <input style="width: 25px; height: 25px;display: block;   ;margin-top: 20px;" id="raceType2" class="form-check-input" type="checkbox" data-val="true"  value="true" id="flexCheckDefault">'+
            '       </div>'+
            '       <button style="margin: 8px;" type="submit" class="login-button">Vytvoriť</button>' +
            '   </div>' +
            '   <div class="login-footer">' +
            '       <span onclick="jBoxLogin.jBox.showContent(\'details\')">Detaily</span>' +
            '       <br>' +
            '       <span onclick="jBoxLogin.jBox.showContent(\'settings\')">Nastavenia</span>' +
            '       <br>' +
            '   </div>' +
            '</div>',

        details: '<div id="LoginContainer-details" class="login-container">' +
            '           <div class="login-body">' +
            '               <input type="text" id="director2" class="login-textfield" placeholder="Riaditeľ" autocomplete="off"  autocorrect="off" autocapitalize="off" spellcheck="false">' +
            '               <input type="text" id="arbitrator2" class="login-textfield" placeholder="Rozhodca" autocomplete="off"  autocorrect="off" autocapitalize="off" spellcheck="false">' +
            '               <input type="text" id="technicalDelegate2" class="login-textfield" placeholder="Technický delegát" autocomplete="off"  autocorrect="off" autocapitalize="off" spellcheck="false">' +
            '               <input type="text" id="note2" class="login-textfield" placeholder="Poznámka" autocomplete="off"  autocorrect="off" autocapitalize="off" spellcheck="false">' +
            '           </div>' +
            '       <div class="login-footer">' +
            '           <span onclick="jBoxLogin.jBox.showContent(\'newRace\')">Vytvorenie závodu</span>' +
            '           <br>' +
            '           <span onclick="jBoxLogin.jBox.showContent(\'settings\')">Nastavenia</span>' +
            '           <br>' +
            '       </div>' +
            '       </div>',
        settings: '<div id="LoginContainer-settings" class="login-container">' +
            '           <div class="login-body">' +
            '               <select id="cameraType2" class="login-textfield" placeholder="Camera" autocomplete="off"  autocorrect="off" autocapitalize="off" spellcheck="false">' +
            '               <option value="omega">Omega</option>'   +
            '               </select>' +
            '               <select id="typeScoring2" class="login-textfield" placeholder="Scoring" autocomplete="off"  autocorrect="off" autocapitalize="off" spellcheck="false">' +
            '               <option value="comptetitor_race">Súťaž jednotlivcov</option>'   +
            '               <option value="club_competition">Súťaž klubov</option>'   +
            '               </select>' +
            '       <div class="login-remember">' +
            '       <div class="form-check">' +
            '<label> Pretekári mimo súťaž na koniec'+
            '       <input style="width: 25px; height: 25px" id="outCompetition2" class="form-check-input" type="checkbox" data-val="true"  value="true"id="flexCheckDefault">'+
            '</label> '+
            '       </div>'+
            '       <div class="form-check">' +
            '<label> Zobrazovanie reakcií'+
            '       <input style="width: 25px; height: 25px" id="reactions2" class="form-check-input" type="checkbox" data-val="true"  value="true" id="flexCheckDefault">'+
            '</label>'+
            '       </div>'+
            '       <input type="number" id="numberOfTracks2" class="login-textfield" min="1" max="10" placeholder="8" value="8" autocomplete="off"  autocorrect="off" autocapitalize="off" spellcheck="false">' +
            '       </div>' +
            '           </div>' +

                '       <div class="login-footer">' +
                '           <span onclick="jBoxLogin.jBox.showContent(\'newRace\')">Vytvorenie závodu</span>' +
                '           <br>' +
                '           <span onclick="jBoxLogin.jBox.showContent(\'details\')">Detaily</span>' +
                '           <br>' +
                '       </div>' +

            '       </div>'

    },

    // Corresponding titles for content elements

    title: {
        newRace: 'Atletická súťaž',
        details: 'Detaily',
        settings: 'Nastavenia'

    },

    // These tooltips will show when a textelemet gets focus

    textfieldTooltips: {
        raceName2: 'Názov závodu',
        place2: 'Miesto preteku',
        organizer2: 'Organizátor',
        resultsManager2: 'Spracovateľ výsledkov',
        phone2: 'Telefónny kontakt',
        startDate2: 'Začiatok preteku',
        endDate2: 'Koniec preteku',
        raceType2: 'Halová sezóna',
        director2: 'Riaditeľ preteku',
        arbitrator2: 'Hlavný rozhodca',
        technicalDelegate2: 'Technický delegát',
        note2: 'Poznámky',
        cameraType2: 'Typ kamery',
        typeScoring2: 'Typ závodu',
        outCompetition2: 'Pretekári mimo súťaž na koniec',
        reactions2: 'Zobrazovanie reakcií',
        numberOfTracks2: 'Počet dráh'
    }
};

$(document).ready(function () {
    function ajaxPostGetRaceById(id){
        // DO POST
        id= {
            id:id
        };
        $.ajax({
            type : "POST",
            contentType : "application/json",
            accept: 'text/plain',
            url : window.location + "races/findRace",
            data : JSON.stringify(id),
            dataType: 'text',
            success : function(result) {
                setData(result);
                jBoxLogin.jBox.open();
            },
            error : function(e) {
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'red',
                    content: 'Atletická súťaž nebola nájdená !!',
                    delayOnHover: true,
                    showCountdown: true
                });
                console.log("ERROR: ", e);
            }
        });
    }
         let table_main = $('#table').DataTable({
                "columnDefs": [
                    { type: 'de_date', targets: 0 },

                    {
                    "targets": [4, 5 ,6],
                    "orderable": false
                 } ]
            }

        );
            if (!$('#addRace').length) {
                return;
            }

            // On domready create the login modal

            jBoxLogin.jBox = new jBox('Modal', {

                // Unique id for CSS access
                id: 'jBoxLogin',

        // Dimensions
        width: 450,
        height: 650,

        // Attach to elements
        attach: '#addRace',

        // Create the content with the html provided in global var
        content: '<form id="createNewRace"><div id="LoginWrapper">' + jBoxLogin.html.newRace + jBoxLogin.html.details + jBoxLogin.html.settings +'</div></form>',

        // Adjust header when scroll is blocked
        blockScrollAdjust: ['header'],

        // When the jBox is being initialized add internal functions
        onInit: function () {

            // Internal function to show content
            this.showContent = function (id, force) {

                // Abort if an ajax call is loading
                if (!force && $('#LoginWrapper').hasClass('request-running')) return null;

                // Set the title depending on id
                this.setTitle(jBoxLogin.title[id]);

                // Show content depending on id
                $('.login-container.active').removeClass('active');
                $('#LoginContainer-' + id).addClass('active');

                // Remove error tooltips
                $.each(jBoxLogin.textfieldTooltips, function (id, tt) {
                    $('#' + id).data('jBoxTextfieldError') && $('#' + id).data('jBoxTextfieldError').close();
                });

            };

            // Initially show content for login
            this.showContent('details', true);

            // Add focus and blur events to textfields
            $.each(jBoxLogin.textfieldTooltips, function (id, tt) {

                // Focus an textelement
                $('#' + id).on('focus', function () {

                    // When there is an error tooltip close it
                    $(this).data('jBoxTextfieldError') && $(this).data('jBoxTextfieldError').close();

                    // Remove the error state from the textfield
                    $(this).removeClass('textfield-error');

                    // Store the tooltip jBox in the elements data
                    if (!$(this).data('jBoxTextfieldTooltip')) {
                        $(this).data('jBoxTextfieldTooltip', new jBox('Tooltip', {
                            width: 310,
                            theme: 'TooltipSmall',
                            addClass: 'LoginTooltipSmall',
                            target: $(this),
                            position: {
                                x: 'left',
                                y: 'top'
                            },
                            outside: 'y',
                            offset: {
                                y: 6,
                                x: 8
                            },
                            pointer: 'left:17',
                            content: tt,
                            animation: 'move'
                        }));
                    }

                    $(this).data('jBoxTextfieldTooltip').open();

                    // Loose focus of textelement
                }).on('blur', function () {
                    $(this).data('jBoxTextfieldTooltip').close();
                });
            });

            // Internal function to show errors
            this.showError = function (element, message) {

                if (!element.data('errorTooltip')) {
                    element.data('errorTooltip', new jBox('Tooltip', {
                        width: 310,
                        theme: 'TooltipError',
                        addClass: 'LoginTooltipError',
                        target: element,
                        position: {
                            x: 'left',
                            y: 'top'
                        },
                        outside: 'y',
                        offset: {
                            y: 6
                        },
                        pointer: 'left:9',
                        content: message,
                        animation: 'move'
                    }));
                }

                element.data('errorTooltip').open();
            };

            // Internal function to change checkbox state
            this.toggleCheckbox = function () {
                // Abort if an ajax call is loading
                if ($('#LoginWrapper').hasClass('request-running')) return null;

                $('.login-checkbox').toggleClass('login-checkbox-active');
            };

            // Add checkbox events to checkbox and label
            $('.login-checkbox, .login-checkbox-label').on('click', function () {
                this.toggleCheckbox();
            }.bind(this));

            // Parse an ajax repsonse
            this.parseResponse = function (response) {
                try {
                    response = JSON.parse(response.responseText || response);
                } catch (e) {}
                return response;
            };

            // Show a global error
            this.globalError = function () {
                new jBox('Notice', {
                    color: 'red',
                    content: 'Oops, something went wrong.',
                    attributes: {
                        x: 'right',
                        y: 'bottom'
                    }
                });
            };

            // Internal function to disable or enable the form while request is running
            this.startRequest = function () {
                this.toggleRequest();
            }.bind(this);

            this.completeRequest = function () {
                this.toggleRequest(true);
            }.bind(this);

            this.toggleRequest = function (enable) {
                $('#LoginWrapper')[enable ? 'removeClass' : 'addClass']('request-running');
                $('#LoginWrapper button')[enable ? 'removeClass' : 'addClass']('loading-bar');
                $('#LoginWrapper input, #LoginWrapper button').attr('disabled', enable ? false : 'disabled');
            }.bind(this);
            // Bind ajax login function to login button
        },
        onOpen: function () {
            // Go back to login when we open the modal
            this.showContent('newRace', true);
        },
        onClose: function () {
            // Remove error tooltips
            $.each(jBoxLogin.textfieldTooltips, function (id, tt) {
                $('#' + id).data('jBoxTextfieldError') && $('#' + id).data('jBoxTextfieldError').close();
            });
        }
    });
    $("#createNewRace").submit(function(event){
        event.preventDefault();
        let formRace = {
            id: $("#id2").val(),
            raceName : $("#raceName2").val(),
            place : $("#place2").val(),
            organizer : $("#organizer2").val(),
            resultsManager : $("#resultsManager2").val(),
            phone : $("#phone2").val(),
            startDate : $("#startDate2").val(),
            endDate : $("#endDate2").val(),
            raceType : $("#raceType2").is(":checked"),
            director : $("#director2").val(),
            arbitrator : $("#arbitrator2").val(),
            technicalDelegate : $("#technicalDelegate2").val(),
            note : $("#note2").val(),
            cameraType : $("#cameraType2").val(),
            typeScoring : $("#typeScoring2").val(),
            outCompetition : $("#outCompetition2").is(":checked"),
            reactions : $("#reactions2").is(":checked"),
            numberOfTracks : $("#numberOfTracks2").val(),
        };
        resetData();
        jBoxLogin.jBox.close();
        ajaxPost();





        function ajaxPost(){

            // DO POST
            $.ajax({
                type : "POST",
                contentType : "application/json",
                accept: 'text/plain',
                url : window.location + "races/save",
                data : JSON.stringify(formRace),
                dataType: 'text',
                success : function(result) {
                    if(result=="Post update Successfully"){
                        new jBox('Notice', {
                            animation: 'flip',
                            color: 'green',
                            content: 'Atletická súťaž bola úspešne aktualizovaná',
                            delayOnHover: true,
                            showCountdown: true
                        });
                        ajaxGet();
                    }
                    else if(result=="Post failed because startDate is after endDate"){
                        new jBox('Notice', {
                            animation: 'flip',
                            color: 'red',
                            content: 'Chyba!! Dátum začiatku nesmie byť po dátume konca !!',
                            delayOnHover: true,
                            showCountdown: true
                        });
                    }
                    else{
                        new jBox('Notice', {
                            animation: 'flip',
                            color: 'green',
                            content: 'Atletická súťaž bola úspešne vytvorená',
                            delayOnHover: true,
                            showCountdown: true
                        });
                        ajaxGet();
                    }

                },
                error : function(e) {
                    new jBox('Notice', {
                        animation: 'flip',
                        color: 'red',
                        content: 'Bohužial, Atletickú súťaž sa nepodarilo vytvoriť',
                        delayOnHover: true,
                        showCountdown: true
                    });
                    console.log("ERROR: ", e);
                }
            });
        }

    });
    function ajaxPostDelete(id){
        id = {
            id:id
        };
        $.ajax({
            type : "POST",
            contentType : "application/json",
            accept: 'text/plain',
            url : window.location + "races/delete",
            data : JSON.stringify(id),
            dataType: 'text',
            success : function(result) {
                new jBox('Notice', {
                    theme: 'NoticeFancy',
                    attributes: {
                        x: 'left',
                        y: 'bottom'
                    },
                    color: "green",
                    content: "Atletická súťaž bola úspešne zmazaná",
                    animation: {
                        open: 'slide:bottom',
                        close: 'slide:left'
                    }
                });
                ajaxGet();
                if(id.id == $("#activeRaceId").attr("value")){
                    let date = new Date();
                    let d = date.getDate();
                    let m = date.getMonth()+1;
                    let y = date.getFullYear();
                    $("#activeRaceName").text("Žiadny aktívny závod");
                    $("#activeRacePlace").text("xxx");
                    $("#activeRaceStartDate").text(d+"."+m+"."+y);
                    $("#activeRaceEndDate").text(d+"."+m+"."+y);
                }
            },
            error : function(e) {
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'red',
                    content: 'Atletická súťaž nebola odstránená !!',
                    delayOnHover: true,
                    showCountdown: true
                });
                console.log("ERROR: ", e);
            }
        });
    }

    function resetData(){
        $("#id2").val("");
        $("#raceName2").val("");
        $("#place2").val("");
        $("#organizer2").val("");
        $("#resultsManager2").val("");
        $("#phone2").val("");
        $("#startDate2").val("");
        $("#endDate2").val("");
        $("#raceType2").prop( "checked", false );
        $("#director2").val("");
        $("#arbitrator2").val("");
        $("#technicalDelegate2").val("");
        $("#note2").val("");
        $("#outCompetition2").prop( "checked", false );
        $("#reactions2").prop( "checked", false );
        $("#numberOfTracks2").val("8");
        $('.login-button').text("Vytvoriť");
    }




    function ajaxGet(){
        $.ajax({
            type : "GET",
            url : window.location + "races/all",
            success: function(result){
                table_main.clear()
                    .draw();
                var text = "";
                $.each(result, function(i, race){
                    if(race.activity==1){
                        text ='<img alt="active" class="activeButton" src="/images/activated.png">';
                    }else{
                        text ='<a class="activeButtons"  data-confirm="Naozaj chcete zmeniť aktivitu preteku?" href="/activeRace/'+race.id+'"><img alt="active" class="activeButton" src="/images/active.png"></a>';
                    }
                    let date = new Date(race.startDate);
                    let month = +date.getMonth()+1;
                        table_main.row.add([
                            date.getDate()+"."+month+"."+date.getFullYear(),
                        race.raceName,
                        race.place,
                        race.organizer,
                        text,
                        '<a   onclick="ajaxPostGetRaceById('+race.id+')"><img th:type="button" alt="active"  class="activatedButton" src="/images/edit.png"></a>',
                        '<a   th:value="'+race.id+'" class="deleteButton"><img alt="active" class="activatedButton" src="/images/delete.png"></a>'
                    ]).draw(false);
                    $(".deleteButton").unbind();
                    $(".deleteButton").click(function () {
                        let value = this.attributes.item(0).value;
                        new jBox('Confirm', {
                            confirmButton: 'Potvrdiť',
                            cancelButton: 'Zrušiť',
                            content: "Naozaj chcete vymazať túto atletickú súťaž?",
                            confirm: function () {
                                ajaxPostDelete(value);
                            }
                        }).open();

                    });
                    if(race.id == $("#activeRaceId").attr("value")){
                        let startDate = race.startDate;
                        let endDate = race.endDate;
                        startDate = startDate.split("-");endDate = endDate.split("-");
                        $("#activeRaceName").text(race.raceName);
                        $("#activeRacePlace").text(race.place);
                        $("#activeRaceStartDate").text(startDate[2]+"."+startDate[1]+"."+startDate[0]);
                        $("#activeRaceEndDate").text(endDate[2]+"."+endDate[1]+"."+endDate[0]);
                    }


                });
                new jBox('Confirm', {
                    confirmButton: 'Potvrdiť',
                    cancelButton: 'Zrušiť'
                });


                new jBox('Notice', {
                    attributes: {
                        x: 'right',
                        y: 'bottom'
                    },
                    stack: false,
                    animation: {
                        open: 'tada',
                        close: 'zoomIn'
                    },
                    color: "green",
                    title: "Refresh",
                    content: "Zoznam pretekov bol aktualizovaný"
                });
            },
            error : function(e) {
                new jBox('Notice', {
                    attributes: {
                        x: 'right',
                        y: 'bottom'
                    },
                    stack: false,
                    animation: {
                        open: 'tada',
                        close: 'zoomIn'
                    },
                    color: "red",
                    title: "Refresh",
                    content: "Zoznam pretekov nebol aktualizovaný"
                });
                console.log("ERROR: ", e);
            }
        });
    }
    new jBox('Confirm', {
        confirmButton: 'Potvrdiť',
        cancelButton: 'Zrušiť'
    });
    $("#addRace").click(function () {
        resetData();
    });
    $(".deleteButton").click(function () {
        let value = this.attributes.item(0).value;

        new jBox('Confirm', {
            confirmButton: 'Potvrdiť',
            cancelButton: 'Zrušiť',
            content: "Naozaj chcete vymazať túto atletickú súťaž?",
            confirm: function () {
                ajaxPostDelete(value);
            }
        }).open();
    })
});



