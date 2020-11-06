
$(document).ready(function(){

    var isClickEnabled = true;


    $("#mooncake-report-button-id").click(function(){
        if (isClickEnabled) {
            $("#content-mooncake-id").html("start to generate release report for team mooncake ... \n"
                + "please wait for a while ...");
            $.ajax({url:"/release-report/MOONCAKE",success:function(result){
            $("#content-mooncake-id").html(result);
            isClickEnabled = true;
            }});
        }
        isClickEnabled = false;
    });


    $("#bamboo-report-button-id").click(function(){
        if (isClickEnabled) {
            $("#content-bamboo-id").html("start to generate release report for team Bamboo ... \n"
                + " please wait for a while ...");
            $.ajax({url:"/release-report/BAMBOO",success:function(result){
            $("#content-bamboo-id").html(result);
            isClickEnabled = true;
            }});
        }
        isClickEnabled = false;
    });

    $("#bamboo-sprint-status-id").click(function(){
            if (isClickEnabled) {
                var sprintNumber = $("#bamboo-sprint-number").val();
                if (!sprintNumber) {
                    window.alert("please input valid sprint number first");
                }
                $.ajax({url:"/sprint-status/BAMBOO?sprintNumber=" + sprintNumber, success:function(result){
                isClickEnabled = true;
                }});
            }
            isClickEnabled = false;
        });
});

function openInNewTab(url) {
      var win = window.open(url, '_blank');
      win.focus();
}