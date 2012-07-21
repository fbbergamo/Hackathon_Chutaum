<%@page import="br.com.chutaum.model.Politician"%>
<%@page import="br.com.chutaum.model.Action"%>
<%@page import="br.com.chutaum.politician.PoliticianController"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.google.appengine.api.datastore.Entity"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Locale"%>


	<%
		Politician poli = (Politician) request.getAttribute("politician"); 
		Iterable<Entity> array = (Iterable<Entity>) request.getAttribute("actions");
		String URL = (String) request.getAttribute("URL");
		if (array!=null){
			Locale local = new Locale("pt","BR");
	%> 
			<div class="url" data-fetch-url="<%=URL%>"></div> 
			<%
 				SimpleDateFormat monthformat = new SimpleDateFormat("MMMM",local);   
 			 				SimpleDateFormat format = new SimpleDateFormat("dd/MM"); 
 			 				SimpleDateFormat currentMonth = new SimpleDateFormat("M");
 			 				SimpleDateFormat year = new SimpleDateFormat("yyyy");
 			 				Calendar calendar;
 			 				int month = 0;
 			 				for (Entity result : array) {
 			 					Action action = new Action(result);
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
				 <div class="time">
						<img class="pull-left"  src="<%= poli.getPhoto()%>"  height="30" width="30" />
							<div style="margin-left:50px;">
								<small style="font-size:11px;"><%=  action.getNamePolitician() %> --  realizou "<%= action.getKind() %>" em <%= format.format(calendar.getTime()) %></small>
							</div>
					</div>
					
					<span class="corner"></span>
					<p style="clear:both; margin-top:5px;"><%= action.getContent() %></p>
					<p><% request.setAttribute("action", action); %> <jsp:include page="like.jsp" /> </p>		 
					
					<!--   <div class="fb-comments" data-href="http://localhost:8888/<%= action.getKey() %>" data-num-posts="2" data-width="400"></div>-->
				</li>
		<% }
		
		} %>
		</ol>  