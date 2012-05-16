<%@page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="java.net.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<body>

  <%
	com.google.appengine.api.users.UserService userService = UserServiceFactory.getUserService();
     if (!userService.isUserLoggedIn()) {
  
    	 %>
   <a href="https://www.facebook.com/dialog/oauth?client_id=424259360931150&redirect_uri=http://localhost:8888/login/facebook.html&scope=email,user_birthday&response_type=token">Conta Facebook</a>
   <a href="<%=userService.createLoginURL("/login/login-google") %>">Conta Google</a>
   <% } else { %>
      Welcome, <%= userService.getCurrentUser().getEmail() %>!
        <a href="<%=userService.createLogoutURL("/") %>">log out</a>
   <%
     }
   %>
   
</body>
</html>