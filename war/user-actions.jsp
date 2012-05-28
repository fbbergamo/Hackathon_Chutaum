<%@page import="br.com.chutaum.model.UserAction"%>
<%@page import="br.com.chutaum.model.Politician"%>
<%@page import="br.com.chutaum.model.Action"%>
<%@page import="br.com.chutaum.politician.PoliticianController"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.google.appengine.api.datastore.Entity"%>
<%@page import=" java.util.Calendar"%>
<%@page import=" java.util.Locale"%>


	<% 
		
		Iterable<Entity> array = (Iterable<Entity>) request.getAttribute("actions");
		String URL = (String) request.getAttribute("URL");
		if (array!=null){
			Locale local = new Locale("pt","BR");  %> 
			<div class="url" data-fetch-url="<%=URL%>"></div> 
			<%
			SimpleDateFormat monthformat = new SimpleDateFormat("MMMM",local);   
			SimpleDateFormat format = new SimpleDateFormat("dd/MM"); 
			SimpleDateFormat currentMonth = new SimpleDateFormat("M");
			SimpleDateFormat year = new SimpleDateFormat("yyyy");
			Calendar calendar;
			int month = 0;
			for (Entity result : array) {
				
				UserAction action = new UserAction(result);
				calendar = Calendar.getInstance();
				calendar.setTimeInMillis(action.getDateMs());
				if (month!=Integer.parseInt(currentMonth.format(calendar.getTime()))) {
					%>
					<p class="month"><%= monthformat.format(calendar.getTime()) +" "+ year.format(calendar.getTime()) %></p>
					<p></p>
					<% 
					
					month = Integer.parseInt(currentMonth.format(calendar.getTime()));
				}
				%>
				 <li>
					<div class="time"><img  src="<%= action.getPhoto()%>" /> <p><%= action.getNamePolitician() %> --  realizou "<%= action.getKind() %>" em <%= format.format(calendar.getTime()) %></p></div></div>
					<span class="corner"></span>
					<p class="content"><%= action.getContent() %></p>
					<!--   <div class="fb-comments" data-href="http://localhost:8888/<%= action.getUniqueActions() %>" data-num-posts="2" data-width="400"></div>-->
				</li>
		<% }
		
		} %>
		</ol>  