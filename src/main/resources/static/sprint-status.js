
$(document).ready(function(){
    $("#bamboo-sprint-status-id").click(function(){
        var sprintNumber = $("#bamboo-sprint-number").val();
        if (!sprintNumber) {
            window.alert("please input valid sprint number first");
        }
        $.ajax({url:"/sprint-status/BAMBOO?sprintNumber=" + sprintNumber, success:function(result){
        isClickEnabled = true;
        }});
    });
});