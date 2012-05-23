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
	<jsp:include page="header.jsp" />

	
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

	<div class="politician-profile">
		<% Politician poli = (Politician) request.getAttribute("politician"); 
			String URL = (String) request.getAttribute("URL");
		%>
		<img  src=<%= poli.getPhoto() %> />
		<div class="politician-info"> <h2><%= poli.getName() %></h2> <p><%= poli.getParty() %></p> 
		
		<div class="politician-follow-profile"><small><% request.setAttribute("politician", poli); %> 
		<jsp:include page="follow.jsp" /></small>
		</div>
		
		</div>
		
	</div>
	
	
	<ol id="timeline" data-fetch-url="<%=URL%>">
	
	</ol>
	
</body>					

</html>			