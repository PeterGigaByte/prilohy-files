$(document).ready(function() {
    $("#table").DataTable(
        {
            "order": [[ 0, "desc" ]],
            "columnDefs": [

            { type: 'de_date', targets: 0 },

            {
                "targets": [4, 5 ],
                "orderable": false
            } ]});

});