
$(document).ready(function(){
    $("#bamboo-sprint-status-id").click(function(){
        var sprintNumber = $("#bamboo-sprint-number").val();
        var sprintGeneralStatus = $("#bamboo-sprint-status-general-status");
        if (!sprintNumber) {
            window.alert("please input valid sprint number first");
        }
        $.ajax({url:"/sprint-status/BAMBOO?sprintNumber=" + sprintNumber,
            success:function(result){
            // TODO: add result handle codes
//            sprintGeneralStatus.html(result);
        }});
        // TODO:: add status update message codes
//        sprintGeneralStatus.html("start to generate sprint status for Bamboo ...");
    });
});