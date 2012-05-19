package br.com.chutaum.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.chutaum.user.UserController;

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
		    	 res.sendRedirect("/login/");  	 
		        } else {
		        	 res.sendRedirect("/");  	
		        }
			}
	
	
	
}