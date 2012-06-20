package br.com.chutaum.user;

import java.util.Date;

import javax.servlet.http.HttpSession;

import com.google.appengine.api.backends.BackendServiceFactory;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.google.appengine.api.users.UserServiceFactory;

import br.com.chutaum.model.Politician;
import br.com.chutaum.model.User;
import br.com.chutaum.utils.Util;

public class UserController {
	
	public static User findUser(String email) {
		//busca na base e faz cache no servidor. 
		Entity en = Util.findEntityAndAddCache(KeyFactory.createKey("User", email));
		if (en!=null) {
			User user = new User(en);
			return user;  
		}
		return null;
	}
	
	public static User currentUser(HttpSession session){
		com.google.appengine.api.users.UserService userService = UserServiceFactory.getUserService();
		String email = null;
	    //verifica se tem login pelo google 
		if (userService.isUserLoggedIn()) {
	    	 email=userService.getCurrentUser().getEmail();
	    }	
		//verifica se tem cache pelo facebook
	    else if (session.getAttribute("authenticatedUserName")!=null) {
	    	 email = session.getAttribute("authenticatedUserName").toString();
			 }
		//se achou procura o usuario na base de dados e retornar
	     if (email!= null ) {
	    	 return findUser(email);
	     }
	     //se nem acho os cache retorna null direto user nao logado
	     return null;
		}
	
	public String userActions(User user, int sizepage, int offent) {
		Key ancestorKey = KeyFactory.createKey("User", user.getEmail());
    	return Util.writeJSON(Util.listChildren("Actions", ancestorKey,sizepage,offent));
	}
	
	public static void followPolitician(User user, Politician poli){
	
		Entity userEntity = new Entity("UserFollowing", user.getEmail()+poli.getId());
		userEntity.setProperty("Politician",poli.getId());
		Entity policton = Util.findEntity(KeyFactory.createKey("Politician", poli.getId()));
		Entity poliEntity = new Entity("PoliticanFollow",policton.getKey());
		poliEntity.setProperty("User",user.getEmail());
		
 		Util.persistEntity(userEntity);
 		Util.persistEntity(poliEntity);
 		Key second = KeyFactory.createKey("SecondTime",  user.getEmail()+poli.getId());
 		
 		if (Util.findEntity(second)==null) {
 			
 			com.google.appengine.api.taskqueue.Queue queue = QueueFactory.getQueue("UserActionsQueue");
			TaskOptions taskOptions = TaskOptions.Builder.withUrl("/user-actions-queue")
        		  	                          .param("user",user.getEmail() )
        		  	                          .param("poli", Long.toString(poli.getId()))
        		  	                          .method(Method.POST)
        		  	                          .header("Host", BackendServiceFactory.getBackendService().getBackendAddress("action-backend"));
			queue.add(taskOptions);	   	
 			
 		}
 		
 		
	}
	
	public static void unFollowPolitician(User user, Politician poli){
		
		
		Key follow = KeyFactory.createKey("UserFollowing",  user.getEmail()+poli.getId());
		Key politi = KeyFactory.createKey("Politician", poli.getId());
		Util.deleteEntity(follow);
		
		Query query = new Query("PoliticanFollow");
		query.setAncestor(politi);
		query.addFilter("User", FilterOperator.EQUAL,  user.getEmail());

		PreparedQuery pq = DatastoreServiceFactory.getDatastoreService().prepare(query);
		 Iterable<Entity> todelete = pq.asQueryResultIterable();
		 for (Entity en : todelete) {
			 Util.deleteEntity(en.getKey());
		 }

		
		Entity second = new Entity("SecondTime", user.getEmail()+poli.getId());
		Util.persistEntity(second);
		
 		
	}
	
	public static Boolean isFollowing(User user, Politician poli) {
		Entity en =  Util.findEntityAndAddCache(KeyFactory.createKey("UserFollowing", user.getEmail()+poli.getId()));
		if (en!=null) {
			return true;
		}
		return false;
	}
	
		
	public static void addUser(String email) {
		Entity userEntity = new Entity("User", email);
		Date now = new Date();
		userEntity.setProperty("registrationDate",now);
		Util.persistEntity(userEntity);
	}
	
	public static void login(String email) {
		Key key = KeyFactory.createKey("User",email);
		Entity ent = Util.findEntityAndAddCache(key);
		if (ent==null) {
			addUser(email);	
		}
	}
	
	public static String logout() {
		com.google.appengine.api.users.UserService userService = UserServiceFactory.getUserService();
		if (userService.isUserLoggedIn())
			return userService.createLogoutURL("/");
		else
			return "/login/logout";	
	}
	
}
	
	
	
