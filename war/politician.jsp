<%@page import="br.com.chutaum.model.Politician"%>
<%@page import="br.com.chutaum.politician.PoliticianController"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="br.com.chutaum.json.*"%>
 <%@page import=" java.util.Calendar"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="/css/TimeLine.css?v=1" />
</head>

<body>
	<div class="politician-profile">
		<% Politician poli = (Politician) request.getAttribute("politician"); %>
		<img  src=<%= poli.getPhoto() %> />
		<div class="politician-info"> <h2><%= poli.getName() %></h2> <p><%= poli.getParty() %></p> </div>
	</div>

	<ol id="timeline">
	<% JSONArray array = (JSONArray) request.getAttribute("actions");
		if (array!=null){
			SimpleDateFormat format = new SimpleDateFormat("dd/MM"); 
			Calendar calendar;
			
		for (int i = 0; i < array.length(); i++) {
			JSONObject json = array.getJSONObject(i);
			calendar = Calendar.getInstance();
			calendar.setTimeInMillis(json.getLong("DateMs"));
			
				%>
			
				 <li>
					 <div class="time">Realizou uma <%= json.getString("Kind") %> em <%= format.format(calendar.getTime()) %></div>
					<span class="corner"></span>
					<p class="content"><%= json.getString("Content") %></p>
				</li>
		<% }
		
		} %>
		</ol>  
</body>					

</html>			