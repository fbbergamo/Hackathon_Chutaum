package br.com.chutaum.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.chutaum.user.UserController;
import br.com.chutaum.utils.Util;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserServiceFactory;




public class LoginGoogle extends HttpServlet {

	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
		        throws ServletException, IOException {
			com.google.appengine.api.users.UserService userService = UserServiceFactory.getUserService();
		     if (userService.isUserLoggedIn()) {
		    	 UserController.login(userService.getCurrentUser().getEmail());
		    	 
		    		Key ancestorKey = KeyFactory.createKey("User",userService.getCurrentUser().getEmail());
	        		Iterable<Entity> actions = Util.listChildren("UserAction", ancestorKey, 2, 0);
	        		int count =0;
	        		for (Entity tmp : actions) {
	        			count++;
	        		}
	        		
	        	
	        		if (count==0){
	        			res.sendRedirect("/"); 
	        		}
	        		else {
					 res.sendRedirect("/cidadao");  
	        		}
		    	 
		    	
		    	 
		    	 
		        } else {
		        	 res.sendRedirect("/");  	
		        }
			}
	
	
	
}