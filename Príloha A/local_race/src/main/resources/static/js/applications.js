var jBoxLogin = {
    jBox: null,

    // The html of each of the content containers

    html: {
        addAthletes:
            '<div id="LoginContainer-addAthletes" class="login-container">' +
            '   <div class="login-body">' +
            '       <input type="text" id="id" class="login-textfield hidden" >' +
            '       <select style="width: 340px;  float: left; margin-left: 20px; margin-top:26px" size="8" aria-label="size 8 select example"   id="clubs" class="login-textfield select-css"  autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" ></select>' +
            '       <select style="width: 340px; display: inline-block; float: left; margin-left: 20px; margin-top:26px" size="8" aria-label="size 8 select example"    id="athletes" class="login-textfield select-css" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" >' +
            '<option value="'+null+'">Vyber</option>'+
            '</select>' +
            '       <select style="width: 130px;  float: left; margin-left: 20px; margin-top:26px" id="gender" class="login-textfield select-css" placeholder="Fáza" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" ></select>' +
            '       <img id="addAthlete"  style=" display: inline-block; float: left;margin-left: 20px; margin-top:26px" alt="addAthletes" class="plus" src="images/plus.png"> '+

            '<table style=" margin-top: 30px;width: 700px "  id="tableFormADD">' +
            '<thead>' +
            '<tr id="first_tr">' +
            '<th style=" word-break: normal!important;">Dráha'+
            '</th>'+
            '<th style=" word-break: normal!important;">Číslo'+
            '</th>'+
            '<th style=" word-break: normal!important;">Meno a ročník'+
            '</th>'+
            '<th style=" word-break: normal!important;">Klub'+
            '</th>'+
            '<th style=" word-break: normal!important;">Štartový výkon'+
            '</th>'+
            '<th style=" word-break: normal!important;">Zmazať'+
            '</th>'+
            '</tr>'+
            '</thead>'+
            '<tbody>'+

            '</tbody>'+
            '</table>'+
            '       </div>'+
            '       <button style="margin: 8px;" type="submit" class="login-button">Vytvoriť</button>' +
            '   </div>' +
            '   <div class="login-footer">' +
            '   </div>' +
            '</div>',



    },

    // Corresponding titles for content elements

    title: {
        addAthletes: 'Pridať atlétov',


    },

    // These tooltips will show when a textelemet gets focus

    textfieldTooltips: {

    }
};
$(document).ready(function() {
    let addForm;
    let tableInForm;
    let bibNumbers = new Map();
    let selectedDisciplines;
    let disciplineTables = $(".display");
    let table = $('#tableLeft').DataTable({
        "searching": false,
        "ordering": false,
        "paging":   false,
        "info":     false,
        select:         true
    });
    if($.cookie('selectedDisciplines')!=null){
        selectedDisciplines=$.parseJSON($.cookie("selectedDisciplines"))
        $(".display").addClass("hidden");
        for(let i = 0; i < selectedDisciplines.length; i++){
            $('#'+selectedDisciplines[i]).removeClass("hidden");
            table.row(+table.row($('#'+selectedDisciplines[i]+'aside')).index()).select();
        }
    }
    table.on( 'select', function ( e, dt, type, indexes ) {
        selectedDisciplines = $.map(table.rows('.selected').data(), function (item) {
            return item[0];
        });
        $(".display").addClass("hidden");
        for(let i = 0; i < selectedDisciplines.length; i++){
            $('#'+selectedDisciplines[i]).removeClass("hidden");

        }
        $.cookie("selectedDisciplines", JSON.stringify(selectedDisciplines));

        // do something with the ID of the selected items
    } );
    let athletes = $(".addAthletes");

    //
    jBoxLogin.jBox = new jBox('Modal', {

        // Unique id for CSS access
        id: 'jBoxLogin',

        // Dimensions
        width: 800,
        height: 800,

        // Attach to elements
        attach: '.addAthletes',

        // Create the content with the html provided in global var
        content: '<form id="addAthletesToDiscipline"><div id="LoginWrapper">' +  jBoxLogin.html.addAthletes +'</div></form>',
        adjustPosition: true,
        adjustTracker: true,
        responsiveHeight:true,
        fixed:true,
        blockScroll:false,
        isolateScroll: false,
        // Adjust header when scroll is blocked

        onCreated: function(){
            tableInForm = $("#tableFormADD").DataTable({
                "searching": false,
                "paging":   false,
                "bInfo": false,
                "bPaginate": false,
                "lengthChange": false,
                "info":  false,
                select:  false,
                "scrollY":        "200px",
                "scrollCollapse": true,

            });
            $('#tableFormADD').on('click', '.remove', function () {
                var table = $('#tableFormADD').DataTable();
                table
                    .row($(this).parents('tr'))
                    .remove()
                    .draw();
            });
            $(".addAthletes").click(function () {
                let id = $(this).parents("div")[3];
                $("#id").val(id.attributes.item(0).value);
            });
            let clubs = $("#clubs");
            let gender = $("#gender");
            let athletes = $("#athletes");
            let athleteButton = $("#addAthlete");
            loadClubs(clubs);
            gender.append('<option th:value="male">male</option>' +
                '<option th:value="female">female</option>');
            gender.change(function () {
                let request = {idClub:clubs.val(),
                    gender:gender.val()
                };
                loadAthletes(request,athletes);
            });
            clubs.change(function () {
                let request = {idClub:clubs.val(),
                               gender:gender.val()
                };
                loadAthletes(request,athletes);
            });
            athleteButton.click(function () {
                if(clubs.val() != null && clubs.val() != 'null' && athletes.val() != null && athletes.val() != 'null'  ){
                    let bibNumber;
                    console.log(bibNumbers.has(parseInt(athletes.val())));
                    if(bibNumbers.has(parseInt(athletes.val()))){
                        bibNumber=bibNumbers.get(parseInt(athletes.val()));
                    }
                    else{
                        bibNumber=0;
                    }
                    tableInForm.row.add([
                        '<input type="number" value="0"  class="login-textfield " placeholder="Dráha" autocomplete="off"  autocapitalize="off" spellcheck="false" required>',
                        '<input type="number"  class="login-textfield " value="'+bibNumber+'" placeholder="Číslo" autocomplete="off"  autocapitalize="off" spellcheck="false" required>',
                        $( "#athletes option:selected" ).text(),
                        $( "#clubs option:selected" ).text(),
                        '<input type="text"  class="login-textfield " value="0" placeholder="Výkon" autocomplete="off"  autocapitalize="off" spellcheck="false" required>',
                        '<a value="'+athletes.val()+'"  class="remove"><img alt="active" class="activatedButton" src="/images/delete.png"></a>'
                    ]).draw(false);
                    $('#athletes option:selected').remove();

                }

            });
            $("#addAthletesToDiscipline").submit(function (event) {
                event.preventDefault();
                setForm();
                ajaxSaveAthletes(addForm);

            });
        },
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
            this.showContent('addAthletes', true);

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
            this.showContent('addAthletes', true);



        },
        onClose: function () {
            // Remove error tooltips
            $.each(jBoxLogin.textfieldTooltips, function (id, tt) {
                $('#' + id).data('jBoxTextfieldError') && $('#' + id).data('jBoxTextfieldError').close();
            });
        }
    });
    function loadClubs(clubSelect) {
        $.ajax({
            type : "GET",
            url : "/athletes/getAllClubs",
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
    function loadAthletes(request,athletes) {

        $.ajax({
            type : "POST",
            contentType : "application/json",
            accept: 'text/plain',
            url : window.location + "/athletes",
            data : JSON.stringify(request),
            dataType: 'json',
            success: function(result){
                athletes.empty();
                athletes.append('<option value='+null+'>Vyber</option>');
                $.each(result,function (i,club_transfer) {
                    let date = club_transfer.athlete.dateBirth;
                    date = date.split("-");
                    date = date[2]+"."+date[1]+"."+date[0];
                    athletes.append('<option value="'+club_transfer.athlete.id+'">'+club_transfer.athlete.firstName+' '+club_transfer.athlete.surname+', '+date+'</option>');
                });
            },
            error : function(e) {
                console.log(e)
            }
        });

    }
    function getBibNumbers(bibNumbers) {
        $.ajax({
            type : "GET",
            url : window.location + "/bibNumbers",
            success: function(result){
                bibNumbers.clear();
                $.each(result, function(i, bib){
                    bibNumbers.set(bib.athlete.id,bib.bib);
                });
            },
            error : function(e) {
                console.log(e)
            }
        });
    }
    getBibNumbers(bibNumbers);
    function ajaxSaveAthletes(addForm) {
        console.log(addForm);
        $.ajax({
            type:"POST",
            contentType:"application/json",
            accept: 'text/plain',
            url : window.location + "/save",
            data : JSON.stringify(addForm),
            dataType: 'text',
            success: function(result){
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'green',
                    content: 'Atléti boli úspesne pridaný.',
                    delayOnHover: true,
                    showCountdown: true
                });
                setTimeout(function () {
                    location.reload();
                }, 500);
                console.log(result);
            },
            error : function(e) {
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'red',
                    content: 'Atlétov sa nepodarilo pridať !!',
                    delayOnHover: true,
                    showCountdown: true
                });
                console.log(e)
            }
        });
    }

    function setForm() {
        addForm = $('#tableFormADD').tableToJSON({
            extractor : {
                0 : function(cellIndex, $cell) {
                    return $cell.find('input').val();
                },
                1 : function(cellIndex, $cell) {
                    return $cell.find('input').val();
                },
                2 : function(cellIndex, $cell) {
                    return $cell.text();
                },
                3 : function(cellIndex, $cell) {
                    return $cell.text();
                },
                4 : function(cellIndex, $cell) {
                    return $cell.find('input').val();
                },
                5 : function(cellIndex, $cell) {
                    return $cell.find('a').attr('value');
                },
            }
        });
        let discipline = {};
        discipline ["id"] = $("#id").val();
        addForm.push(discipline);
        console.log(addForm);
    }
    const createdCell = function(cell) {
        let original;
        cell.setAttribute('contenteditable', true);
        cell.setAttribute('spellcheck', false);
        cell.addEventListener("focus", function(e) {
            original = e.target.textContent
        });
        cell.addEventListener("blur", function(e) {
            if (original !== e.target.textContent) {
                const row = table.row(e.target.parentElement);
                row.invalidate();
            }
        })
    };

    $(".rightTable").DataTable({
        "searching": false,
        "paging":   false,
        "bInfo": false,
        "bPaginate": false,
        "info":  false,
        select:  false,
        "ordering": true,
        "autoWidth": false,
        "columnDefs" : [{"targets":3, "type":"date-eu"},{ "targets": 0, createdCell: createdCell},{ "targets": 1, createdCell: createdCell},{ "targets": 5, createdCell: createdCell}],

    });
    function exportCsv() {
        $.ajax({
            type : "GET",
            url : window.location + "/exportStartList",
            success: function(result){
                if(result=="Success"){
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'green',
                    content: 'Štartová listina bola úspešne exportovaná.',
                    delayOnHover: true,
                    showCountdown: true
                });}
                else{
                    new jBox('Notice', {
                        animation: 'flip',
                        color: 'red',
                        content: 'Bohužial, nepodarilo sa exportovať prihlášky !!',
                        delayOnHover: true,
                        showCountdown: true
                    });
                }
            },
            error : function(e) {
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'red',
                    content: 'Bohužial, nepodarilo sa exportovať prihlášky !!',
                    delayOnHover: true,
                    showCountdown: true
                });
                console.log(e)

            }
        });
    }
    $("#exportCsv").click(function () {
        exportCsv();
    });

    $(".saveAthletes").click(function () {
        let saveTable = $(this).closest('table');
        let tbody = saveTable.find("tbody > tr > td");
        if(tbody.length == 1){
            console.log("empty");
            return;
        }
        let disciplineId = this.attributes.item(0).value;
        let tableForm = saveTable.tableToJSON({
            headings:{0:"Line",1:"Bib",2:"Meno",3:"Dátum narodenia",4:"Klub",5:"Štartový výkon"},
            extractor : {
                0 : function(cellIndex, $cell) {
                    return $cell.text();
                },
                1 : function(cellIndex, $cell) {
                    return $cell.text();
                },
                2 : function(cellIndex, $cell) {
                    return $cell.attr('value');
                },
                3 : function(cellIndex, $cell) {
                    return $cell.text();
                },
                4 : function(cellIndex, $cell) {
                    return $cell.text();
                },
                5 : function(cellIndex, $cell) {
                    return $cell.text();
                },
            }

        });
        tableForm.splice(0,1);tableForm.splice(0,1);
        let discipline = {};
        discipline ["id"] = disciplineId;
        tableForm.push(discipline);
        ajaxEditApplications(tableForm)
    });
    function ajaxEditApplications(tableForm) {
        $.ajax({
            type : "POST",
            contentType : "application/json",
            accept: 'text/plain',
            url : window.location + "/edit",
            data : JSON.stringify(tableForm),
            dataType: 'text',
            success: function(result){
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'green',
                    content: 'Prihlášky boli úspešne editované.',
                    delayOnHover: true,
                    showCountdown: true
                });
               setTimeout(function () {
                    location.reload();
               }, 500);
            },
            error : function(e) {
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'red',
                    content: 'Nepodarilo sa upraviť prihlášky !!',
                    delayOnHover: true,
                    showCountdown: true
                });
                console.log(e);
            }
        });
    }
    $(".deleteButton").click(function () {
        let startResultId = {id:this.attributes.item(0).value};
        new jBox('Confirm', {
            confirmButton: 'Potvrdiť',
            cancelButton: 'Zrušiť',
            content: "Naozaj vymazať pretekára zo štartovej listiny?",
            confirm: function () {
                ajaxDeleteStartList(startResultId);
            }
        }).open();
    });
    function ajaxDeleteStartList(startResultId){
        $.ajax({
            type : "POST",
            contentType : "application/json",
            accept: 'text/plain',
            url : window.location + "/delete/StartList/",
            data : JSON.stringify(startResultId),
            dataType: 'text',
            success: function(result){
                if(result=="success"){
                    new jBox('Notice', {
                        animation: 'flip',
                        color: 'green',
                        content: 'Atlét bol zmazaný zo štartovej listiny.',
                        delayOnHover: true,
                        showCountdown: true
                    });
                    setTimeout(function () {
                        location.reload();
                    }, 500);
                }
                else{
                    new jBox('Notice', {
                        animation: 'flip',
                        color: 'red',
                        content: 'Bohužial, nepodarilo sa vymazať atléta!!',
                        delayOnHover: true,
                        showCountdown: true
                    });
                }
            },
            error : function(e) {
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'red',
                    content: 'Bohužial, nepodarilo sa vymazať atléta!!',
                    delayOnHover: true,
                    showCountdown: true
                });
                console.log(e)

            }
        });
    }
    $(".split").click(function () {
        let disciplineId = {id:this.attributes.item(0).value};
        new jBox('Confirm', {
            confirmButton: 'Potvrdiť',
            cancelButton: 'Zrušiť',
            content: "Naozaj chcete rozdeliť pretekárov do viacerých disciplín a určiť dráhy pretekárom?",
            confirm: function () {
                console.log("confirmed "+ disciplineId);
                ajaxSplitAthletes(disciplineId);
            }
        }).open();
    });
    function ajaxSplitAthletes(id) {
        $.ajax({
            type : "POST",
            contentType : "application/json",
            accept: 'text/plain',
            url : window.location + "/splitAthletes",
            data : JSON.stringify(id),
            dataType: 'text',
            success: function(result){
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'green',
                    content: 'Atléti boli úspešne rozdelený.',
                    delayOnHover: true,
                    showCountdown: true
                });
                setTimeout(function () {
                    location.reload();
                }, 500);
            },
            error : function(e) {
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'red',
                    content: 'Nepodarilo sa rozdeliť atlétov !!',
                    delayOnHover: true,
                    showCountdown: true
                });
                console.log(e);
            }
        });
    }
    new jBox('Tooltip', {
        attach: '#Tooltip-2',
        theme: 'TooltipBorderThick',
        width: 200,
        position: {
            x: 'left',
            y: 'center'
        },
        outside: 'x',
        pointer: 'top:15',
        content: 'Id závodu: ' + $("#raceId").text(),
        animation: 'move'
    });
    let loading = $("#loading");
    let overlay = $("#overlay");
    $("#uploadApp").click(function () {
        uploadDbsAjax();
    });
    function uploadDbsAjax() {
        loading.addClass("visible");
        overlay.addClass("visible");
        $.ajax({
            type : "GET",
            contentType : "application/json",
            accept: 'text/plain',
            url : window.location + "/upload/dbs",
            success: function(result){
                console.log(result);
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'green',
                    content: 'Prihlášky boli úspešne nahrané.',
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
                    content: 'Prihlášky sa nepodarilo nahrať !!',
                    delayOnHover: true,
                    showCountdown: true
                });
                console.log(e);
            }
        });
    }
} );

