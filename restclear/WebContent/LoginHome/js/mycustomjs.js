//LiveData on off button -- start


$('#toggle_event_editing button').click(function(){

	/* reverse locking status */
	$('#toggle_event_editing button').eq(0).toggleClass('locked_inactive locked_active btn-default btn-info');
	$('#toggle_event_editing button').eq(1).toggleClass('unlocked_inactive unlocked_active btn-info btn-default');
});

//LiveData on off button --end
$("#dashb").click(function(){
    event.preventDefault(); 
   
            $('#content').html("<div></div>");         

});
$("#dasbimg").click(function(){
    event.preventDefault(); 
 
            $('#content').html("<div></div>");         
       
});
$("#homeb").click(function(){
    event.preventDefault(); 
 
            $('#content').html("<div></div>");         
       
});
$("#signout").click(function(){
    
 
    $.get('Logoutservlet',{request:"form"},function(responseText) { 
		  
  });
    location.reload(true);
       
});


var timerID
$(document).ready(function(){
   
	
	 
	 $("#myonoffswitch").click(function(){
		  
	        if($(myonoffswitch).is(':checked') == true) 
			{
	        	 $("#Livesclicktatusval").text("Updating");
	  		   		   
	        	
	        	 $.post( "./rest/RTUpdateService", {update : "update"},function(result) { });
	        	 $.post( "./rest/UpdateService", {update : "update"},function(result) { });
	 	
	        	
	      	
	        	
	  		   
			  timerID = setInterval(function() {
				 
				 
			  
			   
			  
				  $.post( "./rest/RTUpdateService", {update : "update"},function(result) { });
			  
			  
				 
			  
			  
			  }, 120 * 1000);
			  
			 
			}
	        else
			{
			 
			  clearInterval(timerID); 
			
			
			}
			  
			});
    $("#Inv-st1").click(function(){
	   event.preventDefault(); 
	  
	   $('#content').html("<div>Loading Data.....</div>");    
       $.post('InvestStrategyTrend',{request:"form"},function(responseText) { 
                $('#content').html(responseText);         
            });
    });
    
    
    $("#Inv-st2").click(function(){
 	   event.preventDefault(); 
 	   $('#content').html("<div>Loading Data.....</div>");    
        $.get('InvestStrategyWave',{request:"form"},function(responseText) { 
                 $('#content').html(responseText);         
             });
     });
    
    $("#Swi-st1").click(function(){
  	   event.preventDefault(); 
  	   $('#content').html("<div>Loading Data.....</div>");    
         $.get('EodfiltershowliveServlet',{request:"form"},function(responseText) { 
                  $('#content').html(responseText);         
              });
      });
    
    $("#Swi-st2").click(function(){
  	   event.preventDefault(); 
  	   $('#content').html("<div>Loading Data.....</div>");    
         $.get('AllTimeHighLowServlet',{request:"form"},function(responseText) { 
                  $('#content').html(responseText);         
              });
      });
    
    
    $("#Fst-st1").click(function(){
   	   event.preventDefault(); 
   	   $('#content').html("<div>Loading Data.....</div>");    
          $.get('Week52HighLow',{request:"form"},function(responseText) { 
                   $('#content').html(responseText);         
               });
       });
    
    $("#Fst-st2").click(function(){
 	   event.preventDefault(); 
 	   $('#content').html("<div>Loading Data.....</div>");    
        $.get('CandlePatternServlet',{request:"form"},function(responseText) { 
                 $('#content').html(responseText);         
             });
     });
	 
	  $("#Pv-Daily").click(function(){
 	   event.preventDefault(); 
 	   $('#content').html("<div>Loading Data.....</div>");    
        $.get('CandlePatternServlet',{request:"form"},function(responseText) { 
                 $('#content').html(responseText);         
             });
     });
	 
	  $("#Pv-Weekly").click(function(){
 	   event.preventDefault(); 
 	   $('#content').html("<div>Loading Data.....</div>");    
        $.get('CandlePatternServlet',{request:"form"},function(responseText) { 
                 $('#content').html(responseText);         
             });
     });
	 
	  $("#Pv-Monthly").click(function(){
 	   event.preventDefault(); 
 	   $('#content').html("<div>Loading Data.....</div>");    
        $.get('CandlePatternServlet',{request:"form"},function(responseText) { 
                 $('#content').html(responseText);         
             });
     });
	 
	  $("#Pv-Yearly").click(function(){
 	   event.preventDefault(); 
 	   $('#content').html("<div>Loading Data.....</div>");    
        $.get('CandlePatternServlet',{request:"form"},function(responseText) { 
                 $('#content').html(responseText);         
             });
     });
	 
	 $("#MS-settings").click(function(){
 	   event.preventDefault(); 
 	   $('#content').html("<div>Loading Data.....</div>");    
        $.get('CandlePatternServlet',{request:"form"},function(responseText) { 
                 $('#content').html(responseText);         
             });
     });
	 
	 $("#MS-strategy").click(function(){
 	   event.preventDefault(); 
 	   $('#content').html("<div>Loading Data.....</div>");    
        $.get('CandlePatternServlet',{request:"form"},function(responseText) { 
                 $('#content').html(responseText);         
             });
     });
	 
	 $("#Alert-view").click(function(){
 	   event.preventDefault(); 
 	   $('#content').html("<div>Loading Data.....</div>");    
        $.get('CandlePatternServlet',{request:"form"},function(responseText) { 
                 $('#content').html(responseText);         
             });
     });
	 
	 $("#Alert-Manage").click(function(){
 	   event.preventDefault(); 
 	   $('#content').html("<div>Loading Data.....</div>");    
        $.get('CandlePatternServlet',{request:"form"},function(responseText) { 
                 $('#content').html(responseText);         
             });
     });
	 
	 
	 
	 
	 
	 
    
    
	
	
});



