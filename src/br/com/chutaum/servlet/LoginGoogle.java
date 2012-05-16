package br.com.chutaum.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;
import com.google.apphosting.api.UserServicePb.UserService;



public class LoginGoogle extends HttpServlet {

	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
		        throws ServletException, IOException {
		
			com.google.appengine.api.users.UserService userService = UserServiceFactory.getUserService();
			//armazenar em cache está url do usuario
			String thisUrl = req.getRequestURI();
			
		     if (userService.isUserLoggedIn()) {
		    	 res.sendRedirect("/login/login.jsp");
		    	 res.getWriter().println("Logo  " + userService.createLogoutURL(thisUrl) + " " + userService.getCurrentUser().getEmail());
		        } else {
		        	//res.sendRedirect("/");
		        }
		   
			}
	
	
	
}