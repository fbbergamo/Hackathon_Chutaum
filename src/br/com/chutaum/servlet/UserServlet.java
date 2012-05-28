package br.com.chutaum.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import br.com.chutaum.model.Politician;
import br.com.chutaum.model.User;

import br.com.chutaum.user.UserController;
import br.com.chutaum.utils.Util;



import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;






public class UserServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse res)
	      throws ServletException, IOException {
    	User user = UserController.currentUser(req.getSession());
    	if (user!=null) {

    		try {
				req.setAttribute("user", user );
				req.setAttribute("URL", "/actions?user="+user.getEmail()+"&offset=0");
		    	RequestDispatcher rd = req.getRequestDispatcher("user.jsp");
			    rd.forward(req, res);
			    
			} catch (Exception e) {
				e.printStackTrace();
				
			}    		
    	}
    	else {
    		 res.sendRedirect("/"); 
 			
    		
    	}
	
	
	}
    
}