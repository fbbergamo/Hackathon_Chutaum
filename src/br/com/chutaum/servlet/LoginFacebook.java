package br.com.chutaum.servlet;


import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import br.com.chutaum.json.JSONException;
import br.com.chutaum.json.JSONObject;
import br.com.chutaum.json.JSONTokener;
import br.com.chutaum.user.UserController;

public class LoginFacebook extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException {
		if (req.getParameter("access_token")!=null) {
				URL   u = new URL("https://graph.facebook.com/me?access_token="+req.getParameter("access_token").toString());
				
				JSONTokener reader = new JSONTokener(new InputStreamReader(u.openStream()));
	            JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(reader);
					if (jsonObject.getString("email")!=null) {
						HttpSession session = req.getSession();
						UserController.login(jsonObject.getString("email"));
						session.setAttribute("authenticatedUserName", jsonObject.getString("email"));
						 res.sendRedirect("/");  	
					}
					else {
						 res.sendRedirect("/");  	
					}
				} catch (JSONException e) {
					 res.sendRedirect("/");  	
				}
		}
	}
		
}
