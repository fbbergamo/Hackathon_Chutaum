<%@page import="br.com.chutaum.model.Politician"%>
<%@page import="com.google.appengine.api.datastore.Entity"%>
<%@page import="br.com.chutaum.user.UserController"%>
<%@page import="com.google.appengine.api.users.*"%>
<%@page import="br.com.chutaum.politician.PoliticianController"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@ page import="java.net.*"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>


<jsp:include page="header.jsp" />



	<div class="box-content" style="overflow: hidden">
		<h1>Siga os vereadores de São Paulo</h1>
		<div style="margin: 10px; text-align: center">
		<h5>
			Saiba como é a participação de cada vereador na Câmara Municipal.<br> 
			Informe-se sobre suas votações e suas propostas de projetos.<br> 
			Siga os vereadores do seu interesse e acompanhe sua atuação.
		</h5>
		</div>
		<div class="sub-header" style="margin: 10px; text-align: center">
			<iframe src="//www.facebook.com/plugins/likebox.php?href=https%3A%2F%2Fwww.facebook.com%2Fpages%2Fvereadoresorg%2F251890488264107&amp;width=400&amp;height=290&amp;colorscheme=light&amp;show_faces=true&amp;border_color&amp;stream=false&amp;header=true&amp;appId=424259360931150" scrolling="no" frameborder="0" style="border:none; overflow:hidden; width:400px; height:290px;" allowTransparency="true"></iframe>

		</div>
		<%
			Iterable<Entity> politicians;
			politicians = PoliticianController.allPoliticians();

			for (Entity ent : politicians) {
				Politician politician = new Politician(ent);
		%>
		<div class="list-politician" style="">
			<a href="/politician?vereador=<%=politician.getId()%>"> <img
				src="<%=politician.getPhoto()%>" />
				<p class="poli-name"><%=politician.getName()%></p></a> <small>
				<%
					request.setAttribute("politician", politician);
				%> <jsp:include
					page="follow.jsp" />
			</small>
		</div>


		<%
			}
			com.google.appengine.api.users.UserService userService = UserServiceFactory
					.getUserService();
			br.com.chutaum.model.User user = UserController
					.currentUser(session);

			if (user == null) {
		%>

		<%
			} else {
		%>

		<%
			}
		%>

	</div>

	<div class="footer"
		style="margin-top: 100px; /* negative value of footer height */ width: 100%; background-color: #D2D9E7; height: 299px;">
		<div class="box-content" style="padding-top: 25px;">
			<h4 style="margin-bottom: 20px;">contato@vereadores.org</h4>
			<h4>Vereadores.org na Imprensa:</h4>
			<a
				href="http://cbn.globoradio.globo.com/cbn-sp/2012/06/09/EQUIPE-VENCE-MARATONA-TECNOLOGICA-COM-FACEBOOK-DE-VEREADORES-PAULISTANOS.htm"
				target="new"><img class="press" src="/images/cbn.jpg"></a> <a
				href="http://www.estadao.com.br/noticias/impresso,facebook-de-vereadores-ganha-concurso-,883368,0.htm"
				target="new"><img class="press" src="/images/estadao.jpg"></a>
			<a
				href="http://g1.globo.com/tecnologia/noticia/2012/06/rede-social-de-vereadores-baseada-no-facebook-ganha-concurso-em-sp.html"
				target="new"><img class="press" src="/images/g1.jpg"></a> <a
				href="http://blogs.estadao.com.br/jt-cidades/camara-tera-facebook-de-vereadores/"
				target="new"><img class="press" src="/images/jornaldatarde.jpg"></a>
			<p style="color: rgb(119, 119, 119);">Esse site é uma organização
				independente e, portanto, não possui vínculo algum com qualquer
				político ou partido. O objetivo é prover à população uma maneira
				rápida e interativa de acesso a informações referentes aos atuais
				vereadores do município. Todo conteúdo foi retirado do site da
				Câmara Municipal de São Paulo - http://www.camara.sp.gov.br</p>

		</div>

	</div>

	</div>
</body>

</html>

