package br.com.chutaum.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.chutaum.model.User;
import br.com.chutaum.user.UserController;


public class UndislikeServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		User user = UserController.currentUser(req.getSession());
		
		String actionId = req.getParameter("id");
		String mail = req.getParameter("mail");
		
		
		if(user != null){
			//verifica se é o usuário corrente
			if(user.getEmail().equals(mail)){
				//toshiu esse metodo deve retornar o numero atualizado do vote pq dae vc pode fazer um 
				int count = UserController.undislikeAction(mail, actionId);
				 resp.setContentType("text/html");
				 PrintWriter out = resp.getWriter();
				 out.print("<span style='color:black; margin-right:16px' class='badge badge-success'>"+Integer.toString(count)+"<a style='color:black;' class='vote' href='/like?id=[ID]&mail=[EMAIL]%>'><i class='icon-thumbs-up'></i>CONCORDAR</a></span>");
			}

			
		}

		
	}
	
}
