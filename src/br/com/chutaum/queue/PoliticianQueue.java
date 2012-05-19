package br.com.chutaum.queue;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.chutaum.model.Politician;
import br.com.chutaum.utils.Util;

import com.google.appengine.api.datastore.Entity;

public class PoliticianQueue extends HttpServlet {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException {
       		String [] input = req.getParameter("politician").split(";");   		 
       		Politician politician = new Politician();
       		politician.setId(Integer.parseInt(input[0]));
       		politician.setName(input[1]);
       		politician.setParty(input[2]);
       		politician.setPhoto(input[3]);
	 		Entity entity = new Entity("Politician", politician.getId());
	 		entity.setProperty("Name",politician.getName());
	 		entity.setProperty("Party",politician.getParty());
	 		entity.setProperty("Photo",politician.getPhoto());
	 		Util.persistEntity(entity);
	
    }
}