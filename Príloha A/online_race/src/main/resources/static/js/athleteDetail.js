var jBoxLogin = {
    jBox: null,
    // The html of each of the content containers
    html: {
        transfer:
            '<div id="LoginContainer-transfer" class="login-container">' +
            '   <div class="login-body">' +
            '       <input type="text" name="reason" id="reason" class="login-textfield" placeholder="Dôvod" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required>' +
            '       <input type="date" name="since" id="since" class="login-textfield" placeholder="Odkedy" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required>' +
            '       <input type="date" name="to"   id="to" class="login-textfield" placeholder="Dokedy" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" >' +
            '       <select  style="width: 300px;margin-left: 12%; margin-top:30px"  id="clubSelect" name="clubSelect" class="login-textfield select-css" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required></select>' +
            '       </div>'+
            '       <button id="createTransfer" value="create" style="margin-left: 26%;width: 200px;margin-top: 20px;" type="submit" class="login-button">Vytvoriť transfer</button>' +
            '   </div>' +
            '   <div class="login-footer">' +
            '       <br>' +
            '   </div>' +
            '</div>',
    },
    // Corresponding titles for content elements
    title: {
        transfer: 'Pridať prestup',
    },
    // These tooltips will show when a textelemet gets focus
    textfieldTooltips: {
        reason : "Dôvod prestupu",
        since : "Odkedy",
        to : "Dokedy",
        clubSelect: "Klub",
    },
};
$(document).ready(function () {

   $("#tableResults").DataTable();
   $("#tableClubs").DataTable();


    jBoxLogin.jBox = new jBox('Modal', {

        // Unique id for CSS access
        id: 'jBoxLogin',

        // Dimensions
        width: 500,
        height: 450,

        // Attach to elements
        attach: '#addTransaction',

        // Create the content with the html provided in global var
        content: '<form id="addTransfer"><div id="LoginWrapper">' + jBoxLogin.html.transfer +'</div></form>',

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
            this.showContent('transfer', true);

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
            this.showContent('transfer', true);
        },
        onClose: function () {
            // Remove error tooltips
            $.each(jBoxLogin.textfieldTooltips, function (id, tt) {
                $('#' + id).data('jBoxTextfieldError') && $('#' + id).data('jBoxTextfieldError').close();
            });
        }
    });
    let clubSelect = $("#clubSelect");
    let reason = $("#reason");
    let since = $("#since");
    let to = $("#to");
    function setClubs(){
        $.ajax({
            type : "GET",
            url : "/athletes/getAllClubs",
            success: function(result){
                clubSelect.empty();
                $.each(result, function(i, club){
                    clubSelect.append('<option value="'+club.id+'">'+club.clubName+'</option>')
                });
            },
            error : function(e) {
                console.log(e)
            }
        });
    }
    setClubs();
    $("#addTransfer").submit(function (event) {
        event.preventDefault();
        let clubTransfer = {
            idAthlete:$("#id").val(),
            idClub:clubSelect.val(),
            since:since.val(),
            to:to.val(),
            reason:reason.val(),
        };
        ajaxSaveTransfer(clubTransfer);
    });
    function ajaxSaveTransfer(clubTransfer) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            accept: 'text/plain',
            url: window.location + "/saveTransfer",
            data: JSON.stringify(clubTransfer),
            success : function(result) {
                console.log("success");
                location.reload();
            },
            error: function (e) {
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'red',
                    content: 'Prestup sa nepodarilo vytvoriť !!',
                    delayOnHover: true,
                    showCountdown: true
                });
            }
        });
    }
    $(".delete").click(function () {
        let value = this.attributes.item(0).value;
        new jBox('Confirm', {
            confirmButton: 'Potvrdiť',
            cancelButton: 'Zrušiť',
            content: "Naozaj chcete vymazať tento prestup?",
            confirm: function () {
                ajaxDeleteTransfer(value);
            }
        }).open();

    });
    function ajaxDeleteTransfer(id) {
        id = {id:id};
        $.ajax({
            type: "POST",
            contentType: "application/json",
            accept: 'text/plain',
            url: window.location + "/deleteTransfer",
            data: JSON.stringify(id),
            success : function(result) {
                console.log("success");
                location.reload();
            },
            error: function (e) {
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'red',
                    content: 'Prestup sa nepodarilo vymazať !!',
                    delayOnHover: true,
                    showCountdown: true
                });
            }
        });
    }
    setClubs();
    let sex = $("#sex");
    let gender = sex.val();
    if (gender=="male"){
        $("#male").attr('checked', true);
        console.log("test");
    }else{
        $("#female").attr('checked', true);
    }
    $('input:radio[name="gender"]').change(function () {
        if ($(this).is(':checked') && $(this).val() == 'male') {
            sex.val($(this).val())
            // append goes here
        }
        if ($(this).is(':checked') && $(this).val() == 'female') {
            sex.val($(this).val())
            // append goes here
        }
    });
    $("#tableClubs_wrapper").addClass("hidden");
    $("#results").click(function () {
        $("#clubT").removeClass("active");
        $("#results").addClass("active");

        $("#tableClubs_wrapper").addClass("hidden");
        $("#tableResults_wrapper").removeClass("hidden")
    });
    $("#clubT").click(function () {
        $("#results").removeClass("active");
        $("#clubT").addClass("active");

        $("#tableResults_wrapper").addClass("hidden");
        $("#tableClubs_wrapper").removeClass("hidden")
    });
});
