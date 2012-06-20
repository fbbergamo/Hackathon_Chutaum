package br.com.chutaum.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.chutaum.model.Entitys;
import br.com.chutaum.model.Politician;
import br.com.chutaum.politician.PoliticianController;

import br.com.chutaum.user.UserController;
import br.com.chutaum.utils.Util;


import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;






public class ActionServlet extends HttpServlet {


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
    			
        		int id = Integer.parseInt(req.getParameter("vereador"));
        		Politician poli =  PoliticianController.findPolitician(id);

        		Iterable<Entity> actions = PoliticianController.politicianActions(poli,20,i);
        		i+=20;
        		int count =0;
        		
        		for (Entity tmp : actions) {
        			count++;
        			break;
        		}
        		
        		
        		if (count==0){
        			req.setAttribute("URL", "");
        		}
        		else {
        			req.setAttribute("URL", "/actions?vereador="+poli.getId()+"&offset="+i);
        		}
        		req.setAttribute("actions", actions );
				req.setAttribute("politician", poli );
		    	RequestDispatcher rd = req.getRequestDispatcher("actions.jsp");
			    
		    	rd.forward(req, res);
			    
			} catch (Exception e) {
				e.printStackTrace();
			}    
    	}
    	else if ((req.getParameter("user")!=null) &&(req.getParameter("user")!="")) {
    		try {
        		Key ancestorKey = KeyFactory.createKey("User",req.getParameter("user"));
        		Iterable<Entity> actions = Util.listChildren(Entitys.UserAction, ancestorKey, 20, i);
        		
        		i+=20;
        		int count =0;
        		
        		for (Entity tmp : actions) {
        			count++;
        			break;
        		}
        		
        		if (count==0){
        			req.setAttribute("URL", "");
        		}
        		else {
        			req.setAttribute("URL", "/actions?user="+req.getParameter("user")+"&offset="+i);
        		}
        		
        		req.setAttribute("actions", actions );
			
		    	RequestDispatcher rd = req.getRequestDispatcher("user-actions.jsp");
			    
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