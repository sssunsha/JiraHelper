
$(document).ready(function(){

    var isClickeEnabled = true;


    $("#mooncake-report-button-id").click(function(){
        if (isClickeEnabled) {
            $("#content-mooncake-id").html("start to generate release report for team mooncake ... \n"
                + "please wait for a while ...");
            $.ajax({url:"/release-report/MOONCAKE",success:function(result){
            $("#content-mooncake-id").html(result);
            isClickeEnabled = true;
            }});
        }
        isClickeEnabled = false;
    });


    $("#bamboo-report-button-id").click(function(){
        if (isClickeEnabled) {
            $("#content-bamboo-id").html("start to generate release report for team Bamboo ... \n"
                + " please wait for a while ...");
            $.ajax({url:"/release-report/BAMBOO",success:function(result){
            $("#content-bamboo-id").html(result);
            isClickeEnabled = true;
            }});
        }
        isClickeEnabled = false;
    });

});