<%@page import="br.com.chutaum.model.Politician"%>
<%@page import="br.com.chutaum.model.Action"%>
<%@page import="br.com.chutaum.politician.PoliticianController"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.google.appengine.api.datastore.Entity"%>
<%@page import=" java.util.Calendar"%>
<%@page import=" java.util.Locale"%>

<!DOCTYPE html>
<html>
<head>
	<script type="text/JavaScript" src="/script/jquery.min.js"></script> 
	<script type="text/JavaScript" src="/script/jquery.endless-scroll.js"></script> 
	<script src="//connect.facebook.net/en_US/all.js"></script>
	<link rel="stylesheet" type="text/css" href="/css/TimeLine.css?v=2" />
	<script type="text/JavaScript" src="/script/load-actions.js?v=3"></script> 
	
</head>

<body>
 
	<div id="fb-root"></div>
	<script>
  window.fbAsyncInit = function() {
    FB.init({
      appId      : '377536698931807', // App ID
      status     : true, // check login status
      cookie     : true, // enable cookies to allow the server to access the session
      xfbml      : true  // parse XFBML
    });

    // Additional initialization code here
  };

  // Load the SDK Asynchronously
  (function(d){
     var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement('script'); js.id = id; js.async = true;
     js.src = "//connect.facebook.net/pt_BR/all.js";
     ref.parentNode.insertBefore(js, ref);
     FB.Canvas.setAutoResize();
   }(document));
  
</script>
<jsp:include page="header.jsp" />
	<div class="politician-profile">
		<% Politician poli = (Politician) request.getAttribute("politician"); 
			String URL = (String) request.getAttribute("URL");
			
		
		%>
		<img  src=<%= poli.getPhoto() %> />
		<div class="politician-info"> <h2><%= poli.getName() %></h2> <p><%= poli.getParty() %></p> </div>
	</div>
	
	
	<ol id="timeline" data-fetch-url="<%=URL%>">
	
	</ol>
	
</body>					

</html>			