$(document).ready(function() {

    let selectedDisciplines;
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
    var nameType = $.fn.dataTable.absoluteOrder( {
        value: '', position: 'bottom'
    } );

    $(".rightTable").DataTable({
        "searching": false,
        "paging":   false,
        "bInfo": false,
        "bPaginate": false,
        "info":  false,
        select:  false,
        "ordering": true,
        "autoWidth": false,
        "columnDefs" : [{
            'bSortable': false,
            'aTargets': ['nosort']},{"targets":3, "type":"date-eu"},{ targets: 0, type: nameType },{ targets: 6, type: nameType },{
            "targets": [3],
            "orderable": false
        }],

    });



} );

