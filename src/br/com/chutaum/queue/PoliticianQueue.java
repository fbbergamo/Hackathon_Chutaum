package br.com.chutaum.queue;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.chutaum.politician.Politician;
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
       		
	 		Entity entity = new Entity("Politician", politician.getId());
	 		entity.setProperty("Name",politician.getName());
	 		Util.persistEntity(entity);
	
    }
}