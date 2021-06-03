var jBoxLogin = {
    jBox: null,

    // The html of each of the content containers

    html: {
        discipline:
            '<div id="LoginContainer-discipline" class="login-container">' +
            '   <div class="login-body">' +
            '       <input type="text" id="id" class="login-textfield hidden" >' +
            '       <input style="width: 100px; display: inline-block; float: left" type="time" id="disciplineTime" class="login-textfield " placeholder="Čas štartu" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required>' +
            '       <select style="width: 300px; display: inline-block; float: left; margin-left: 20px; margin-top:26px"   id="disciplineName" class="login-textfield select-css" placeholder="Disciplína" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required></select>' +
            '       <select style="width: 200px; display: inline-block; float: left; margin-left: 20px; margin-top:26px"   id="category" class="login-textfield select-css" placeholder="Kategória" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required></select>' +
            '       <select style="width: 130px; display: inline-block; float: left; margin-left: 20px; margin-top:26px" type="text" id="phaseName" class="login-textfield select-css" placeholder="Fáza" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required></select>' +
            '       <input style="width: 80px; display: inline-block; float: left; margin-left: 20px" type="number" min="1" id="phaseNumber" class="login-textfield" placeholder="Poradie" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required>' +
            '       <input style="display: inline-block;  margin-top: 20px" type="text" id="note" class="login-textfield" placeholder="Poznámka" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" >' +
            '       <button id="createDiscipline" name="createDiscipline" style="width: 200px;" type="submit" class="login-button">Vytvoriť</button>' +
            '       <button id="editDiscipline" name="editDiscipline" style="width: 200px;" type="submit" class="login-button hidden">Editovať</button>' +
            '       <button id="createAndClose" name="createAndClose" style="width: 200px; display: inline-block; float: right; margin-left: 20px" type="submit" class="login-button">Vytvoriť a zavrieť</button>' +

            '   </div>' +
            '   <div class="login-footer">' +
            '       <span id="settings-footer" onclick="jBoxLogin.jBox.showContent(\'settings\')">Nastavenia</span>' +
            '       <br>' +
            '   </div>' +
            '</div>',

        settings:
            '<div id="LoginContainer-settings" class="login-container">' +
            '           <div class="login-body">' +
            '       <input style="width: 100px; display: inline-block; float: left" type="number" min="0" max="10" id="Q" class="login-textfield " placeholder="Q" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" >' +
            '       <input style="width: 100px; display: inline-block; float: left; margin-left: 20px; margin-top:26px" min="0" max="10" type="number"  id="q" class="login-textfield" placeholder="q" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" >' +
            '       <select style="width: 200px; display: inline-block; float: left; margin-left: 20px; margin-top:26px"   id="aim" class="login-textfield select-css" placeholder="Kategória" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" ></select>' +
            '           </div>' +
            '       <div class="login-footer">' +
            '           <span onclick="jBoxLogin.jBox.showContent(\'discipline\')">Disciplína</span>' +
            '           <br>' +
            '       </div>' +
            '       </div>',

    },

    // Corresponding titles for content elements

    title: {
        discipline: 'Disciplína',
        settings: 'Nastavenia',
    },

    // These tooltips will show when a textelemet gets focus

    textfieldTooltips: {
        disciplineTime: 'Čas štartu',
        disciplineName: 'Názov disciplíny',
        category: 'Kategória',
        phaseName: 'Názov fázy',
        phaseNumber: 'Číslo fázy',
        note: 'Poznámka',
        Q:"Q - Podľa umiestnenia v behu",
        q:"q - Podľa umiestnenia celkovo podľa času",
        aim: "Cieľ postupu"

    }
};
$(document).ready(function () {

    let dayWeek = ["Nedeľa" , "Pondelok","Utorok","Streda","Štvrtok","Piatok","Sobota"];
    let disciplines_list_run =["40 m", "50 m", "60 m","100 m","150 m","200 m","400 m",
        "500 m", "600 m", "800 m", "1500 m", "2000 m", "3000 m", "5000 m", "10 000 m",
        "5 km","10 km", "15 km", "20 km", "50 km", "polmaratón", "maratón", "hodinovka",
        "60 m pr. 106,7", "60 y pr.","50 m pr. 83,8", "50 m p 76,2-7,5", "60 m pr. 106,7",
        "60 m pr. 99,1", "60 m pr. 91,4", "60 m pr. 83,8", "60 m p. 76,2-8,5", "chôdza 3000 m",
        "chôdza 5000 m", "5 km chôdza", "10 km chôdza","20 km chôdza", "35 km chôdza","50 km chôdza"
        ];
    let disciplines_list_jump =["Skok do diaľky","Výška","Žrď","Trojskok","Diaľka"];
    let disciplines_list_throw =["Hod kladivom","Guľa 3 kg","Guľa 4 kg","Guľa 5 kg","Guľa 6 kg","Guľa 7,26 kg"];
    let categories_list =["Muži", "Muži 20-22 rokov", "Juniori", "Dorastenci", "Starší žiaci", "Mladší žiaci",
         "Najmladší žiaci", "Ženy", "Ženy 20-22 rokov", "Juniorky", "Dorastenky", "Staršie žiačky",
        "Mladšie žiačky", "Najmladšie žiačky"];
    let phases_list =["Beh", "Vložený beh", "Séria", "Kvalifikácia", "Rozbeh", "Medzibeh", "Semifinále", "Finále"];
    let datesSelect = $("#day");
    let disciplinesSelect = $("#disciplinesType");
    let categoriesSelect = $("#categories");
    let request = {date:"default",discipline:"default",category:"default"};
    refreshDates();
    refreshDisciplines();
    refreshCategories();


    function deleteEmptyDisciplines() {
        $.ajax({
            type : "DELETE",
            url : window.location + "/empty/delete",
            success: function(result){
                refreshDisciplines();
                refreshCategories();
                refreshAim();
                refreshDisciplineTable();
            },
            error : function(e) {
                console.log(e)
            }
        });
    }
    function refreshDates(){
        $.ajax({
            type : "GET",
            url : window.location + "/dates",
            success: function(result){
                datesSelect.empty();
                $.each(result, function(i, date){
                    date = new Date(date);
                    let month = date.getMonth()+1;
                    datesSelect.append('<option value="'+date.getFullYear()+'-'+month+'-'+date.getDate()+'">'+date.getDate()+'.('+dayWeek[date.getDay()]+')'+month+'.'+date.getFullYear()+'</option>')
                });
                request.date= new Date(datesSelect.val());let month = request.date.getMonth()+1;request.date=request.date.getFullYear()+'-'+month+'-'+request.date.getDate();
                request.discipline=disciplinesSelect.val();request.category=categoriesSelect.val();
                refreshDisciplineTable();
            },
            error : function(e) {
                console.log(e)
            }
        });
    }
    function refreshDisciplines(){
        $.ajax({
            type : "GET",
            url : window.location + "/disciplineTypes",
            success: function(result){
                disciplinesSelect.empty();
                $.each(result, function(i, discipline){
                    disciplinesSelect.append('<option value="'+discipline+'">'+discipline+'</option>');
                });

            },
            error : function(e) {
                console.log(e)
            }
        });
    }
    function refreshCategories(){
        $.ajax({
            type : "GET",
            url : window.location + "/categories",
            success: function(result){
                categoriesSelect.empty();
                $.each(result, function(i, category){
                    categoriesSelect.append('<option value="'+category+'">'+category+'</option>');
                });

            },
            error : function(e) {
                console.log(e)
            }
        });
    }
    function refreshAim(){
        let discipline = $("#disciplineName").val();
        discipline = discipline.split("_");
        let before_value = aimSelect.val();
        let data = {
            id:$("#id").val(),
            disciplineName:discipline[1],
            disciplineCategory:$("#category").val()
        };
        $.ajax({
            type : "POST",
            contentType : "application/json",
            accept: 'text/plain',
            url : window.location + "/disciplines",
            data : JSON.stringify(data),
            dataType: 'json',
            success: function(result){
                aimSelect.empty();
                aimSelect.append('<option value="'+null+'">'+"Nevybrané"+'</option>');
                $.each(result, function(i, aim){
                    aimSelect.append('<option value="'+aim.id+'">'+aim.disciplineTime+' - '+aim.phaseName+' - '+aim.phaseNumber+'</option>');
                });
                if(before_value){
                    aimSelect.val(before_value);
                }

            },
            error : function(e) {
                console.log(e)
            }
        });
    }
    function setDisciplineSelect() {
        disciplineNameSelect.empty();
        $.each(disciplines_list_run,function (i,discipline_name) {
            disciplineNameSelect.append('<option  value="run_'+discipline_name+'" >'+discipline_name+'</option>');
        });
        $.each(disciplines_list_jump,function (i,discipline_name) {
            disciplineNameSelect.append('<option  value="jump_'+discipline_name+'" >'+discipline_name+'</option>');
        });
        $.each(disciplines_list_throw,function (i,discipline_name) {
            disciplineNameSelect.append('<option  value="throw_'+discipline_name+'" >'+discipline_name+'</option>');
        });

    }
    function setCategoriesSelect() {
        categoriesListSelect.empty();
        $.each(categories_list,function (i,category_name) {
            categoriesListSelect.append('<option value="'+category_name+'" >'+category_name+'</option>');
        })
    }
    function refreshPhases(){
        phasesSelect.empty();
        $.each(phases_list,function (i,phase_name) {
            phasesSelect.append('<option value="'+phase_name+'" >'+phase_name+'</option>');
        })
    }

    jBoxLogin.jBox = new jBox('Modal', {

        // Unique id for CSS access
        id: 'jBoxLogin',

        // Dimensions
        width: 940,
        height: 300,

        // Attach to elements
        attach: '#addDiscipline',

        // Create the content with the html provided in global var
        content: '<form id="discipline"><div id="LoginWrapper">' + jBoxLogin.html.discipline  + jBoxLogin.html.settings +'</div></form>',

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
            this.showContent('discipline', true);

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
            this.showContent('discipline', true);
        },
        onClose: function () {
            // Remove error tooltips
            $.each(jBoxLogin.textfieldTooltips, function (id, tt) {
                $('#' + id).data('jBoxTextfieldError') && $('#' + id).data('jBoxTextfieldError').close();
            });
        }
    });
    let typeSubmit;
    $("#createDiscipline").click(function () {
        typeSubmit = "createDiscipline";
    });
    $("#createAndClose").click(function () {
        typeSubmit = "createAndClose";
    });
    $("#editDiscipline").click(function () {
        typeSubmit = "edit";
    });

    $("#discipline").submit(function (event) {
       event.preventDefault();

       let disciplineNameAndType = $("#disciplineName").val();
       disciplineNameAndType = disciplineNameAndType.split("_");
       let formDiscipline = {
           id: $("#id").val(),
           disciplineTime:$("#disciplineTime").val(),
           date:$("#day").val(),
           disciplineName:disciplineNameAndType[1],
           disciplineType:disciplineNameAndType[0],
           category:$("#category").val(),
           phaseName:$("#phaseName").val(),
           phaseNumber:$("#phaseNumber").val(),
           note:$("#note").val(),
           Q:$("#Q").val(),
           q:$("#q").val(),
           aim:$("#aim").val(),
       };
        ajaxSaveDiscipline();

        function ajaxSaveDiscipline() {
            $.ajax({
                type : "POST",
                contentType : "application/json",
                accept: 'text/plain',
                url : window.location + "/save",
                data : JSON.stringify(formDiscipline),
                dataType: 'text',
                success : function(result) {
                    if(result == "Post create successfully"){
                        new jBox('Notice', {
                            animation: 'flip',
                            color: 'green',
                            content: 'Atletická disciplína bola úspešne vytvorená',
                            delayOnHover: true,
                            showCountdown: true
                        });
                        requestRefresh();
                        refreshDisciplineTable();
                    }
                    else if(result == "Post edit successfully"){
                        new jBox('Notice', {
                            animation: 'flip',
                            color: 'green',
                            content: 'Atletická disciplína bola úspešne editovaná',
                            delayOnHover: true,
                            showCountdown: true
                        });
                        requestRefresh();
                        refreshDisciplineTable();
                    }
                    else if(result == "Already exist"){
                        new jBox('Notice', {
                            animation: 'flip',
                            color: 'red',
                            content: 'Bohužial, Atletickú disciplínu sa nepodarilo vytvoriť, pretože rovnaká už existuje !!',
                            delayOnHover: true,
                            showCountdown: true
                        });
                    }
                    if(typeSubmit == "createDiscipline"){
                        let selector = $("#phaseNumber");
                        selector.val(parseInt(selector.val())+1);
                    }else if( typeSubmit == "createAndClose"){
                        if(result=="Already exist"){

                        }else{
                            clearForm();
                            jBoxLogin.jBox.close();
                        }
                    }else if( typeSubmit == "edit"){
                        jBoxLogin.jBox.close();
                    }
                    refreshDisciplines();
                    refreshCategories();
                    refreshAim()
                },
                error : function(e) {
                    new jBox('Notice', {
                        animation: 'flip',
                        color: 'red',
                        content: 'Bohužial, Atletickú disciplínu sa nepodarilo vytvoriť',
                        delayOnHover: true,
                        showCountdown: true
                    });
                    console.log("ERROR: ", e);

                },
            });
        }
    });

    function clearForm(){
        $("#id").val("");
        $("#disciplineTime").val("");
        $("#disciplineName").val("");
        $("#category").val("");
        $("#phaseName").val("");
        $("#phaseNumber").val("");
        $("#note").val("");
        $("#Q").val("");
        $("#q").val("");
        $("#aim").val("");
        setDisciplineSelect();
        setCategoriesSelect();

    }
    let categoriesListSelect = $("#category");
    let disciplineNameSelect = $("#disciplineName");
    let phasesSelect = $("#phaseName");
    let aimSelect = $("#aim");
    $("#settings-footer").click(function () {
        refreshAim();

    });
    let deleteEmptyDisciplinesDiv = $("#deleteEmptyDisciplines");
    deleteEmptyDisciplinesDiv.click(function () {
        new jBox('Confirm', {
            confirmButton: 'Potvrdiť',
            cancelButton: 'Zrušiť',
            content: "Naozaj chcete vymazať prázdne atletické disciplíny?",
            confirm: function () {
                deleteEmptyDisciplines();
            }
        }).open();

    });
    setDisciplineSelect();
    setCategoriesSelect();
    refreshPhases();
    refreshAim();

    var groupColumn = 0;
    var table = $('#table').DataTable({
        "columnDefs": [
            { "visible": false, "targets": groupColumn }
        ],
        "order": [[ groupColumn, 'asc' ]],
        "displayLength": 10,
        "drawCallback": function ( settings ) {
            var api = this.api();
            var rows = api.rows( {page:'current'} ).nodes();
            var last=null;

            api.column(groupColumn, {page:'current'} ).data().each( function ( group, i ) {
                if ( last !== group ) {
                    $(rows).eq( i ).before(
                        '<tr style="font-size: 20px;padding: 0; height: 5px;margin: 0; color: white; background-color: #0b5ea0" class="group"><td style="padding-left:20px!important;" colspan="7">'+group+'</td></tr>'
                    );
                    last = group;
                }
            } );
        }
    } );
    // Order by the grouping
    $('#table tbody').on( 'click', 'tr.group', function () {
        var currentOrder = table.order()[0];
        if ( currentOrder[0] === groupColumn && currentOrder[1] === 'asc' ) {
            table.order( [ groupColumn, 'desc' ] ).draw();
        }
        else {
            table.order( [ groupColumn, 'asc' ] ).draw();
        }
    } );


    function refreshDisciplineTable(){
        $.ajax({
            type : "POST",
            contentType : "application/json",
            accept: 'text/plain',
            url : window.location + "/disciplines/table",
            data : JSON.stringify(request),
            dataType: 'json',
            success: function(result){
                table.clear().draw();
                $.each(result, function(i, discipline){
                    table.row.add([
                        'Čas štartu '+discipline.disciplineTime,
                        discipline.participants,
                        discipline.disciplineName,
                        discipline.category+', '+discipline.phaseName+', '+discipline.phaseNumber,
                        discipline.cameraId,
                        discipline.note,
                        '<a   th:value="'+discipline.id+'"class="editButton"><img th:type="button" alt="active"  class="activatedButton" src="/images/edit.png"></a>',
                        '<a   th:value="'+discipline.id+'" class="deleteButton"><img alt="active" class="activatedButton" src="/images/delete.png"></a>'
                    ]).draw(false);
                });
                $(".deleteButton").unbind();
                $(".editButton").unbind();
                $(".deleteButton").click(function () {
                    let value = this.attributes.item(0).value;
                    new jBox('Confirm', {
                        confirmButton: 'Potvrdiť',
                        cancelButton: 'Zrušiť',
                        content: "Naozaj chcete vymazať túto atletickú disciplínu?",
                        confirm: function () {
                            deleteDiscipline(value);
                        }
                    }).open();
                });
                $(".editButton").click(function () {
                    let value = this.attributes.item(0).value;
                    setEditForm(value);

                    //setFormWithIdParemetersAndEditButton
                    jBoxLogin.jBox.open();
                });

            },
            error : function(e) {
                console.log(e)
            }
        });
    }
    function setEditForm(id){
        id = {id:id};
        $.ajax({
            type : "POST",
            contentType : "application/json",
            accept: 'text/plain',
            url : window.location + "/disciplineEdit",
            data : JSON.stringify(id),
            dataType: 'json',
            success: function(discipline){
                $("#id").val(discipline.id);
                $("#disciplineTime").val(discipline.disciplineTime);
                $("#disciplineName").val(discipline.disciplineType+"_"+discipline.disciplineName);
                $("#category").val(discipline.category);
                $("#phaseName").val(discipline.phaseName);
                $("#phaseNumber").val(discipline.phaseNumber);
                $("#note").val(discipline.note);
                id = {id:discipline.id};
                $.ajax({
                    type : "PUT",
                    contentType : "application/json",
                    accept: 'text/plain',
                    url : window.location + "/disciplineEdit/settings",
                    data : JSON.stringify(id),
                    dataType: 'json',
                    success: function(qualificationSettings){
                        $("#Q").val(qualificationSettings.qbyPlace);
                        $("#q").val(qualificationSettings.qbyTime);

                        if(qualificationSettings.disciplineWhere){
                            aimSelect.val(qualificationSettings.disciplineWhere);}
                        else{
                            $("#aim").val("null");
                        }

                        $("#createDiscipline").addClass("hidden");
                        $("#createAndClose").addClass("hidden");
                        $("#editDiscipline").removeClass("hidden");

                    },
                    error : function(e) {
                        console.log(e)
                    }
                });

            },
            error : function(e) {
                console.log(e)
            }
        });
    }
    function deleteDiscipline(id){
        id = {id:id};
        $.ajax({
            type : "POST",
            contentType : "application/json",
            accept: 'text/plain',
            url : window.location + "/delete",
            data : JSON.stringify(id),
            dataType: 'text',
            success: function(result){
                new jBox('Notice', {
                    theme: 'NoticeFancy',
                    attributes: {
                        x: 'left',
                        y: 'bottom'
                    },
                    color: "green",
                    content: "Atletická disciplína bola úspešne zmazaná",
                    animation: {
                        open: 'slide:bottom',
                        close: 'slide:left'
                    }
                });
                refreshDisciplineTable();
                refreshDisciplines();
                refreshCategories();
            },
            error : function(e) {
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'red',
                    content: 'Atletická disciplína nebola odstránená !!',
                    delayOnHover: true,
                    showCountdown: true
                });
                console.log(e)
            }
        });
    }
    datesSelect.change(function () {
        requestRefresh();
        refreshDisciplineTable();

    });
    disciplinesSelect.change(function () {
        requestRefresh();
        refreshDisciplineTable();
    });
    categoriesSelect.change(function () {
        requestRefresh();
        refreshDisciplineTable();
    });
    function requestRefresh() {
        request.date=datesSelect.val();
        request.category=categoriesSelect.val();
        request.discipline=disciplinesSelect.val();
    }
    $("#addDiscipline").click(function () {
        clearForm();
        refreshPhases();
        $("#createDiscipline").removeClass("hidden");
        $("#createAndClose").removeClass("hidden");
        $("#editDiscipline").addClass("hidden");
    });
    $("#cameraNumbering").click(function (){
        new jBox('Confirm', {
            confirmButton: 'Potvrdiť',
            cancelButton: 'Zrušiť',
            content: "Naozaj chcete prečíslovať id kamery?",
            confirm: function () {
                cameraNumbering();
            }
        }).open();

    });
    function cameraNumbering() {
        $.ajax({
            type : "PUT",
            url : window.location + "/camera/numbering",
            success: function(result){
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'green',
                    content: 'Čísla id kamery boli úspešne upravené.',
                    delayOnHover: true,
                    showCountdown: true
                });
                refreshDisciplineTable();
            },
            error : function(e) {
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'red',
                    content: 'Id čísla kamery neboli upravené !!',
                    delayOnHover: true,
                    showCountdown: true
                });
                console.log(e)
            }
        });
    }
});

