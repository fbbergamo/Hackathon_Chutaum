<%@page import="br.com.chutaum.model.User"%>
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
		<% User user = (User) request.getAttribute("user"); 
			String URL = (String) request.getAttribute("URL");
		%>
		
		<div class="politician-info"> <h2><%= user.getEmail() %></h2> 
		
	
		
		</div>
		
	</div>
	
	
	<ol id="timeline" data-fetch-url="<%=URL%>">
	
	</ol>
	
</body>					

</html>			