package br.com.chutaum.queue;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.chutaum.model.Action;
import br.com.chutaum.model.Entitys;
import br.com.chutaum.model.Politician;
import br.com.chutaum.model.User;
import br.com.chutaum.politician.PoliticianController;
import br.com.chutaum.utils.Util;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

public class UserActionsQueue extends HttpServlet {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException {
       	
       		Key ancestorKey = KeyFactory.createKey("Politician",Integer.parseInt(req.getParameter("poli")));
       		Entity enPoli = Util.findEntityAndAddCache(ancestorKey);
       		Politician poli = new Politician(enPoli);
       		List<Action> actions = PoliticianController.politicianActions(poli, 80, 0);
    		
    		for (Action action : actions) {
    			Entity useraction = action.createUserActionEntity(new User(req.getParameter("user")));
    			Util.persistEntity(useraction); 
    		}
    }
}