<%@page import="br.com.chutaum.model.Politician"%>
<%@page import="com.google.appengine.api.datastore.Entity"%>
<%@page import="br.com.chutaum.user.UserController"%>
<%@page import="com.google.appengine.api.users.*"%>
<%@page import="br.com.chutaum.politician.PoliticianController"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@ page import="java.net.*" %>
<!DOCTYPE html>
<html>


<head>

<jsp:include page="header.jsp" />

</head>
<body>


	
<div class="box-content">
	<h1>Siga os vereadores de S�o Paulo</h1>
	<div class="sub-header">
	<div class="fb-like" data-href="http://chutaum.appspot.com/" data-send="true" data-layout="box_count" data-width="450" data-show-faces="true" data-font="verdana"></div>
	
	</div>
	<%	
	Iterable<Entity> politicians;
	politicians = PoliticianController.allPoliticians();
	
	for (Entity ent : politicians) {
		Politician politician = new Politician(ent); %>
		<div class="list-politician">
			<a href="/politician?vereador=<%=politician.getId() %>">
			<img src="<%= politician.getPhoto() %>" />
			<p class="poli-name"><%= politician.getName() %></p></a>
			<small>
			<% request.setAttribute("politician", politician); %> 
			<jsp:include page="follow.jsp" />
			</small>
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
      	<p style="
    float: left;
    margin-top: 40px;
">Contato:  harnbb@gmail.com / hitoshi@pop.com.br </p>	
    </div> 
  
</body>

</html>

