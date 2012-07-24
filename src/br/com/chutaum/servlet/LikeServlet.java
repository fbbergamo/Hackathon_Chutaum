package br.com.chutaum.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.chutaum.model.User;
import br.com.chutaum.user.UserController;

public class LikeServlet extends HttpServlet{
	
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
				//toshiu esse metodo deve retornar o numero atualizado do vote pq dae vc pode fazer um 
				int count = UserController.likeAction(mail, actionId);
				 resp.setContentType("text/html");
				 PrintWriter out = resp.getWriter();
				 out.print("<span style='color:black; margin-right:16px' class='badge badge-success'>"+Integer.toString(count)+"<a style='color:black;' class='vote' href='/unlike?id=[ID]&mail=[EMAIL]%>'><i class='icon-thumbs-up'></i>CANCELAR</a></span>");
			}

			
		}
		
		
	}
}
