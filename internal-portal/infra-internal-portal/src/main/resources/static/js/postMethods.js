$(document).ready(function () {

    $("#newLeaveForm").submit(function (event) {

       //stop submit the form, we will post it manually.
       event.preventDefault();
       
       var leaveDetails = {}
   	   var fromDate = $("#fromDate").val();
       var toDate = $("#toDate").val();
   	   var leaveType = $("#leaveType").val();
   	   var reason = $("#reason").val();
   	   var leaveDetails = { "fromDate" : fromDate, "toDate" : toDate, "leaveType": leaveType, "reason" : reason};
       
   	   fire_ajax_submit(leaveDetails, "#newLeaveForm");
    });
    
    $("#newVacantionTypeForm").submit(function (event) {
    	
       event.preventDefault();
       
       var vacantionType = {}
   	   var type = $("#type").val();
   	   var vacantionType = { "type" : type};
       
   	   fire_ajax_submit(vacantionType, "#newVacantionTypeForm");
    });
    
    $("#newYearlyTimeOffForm").submit(function (event) {

       event.preventDefault();
       
       var yearlyTimeOff = {}
   	   var fromDate = $("#fromDate").val();
   	   var toDate = $("#toDate").val();
   	   var vacantionTypeId = $("#vacantionTypeId").val();
   	   var yearlyTimeOff = { "fromDate" : fromDate, "toDate" : toDate, "vacantionTypeId": vacantionTypeId};
       
       fire_ajax_submit(yearlyTimeOff, "#newYearlyTimeOffForm");
    });

});

function fire_ajax_submit(data, formName) {
	
	 $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        resourceURL: $(formName).attr( "action"),
	        data: JSON.stringify(data),
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (data) {

	        	
	        	var json = "<h4>Result</h4><pre style=\"font-size: 16px; color:green; text-decoration: bold\">"
	                + "Request created sucessfully!" + "</pre>";
	            $('#result-message').html(json);

	            console.log("SUCCESS : ", data);
	            $("#btn-search").prop("disabled", false);

	        },
	        error: function (e) {
	        	var message;
	        	if(e.responseText.startsWith("ERROR")){
	        		message = e.responseText;
	        	} else {
	        		var res = e.responseText.split(":");
	        		if(res.length === 1){
	        			message = e.responseText;
	        		} else {
	        			message = res[res.length -1].substr(0,res[res.length -1].length - 2);
	        		}
	        	}
	            var json = "<h4>Request results </h4><pre style=\"font-size: 16px; color:red; text-decoration: bold\">"
	                + message + "</pre>";
	            $('#result-message').html(json);
	        }
	    });

}