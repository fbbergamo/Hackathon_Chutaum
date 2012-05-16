package br.com.chutaum.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mortbay.util.ajax.JSON;

import br.com.chutaum.json.JSONException;
import br.com.chutaum.json.JSONObject;
import br.com.chutaum.json.JSONTokener;

import sun.net.www.http.HttpClient;


public class LoginFacebook extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException {
		if (req.getParameter("access_token")!=null) {
			
			//get facebook data 
			URL   u = new URL("https://graph.facebook.com/me?access_token="+req.getParameter("access_token").toString());
			
			 
				JSONTokener reader = new JSONTokener(new InputStreamReader(u.openStream()));
	            PrintWriter out = res.getWriter();
	            JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(reader);
					if (jsonObject.getString("email")!=null) {
						//faz o cadastro dele ou pega a Entyt e faz o Session
						
//fazer os sessions. current user e etc. 
						HttpSession session = req.getSession();
						session.setAttribute("authenticatedUserName", jsonObject.getString("email"));
						
						out.print(session.getAttribute("authenticatedUserName"));
					}
				} catch (JSONException e) {
					//user não deve ter aceitado email, redirect p home 
				}
	              
	            
	           
          
       
		
		}
	}
		
}
