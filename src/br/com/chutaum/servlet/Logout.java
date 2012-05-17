package br.com.chutaum.servlet;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




public class Logout extends HttpServlet {

	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
		        throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.removeAttribute("authenticatedUserName");
		res.sendRedirect("/");
	}
}
