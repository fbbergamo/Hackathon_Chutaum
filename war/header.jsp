<%@page import="br.com.chutaum.user.UserController"%>
<%@page import="com.google.appengine.api.users.*"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@ page import="java.net.*" %>

<link rel="stylesheet" type="text/css" href="/css/master.css?v=1" />

<div id="header">
<div class="box-content">
<div class="logo"><p>Aqui vai o Logo</p></div>

<div class="login">  <%
	com.google.appengine.api.users.UserService userService = UserServiceFactory.getUserService();
 	 br.com.chutaum.model.User user = UserController.currentUser(session);
 	 Set<String> attributes = new HashSet();
 	 	
     if (user==null) {
  
    	 %>
    	 
    	 <p>Login / Cadastro: </p>
   <a href="https://www.facebook.com/dialog/oauth?client_id=424259360931150&redirect_uri=http://<%= request.getServerName() %>:8888/login/facebook.html&scope=email,user_birthday&response_type=token"><img src="/images/facebook.png" /></a>
   <a href="<%=userService.createLoginURL("/login/login-google","Yahoo","yahoo.com",attributes) %>"><img src="/images/yahoo.png" /> </a>
    <a href="<%=userService.createLoginURL("/login/login-google") %>"><img src="/images/google.png" /></a><p>
   <% } else { %>
    <p class="login-user"> Cidadão: <%= user.getEmail() %><a href="<%= UserController.logout() %>">Sair</a></p>
   <%
     }
   %>
   
</div>
</div>

</div>