<%@page import="br.com.chutaum.user.UserController"%>
<%@page import="com.google.appengine.api.users.*"%>
<%@page import="br.com.chutaum.model.Action"%>
<%@page import="br.com.chutaum.utils.Util"%>
<%@page import="com.google.appengine.api.datastore.*"%>
<%  
	com.google.appengine.api.users.UserService userService = UserServiceFactory.getUserService();
 	br.com.chutaum.model.User user = UserController.currentUser(session);
	Action action = (Action) request.getAttribute("action"); 

 	//user nao null
	if (user!=null) {
		Key likeActionKey = KeyFactory.createKey("LikeAction", user.getEmail()+action.getId());
		Key disLikeActionKey = KeyFactory.createKey("DislikeAction", user.getEmail()+action.getId());
		
		Entity likeAction = Util.findEntity(likeActionKey);
		Entity dislikeAction = Util.findEntity(disLikeActionKey);
%> 
	<div class="votes">
		<% //if se o usuario não votou em alguma coisa  
			if( likeAction==null && dislikeAction==null) {%>
				<p class="dislike"><% //get o numero de dislike da action usar o action %></p>
				<a class="vote" href="/dislike?id=<%=action.getId()%>&mail=<%= user.getEmail()%>">Não Apoiar</a>
				<p class="like"><% //get o numero de like da action usar o actio %></p>
				<a class="vote" href="/like?id=<%= action.getId() %>&mail=<%= user.getEmail()%>">>Apoiar</a>
			
		<% } else { %>
			<%if(dislikeAction != null) {%>
				<p class="dislike"><% //get o numero de dislike da action usar o actio %></p>
				<a class="vote" href="/undislike?id=<%= action.getId() %>">>Não Apoiar</a>
				<p class="like"><% //get o numero de like da action usar o actio %></p>
				<a href="#"<%= action.getId() %>>Apoiar</a>
			<%}else {%>
				<p class="dislike"><% //get o numero de dislike da action usar o actio %></p>
				<a href="#">Não Apoiar</a>
				<p class="like"><% //get o numero de like da action usar o actio %></p>
				<a class="vote" href="/unlike?id=<%= action.getId() %>">Apoiar</a>
			<%}
		
		}%>	
	</div>
		
		<%
	}
%>