<%@page import="br.com.chutaum.user.UserController"%>
<%@page import="com.google.appengine.api.users.*"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="java.net.*" %>
<%@page import="br.com.chutaum.model.Politician"%>

<%  
	com.google.appengine.api.users.UserService userService = UserServiceFactory.getUserService();
	 br.com.chutaum.model.User user = UserController.currentUser(session);

 	
	if (user==null) {
		%><a href="#" class="follow">Seguir</a>
		
		<%
	}
	else { 
	Politician poli = (Politician) request.getAttribute("politician"); 
	boolean isfolling = br.com.chutaum.user.UserController.isFollowing(user, poli);
	if (isfolling) {
		%><a href="/unfollow?user=<%= user.getEmail()%>&poli=<%= poli.getId()%>" class="follow">Seguindo</a><%
	}
	else {
	%><a href="/follow?user=<%= user.getEmail()%>&poli=<%= poli.getId()%>"  class="follow">Seguir</a>
		<%
	}
	}

%>