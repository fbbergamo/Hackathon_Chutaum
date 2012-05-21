<%@page import="br.com.chutaum.model.Politician"%>
<%@page import="com.google.appengine.api.datastore.Entity"%>
<%@page import="br.com.chutaum.user.UserController"%>
<%@page import="com.google.appengine.api.users.*"%>
<%@page import="br.com.chutaum.politician.PoliticianController"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@ page import="java.net.*" %>

<html>
<title>Linha do Tempo</title>
<body>
	<jsp:include page="header.jsp" />
<div class="box-content">
	<h1>Siga os Vereadores de sua preferência</h1>
	<%	
	Iterable<Entity> politicians;
	politicians = PoliticianController.allPoliticians();
	
	for (Entity ent : politicians) {
		Politician politician = new Politician(ent); %>
		<div class="list-politician">
			<a href="/politician?vereador=<%=politician.getId() %>">
			<img src="<%= politician.getPhoto() %>" />
			<small><%= politician.getName() %></small></a>
		</div>
		<% 
	}
	 com.google.appengine.api.users.UserService userService = UserServiceFactory.getUserService();
 	 br.com.chutaum.model.User user = UserController.currentUser(session);
 	 	
     if (user==null) {
    	
    	 
    	 %>
     
    <%
     }
     else {
     
     
     %>
     
     <% 
     }
     %>
    </div> 
</body>
</html>

