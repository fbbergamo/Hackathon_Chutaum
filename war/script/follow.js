
$(document).ready(function() {
	


$(".follow").live('click',function(e) {
		
	e.preventDefault();
	
	 var jqXHR = $.ajax({
         url: $(this).attr('href'),
         context: $(this),
         doNotRetry: false
     });
	 if ($(this).attr('href')=="#") {
		 alert("Para Seguir um vereador � nescessario fazer o Login ou Cadastro"  );
		 
	 }
	 else {
     if (jqXHR) {
         jqXHR.done(function (data) {
        	 $(this).replaceWith(data); 
         	  });
     }

         jqXHR.fail("");
}
});

});


