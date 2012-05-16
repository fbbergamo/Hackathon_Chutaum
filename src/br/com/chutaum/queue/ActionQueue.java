package br.com.chutaum.queue;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.bytecode.opencsv.CSVReader;
import br.com.chutaum.model.Action;
import br.com.chutaum.utils.Util;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;

public class ActionQueue  extends HttpServlet {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException {
		
       		String [] input = req.getParameter("action").split(";"); 
       		 
       		Action action = new Action();
		 	action.setIdPolition(Integer.parseInt(input[0]));
		 	action.setContent(input[1]);
		 	action.setKind(input[2]);
		 	
		 			 	
		 	try {
		 		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  
		 		Date date = format.parse(input[3]);
				action.setDate(date);
				action.setDateMs(date.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		 	
		 	Entity product = Util.findEntity(KeyFactory.createKey("Politician", action.getIdPolition()));

	 		Entity entity = new Entity("Action", product.getKey());
	 		entity.setProperty("Content",action.getContent());
	 		entity.setProperty("Date",action.getDate());
	 		entity.setProperty("DateMs", action.getDateMs());
	 		entity.setProperty("Kind",action.getKind());
	 		Util.persistEntity(entity);

	 		System.out.println(action.getContent());
	
    }
}
