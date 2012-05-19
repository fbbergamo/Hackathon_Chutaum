<%@page import="br.com.chutaum.user.UserController"%>
<%@page import="com.google.appengine.api.users.*"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@ page import="java.net.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<body>

  <%
	com.google.appengine.api.users.UserService userService = UserServiceFactory.getUserService();
 	 br.com.chutaum.model.User user = UserController.currentUser(session);
 	 Set<String> attributes = new HashSet();
 	 	
     if (user==null) {
  
    	 %>
   <a href="https://www.facebook.com/dialog/oauth?client_id=424259360931150&redirect_uri=http://localhost:8888/login/facebook.html&scope=email,user_birthday&response_type=token">Conta Facebook</a>
   <a href="<%=userService.createLoginURL("/login/login-google","Yahoo","yahoo.com",attributes) %>">Conect Yahoo</a>
    <a href="<%=userService.createLoginURL("/login/login-google") %>">Conect Google</a>
   <% } else { %>
      Welcome, <%= user.getEmail() %> <a href="<%= UserController.logout() %>">log out</a> <%= user.getEmail() %>
   <%
     }
   %>
   
</body>
</html>