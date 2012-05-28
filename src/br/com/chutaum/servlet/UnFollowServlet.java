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
import br.com.chutaum.politician.PoliticianController;

import br.com.chutaum.user.UserController;
import br.com.chutaum.utils.Util;


import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;






public class UnFollowServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse res)
	      throws ServletException, IOException {
    	User user = UserController.currentUser(req.getSession());
    	if (user!=null) {
    	Politician poli =  PoliticianController.findPolitician(Integer.parseInt(req.getParameter("poli")));
    	UserController.unFollowPolitician(user,poli );
    	req.setAttribute("politician", poli );
    	RequestDispatcher rd = req.getRequestDispatcher("follow.jsp");
    	rd.forward(req, res);
    	}
    	
    	
    }
    
}