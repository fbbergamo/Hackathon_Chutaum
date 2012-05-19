<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="br.com.chutaum.json.*"%>

<% JSONArray array = (JSONArray) request.getAttribute("actions");
	if (array!=null){
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
	for (int i = 0; i < array.length(); i++) {
			JSONObject json = array.getJSONObject(i); %>
			<h1><%= json.getString("Content") %></h1>
			<p><%= json.getString("Kind") %></p>
			<p><%= json.getString("Date") %></p>
	<% }} %>
						