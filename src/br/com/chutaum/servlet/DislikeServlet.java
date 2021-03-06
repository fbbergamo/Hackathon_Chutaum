package br.com.chutaum.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.chutaum.model.User;
import br.com.chutaum.politician.PoliticianController;
import br.com.chutaum.user.UserController;

public class DislikeServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		User user = UserController.currentUser(req.getSession());
		
		String actionId = req.getParameter("id");
		String mail = req.getParameter("mail");
		
		
		if(user != null){
			//verifica se � o usu�rio corrente
			if(user.getEmail().equals(mail)){
				UserController.DislikeAction(mail, actionId);
			   	req.setAttribute("action", PoliticianController.findAction(actionId));
		    	RequestDispatcher rd = req.getRequestDispatcher("like.jsp");
		    	rd.forward(req, resp);
			}

			
		}
		
		
	}
}

