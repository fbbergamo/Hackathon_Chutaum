<%@page import="br.com.chutaum.user.UserController"%>
<%@page import="com.google.appengine.api.users.*"%>
<%@page import="br.com.chutaum.model.Action"%>

<%  
	com.google.appengine.api.users.UserService userService = UserServiceFactory.getUserService();
	 br.com.chutaum.model.User user = UserController.currentUser(session);
		Action action = (Action) request.getAttribute("action"); 

 	//user nao null
	if (user!=null) {
		
		
		
		%> 
		<div class="votes">
		<% //if se o usuario não votou em alguma coisa  %>
			<p class="dislike"><% //get o numero de dislike da action usar o actio %></p>
			<a class="vote" href="/dislike?id=<%= action.getId() %>">Não Apoiar</a>
			<p class="like"><% //get o numero de like da action usar o actio %></p>
			<a class="vote" href="/like?id=<%= action.getId() %>">>Apoiar</a>
			<% //else se o usuario já votou  %>
				<%//if se o usuario votou em dislike %>
				<p class="dislike"><% //get o numero de dislike da action usar o actio %></p>
				<a class="vote" href="/undislike?id=<%= action.getId() %>">>Não Apoiar</a>
				<p class="like"><% //get o numero de like da action usar o actio %></p>
				<a href="#"<%= action.getId() %>>Apoiar</a>
					<%//if se o usuario votou em like %>
						<p class="dislike"><% //get o numero de dislike da action usar o actio %></p>
						<a href="#">Não Apoiar</a>
						<p class="like"><% //get o numero de like da action usar o actio %></p>
						<a class="vote" href="/unlike?id=<%= action.getId() %>">Apoiar</a>
			<%//fechar o else  %>	
		</div>
		
		<%
	}
%>