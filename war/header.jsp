<%@page pageEncoding="UTF-8"%>
<%@page import="br.com.chutaum.user.UserController"%>
<%@page import="com.google.appengine.api.users.*"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@ page import="java.net.*" %>
<title>Vereadores.org</title>
<script type="text/JavaScript" src="/script/jquery-1.7.2.min.js?v=2"></script> 
<script type="text/JavaScript" src="/script/follow.js?v=1"></script> 
<script type="text/JavaScript" src="/script/vote.js?v=12"></script> 

<script type="text/JavaScript" src="/script/jquery.endless-scroll.js?v=2"></script> 
<link rel="stylesheet" type="text/css" href="/css/TimeLine.css?v=21" />
<script type="text/JavaScript" src="/script/load-actions.js?v=22"></script> 
<link rel="stylesheet" type="text/css" href="/css/master.css?v=23" />
<script src="//connect.facebook.net/en_US/all.js"></script>


	<link href="css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css" />
	<link href="Fort/css/font-awesome.css" media="screen" rel="stylesheet" type="text/css" />

<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-32067210-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>



<div id="header">
<div class="box-content">
<div class="logo"><a href="/">VEREADORES.ORG</a></div>

<div class="login"> 


 <%
	com.google.appengine.api.users.UserService userService = UserServiceFactory.getUserService();
 	 br.com.chutaum.model.User user = UserController.currentUser(session);
 	 Set<String> attributes = new HashSet();
 	 	
     if (user==null) {
  
    	 %>
    	 
    	 <p>Login / Cadastro: </p>
   <a href="https://www.facebook.com/dialog/oauth?client_id=424259360931150&redirect_uri=http://<%= request.getServerName() %>/login/facebook.html&scope=email,user_birthday&response_type=token"><img src="/images/facebook.png" /></a>
   <a href="<%=userService.createLoginURL("/login/login-google","Yahoo","yahoo.com",attributes) %>"><img src="/images/yahoo.png" /> </a>
    <a href="<%=userService.createLoginURL("/login/login-google") %>"><img src="/images/google.png" /></a>
    <img  src="https://developers.google.com/appengine/images/appengine-noborder-120x30.gif" 
alt="Powered by Google App Engine" />
   <% } else { %>
   
   <a href="/">Home</a> <a href="/cidadao">Meus Vereadores</a>
    <span> Cidadão: <%= user.getEmail() %><a href="<%= UserController.logout() %>">Sair</a></span>
   <%
     }
   %>
   
</div>
</div>

</div>