
$(document).ready(function() {
	

$(".follow").live('click',function(e) {
		
	e.preventDefault();
	
	 if ($(this).attr('href')=="#") {
		 alert("Para Seguir um vereador é necessario fazer o Login ou Cadastro"  );
	 }
	 else {
		 
		 var jqXHR = $.ajax({
	         url: $(this).attr('href'),
	         context: $(this),
	         doNotRetry: false
	     });
		
	     if (jqXHR) {
	         jqXHR.done(function (data) {
	        	 $(this).replaceWith(data); 
	         	  });
	     }

         jqXHR.fail(function () { alert("Erro ao conectar o servidor") });
}
});

});



