(function() {
    "use strict";

    // custom scrollbar

    $("html").niceScroll({styler:"fb",cursorcolor:"#1b93e1", cursorwidth: '6', cursorborderradius: '10px', background: '#FFFFFF', spacebarenabled:false, cursorborder: '0',  zindex: '1000'});

    $(".scrollbar1").niceScroll({styler:"fb",cursorcolor:"#1b93e1", cursorwidth: '6', cursorborderradius: '0',autohidemode: 'false', background: '#FFFFFF', spacebarenabled:false, cursorborder: '0'});

	
	
    $(".scrollbar1").getNiceScroll();
    if ($('body').hasClass('scrollbar1-collapsed')) {
        $(".scrollbar1").getNiceScroll().hide();
    }

    $("#newVacantionTypeButton").click(function(event){
        event.preventDefault();
        $("#select-existing-vacantion-type").hide();
        $("#create-new-vacantion-type").show();
        
        
      });
    
    $("#selectVacantionType").click(function(event){
        event.preventDefault();
        $("#select-existing-vacantion-type").show();
        $("#create-new-vacantion-type").hide();
        
        
      });
    
    $("#submit-button").click(function(event){
        event.preventDefault();
        
         var values = {
        	            'fromDate': document.getElementById('fromDateId').value,
        	            'toDate': document.getElementById('toDateId').value,
        				'vacantionType': document.getElementById('vacantionTypeId').value
        				
        };
              
       	$.ajax({
        		 url: "http://localhost:8080/user/apply-yearly-time-off",
        		 type: "POST",
        		 data: values,
        		dataType: 'JSON'
              }); 
       
        
        
      });
    
})(jQuery);


                     
     
  