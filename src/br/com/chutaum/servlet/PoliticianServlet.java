package br.com.chutaum.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import br.com.chutaum.json.JSONArray;
import br.com.chutaum.json.JSONException;
import br.com.chutaum.json.JSONObject;
import br.com.chutaum.model.Politician;

import br.com.chutaum.utils.Util;


import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;





public class PoliticianServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse res)
	      throws ServletException, IOException {
    	int i;
    	if (req.getParameter("offset") != null) {
    		 i = Integer.parseInt(req.getParameter("offset"));
		}
    	else {
    			i	= 0;	
    	}
    	if ((req.getParameter("vereador")!=null) &&(req.getParameter("vereador")!="")) {

    		try {
        		Key ancestorKey = KeyFactory.createKey("Politician",Integer.parseInt(req.getParameter("vereador")));   
        		Politician poli =  new Politician(Util.findEntity(ancestorKey));
				req.setAttribute("politician", poli );
				req.setAttribute("URL", "/actions?vereador="+poli.getId()+"&offset=0");
		    	RequestDispatcher rd = req.getRequestDispatcher("politician.jsp");
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

