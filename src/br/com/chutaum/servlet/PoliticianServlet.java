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

import br.com.chutaum.utils.Util;


import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;





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
    	if (req.getParameter("vereador")!=null) {
    		Key ancestorKey = KeyFactory.createKey("Politician",Integer.parseInt(req.getParameter("vereador")));
    		String json = Util.writeJSON(Util.listChildren("Action", ancestorKey, 5, i));
    		
    		try {
    			JSONObject tmp = new JSONObject(json);
				JSONArray array = tmp.getJSONArray("data");
				req.setAttribute("actions", array );
		    	RequestDispatcher rd = req.getRequestDispatcher("politician.jsp");
			    rd.forward(req, res);
			    

					
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				
			}
    		
    		


    
    	}
	
	
	}
    
}

