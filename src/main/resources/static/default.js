
$(document).ready(function(){

    isClickEnabled = true;


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
});

function openInNewTab(url) {
      var win = window.open(url, '_blank');
      win.focus();
}