package br.com.chutaum.user;

import java.util.Date;

import javax.servlet.http.HttpSession;

import br.com.chutaum.model.Politician;
import br.com.chutaum.model.User;
import br.com.chutaum.utils.Constants;
import br.com.chutaum.utils.Util;

import com.google.appengine.api.backends.BackendServiceFactory;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.google.appengine.api.users.UserServiceFactory;

public class UserController {
	
	private static final String SECOND_TIME = "SecondTime";
	private static final String USER_FOLLOWING = "UserFollowing";
	private static final String USER = "User";
	private static final String POLITICAN_FOLLOW = "PoliticanFollow";
	private static final String POLITICIAN = "Politician";
	private static final String DISLIKE_COUNT = "DislikeCount";
	private static final String VOTE = "Vote";
	private static final String LIKE_COUNT = "LikeCount";
	private static final String ACTION_COUNT = "ActionCount";
	private static final String VOTE_ACTION = "VoteAction";

	public static User findUser(String email) {
		//busca na base e faz cache no servidor. 
		Entity en = Util.findEntityAndAddCache(KeyFactory.createKey(USER, email));
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
		Key ancestorKey = KeyFactory.createKey(USER, user.getEmail());
    	return Util.writeJSON(Util.listChildren("Actions", ancestorKey,sizepage,offent));
	}
	
	private static String generateKey(String mail, String actionID){
		return mail+actionID;
	}

	/**
	 * Cria uma entidade LikeAction que guarda o voto de um usuário numa ação
	 * @param mail
	 * @param actionId
	 */
	public static void likeAction(String mail, String actionId) {
		String voteActionId = generateKey(mail, actionId);

		//cria logo em vez de consultar na base 1 vez. 
		Entity voteAction = new Entity(VOTE_ACTION, voteActionId);
			
		voteAction.setProperty(VOTE, Constants.LIKE);
		
		Entity actionCount = Util.findEntity(KeyFactory.createKey(ACTION_COUNT, actionId));
		if(actionCount == null){
			actionCount = createActionEntity(actionId);
		}
		long count = 0;
		
		if(actionCount.getProperty(LIKE_COUNT) instanceof Integer){
			count = (Integer)actionCount.getProperty(LIKE_COUNT);
		}
		if(actionCount.getProperty(LIKE_COUNT) instanceof Long){
			count = (Long) actionCount.getProperty(LIKE_COUNT);
		}
		actionCount.setProperty(LIKE_COUNT, ++count);
		
		
		Util.persistEntity(actionCount);
		Util.persistEntity(voteAction);
		
	}
	
	private static Entity createActionEntity(String id){
		Entity actionCount = new Entity(ACTION_COUNT,id);
		actionCount.setProperty(LIKE_COUNT, 0);
		actionCount.setProperty(DISLIKE_COUNT, 0);
		return actionCount;
		
	}
	
	public static void DislikeAction(String mail, String actionId){
		
		String voteActionId = generateKey(mail, actionId);
		
		Entity voteAction = new Entity(VOTE_ACTION, voteActionId);
		
		voteAction.setProperty(VOTE, Constants.DISLIKE);

		Entity actionCount = Util.findEntity(KeyFactory.createKey(ACTION_COUNT, actionId));
		
		if(actionCount == null){
			actionCount = createActionEntity(actionId);
		}
		
		int count = (Integer)actionCount.getProperty(DISLIKE_COUNT);
		actionCount.setProperty(DISLIKE_COUNT, ++count);

		Util.persistEntity(actionCount);
		Util.persistEntity(voteAction);
	}
	
	
	public static void followPolitician(User user, Politician poli){
	
		Entity userEntity = new Entity(USER_FOLLOWING, user.getEmail()+poli.getId());
		userEntity.setProperty(POLITICIAN,poli.getId());
		
		Entity policton = Util.findEntity(KeyFactory.createKey(POLITICIAN, poli.getId()));
		Entity poliEntity = new Entity(POLITICAN_FOLLOW,policton.getKey());
		poliEntity.setProperty(USER,user.getEmail());
		
 		Util.persistEntity(userEntity);
 		Util.persistEntity(poliEntity);

 		Key second = KeyFactory.createKey(SECOND_TIME,  user.getEmail()+poli.getId());
 		
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
		
		
		Key follow = KeyFactory.createKey(USER_FOLLOWING,  user.getEmail()+poli.getId());
		Key politi = KeyFactory.createKey(POLITICIAN, poli.getId());
		Util.deleteEntity(follow);
		
		Query query = new Query(POLITICAN_FOLLOW);
		query.setAncestor(politi);
		query.addFilter(USER, FilterOperator.EQUAL,  user.getEmail());

		PreparedQuery pq = DatastoreServiceFactory.getDatastoreService().prepare(query);
		 Iterable<Entity> todelete = pq.asQueryResultIterable();
		 for (Entity en : todelete) {
			 Util.deleteEntity(en.getKey());
		 }

		
		Entity second = new Entity(SECOND_TIME, user.getEmail()+poli.getId());
		Util.persistEntity(second);
		
 		
	}
	
	public static Boolean isFollowing(User user, Politician poli) {
		Entity en =  Util.findEntityAndAddCache(KeyFactory.createKey(USER_FOLLOWING, user.getEmail()+poli.getId()));
		if (en!=null) {
			return true;
		}
		return false;
	}
	
		
	public static void addUser(String email) {
		Entity userEntity = new Entity(USER, email);
		Date now = new Date();
		userEntity.setProperty("registrationDate",now);
		Util.persistEntity(userEntity);
	}
	
	public static void login(String email) {
		Key key = KeyFactory.createKey(USER,email);
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
	
	
	
