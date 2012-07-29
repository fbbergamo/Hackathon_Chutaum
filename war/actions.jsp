<%@page import="br.com.chutaum.model.Politician"%>
<%@page import="br.com.chutaum.model.Action"%>
<%@page import="br.com.chutaum.politician.PoliticianController"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.google.appengine.api.datastore.Entity"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.List;"%>
<%@page pageEncoding="UTF-8"%>

	<%
	
		List<Action> array = (List<Action>) request.getAttribute("actions");
		
		String URL = (String) request.getAttribute("URL");
		
		if (array!=null){
			
	%> 
			<div class="url" data-fetch-url="<%=URL%>"></div> 
			<%

 			 				int month = 0;
 			 				for (Action action : array) {
 			 					if (month!=action.getMonth()) {
						 			%>
											<p class="month"><%= action.getMonthText() +" "+ action.getYear() %></p>
											<p></p>
											<% 
											month = action.getMonth();
										}
										%>
				 <li>
				 <div class="time">
						<img class="pull-left"  src="<%= action.getPhoto() %>"  height="30" width="30" />
							<div style="margin-left:50px;">
								<small style="font-size:11px;"><%=  action.getNamePolitician() %> --  realizou "<%= action.getApp().getName() %>" em <%= action.getFormatDate() %></small>
							</div>
					</div>
					
					<span class="corner"></span>
					<p style="clear:both; margin-top:5px;"><%= action.getContent() %></p>
					<p><% request.setAttribute("action", action); %> <jsp:include page="like.jsp" /> </p>		 
			
				</li>
		<% }
		
		} %>
		</ol>  