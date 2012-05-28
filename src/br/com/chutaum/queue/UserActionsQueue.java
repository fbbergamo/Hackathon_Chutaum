package br.com.chutaum.queue;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.chutaum.model.Action;
import br.com.chutaum.model.Politician;
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
       		Entity enPoli = Util.findEntity(ancestorKey);
       		Politician poli = new Politician(enPoli);
    		Iterable<Entity> actions = Util.listChildren("Action", ancestorKey, 80, 0);
    		
    		for (Entity en : actions) {
    			Entity user = Util.findEntity(KeyFactory.createKey("User", req.getParameter("user")));
    			Entity useraction = new Entity("UserAction", user.getKey());
    			Action action = new Action(en);
    			Text text = new Text(action.getContent());
    			useraction.setProperty("Content", text);
    			useraction.setProperty("Date",action.getDate());
    			useraction.setProperty("DateMs", action.getDateMs());
    			useraction.setProperty("Kind",action.getKind());
    			useraction.setProperty("UniqueActions",action.getKey());
    			useraction.setProperty("IdPolitician",poli.getId());
    			useraction.setProperty("NamePolitician",poli.getName());
    			useraction.setProperty("Party",poli.getParty());
    			useraction.setProperty("Photo",poli.getPhoto());
    			Util.persistEntity(useraction); 
    		}
    }
}