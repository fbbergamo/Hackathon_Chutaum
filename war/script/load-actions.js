var chutaum = window.chutaum || {};

chutaum.actions = new function () {

    this.init = function () {
        this.bindEvents();
        this.timeline = $('#timeline');
        this.timeline.trigger('init-actions');
    };

    this.bindEvents = function () {
        $("#timeline").live('init-actions', this.initActions);
        $("#timeline").live('ajax-call', this.ajaxCall);
    };

    this.initActions = function () {
    	
    	$(document).endlessScroll({
    	    fireOnce: true,
    	    fireDelay: false,
    	   
    	    callback: function(p){
    	    	chutaum.actions.timeline.trigger('ajax-call')
    	    }
    	  });
    	
        $(this).trigger('ajax-call'); 
    };

    this.ajaxCall = function () {
    	if ( $(this).attr('data-fetch-url')!='') {
	        var jqXHR = $.ajax({
	            url: $(this).attr('data-fetch-url'),
	            context: $(this),
	            doNotRetry: true
	        });
	
	        if (jqXHR) {
	            jqXHR.done(function (data) {
	            	$(this).append(data);
	                var url = $(".url").attr('data-fetch-url');
	                $(".url").remove();
	                this.attr('data-fetch-url', url); 
	
	            });
	
	            jqXHR.fail(function () { alert("Erro ao conectar o servidor") });
	        }
    	}
    };

}

$(document).ready(function() {
	
	chutaum.actions.init();

});



