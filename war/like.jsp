<%@page import="br.com.chutaum.user.UserController"%>
<%@page import="com.google.appengine.api.users.*"%>
<%@page import="br.com.chutaum.model.Action"%>
<%@page import="br.com.chutaum.utils.Util"%>
<%@page import="com.google.appengine.api.datastore.*"%>
<%  
	com.google.appengine.api.users.UserService userService = UserServiceFactory.getUserService();
 	br.com.chutaum.model.User user = UserController.currentUser(session);
	Action action = (Action) request.getAttribute("action"); 
	Entity actionCount = Util.findEntity(KeyFactory.createKey("ActionCount", action.getId()));
	long likeCount = 0;
	long dislikeCount = 0;
	
	if(actionCount != null) {
		likeCount = (Long) actionCount.getProperty("LikeCount");
		dislikeCount = (Long) actionCount.getProperty("DislikeCount");
	}

 	//user nao null
	if (user!=null) {
		
		//mauricio aqui devia ter uma entidade só e colocar uma coluna como argumento do q ele votou 
		//imagina q agnt está fazendo dois acessos ao banco quando só precisava fazer um 
		//fazer um metodo em um controler
		Key likeActionKey = KeyFactory.createKey("VoteAction", user.getEmail()+action.getId());
		Entity likeAction = Util.findEntity(likeActionKey);
		
		long vote = 2;
		
		if (likeAction != null ) {
			//Workaround - verificar depois.
			//Nos teste uma hora vinha Integer outra hora vinha Long
			if(likeAction.getProperty("Vote") instanceof Long)
				vote = (Long) likeAction.getProperty("Vote");
			if(likeAction.getProperty("Vote") instanceof Integer)
				vote = (Integer) likeAction.getProperty("Vote");
		}
		
%> 
	<div class="row"style="margin-left:0;">
		<% //if se o usuario não votou em alguma coisa  
			if ((likeAction==null) || (vote==2)) {%>
				<span class="badge badge-success" style="color:white; margin-right:16px"><%=likeCount %>
				<a class="vote"  style="color:white;" href="/like?id=<%= action.getId() %>&mail=<%= user.getEmail()%>"><i class="icon-thumbs-up"></i> CONCORDAR</a></span>
				<span class="badge badge-important" style="color:white;"><%=dislikeCount %>
				<a class="vote" style="color:white;" href="/dislike?id=<%=action.getId()%>&mail=<%= user.getEmail()%>"><i class="icon-thumbs-down"></i>
				DISCORDAR</a></span>
				
			
		<% } else { %>
			<% 
				if (vote == 0) {%>
				<span class="badge badge-success" style="color:white;"><%=likeCount %>
				<span style="color:white;"><i class="icon-thumbs-up"></i> CONCORDAR</span></span>
				
				<span class="badge badge-important" style="color:black; margin-left:16px"><%=dislikeCount %>
				<a class="vote" style="color:black;"  href="/undislike?id=<%=action.getId()%>&mail=<%= user.getEmail()%>"><i class="icon-thumbs-down"></i>CANCELAR</a></span>
				
			<%}else if (vote == 1)  {%>
				
				<span style="color:black; margin-right:16px" class="badge badge-success"><%=likeCount %>
				<a style="color:black;" class="vote" href="/unlike?id=<%= action.getId()%>&mail=<%= user.getEmail()%>"><i class="icon-thumbs-up"></i>CANCELAR</a></span>
				
				<span class="badge badge-important"><%=dislikeCount %>
				<span><i class="icon-thumbs-down"></i>DISCORDAR</a>
				</span> </span>
			<%}
		
		}%>	
	</div>
		
		<%
	}else {%>
		
		<span class="badge badge-success" style="color:white; margin-right:16px"><%=likeCount %>
		<i class="icon-thumbs-up"></i> CONCORDAR</span>
		<span class="badge badge-important" style="color:white;"><%= dislikeCount %>
		<i class="icon-thumbs-down"></i>
		DISCORDAR</span>
		
	<%}
%>