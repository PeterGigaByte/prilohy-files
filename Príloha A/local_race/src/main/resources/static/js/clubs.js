var jBoxLogin = {
    jBox: null,

    // The html of each of the content containers

    html: {
        club:
            '<div id="LoginContainer-club" class="login-container">' +
            '   <div class="login-body">' +
            '       <input type="number" name="id"  id="id" class="login-textfield hidden" >' +
            '       <input type="text" name="clubName"   id="clubName" class="login-textfield" placeholder="Názov klubu" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required>' +
            '       <input type="text" name="shortcutClubName" id="shortCut" class="login-textfield" placeholder="Skratka klubu" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required>' +
            '       <input type="text" name="responsiblePerson"   id="responsiblePerson" class="login-textfield" placeholder="Vedúci klubu" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required>' +
            '       <input type="text" name="residence"  id="residence" class="login-textfield" placeholder="Sídlo" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required>' +
            '       <input style="margin-top: 20px" id="image" name="logoImage"   type="file">' +
            '       </div>'+
            '       <button id="createClub" value="create" style="margin-left: 25%;width: 200px;margin-top: 20px;" type="submit" class="login-button">Vytvoriť</button>' +
            '       <button id="editClub" value="edit" style="margin-left: 25%;width: 200px;margin-top: 20px;" type="submit" class="login-button hidden">Editovať</button>' +
            '   </div>' +
            '   <div class="login-footer">' +
            '   <img id="preview" src="/images/nologo.png" style="height: 100px; width: auto;margin-left: 32%"  alt="logoPreview"> '+
            '       <br>' +

            '   </div>' +
            '</div>',


    },

    // Corresponding titles for content elements

    title: {
        club: 'Klub',

    },

    // These tooltips will show when a textelemet gets focus

    textfieldTooltips: {
        clubName : "Názov klubu",
        shortCut : "Skratka klubu",
        responsiblePerson : "Zodpovedná osoba",
        residence : "Sídlo klubu"
    },

};


$(document).ready(function () {
    jBoxLogin.jBox = new jBox('Modal', {

        // Unique id for CSS access
        id: 'jBoxLogin',

        // Dimensions
        width: 450,
        height: 500,

        // Attach to elements
        attach: '#addClub',

        // Create the content with the html provided in global var
        content: '<form action="clubs/create" method="post" enctype="multipart/form-data" id="createNewClub"><div id="LoginWrapper">' + jBoxLogin.html.club +'</div></form>',

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
            this.showContent('club', true);


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
            this.showContent('club', true);
        },
        onClose: function () {
            // Remove error tooltips
            $.each(jBoxLogin.textfieldTooltips, function (id, tt) {
                $('#' + id).data('jBoxTextfieldError') && $('#' + id).data('jBoxTextfieldError').close();
            });
        }
    });
    let preview = document.getElementById("preview");
    let image = document.getElementById("image");
    image.addEventListener("change",changeImage);
    function changeImage() {
        let file = image.files[0];
        let reader = new FileReader();
        reader.onload = function(e){
            preview.src =e.target.result;
        };
        reader.readAsDataURL(file);

    }



        let table = $('#table').DataTable();

     $(".deleteButton").click(function () {
         let id ={id:this.attributes.item(0).value};
         deleteClub(id);
     });
    $(".editButton").click(function () {
        let value = this.attributes.item(0).value;
        setEditForm(value);
        jBoxLogin.jBox.open();
    });
    function refreshClubs(){
        $.ajax({
            type : "GET",
            url : window.location + "/getAllClubs",
            success: function(result){
                table.clear().draw();
                $.each(result, function(i, club){
                    table.row.add([
                        club.clubName,
                        '<img src="club.getLogoImage()"  style="margin-left: -10px; width: 70px;height: auto" alt="logo" />',
                        club.residence,
                        club.responsiblePerson,
                        club.shortcutClubName,
                        '<a   th:value="'+club.id+'"class="editButton"><img th:type="button" alt="active"  class="activatedButton" src="/images/edit.png"></a>',
                        '<a   th:value="'+club.id+'" class="deleteButton"><img alt="active" class="activatedButton" src="/images/delete.png"></a>'
                    ]).draw(false);
                });
                $(".deleteButton").unbind();
                $(".editButton").unbind();
                $(".deleteButton").click(function () {
                    let id ={id:this.attributes.item(0).value};
                    deleteClub(id);
                });
                $(".editButton").click(function () {
                    let value = this.attributes.item(0).value;
                    setEditForm(value);
                    jBoxLogin.jBox.open();
                });

            },
            error : function(e) {
                console.log(e)
            }
        });
    }
    function deleteClub(id) {
        new jBox('Confirm', {
            confirmButton: 'Potvrdiť',
            cancelButton: 'Zrušiť',
            content: "Naozaj chcete vymazať tento atletický klub?",
            confirm: function () {
                $.ajax({
                    type : "POST",
                    contentType : "application/json",
                    accept: 'text/plain',
                    url : window.location + "/delete",
                    data : JSON.stringify(id),
                    dataType: 'text',
                    success : function(result) {
                        new jBox('Notice', {
                            animation: 'flip',
                            color: 'green',
                            content: 'Klub bol úspešne vymazaný',
                            delayOnHover: true,
                            showCountdown: true
                        });
                        refreshClubs()
                    },
                    error : function(e) {
                        new jBox('Notice', {
                            animation: 'flip',
                            color: 'red',
                            content: 'Bohužial, Klub sa nepodarilo vymazať',
                            delayOnHover: true,
                            showCountdown: true
                        });
                        console.log("ERROR: ", e);
                    }
                });
            }
        }).open();

    }
    function setEditForm(id){
        clearForm();
        id = {id:id};
        $.ajax({
            type : "POST",
            contentType : "application/json",
            accept: 'text/plain',
            url : window.location + "/getClub",
            data : JSON.stringify(id),
            dataType: 'json',
            success: function(club){
                $("#id").val(club.id);
                $("#clubName").val(club.clubName);
                $("#shortCut").val(club.shortcutClubName);
                $("#responsiblePerson").val(club.responsiblePerson);
                $("#residence").val(club.residence);
                $("#preview").addClass("hidden");
                $("#image").addClass("hidden");
                $("#createClub").addClass("hidden");
                $("#editClub").removeClass("hidden");
            },
            error : function(e) {
                console.log(e)
            }
        });
    }

    function clearForm(){
        $("#id").val("");
        $("#clubName").val("");
        $("#shortCut").val("");
        $("#responsiblePerson").val("");
        $("#residence").val("");
        $("#preview").removeClass("hidden");
        $("#image").removeClass("hidden");
        $("#createClub").removeClass("hidden");
        $("#editClub").addClass("hidden");
    }
    $("#addClub").click(function () {
        clearForm();
    })
});

