
$(document).ready(function() {
	

$(".vote").live('click',button);

});

function button(e) {
	
	e.preventDefault();
			
			 var jqXHR = $.ajax({
		         url: $(this).attr('href'),
		         context: $(this),
		         doNotRetry: false
		     });
			
			
		     if (jqXHR) {
		         jqXHR.done(function (data) {
		        $(this).parent().parent().replaceWith(data); 
		         	  });
		     }
		     
	         jqXHR.fail(function () { alert("Erro ao conectar o servidor") });
		  
	 
}



