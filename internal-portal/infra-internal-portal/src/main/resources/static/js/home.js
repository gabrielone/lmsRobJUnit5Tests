var urlSrc = '/get-all-leaves';
$(document).ready(function() {

    $('#mycalendar').fullCalendar({
      header: {
        left: 'prev,next today',
        center: 'title',
        right: 'month,agendaWeek,listWeek'
      },
      defaultDate: moment().format("YYYY-MM-DD"),
      eventLimit: true, // allow "more" link when too many events
      events: urlSrc    	  // this will make a request to: /get-all-leaves?start=2020-03-01&end=2020-04-12&_=1584603008557
    });
    
    $('input:checkbox').change(function(){ // when checkbox selected, this will make a new request to /get-all-leaves?accepted=
    	console.log('your message');
    	$('#mycalendar').fullCalendar('removeEventSource',urlSrc);
    	urlSrc = '/get-all-leaves?pending='+$('#pending').is(':checked')+'&accepted='+$('#accepted').is(':checked')+'&rejected='+$('#rejected').is(':checked')+'';
    	console.log(urlSrc);
    	$('#mycalendar').fullCalendar('removeEvents');
    	$('#mycalendar').fullCalendar('addEventSource',urlSrc);
    	$('#mycalendar').fullCalendar('refetchEvents');
        
    });

  });
