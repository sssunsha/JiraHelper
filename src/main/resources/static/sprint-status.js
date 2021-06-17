
var tickets;

var tasks;
var stories;
var bugs;

var taskTabInstance;
var storyTabInstance;
var bugTabInstance;

function displayTicketsReport() {
    tasks = tickets.tasks;
    bugs = tickets.bugs;
    stories = tickets.stories;
}

function handleSprintGo() {
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
}

function init() {
    taskTabInstance = $('#tasks');
    storyTabInstance = $('#stories');
    bugTabInstance = $('#bugs');
}


$(document).ready(function(){

    init();
    
    $("#bamboo-sprint-status-id").click(handleSprintGo());
});


