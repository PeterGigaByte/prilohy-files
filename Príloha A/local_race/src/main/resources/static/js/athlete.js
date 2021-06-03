var jBoxLogin = {
    jBox: null,
    // The html of each of the content containers
    html: {
        athlete:
            '<div id="LoginContainer-athlete" class="login-container">' +
            '   <div class="login-body">' +
            '       <input type="number" name="id"  id="id" class="login-textfield hidden" >' +
            '       <input type="text" name="firstName" id="firstName" class="login-textfield" placeholder="Meno atléta" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required>' +
            '       <input type="text" name="surname" id="surname" class="login-textfield" placeholder="Priezvisko atléta" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required>' +
            '       <input type="date" name="birth"   id="birth" class="login-textfield" placeholder="Dátum narodenia" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required>' +
            '       <select style="width: 300px; margin-top:30px;margin-bottom:30px"  id="club" name="club" class="login-textfield select-css" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required></select>' +
               ' <input type="radio" id="male" name="gender" value="male" checked>'+
                '<label for="male">Male</label>'+
               ' <input type="radio" id="female" name="gender" value="female">'+
                '<label for="female">Female</label><br>'+
            '       </div>'+
            '       <button id="editAthlete" value="create" style="margin-left: 30%;width: 200px;margin-top: 20px;" type="submit" class="login-button">Vytvoriť</button>' +
            '       <button id="createAthlete" value="edit" style="margin-left: 30%;width: 200px;margin-top: 20px;" type="submit" class="login-button hidden">Editovať</button>' +
            '   </div>' +
            '   <div class="login-footer">' +
            '       <br>' +
            '   </div>' +
            '</div>',
    },
    // Corresponding titles for content elements
    title: {
        athlete: 'Atlét',
    },
    // These tooltips will show when a textelemet gets focus
    textfieldTooltips: {
        firstName : "Meno atléta",
        surname : "Priezvisko atléta",
        birth : "Dátum narodenia",
        club: "Klub",
    },

};
$(document).ready(function () {
    jBoxLogin.jBox = new jBox('Modal', {

        // Unique id for CSS access
        id: 'jBoxLogin',

        // Dimensions
        width: 500,
        height: 500,

        // Attach to elements
        attach: '#addAthlete',

        // Create the content with the html provided in global var
        content: '<form id="athlete"><div id="LoginWrapper">' + jBoxLogin.html.athlete  +'</div></form>',

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
            this.showContent('athlete', true);

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
            this.showContent('athlete', true);
        },
        onClose: function () {
            // Remove error tooltips
            $.each(jBoxLogin.textfieldTooltips, function (id, tt) {
                $('#' + id).data('jBoxTextfieldError') && $('#' + id).data('jBoxTextfieldError').close();
            });
        }
    });
    let idO = $('#id');
    let firstNameO = $('#firstName');
    let surnameO = $('#surname');
    let birthO = $('#birth');
    let addAthlete = $('#addAthlete');
    let table = $('#table').DataTable();
    let clubSelect = $('#club');
    let transfers = new Map();
    addAthlete.click(function () {
       clearForm();
    });
    $("#athlete").submit(function (event) {
        event.preventDefault();
        let formAthlete = {
            gender:$("input[type='radio'][name='gender']:checked").val(),
            id:idO.val(),
            firstName:firstNameO.val(),
            surname:surnameO.val(),
            birth:birthO.val(),
            club:clubSelect.val(),
        };
        ajaxSaveAthlete(formAthlete);
    });
    function ajaxSaveAthlete(formAthlete) {
        $.ajax({
            type:"POST",
            contentType:"application/json",
            accept: 'text/plain',
            url : window.location + "/save",
            data : JSON.stringify(formAthlete),
            dataType: 'text',
            success : function(result) {
                //úspech
                //refreshni tabulku
                refreshAthletesTable();
                clearForm();
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'green',
                    content: 'Atlét bol úspešne uložený.',
                    delayOnHover: true,
                    showCountdown: true
                });
            },
            error : function (e) {
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'red',
                    content: 'Athlét nebol uložený !!',
                    delayOnHover: true,
                    showCountdown: true
                });
                //neúspech
            }
        })
    }
    function refreshAthletesTable() {
        getClubsTransfers(transfers);
        let request = "getAll";
        $.ajax({
            type : "POST",
            contentType : "application/json",
            accept: 'text/plain',
            url : window.location + "/getAll",
            data : JSON.stringify(request),
            success : function(result) {
                table.clear().draw();
                $.each(result, function (i, athlete) {
                    let date = new Date(athlete.dateBirth);
                    let month = +date.getMonth()+1;
                    let club = "";
                    if(transfers.has(athlete.id)){
                        club = transfers.get(athlete.id);
                    }
                    table.row.add([
                        athlete.firstName+' '+athlete.surname,
                        club,
                        date.getDate()+"."+month+"."+date.getFullYear(),
                        '<a href="/athletes/'+athlete.id+'"   ><img  style="width: 35px; height: 35px" alt="active" class="activatedButton" src="/images/Settings-icon.png"></a>',
                        '<a value="'+athlete.id+'" class="deleteButton"  ><img  style="width: 35px; height: 35px" alt="active" class="activatedButton" src="images/delete.png"></a>',

                    ]).draw(false);
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
                    content: "Zoznam atlétov bol aktualizovaný."
                });
                $(".deleteButton").unbind();
                $(".deleteButton").click(function () {
                    let value = this.attributes.item(0).value;
                    new jBox('Confirm', {
                        confirmButton: 'Potvrdiť',
                        cancelButton: 'Zrušiť',
                        content: "Naozaj chcete vymazať tohoto athléta?",
                        confirm: function () {
                            deleteAthlete(value)
                        }
                    }).open();
                })
            },
            error : function (e) {
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
                //neúspech
            }
        })
    }
    function deleteAthlete(id) {
        id = {id: id};
        $.ajax({
            type: "POST",
            contentType: "application/json",
            accept: 'text/plain',
            url: window.location + "/delete",
            data: JSON.stringify(id),
            dataType: 'text',
            success: function (result) {
                new jBox('Notice', {
                        theme: 'NoticeFancy',
                        attributes: {
                            x: 'left',
                            y: 'bottom'
                        },
                        color: "green",
                        content: "Atlét bol úspešne zmazaný.",
                        animation: {
                            open: 'slide:bottom',
                            close: 'slide:left'
                        }
                });
                refreshAthletesTable();
            },
            error: function (e) {
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'red',
                    content: 'Atlét nebol odstránený !!',
                    delayOnHover: true,
                    showCountdown: true
                });
                console.log(e);
            }
        });
    }
    function clearForm() {
         idO.val("");
         firstNameO.val("");
         surnameO.val("");
         birthO.val("");
         clubSelect.val("null");
    }
    $(".deleteButton").click(function () {
        let value = this.attributes.item(0).value;
        new jBox('Confirm', {
            confirmButton: 'Potvrdiť',
            cancelButton: 'Zrušiť',
            content: "Naozaj chcete vymazať tohto pretekára?",
            confirm: function () {
                deleteAthlete(value)
            }
        }).open();
    });
    function setClubs(){
        $.ajax({
            type : "GET",
            url : window.location + "/getAllClubs",
            success: function(result){
                clubSelect.empty();
                clubSelect.append('<option value="'+null+'">'+"Bez klubu"+'</option>');
                $.each(result, function(i, club){
                    clubSelect.append('<option value="'+club.id+'">'+club.clubName+'</option>')
                });
            },
            error : function(e) {
                console.log(e)
            }
        });
    }
    function getClubsTransfers(transfers) {
        $.ajax({
            type : "GET",
            url : window.location + "/getTransfers",
            async:   false,
            success: function(result){
                transfers.clear();
                $.each(result, function(i, transfer){
                    transfers.set(transfer.athlete.id,transfer.club.clubName);
                });

            },
            error : function(e) {
                console.log(e)
            }
        });
    }
    setClubs();

});
