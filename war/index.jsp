<%@page import="br.com.chutaum.model.Politician"%>
<%@page import="com.google.appengine.api.datastore.Entity"%>
<%@page import="br.com.chutaum.user.UserController"%>
<%@page import="com.google.appengine.api.users.*"%>
<%@page import="br.com.chutaum.politician.PoliticianController"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@ page import="java.net.*" %>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>


<head>

<jsp:include page="header.jsp" />

</head>
<body>


	
<div class="box-content" style="overflow:hidden">
	<h1>Siga os vereadores de São Paulo</h1>
	<div class="sub-header">
	<div class="fb-like" data-href="http://chutaum.appspot.com/" data-send="true" data-layout="box_count" data-width="450" data-show-faces="true" data-font="verdana"></div>
	
	</div>
	<%	
	Iterable<Entity> politicians;
	politicians = PoliticianController.allPoliticians();
	
	for (Entity ent : politicians) {
		Politician politician = new Politician(ent); %>
		<div class="list-politician" style="">
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
    
    </div> 
  
     <div class="footer" 
   style="
	margin-top: 100px; /* negative value of footer height */
	width: 100%;
	background-color: #D2D9E7;
	height: 250px;	">
		<div class="box-content" style="padding-top: 25px;">      	<h4 style="margin-bottom:20px;">contato@vereadores.org</h4>	
		<h4>Vereadores.org na Imprensa: </h4>
		<a href="http://cbn.globoradio.globo.com/cbn-sp/2012/06/09/EQUIPE-VENCE-MARATONA-TECNOLOGICA-COM-FACEBOOK-DE-VEREADORES-PAULISTANOS.htm" target="new"><img class="press" src="/images/cbn.jpg" /></a>
		<a href="http://www.estadao.com.br/noticias/impresso,facebook-de-vereadores-ganha-concurso-,883368,0.htm" target="new"><img class="press" src="/images/estadao.jpg" /></a>
		<a href="http://g1.globo.com/tecnologia/noticia/2012/06/rede-social-de-vereadores-baseada-no-facebook-ganha-concurso-em-sp.html" target="new"><img  class="press" src="/images/g1.jpg" /></a>
		<a href="http://blogs.estadao.com.br/jt-cidades/camara-tera-facebook-de-vereadores/" target="new"><img class="press" src="/images/jornaldatarde.jpg" /></a>
		</div>
		
    </div>
</body>

</html>

