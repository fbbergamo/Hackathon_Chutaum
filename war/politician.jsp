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
 

	<div class="politician-profile">
		<% Politician poli = (Politician) request.getAttribute("politician"); 
			String URL = (String) request.getAttribute("URL");
		%>
		
		<img  src=<%= poli.getPhoto() %> />
		<div class="politician-info"> <h2><%= poli.getName() %></h2> <p><%= poli.getParty() %></p> 

		
		<div class="politician-follow-profile"><small><% request.setAttribute("politician", poli); %>
		
		<jsp:include page="follow.jsp" /></small>
		
		</div>
		</br>
		<div class="fb-like" data-href="http://chutaum.appspot.com/?vereador=<%= poli.getId() %>" data-send="true" data-layout="button_count"  data-width="200" data-show-faces="false" data-font="verdana"></div>
		</div>
		
	</div>
	
	
	<ol id="timeline" data-fetch-url="<%=URL%>">
	
	</ol>
	
</body>					

</html>			