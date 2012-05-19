package br.com.chutaum.queue;


import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import br.com.chutaum.json.JSONArray;
import br.com.chutaum.json.JSONException;
import br.com.chutaum.json.JSONObject;
import br.com.chutaum.model.Action;
import br.com.chutaum.model.Politician;
import br.com.chutaum.politician.PoliticianController;
import br.com.chutaum.utils.Util;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

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
		 	Entity politician = Util.findEntity(KeyFactory.createKey("Politician", action.getIdPolition()));
	 		Entity entity = new Entity("Action", politician.getKey());
	 		Text text = new Text(action.getContent());
	 		entity.setProperty("Content", text);
	 		entity.setProperty("Date",action.getDate());
	 		entity.setProperty("DateMs", action.getDateMs());
	 		entity.setProperty("Kind",action.getKind());
	 		Key actionKey =	Util.persistEntity(entity);
	 		
	 		Politician poli = new Politician(politician);
	 		
	 		try {
	 			JSONArray array = PoliticianController.politicianFollow(poli);
		 		for (int i = 0; i < array.length(); i++) {
		 			 	String email = null;
			 		    JSONObject json = array.getJSONObject(i);
						email = json.getString("ID");
				 		Entity user = Util.findEntity(KeyFactory.createKey("User", email));
				 		Entity useraction = new Entity("UserAction", user.getKey());
				 		useraction.setProperty("Content",action.getContent());
				 		useraction.setProperty("Date",action.getDate());
				 		useraction.setProperty("DateMs", action.getDateMs());
				 		useraction.setProperty("Kind",action.getKind());
				 		useraction.setProperty("UniqueActions",actionKey);
				 		Util.persistEntity(entity); 
		 		}
	 		} catch (JSONException e) {
			//tratar caso o json venha zuado 	
			}
    }
}
