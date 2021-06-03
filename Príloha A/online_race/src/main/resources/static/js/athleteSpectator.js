$(document).ready(function () {

    $("#tableResults").DataTable();
    $("#tableClubs").DataTable();


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
