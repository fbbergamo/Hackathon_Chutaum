
<%@page import="br.com.chutaum.user.UserController"%>
<%@page import="com.google.appengine.api.users.*"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@ page import="java.net.*" %>
<title>Vereadores.org</title>
<script type="text/JavaScript" src="/script/jquery.min.js"></script> 
<script type="text/JavaScript" src="/script/follow.js"></script> 
<script type="text/JavaScript" src="/script/vote.js"></script> 

<script type="text/JavaScript" src="/script/jquery.endless-scroll.js?v=1"></script> 
<link rel="stylesheet" type="text/css" href="/css/TimeLine.css?v=31" />
<script type="text/JavaScript" src="/script/load-actions.js?v=32"></script> 
<link rel="stylesheet" type="text/css" href="/css/master.css?v=13" />
<script src="//connect.facebook.net/en_US/all.js"></script>

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

 <div id="fb-root"></div>
	<script>
  window.fbAsyncInit = function() {
    FB.init({
      appId      : '377536698931807', // App ID
      status     : true, // check login status
      cookie     : true, // enable cookies to allow the server to access the session
      xfbml      : true  // parse XFBML
    });

    // Additional initialization code here
  };

 
  (function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/pt_BR/all.js#xfbml=1&appId=424259360931150";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk'));
  
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