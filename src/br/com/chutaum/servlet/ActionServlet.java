package br.com.chutaum.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.chutaum.model.Action;
import br.com.chutaum.model.Entitys;
import br.com.chutaum.model.Politician;
import br.com.chutaum.model.User;

import br.com.chutaum.politician.PoliticianController;

import br.com.chutaum.user.UserController;







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

        		List<Action> actions = PoliticianController.politicianActions(poli,20,i);
        		i+=20;
        		
        		
        		if (actions.size()==0){
        			req.setAttribute("URL", "");
        		}
        		else {
        			req.setAttribute("URL", "/actions?vereador="+poli.getId()+"&offset="+i);
        		}
        		req.setAttribute("actions", actions );
		    	RequestDispatcher rd = req.getRequestDispatcher("actions.jsp");
			    
		    	rd.forward(req, res);
			    
			} catch (Exception e) {
				e.printStackTrace();
			}    
    	}
    	else if ((req.getParameter("user")!=null) &&(req.getParameter("user")!="")) {
    		try {
        		String email = req.getParameter("user");
        		
    			List<Action> actions = UserController.userActions(new User(email), 20, i);
        		i+=20;
        		
        		
        		if (actions.size()==0){
        			req.setAttribute("URL", "");
        		}
        		
        		else {
        			req.setAttribute("URL", "/actions?user="+email+"&offset="+i);
        		}
        		
        		req.setAttribute("actions", actions );
		    	RequestDispatcher rd = req.getRequestDispatcher("actions.jsp");
			    
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