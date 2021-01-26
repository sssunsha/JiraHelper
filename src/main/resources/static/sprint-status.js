
var tickets;

var tasks;
var stories;
var bugs;

function displayTicketsReport() {
    tasks = tickets.tasks;
    bugs = tickets.bugs;
    stories = tickets.stories;
}

$(document).ready(function(){
    $("#bamboo-sprint-status-id").click(function(){
        var sprintNumber = $("#bamboo-sprint-number").val();
        var sprintGeneralStatus = $("#bamboo-sprint-status-general-status");
        if (!sprintNumber) {
            window.alert("please input valid sprint number first");
        }
        $.ajax({url:"/sprint-status/BAMBOO?sprintNumber=" + sprintNumber,
            success:function(result){
            tickets = result;
            displayTicketsReport();
        }});
    });
});


